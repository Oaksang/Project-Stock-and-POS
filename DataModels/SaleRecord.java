package DataModels;

import java.time.LocalDateTime;

public class SaleRecord {
    private String orderId;
    private double totalSales;

    public SaleRecord(String orderId, double totalSales) {
        this.orderId = orderId;
        this.totalSales = totalSales;

    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public double getTotalSales() {
        return totalSales;
    }
    

}
