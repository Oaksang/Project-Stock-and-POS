package Services;
import java.util.List;
import DataModels.*;
import Exception.*;

/**
 * จัดการสต็อก + ค้น/เรียง/เช็คของใกล้หมด 
 * - เทียบ SKU แบบไม่สนตัวพิมพ์ (ทำใน memmory)
 * - decrease แบบ Strict: สต็อกไม่พอ  โยน InvalidOperationException
 * - เมธอดที่ใช้ SKU แล้วไม่พบ  โยน ProductNotFoundException
 */
public interface InventoryService {

    List<Product> getAll(); // รับสินค้าทั้งหมดในคลัง
    Product getBySku(String sku) throws ProductNotFoundException; // รับสินค้าตาม SKU
    boolean CheckSku(String sku); // ตรวจสอบว่ามี SKU นี้ในคลังหรือไม่
    List<Product> searchByName(String nameword); // ค้นหาสินค้าตามคำในชื่อ (ไม่สนตัวพิมพ์)
    List<Product> searchBySku(String sku); // ค้นหาสินค้าตามคำใน SKU (ไม่สนตัวพิมพ์)
    List<Product> sortByPrice(boolean isAscending,List<Product> select); // เรียงสินค้าตามราคา
    List<Product> sortByStock(boolean isAscending,List<Product> select); // เรียงสินค้าตามจำนวนในคลัง
    List<Product> lowStock(int lowStocksort); // รับสินค้าที่มีจำนวนในคลังต่ำกว่าค่าที่กำหนด

    void addProduct(Product product); // เพิ่มสินค้าใหม่
    void removeBySku(String sku) throws ProductNotFoundException; // ลบสินค้าตาม SKU

    void setStock(String sku, int newStock) // กำหนดจำนวนสินค้าในคลังใหม่
                throws ProductNotFoundException, InvalidOperationException;

    void increase(String sku, int qty) // เพิ่มจำนวนสินค้าในคลัง
                throws ProductNotFoundException, InvalidOperationException;

    void decrease(String sku, int qty) // ลดจำนวนสินค้าในคลัง
                throws ProductNotFoundException, InvalidOperationException;
}