package DataModels;

import java.time.LocalDateTime;
import java.util.List;

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

    

}
