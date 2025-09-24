package PricingServices;


import DataModels.Order;

/**
 * คลาสที่ implement DiscountStategy โดยมี logic การคำนวณมาตรฐาน
 * ใช้ประเภทข้อมูล double ซึ่งอาจมีความแม่นยำจำกัดในการคำนวณทางการเงิน
 */
public class StandardPricingService implements DiscountStategy {

    // สมมติฐาน: ส่วนลดคงที่
    private static final double DISCOUNT_RATE_SALE20 = 0.20; // 20%
    private static final double DISCOUNT_AMOUNT_TENOFF = 10.0; // 10 บาท

    // สมมติฐาน: อัตราภาษี 7%
    private static final double TAX_RATE = 0.07;

    @Override
    public double calculate(Order order, String discountCode, boolean applyTax) {
        double subtotal = order.calculateSubtotal();
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

}