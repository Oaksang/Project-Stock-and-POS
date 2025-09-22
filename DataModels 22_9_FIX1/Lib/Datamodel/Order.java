package Lib.Datamodel;

import java.time.LocalDateTime;
import java.util.List;

// get getSubtotal ยังไม่ได้ทำ
public class Order {
    private String OrderId;
    private LocalDateTime timestamp;
    private List<OrderItem> items;


    public Order(String OrderId, LocalDateTime timestamp, List<OrderItem> items) {
        
        
        this.OrderId = OrderId;
        this.timestamp = timestamp;
        this.items = items;
    }

    // Getters
    public String getOrderId() {
        return OrderId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<OrderItem> getItems() {
        return items;
    }
    public void addItem(OrderItem item) {
        if (item == null) throw new RuntimeException("item is unknow");
        items.add(item);
    }
     public void removeItem(int index) {
        items.remove(index);
    }
    
}
