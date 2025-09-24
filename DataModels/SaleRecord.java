package DataModels;
import Services.PricingService;

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
        if (orderId == null || orderId.isEmpty()) throw new RuntimeException("orderId is unknown");
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

    // Test SaleRecord class
    public static void main(String[] args) {
        SaleRecord saleRecord = new SaleRecord("O001",1234.56,1000.00,50.00,70.00,1020.00,"Cash","SALE20");
        System.out.println("Order ID: " + saleRecord.getOrderId());
        System.out.println("Total Sales: " + saleRecord.getTotalSales());
        System.out.println("Subtotal: " + saleRecord.getSubtotal());
        System.out.println("Discount: " + saleRecord.getDiscount());
        System.out.println("Tax: " + saleRecord.getTax());
        System.out.println("Total: " + saleRecord.getTotal());
        System.out.println("Payment Method: " + saleRecord.getPaymentMethod());
        System.out.println("Discount Code: " + saleRecord.getDiscountCode());

    }

}
