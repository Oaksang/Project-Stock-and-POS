package DataModels;


public class OrderItem {
    private Product product;
    private int quantity;
    private double price;

    public OrderItem(Product product, int quantity, double price) {
        if (product == null) 
        throw new RuntimeException("product is unknown");
        if (quantity < 1) 
        throw new RuntimeException("quantity need more than or equal 1");
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotal() {
        return product.price() * quantity;
    }

    // Test OrderItem
    public static void main(String[] args) {
        Product product = new Product("P001", "Laptop", 1200, 50);
        OrderItem orderItem = new OrderItem(product, 5, product.price());
        System.out.println("Product: " + orderItem.getProduct().name());
        System.out.println("Quantity: " + orderItem.getQuantity());
        System.out.println("Price: " + orderItem.getPrice());
        System.out.println("Total: " + orderItem.getTotal());
    }
}
