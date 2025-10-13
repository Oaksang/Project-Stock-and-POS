package UI.chart;

/** เก็บข้อมูลชื่อสินค้าและยอดขาย (ใช้เป็นข้อมูลกราฟ) */
public class LabelValue {
    public final String label;
    public final double value;

    public LabelValue(String label, double value) {
        this.label = label;
        this.value = value;
    }
}