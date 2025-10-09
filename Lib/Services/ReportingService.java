package Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DataModels.Product;
import DataModels.SaleRecord;

public class ReportingService {
    private static final String CSV_HEADER = "orderId,cost,date";
    ArrayList<SaleRecord> saleRecords ;

    public ReportingService() {
        saleRecords = new ArrayList<>();
    }

    public void appendSaleRecord(SaleRecord saleRecord) {
        if (saleRecord == null) throw new RuntimeException("SaleRecord is unknown");
        saleRecords.add(saleRecord);
    }

    public double summarizeDailySales(LocalDate date) {
        double totalSales = saleRecords.stream()
                .filter(record -> record.getSaleTime().equals(date))
                .mapToDouble(SaleRecord::getTotal)
                .sum();
        System.out.println("Total sales for " + date + ": $" + totalSales);
        return totalSales;
    }
    public void writeReportToFile(String filename,LocalDate date){
        // Implement file writing logic here
        File f = new File(filename);
        FileWriter fw = null;
        BufferedWriter bw = null;
        boolean check_header;
        try {
            fw = new FileWriter(f,true);
            bw = new BufferedWriter(fw);
            check_header=f.exists()&&f.length()>0;
            for (SaleRecord record : saleRecords) {
                if(!check_header){
                    bw.write(CSV_HEADER);
                    bw.newLine();
                }
                bw.write(record.getOrderId() + "," + record.getTotal()+"," + record.getSaleTime());
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.err.println("Error writing report to file: " + e.getMessage());
        }
            finally {
                try {
                    if (bw != null) bw.close();
                    if (fw != null) fw.close();
                } catch (Exception e) {
                    System.err.println("Error closing file resources: " + e.getMessage());
                }
            }
    }
    // Test ReportingService class
    public static void main(String[] args) {
        ReportingService reportingService = new ReportingService();
        SaleRecord saleRecord1 = new SaleRecord("O001",1234.56,
                1000.00,50.00,70.00,1020.00,"Cash","SALE20");
        SaleRecord saleRecord2 = new SaleRecord("O002",789.10,
                700.00,20.00,30.00,710.00,"Credit Card","NEW10");
        reportingService.appendSaleRecord(saleRecord1);
        reportingService.appendSaleRecord(saleRecord2);
        reportingService.summarizeDailySales(LocalDate.now());
        reportingService.writeReportToFile("./FileCSV/daily_report.csv", LocalDate.now());
    }  
}