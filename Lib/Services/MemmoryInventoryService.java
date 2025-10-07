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
    // ตัวแปรสำหรับเขียนไฟล์ CSV
    private final ProductCSVWriter csvWriter; 

    /**
     * สร้างคลังสินค้าเปล่า
     */
    public MemmoryInventoryService(List<DataModels.Product> initialProducts) {
        if (initialProducts != null) {
            this.productList.addAll(initialProducts);
        }
        this.csvWriter = new ProductCSVWriter(); 
    }
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
        if (CheckSku(product.sku())) {
            throw new RuntimeException("SKU already exists: " + product.sku());
        }
        productList.add(product);
        // บันทึกรายการสินค้าทั้งหมดลงใน CSV
        csvWriter.writeAllProductsToCSV(productList);
    }

    @Override
    public void removeBySku(String sku) throws ProductNotFoundException {
        
        int index = indexOfSku(sku);
        
        if (index < 0) {
            throw new ProductNotFoundException("SKU not found: " + sku);
        }

        productList.remove(index);
        csvWriter.writeAllProductsToCSV(productList);
}

@Override
    public void setStock(String sku, int newStock)
            throws ProductNotFoundException, InvalidOperationException {
        // ... (Existing logic to find product, set stock) ...
        int index = indexOfSku(sku);
        if (index < 0) {
            throw new ProductNotFoundException("SKU not found: " + sku);
        }
        
        Product current = productList.get(index);
        
        if (newStock < 0) {
             throw new InvalidOperationException("Stock cannot be negative.");
        }
        
        current.setStock(newStock); 
        // บันทึกรายการสินค้าทั้งหมดลงใน CSV
        csvWriter.writeAllProductsToCSV(productList);
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

        // บันทึกรายการสินค้าทั้งหมดลงใน CSV
        csvWriter.writeAllProductsToCSV(productList);
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
        csvWriter.writeAllProductsToCSV(productList); // บันทึกรายการสินค้าทั้งหมดลงใน CSV
        return -1; // ถ้าไม่พบ
    }
    

}
