package Services;


import java.util.List;
import java.time.LocalDateTime;

import DataModels.Order;
import DataModels.OrderItem;
import DataModels.Product;

/**
 * คลาสที่ implement DiscountStategy โดยมี logic การคำนวณมาตรฐาน
 * ใช้ประเภทข้อมูล double ซึ่งอาจมีความแม่นยำจำกัดในการคำนวณทางการเงิน
 */
public class PricingService implements DiscountStategy {

    // สมมติฐาน: ส่วนลดคงที่
    private static final double DISCOUNT_RATE_SALE20 = 0.20; // 20%
    private static final double DISCOUNT_AMOUNT_TENOFF = 10.0; // 10 บาท

    // สมมติฐาน: อัตราภาษี 7%
    private static final double TAX_RATE = 0.07;

    @Override
    public double calculate(Order order, String discountCode, boolean applyTax) {
        double subtotal = order.getSubtotal();
        double totalAfterDiscount = subtotal - calDiscount(subtotal, discountCode);
        
        if (applyTax) {
            return totalAfterDiscount + calTax(totalAfterDiscount);
        }
        
        return totalAfterDiscount;
    }

    @Override
    public double calDiscount(double amount, String code) {
        if ("SALE20".equals(code)) {
            return amount * DISCOUNT_RATE_SALE20;
        } else if ("TENOFF".equals(code)) {
            return DISCOUNT_AMOUNT_TENOFF;
        }
        return 0.0;
    }

    @Override
    public double calTax(double amount) {
        return amount * TAX_RATE;
    }

    // Test PricingService
    public static void main(String[] args) {
        PricingService pricingService = new PricingService();
        Product product1 = new Product("P001", "Laptop", 1200, 50);
        Product product2 = new Product("P002", "Mouse", 25, 200);
        OrderItem orderItem1 = new OrderItem(product1, 2, product1.price());
        OrderItem orderItem2 = new OrderItem(product2, 3, product2.price());
        List<OrderItem> itemList = List.of(orderItem1, orderItem2);
        Order order = new Order("O001", LocalDateTime.now(), itemList);
        double subtotal = order.getSubtotal();
        double discount = pricingService.calDiscount(subtotal, "SALE20");
        double tax = pricingService.calTax(subtotal - discount);
        double total = pricingService.calculate(order, "SALE20", true);
        System.out.println("Subtotal: " + subtotal);
        System.out.println("Discount: " + discount);
        System.out.println("Tax: " + tax);
        System.out.println("Total: " + total);
    }
}