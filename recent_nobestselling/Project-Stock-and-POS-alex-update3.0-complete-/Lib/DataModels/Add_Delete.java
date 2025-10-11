package DataModels;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Add_Delete {
    private static final String CSV_FILE = "./FileCSV/ADDandDELETE_Report.csv"; 
    private static final String CSV_HEADER = "SKU,Name,Date,Quantity";
    private File f=new File("./FileCSV/ADDandDELETE_Report.csv");
    private FileReader fr=null;
    private BufferedReader br=null;
    private LocalDateTime date=LocalDateTime.now();
    List <String[]> record;
    public void writeCSV(List<Product> products,LocalDateTime dateSale) {
        File file = new File(CSV_FILE);

        try (FileWriter fw = new FileWriter(file, false); 
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(CSV_HEADER);
            bw.newLine();

            for (Product product : products) {
                String productLine = String.join(",",
                    product.sku(),
                    product.name(),
                    //String.valueOf(dateSale)
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
    
    public List<String[]> readSaleFromCSV(){
    record=new ArrayList<>();
    boolean isHeader =true;
       try {
        fr=new FileReader(f);
        br=new BufferedReader(fr);
        String s;
        while((s=br.readLine())!=null){
        if (isHeader) {
                    isHeader = false;
                    continue;
                }
        
        String[] data=s.split(",");
                record.add(data);
            }
        }catch (Exception e) {
        System.out.println(e);
       } finally{
        try {
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
       return record;
    }

}
