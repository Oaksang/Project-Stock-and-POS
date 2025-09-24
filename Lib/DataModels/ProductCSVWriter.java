package DataModels;

import DataModels.Product;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class ProductCSVWriter {

    private static final String CSV_FILE = "products.csv";
    private static final String CSV_HEADER = "SKU,Name,Price,Stock";

    /**
     * Writes a new product to the CSV file.
     * If the file does not exist, it creates the file with a header.
     * @param product The Product object to be written to the file.
     */
    public void writeProductToCSV(Product product) {
        File file = new File(CSV_FILE);
        boolean fileExists = file.exists();

        try (FileWriter fw = new FileWriter(file, true); // The 'true' argument enables append mode
             BufferedWriter bw = new BufferedWriter(fw)) {

            // Write the header only if the file is newly created
            if (!fileExists) {
                bw.write(CSV_HEADER);
                bw.newLine();
            }

            // Format the product data as a CSV line
            String productLine = String.join(",",
                product.sku(),
                product.name(),
                String.valueOf(product.price()),
                String.valueOf(product.stock())
            );

            // Write the product line and a new line
            bw.write(productLine);
            bw.newLine();
            
            System.out.println("Product '" + product.name() + "' has been added to the CSV file.");
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

}
