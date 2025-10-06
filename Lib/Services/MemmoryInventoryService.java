package Services;

import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

import DataModels.*;
import Exception.*;

/**
 * เก็บสินค้าในหน่วยความจำด้วย ArrayList
 * Product เป็น record (immutable)  ทุกครั้งที่แก้ stock จะ "New Obj" แล้วแทนที่ของเดิม
 * เทียบ SKU แบบไม่สนตัวพิมพ์ (lowercase เทียบ)
 */
public class MemmoryInventoryService implements InventoryService{

    private final List<Product> productList = new ArrayList<>();
    @Override
    public List<Product> getAll() {
        return new ArrayList<>(productList); 
    }
    @Override // หา สินค้า 1 ชิ้น ด้วยรหัส SKU (ไม่สนตัวพิมพ์) จากฟังชัน  indexOfSku
        public Product getBySku(String sku) throws ProductNotFoundException {
        int index = indexOfSku(sku);
        if (index < 0) throw new ProductNotFoundException("SKU not found: " + sku);
        return productList.get(index);
    }
    @Override // Check ว่ามี sku มั้ย
    public boolean CheckSku(String sku) {
        return indexOfSku(sku) >= 0;
    }
     @Override
    public List<Product> searchBySku(String sku) {
        String skuword;

        if (sku == null) {
            skuword = "";
        } else {
            skuword = sku.toLowerCase().trim();
        }

        List<Product> resultList = new ArrayList<>();

        for (int i = 0; i < productList.size(); i++) {
            Product p = productList.get(i);
            String skuLower = p.sku().toLowerCase();

            if (skuLower.contains(skuword)) {
                resultList.add(p);
            } else {
                // กรณีไม่ตรง
            }
        }

        return resultList;
    }

    @Override
    public List<Product> searchByName(String nameword) {
    String namewordlow;

    if (nameword == null) {
        namewordlow = "";
    } else {
        namewordlow = nameword.toLowerCase().trim();
    }

    List<Product> resultList = new ArrayList<>();

    for (Product p : productList) {
        String productNameLower = p.name().toLowerCase();

        if (productNameLower.contains(namewordlow)) {
            resultList.add(p);
        } else {
            // กรณีไม่ตรง
        }
    }
    return resultList;
    
}
    

    @Override
    public void addProduct(Product product) {
        Productnotnull(product, "product"); // กันพลาด: ต้องมีสินค้า
        int index = indexOfSku(product.sku()); // หาในคลัง เทียบ SKU ไม่สนตัวพิมพ์

         if (index >= 0) { // มีProductอยู่แล้ว                          
        productList.set(index, product);
            } else { // ยังไม่มี
        productList.add(product);                  
    }
}

    @Override
    public void removeBySku(String sku) throws ProductNotFoundException {
        int index = indexOfSku(sku); //หาในคลัง                          
        if (index < 0) { // หาไม่เจอ                                   
            throw new ProductNotFoundException("SKU not found: " + sku);
    }

    productList.remove(index); 
}

    @Override
public void setStock(String sku, int newStock)
        throws ProductNotFoundException, InvalidOperationException {
    if (newStock < 0) {
        throw new InvalidOperationException("newStock must be >= 0");
    }

    int index = indexOfSku(sku);
    if (index < 0) {
        throw new ProductNotFoundException("SKU not found: " + sku);
    }

    Product current = productList.get(index);
    current.setStock(newStock);
}

  @Override
public void increase(String sku, int qty)
        throws ProductNotFoundException, InvalidOperationException {
    if (qty <= 0) {
        throw new InvalidOperationException("quantity must be > 0");
    }

    int index = indexOfSku(sku);
    if (index < 0) {
        throw new ProductNotFoundException("SKU not found: " + sku);
    }

    Product current = productList.get(index);
    int newStock = current.stock() + qty;

    current.setStock(newStock);
}

    @Override
    public void decrease(String sku, int qty)
        throws ProductNotFoundException, InvalidOperationException {
        if (qty <= 0) {
            throw new InvalidOperationException("quantity must be > 0");
    }

        int index = indexOfSku(sku);
            if (index < 0) {
                    throw new ProductNotFoundException("SKU not found: " + sku);
    }

    Product current = productList.get(index);
        int Stock_left = current.stock() - qty;
            if (Stock_left < 0) {
                    throw new InvalidOperationException("Not space stock for " + current.sku()+ " (have " + current.stock() + ", need " + qty + ")");
    }

        current.setStock(Stock_left);
}

    // Indexofsku หาสินค้าด้วย index and sku
    private int indexOfSku(String sku) {
        if (sku == null) return -1; // การตรวจสอบความถูกต้องของข้อมูล 
        String skulow = sku.toLowerCase(); //ทำตัวเล็กเพื่อตรงกัน
        for (int i = 0; i < productList.size(); i++) { //ดึงตำแหน่ง product ที่ i 
            if (productList.get(i).sku().toLowerCase().equals(skulow)) { //เข้าถึง sku ของ obj แลเวแปลงรหัสออกมาเป็นตัวเล็ก
                return i; // พบสินค้า
            }
        }
        return -1; // ถ้าไม่พบ
    }


    private static void Productnotnull(Object value, String name) { //ป้องกันการไม่มีสินค้า
        if (value == null) throw new IllegalArgumentException(name + " is required");
    }
    

}
