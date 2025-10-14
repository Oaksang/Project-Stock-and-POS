package UI.chart;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * หน้าที่ (Responsibility):
 * - extends JPanelสำหรับวาดกราฟวงกลม Pie Chart
 * - ใช้เปรียบเทียบสัดส่วนของสินค้า Top 3 (ชื่อ + ค่ายอดขาย) ที่ส่งเข้ามา
 *
 *  สัญญา (Contract):
 * - Input: List<LabelValue> (label = ชื่อสินค้า, value = ยอดขาย)
 * - จะวาดเฉพาะ "3 อันดับแรก" (ถ้ามีมากกว่า 3) และคำนวณสัดส่วนภายในกลุ่ม 3 อันดับเท่านั้น
 * - ไม่แก้ไขข้อมูลต้นทาง (list ภายนอก) — ภายในเก็บอ้างอิง read-only
 *
 * การออกแบบ (Design Notes):
 * - แยกขั้นตอนย่อย: คำนวณขนาด, วาดแต่ละชิ้น, วาด legend, จัดการ no-data
 * - ใช้ Anti-Aliasing เพิ่มความคม (RenderingHints)
 * - ค่าคงที่ด้านสไตล์/มิติต่าง ๆ ถูกกำหนดส่วนบนเพื่อแก้ไขง่าย
 *
 * ความปลอดภัย (Robustness):
 * - ป้องกันกรณีข้อมูลว่าง: แสดง “ไม่มีข้อมูล”
 */

public class PieChartPanel extends JPanel {
    // ค่าคงที่สำหรับการปรับแต่ง
    private static final int width = 600; // pack() คำนวน
    private static final int hight = 400;
    private static final int around_circle_space = 150;
    private static final int offset_x = 40;
    private static final int offset_y = 30;
    private static final int offset_space = 30;
    private static final int box_color_size = 20;
    private static final int box_color_space = 30;
    
    // สี
    private static final Color[] color_c = {
        new Color(255, 99, 132),   // ชมพู
        new Color(54, 162, 235),   // น้ำเงิน
        new Color(255, 206, 86),   // เหลือง
        new Color(75, 192, 192),   // เขียวอมฟ้า
        new Color(153, 102, 255),  // ม่วง
        new Color(255, 159, 64)    // ส้ม
    };
    
    private final List<LabelValue> data;

    public PieChartPanel(List<LabelValue> data) {
        this.data = getTop3(data);
        setPreferredSize(new Dimension(width, hight));
        setBackground(Color.WHITE);
    }
    
    // เอาแค่ 3 อันดับแรก 
    private List<LabelValue> getTop3(List<LabelValue> data) {
        if (data == null || data.size() <= 3) {
            return data;
        }
    // เรียงลำดับจากค่ามากไปน้อย (descending order)
    // โดยเปรียบเทียบ value ของแต่ละ LabelValue
    // (b, a) แทน (a, b) เพื่อให้เรียงจากมากไปน้อย
        return data.stream()
            .sorted((a, b) -> Double.compare(b.value, a.value)) //sort มากไปน้อย  แล้วเก็บ list
            .limit(3)                                   
            .collect(java.util.stream.Collectors.toList());
    }

    /**
     * paintComponent - method หลักที่ถูกเรียกเมื่อต้องวาดกราฟ
     * 
     * Override มาจาก JPanel เพื่อกำหนดว่าจะวาดอะไรบน Panel
     * method นี้จะถูกเรียกโดยอัตโนมัติเมื่อ Panel ต้องการ repaint
     * เช่น เมื่อ:
     * - เปิด window ครั้งแรก
     * - ขยาย/ย่อหน้าต่าง
     * - เรียก repaint() ด้วยตัวเอง
     * 
     * @param g Graphics object ที่ใช้ในการวาด
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (isDataEmpty()) {
            drawunfounddata(g);
            return;
        }

        Graphics2D g2 = createGraphics2D(g);
        drawPieChart(g2);
        g2.dispose();
    }

    private boolean isDataEmpty() {
        return data == null || data.isEmpty();
    }

    private void drawunfounddata(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.drawString("ไม่มีข้อมูล", 20, 20);
    }

    // สร้าง Graphics2D พร้อมตั้งค่า 
    private Graphics2D createGraphics2D(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        // ทำให้สวย
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        return g2;
    }

    /**
     * วาดกราฟวงกลมแบบสมบูรณ์ (ทั้งชิ้นพายและ Legend)
     * 
     * เป็น method หลักที่ควบคุมการวาดกราฟทั้งหมด โดยจะ:
     * 1. คำนวณขนาดและตำแหน่งของกราฟ
     * 2. คำนวณผลรวมทั้งหมดของข้อมูล
     * 3. วนลูปวาดแต่ละชิ้นพาย พร้อม Legend
     * 
     * @param g2 Graphics2D  ที่ตั้งค่าเรียบร้อยแล้ว
     */

