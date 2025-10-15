package Services;
import DataModels.Order;


/**
 * คลาสที่ implement DiscountStategy โดยมี logic การคำนวณมาตรฐาน
 * ใช้ประเภทข้อมูล double ซึ่งอาจมีความแม่นยำจำกัดในการคำนวณทางการเงิน
 * หน้าที่ของคลาสนี้คือการคำนวณราคาสุทธิหลังหักส่วนลดและบวกภาษี
 * รวมถึงการคำนวณส่วนลดและภาษีตามเงื่อนไข่นที่กำหนด
 * @param DISCOUNT_RATE_SALE20 อัตราส่วนลด 20% , DISCOUNT_AMOUNT_TENOFF ส่วนลด 10 บาท , TAX_RATE อัตราภาษี 7%
 * @return ราคาสุทธิหลังหักส่วนลดและบวกภาษี, ส่วนลดตามรหัสส่วนลด, ภาษีจากยอดเงิน
 */
public class PricingService implements DiscountStategy {

    // สมมติฐาน: ส่วนลดคงที่
    private static final double DISCOUNT_RATE_SALE20 = 0.20; // 20%
    private static final double DISCOUNT_AMOUNT_TENOFF = 10.0; // 10 บาท

    // สมมติฐาน: อัตราภาษี 7%
    private static final double TAX_RATE = 0.07;

    @Override // คำนวณราคาสุทธิหลังหักส่วนลดและบวกภาษี
    public double calculate(Order order, String discountCode, boolean applyTax) {
        double subtotal = order.getSubtotal();
        double totalAfterDiscount = subtotal - calDiscount(subtotal, discountCode);
        
        if (applyTax) {
            return totalAfterDiscount + calTax(totalAfterDiscount);
        }
        
        return totalAfterDiscount;
    }

    @Override // คำนวณส่วนลดตามรหัสส่วนลด
    public double calDiscount(double amount, String code) {
        if ("SALE20".equals(code)) {
            return amount * DISCOUNT_RATE_SALE20;
        } else if ("TENOFF".equals(code)) {
            return DISCOUNT_AMOUNT_TENOFF;
        }
        return 0.0;
    }

    @Override // คำนวณภาษีจากยอดเงิน
    public double calTax(double amount) {
        return amount * TAX_RATE;
    }
}