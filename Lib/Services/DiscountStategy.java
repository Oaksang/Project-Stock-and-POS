package Services;


import DataModels.Order;

public interface DiscountStategy {
    /**
     * คำนวณราคาสุทธิของ Order
     *
     * @param order        ออบเจกต์ Order ที่ต้องการคำนวณ
     * @param discountCode รหัสส่วนลด
     * @param applyTax     true ถ้าต้องการรวมภาษี, false ถ้าไม่รวม
     * @return ราคารวมสุทธิหลังคำนวณ
     */
    double calculate(Order order, String discountCode, boolean applyTax);

    /**
     * คำนวณส่วนลดจากยอดเงินและรหัสส่วนลด
     *
     * @param amount ยอดเงินก่อนหักส่วนลด
     * @param code   รหัสส่วนลด
     * @return จำนวนเงินที่หักส่วนลด
     */
    double calDiscount(double amount, String code);

    /**
     * คำนวณภาษีจากยอดเงิน
     *
     * @param amount ยอดเงินที่ต้องการคำนวณภาษี
     * @return จำนวนเงินภาษี
     */
    double calTax(double amount);
}
