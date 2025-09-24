package Services;

public class OrderService {
    private PricingService pricingService;
    private InventoryService inventoryService;
    private ReportingService reportingService;
    // สมมติฐาน: มีคลาส OrderRepository สำหรับจัดการการเก็บข้อมูล Order
    // private OrderRepository orderRepository;

    public OrderService(PricingService pricingService, InventoryService inventoryService, ReportingService reportingService) {
        this.pricingService = pricingService;
        this.inventoryService = inventoryService;
        this.reportingService = reportingService;
    }
    
}
