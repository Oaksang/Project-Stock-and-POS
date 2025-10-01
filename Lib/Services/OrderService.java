package Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import DataModels.Order;
import DataModels.OrderItem;
import DataModels.SaleRecord;
import Exceptions.InvalidOperationException;
import Exceptions.ProductNotFoundException;
import Repository.InMemoryOrderRepository;
import Repository.InMemorySalesRepository;

public class OrderService {
    private final InventoryService inventoryService;
    private final PricingService pricingService;
    private final InMemoryOrderRepository orderRepository; 
    private final InMemorySalesRepository salesRepository;

    public OrderService(InventoryService inventoryService, PricingService pricingService, 
        InMemoryOrderRepository orderRepository, InMemorySalesRepository salesRepository) {
        this.inventoryService = inventoryService;
        this.pricingService = pricingService;
        this.orderRepository = orderRepository;
        this.salesRepository = salesRepository;
    }

    /**
     * ดำเนินการ checkout สำหรับการสั่งซื้อ
     * @param items รายการสินค้าที่ลูกค้าต้องการซื้อ
     * @return Order ที่ถูกบันทึกสำเร็จ
     * @throws ProductNotFoundException 
     * @throws InvalidOperationException 
     */
    public Order processCheckout(List<OrderItem> items) throws ProductNotFoundException, InvalidOperationException {
        // 1. ตรวจสอบสต็อก
        for (OrderItem item : items) {
            if (!inventoryService.hasSufficientStock(item.getProduct().sku(), item.getQuantity())) {
                throw new RuntimeException("Stock insufficient for: " + item.getProduct().name());
            }
        }

        // 2. คำนวณราคาทั้งหมด
        double subtotal = items.stream().mapToDouble(OrderItem::getTotal).sum();
        double discount = pricingService.calDiscount(subtotal);
        double priceAfterDiscount = subtotal - discount;
        double tax = pricingService.calTax(priceAfterDiscount);
        double total = priceAfterDiscount + tax;
        
        // 3. หักสต็อก
        for (OrderItem item : items) {
            inventoryService.decrease(item.getProduct().sku(), item.getQuantity());
        }

        // 4. สร้างและบันทึก Order
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8);
        
        // *** แก้ไข Order.java constructor ที่รับ LocalDate ให้เป็น LocalDateTime ***
        Order newOrder = new Order(orderId, LocalDateTime.now(), items);
        
        // 5. สร้างและบันทึก SaleRecord (ข้อมูลการจ่ายเงินสำเร็จ)
        SaleRecord sale = new SaleRecord(
            orderId, 
            total, // totalSales
            subtotal, 
            discount, 
            tax, 
            total, 
            "Cash", // สมมติ payment method
            ""      // สมมติ discount code
        );
        salesRepository.save(sale);
        
        // 6. บันทึก Order
        orderRepository.save(newOrder); 
        
        // 7. แจ้ง Event (EventBus/PropertyChangeSupport) - โค้ดต้นแบบละไว้
        
        return newOrder;
    }
}
