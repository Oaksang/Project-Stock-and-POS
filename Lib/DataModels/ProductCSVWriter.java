package DataModels;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.List;

/**
 * class ที่ใช้ในการเขียนข้อมูลสินค้าลงในไฟล์ CSV
 * หน้าที่ของคลาสนี้คือการเปิดไฟล์ CSV ที่เก็บข้อมูลสินค้า
 * เขียนข้อมูลสินค้าทั้งหมดลงในไฟล์
 */
public class ProductCSVWriter {

    private static final String CSV_FILE = "./FileCSV/products.csv"; 
    private static final String CSV_HEADER = "SKU,Name,Price,Stock";
    public void writeAllProductsToCSV(List<Product> products) {
        File file = new File(CSV_FILE);

        // false ใน FileWriter หมายถึง Overwrite (เขียนทับไฟล์เดิม)
        try (FileWriter fw = new FileWriter(file, false); 
             BufferedWriter bw = new BufferedWriter(fw)) {

            // เขียน Header
            bw.write(CSV_HEADER);
            bw.newLine();

            // เขียนข้อมูลสินค้าทุกรายการ
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