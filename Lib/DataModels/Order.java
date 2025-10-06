package DataModels;

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
        if (item == null) throw new RuntimeException("item is unknown");
        items.add(item);
    }
    public void removeItem(int index) {
        items.remove(index);
    }
    
    public double getSubtotal() {
        return items.stream().mapToDouble(OrderItem::getTotal).sum();
    }

    // Test Order class
    public static void main(String[] args) {
        Product product1 = new Product("P001", "Laptop", 1200, 50);
        Product product2 = new Product("P002", "Mouse", 25, 200);
        OrderItem orderItem1 = new OrderItem(product1, 2, product1.price());
        OrderItem orderItem2 = new OrderItem(product2, 3, product2.price());
        List<OrderItem> itemList = List.of(orderItem1, orderItem2);
        Order order = new Order("O001", LocalDateTime.now(), itemList);
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Timestamp: " + order.getTimestamp());
        System.out.println("Items:");
        for (OrderItem item : order.getItems()) {
            System.out.println("- " + item.getProduct().name() + " x" + item.getQuantity() + " @ " + item.getPrice() + " each, Total: " + item.getTotal());
        }
        System.out.println("Subtotal: " + order.getSubtotal());
    }
}