    private void drawPieChart(Graphics2D g2) {
        // คำนวณตำแหน่ง (x, y) และขนาด (diameter) ของกราฟ
        ChartDimensions dimensions = calchartdimention();
        double total = caltotal();
        double start_angle = 0.0;
        // วนลูปแต่ละชิ้น pie
        for (int i = 0; i < data.size(); i++) {
            // ดึงข้อมูล i
            LabelValue item = data.get(i);
            //  มุม = 360 * (ค่าของ รายการนี้ / ผลรวม)
            double total_angle = calculateArcAngle(item.value, total);
            // วาดชิ้นพายส่วนโค้งของวงกลม
            drawPieSlice(g2, dimensions, start_angle, total_angle, i);
            // วาดรายการใน กล่องสี + ข้อความ ขวามือ
            drawLegendItem(g2, dimensions, item, i);
            
            start_angle += total_angle;
        }
    }

     /**
     * คำนวณตำแหน่งและขนาดของกราฟวงกลม
     * 
     * คำนวณจาก:
     * - ขนาดของ Panel ปัจจุบัน อาจเปลี่ยนได้
     * - CHART_MARGIN ที่กำหนดไว้
     * เพื่อให้กราฟอยู่ตรงกลางและมีพื้นที่สำหรับ Legend
     * 
     * @return ChartDimensions object ที่เก็บ x, y, diameter
     */
    private ChartDimensions calchartdimention() {
        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height) - around_circle_space;
        int x = (width - diameter) / 2;
        int y = (height - diameter) / 2;
        
        return new ChartDimensions(x, y, diameter);
    }

    // คำนวณผลรวมทั้งหมด
    private double caltotal() {
    double total = 0;
    for (LabelValue d : data) {
        total += d.value;  
    }
    return total;
}

    // คำนวณมุมของชิ้นพาย 
    private double calculateArcAngle(double value, double total) {
        return 360.0 * value / total;
    }

/**
     * วาดชิ้นพาย (ส่วนโค้งของวงกลม)
     * 
     * ใช้ fillArc() ในการวาดส่วนโค้งที่เติมสี
     * 
     * @param g2 Graphics2D object
     * @param dim ข้อมูลตำแหน่งและขนาดของกราฟ
     * @param startAngle มุมเริ่มต้นของชิ้นนี้ (องศา)
     * @param arcAngle ขนาดมุมของชิ้นนี้ (องศา)
     * @param colorIndex ลำดับสีที่จะใช้ (0, 1, 2, ...)
     */
    
     private void drawPieSlice(Graphics2D g2, ChartDimensions dim, double startAngle, double arcAngle, int colorIndex) {
        // ใช้ modulo (%) เผื่อข้อมูลมีมากกว่าจำนวนสี (จะวนกลับมาใช้สีแรก)
        g2.setColor(color_c[colorIndex % color_c.length]);
        // วาดส่วนโค้งที่เติมสี (Filled Arc)
        // fillArc(x, y, width, height, startAngle, arcAngle)
        // - x, y = ตำแหน่งมุมบนซ้ายของ bounding rectangle
        // - width, height = ขนาดของ bounding rectangle (เท่ากับ diameter)
        // - startAngle = มุมเริ่มต้น (0 = ทางขวา, 90 = ด้านบน)
        // - arcAngle = ขนาดมุมที่จะวาด (+ = ทวนเข็มนาฬิกา)
        g2.fillArc(dim.x, dim.y, dim.diameter, dim.diameter, (int) startAngle, (int) arcAngle);
    }

 /**
     * วาดรายการใน Legend (กล่องสี + ข้อความอธิบาย)
     * 
     * Legend จะแสดงอยู่ด้านขวาของกราฟ
     * แต่ละรายการประกอบด้วย:
     * 1. กล่องสีสี่เหลี่ยม (แสดงสีของชิ้นพาย)
     * 2. ข้อความ "ชื่อ (ค่า)" เช่น "สินค้า A (150)"
     * 
     * @param g2 Graphics2D object
     * @param dim ข้อมูลตำแหน่งและขนาดของกราฟ
     * @param item ข้อมูลรายการ (LabelValue)
     * @param index ลำดับของรายการ (0, 1, 2)
     */
        private void drawLegendItem(Graphics2D g2, ChartDimensions dim, LabelValue item, int index) {
        int legendX = dim.x + dim.diameter + offset_x;                   // = ตำแหน่ง x ของกราฟ + เส้นผ่านศูนย์กลาง + ระยะห่าง
        int legendY = dim.y + offset_y + (index * offset_space);         // = ตำแหน่ง y ของกราฟ + ระยะห่างเริ่มต้น + (ลำดับ × ความสูงของรายการ)
        
        // วาดกล่องสี ตั้งสีให้ตรงกับชิ้นพาย
        g2.setColor(color_c[index % color_c.length]);
        g2.fillRect(legendX, legendY - 10, box_color_size, box_color_size); //วาดสี่เหลี่ยมเติมสี
        
        // วาดข้อความ
        g2.setColor(Color.DARK_GRAY);
        String label = String.format("%s (%d)", item.label, (int) item.value);// สร้างข้อความในรูปแบบ "ชื่อ (ค่า)"
        g2.drawString(label, legendX + box_color_space, legendY + 5);
    }

    //เปิดหน้าต่างแสดงกราฟวงกลม 
    public static void openPie(String title, List<LabelValue> data) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new PieChartPanel(data));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // คลาสช่วยเก็บข้อมูลขนาดกราฟ 
    private static class ChartDimensions {
        final int x;
        final int y;
        final int diameter;

        ChartDimensions(int x, int y, int diameter) {
            this.x = x;
            this.y = y;
            this.diameter = diameter;
        }
    }
}


