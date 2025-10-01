package DataModels;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductCSVReader {

    private static final String CSV_FILE = "./FileCSV/products.csv";

    /**
     * อ่านข้อมูลสินค้าทั้งหมดจากไฟล์ CSV
     * @return รายการ Product List
     */
    public List<Product> readProductsFromCSV() {
        List<Product> products = new ArrayList<>();
        String line;
        boolean isHeader = true; // Flag เพื่อข้ามบรรทัด Header

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // ข้ามบรรทัดแรกที่เป็น Header
                    continue;
                }
                
                String[] data = line.split(",");

                // ตรวจสอบว่ามีข้อมูลครบ 4 คอลัมน์หรือไม่ (SKU, Name, Price, Stock)
                if (data.length == 4) {
                    try {
                        String sku = data[0].trim();
                        String name = data[1].trim();
                        // แปลง String เป็น double และ int ตามลำดับ
                        double price = Double.parseDouble(data[2].trim());
                        int stock = Integer.parseInt(data[3].trim());

                        Product product = new Product(sku, name, price, stock);
                        products.add(product);
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping line due to invalid number format: " + line);
                    }
                }
            }
            System.out.println("Loaded " + products.size() + " products from " + CSV_FILE);

        } catch (IOException e) {
            System.err.println("Could not read file " + CSV_FILE + ". Starting with empty inventory.");
            // หากไฟล์ไม่มีอยู่จริง ให้คืนค่ารายการว่าง
        }
        return products;
    }
    // Test ProductCSVReader class
    public static void main(String[] args) {
        ProductCSVReader reader = new ProductCSVReader();
        List<Product> products = reader.readProductsFromCSV();
        for (Product p : products) {
            System.out.println(p);
        }
    }
}