package DataModels;

/**
 * class ที่ใช้เก็บข้อมูลสินค้า
 * @param sku รหัสสินค้า , name ชื่อสินค้า , price ราคาต่อหน่วย , stock จำนวนสินค้าในคลัง
 * @return รหัสสินค้า, ชื่อสินค้า, ราคาต่อหน่วย, จำนวนสินค้าในคลัง
 */
public class Product {

    // sku = รหัสสินค้า , name = ชื่อสินค้า , price = ราคาต่อหน่วย , stock = จำนวนสินค้าในคลัง
    
    private final String sku;
    private final String name;
    private final double price;
    private int stock;

    public Product(String sku, String name, double price, int stock) {
        if (sku == null || sku.isEmpty()) throw new RuntimeException("SKU is unknown");
        if (name == null || name.isEmpty()) throw new RuntimeException("Name is unknown");
        if (price < 0) throw new RuntimeException("Price should be >= 0");
        if (stock < 0) throw new RuntimeException("Stock should be >= 0");
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Methods รับค่าต่างๆ
    public String sku() {
        return sku;
    }
    public String name() {
        return name;
    }
    public double price() {
        return price;
    }
    public int stock() {
        return stock;
    }
    
    // Method สำหรับปรับปรุงจำนวนสินค้าในคลัง
    public void setStock(int stock) {
        if (stock < 0) throw new RuntimeException("Stock should be >= 0");
        this.stock = stock;
    }
}
