package Lib.Datamodel;

public class SaleRecord {
    private String orderId;
    private double totalSales;

    private final double subtotal;
    private final double discount;
    private final double tax;
    private final double total;
    private final String paymentMethod; 
    private final String discountCode;  

    public SaleRecord(String orderId,double totalSales,double subtotal,double discount,double tax,double total,String paymentMethod,String discountCode) {
        if (orderId == null || orderId.isEmpty()) throw new RuntimeException("orderId is unknow");
        if (subtotal < 0 || discount < 0 || tax < 0 || total < 0)
            throw new RuntimeException("amounts should >= 0");
        this.orderId = orderId;
        this.totalSales = totalSales;
        this.subtotal = subtotal;
        this.discount = discount;
        this.tax = tax;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.discountCode = discountCode;

    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public double getTotalSales() {
        return totalSales;
    }
    public double getSubtotal() { 
        return subtotal; }
    public double getDiscount() { 
        return discount; }
    public double getTax() { 
        return tax; }
    public double getTotal() { 
        return total; }

    public String getPaymentMethod() { 
        return paymentMethod; }
        
    public String getDiscountCode() { 
        return discountCode; }

}
