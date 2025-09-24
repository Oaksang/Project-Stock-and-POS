package DataModels;


public class OrderItem {
    private Product product;
    private int quantity;
    private double price;

    public OrderItem(Product product, int quantity, double price) {
        if (product == null) 
        throw new RuntimeException("product is unknow");
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
}
