package DataModels;

/**
 * class ที่ใช้เก็บข้อมูลสินค้าที่อยู่ในการสั่งซื้อ เป็นส่วนหนึ่งของ Order
 * @param product สินค้า , quantity จำนวนสินค้า , price ราคาสินค้า
 * @return สินค้า, จำนวนสินค้า, ราคาสินค้า
 */
public class OrderItem {
    private Product product;
    private int quantity;
    private double price;

    // Constructor
    public OrderItem(Product product, int quantity, double price) {
        if (product == null) 
        throw new RuntimeException("product is unknown");
        if (quantity < 1) 
        throw new RuntimeException("quantity need more than or equal 1");
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    // Methods รับค่าต่างๆ
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
