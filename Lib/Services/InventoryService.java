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

    List<Product> getAll();
    Product getBySku(String sku) throws ProductNotFoundException;
    boolean CheckSku(String sku);
    List<Product> searchByName(String nameword);
    List<Product> searchBySku(String sku);
   // List<Product> sortByPrice(boolean isAscending);
   // List<Product> sortByStock(boolean isAscending);
   // List<Product> lowStock(int lowStocksort);

    void addProduct(Product product);
    void removeBySku(String sku) throws ProductNotFoundException;

    void setStock(String sku, int newStock)     
                throws ProductNotFoundException, InvalidOperationException;

    void increase(String sku, int qty)
                throws ProductNotFoundException, InvalidOperationException;

    void decrease(String sku, int qty)
                throws ProductNotFoundException, InvalidOperationException;
}