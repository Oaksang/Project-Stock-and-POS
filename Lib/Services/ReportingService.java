package Services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;

import DataModels.SaleRecord;

public class ReportingService {
    private static final String CSV_HEADER = "OrderId,Cost,Date"; // หัวข้อของไฟล์ CSV
    ArrayList<SaleRecord> saleRecords ;

    // Constructor
    public ReportingService() {
        saleRecords = new ArrayList<>();
    }

    // บันทึกข้อมูลการขายสินค้าในแต่ละวัน โดยรับข้อมูลเป็น SaleRecord
    public void appendSaleRecord(SaleRecord saleRecord) {
        if (saleRecord == null) throw new RuntimeException("SaleRecord is unknown");
        saleRecords.add(saleRecord);
    }

    // สรุปยอดขายทั้งหมดในแต่ละวัน
    public double summarizeDailySales(LocalDate date) {
        double totalSales = saleRecords.stream()
                .filter(record -> record.getSaleTime().equals(date))
                .mapToDouble(SaleRecord::getTotal)
                .sum();
        return totalSales;
    }

    // สร้างรายงานการขายสินค้าในรูปแบบ CSV
    public void writeReportToFile(String filename,LocalDate date){
        File f = new File(filename);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(f);
            bw = new BufferedWriter(fw);
            bw.write(CSV_HEADER);
            bw.newLine();
            for (SaleRecord record : saleRecords) {
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

}