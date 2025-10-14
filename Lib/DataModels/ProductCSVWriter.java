package DataModels;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.List;

public class ProductCSVWriter {

    // ตรวจสอบให้แน่ใจว่าใช้ชื่อไฟล์เดียวกับ ProductCSVReader
    private static final String CSV_FILE = "./FileCSV/products.csv"; 
    private static final String CSV_HEADER = "SKU,Name,Price,Stock";

    /**
     * เขียนรายการสินค้าทั้งหมดทับไฟล์ CSV เดิม
     * เมทอดนี้ใช้เมื่อมีการเปลี่ยนแปลงข้อมูล เช่น เพิ่มสินค้าใหม่ หรืออัปเดตสต็อก
     * @param products รายการ Product object ทั้งหมดในคลัง
     */
    public void writeAllProductsToCSV(List<Product> products) {
        File file = new File(CSV_FILE);

        // false ใน FileWriter หมายถึง Overwrite (เขียนทับไฟล์เดิม)
        try (FileWriter fw = new FileWriter(file, false); 
             BufferedWriter bw = new BufferedWriter(fw)) {

            // 1. เขียน Header
            bw.write(CSV_HEADER);
            bw.newLine();

            // 2. เขียนข้อมูลสินค้าทุกรายการ
            for (Product product : products) {
                String productLine = String.join(",",
                    product.sku(),
                    product.name(),
                    String.valueOf(product.price()),
                    String.valueOf(product.stock())
                );
                bw.write(productLine);
                bw.newLine();
            }
            
            System.out.println("Inventory saved to CSV successfully. Total: " + products.size());
        } catch (IOException e) {
            System.err.println("Error writing all products to CSV file: " + e.getMessage());
        }
    }
}