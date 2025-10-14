package DataModels;

import java.time.LocalDateTime;
import java.util.List;

    /**
     * class ที่ใช้เก็บข้อมูล Order ที่ได้รับจากการสั่งซื้อสินค้า
     * หน้าที่ของคลาสนี้คือการเก็บข้อมูลคำสั่งซื้อ เช่น รหัสคำสั่งซื้อ, เวลาที่สั่งซื้อ, รายการสินค้าที่สั่งซื้อ
     * และมีฟังก์ชันในการเพิ่มหรือลบสินค้าในคำสั่งซื้อ รวมถึงการคำนวณราคารวมของคำสั่งซื้อ
     * @param OrderId รหัสคำสั่งซื้อ , timestamp เวลาที่สั่งซื้อ , items รายการสินค้าที่สั่งซื้อ
     * @return OrderId, timestamp, items
     */
public class Order {
    private String OrderId; // รหัสคำสั่งซื้อ
    private LocalDateTime timestamp; // เวลาที่สั่งซื้อ
    private List<OrderItem> items; // รายการสินค้าที่สั่งซื้อ

    // Constructor
    public Order(String OrderId, LocalDateTime timestamp, List<OrderItem> items) {
        
        
        this.OrderId = OrderId;
        this.timestamp = timestamp;
        this.items = items;
    }

    // Methods รับค่าต่างๆ
    public String getOrderId() {
        return OrderId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    // เพิ่มสินค้าใน Order
    public void addItem(OrderItem item) {
        if (item == null) throw new RuntimeException("item is unknown");
        items.add(item);
    }

    // ลบสินค้าใน Order
    public void removeItem(int index) {
        items.remove(index);
    }
    
    // คำนวณราคารวมของ Order
    public double getSubtotal() {
        return items.stream().mapToDouble(OrderItem::getTotal).sum(); // ใช้ Stream API ในการคำนวณราคารวม
    }

}
