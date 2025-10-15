package UI;

import javax.swing.*;

import Exception.ProductNotFoundException;
import Services.InventoryService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class remove_inventory extends JFrame implements ActionListener{
    Container cp;
    JButton home,remove;
    JTextField sku,quantity;
    JLabel sku_t,quantity_t;
    private final InventoryService inventoryService;
    private final inventory parentFrame;
    ImageIcon home_pic=new ImageIcon("./picture/home.png");
    public remove_inventory(InventoryService inventoryService,inventory parentFrame){
      super("MR.DRY");
      this.inventoryService = inventoryService; // รับ InventoryService เข้ามา
      this.parentFrame = parentFrame;
       Initial();
       setComponent();
       Finally();
    }
    public void Initial(){
    cp=getContentPane();
    cp.setLayout(null);
    cp.setBackground(new Color(216,191,216));
    }
    public void setComponent(){
        home=new JButton();
    home.setIcon(home_pic);
    home.setBackground(new Color(216,191,216));
    home.setSize(20, 20);
    home.setBorderPainted(false);
    home.setBounds(0,0,20,20);
    sku=new JTextField();
    sku.setBounds(50,50,200,25);
    sku_t=new JLabel("SKU");
    sku_t.setBounds(50, 20, 50,20);
    sku_t.setBackground(new Color(216,191,216));
    sku_t.setForeground(new Color(250,248,228));
    sku_t.setFont(new Font("Garamond",Font.BOLD, 18));
    /*quantity=new JTextField();
    quantity.setBounds(50,120,200,25);
    quantity_t=new JLabel("Quanlity");
    quantity_t.setBounds(50, 90, 100,20);
    quantity_t.setBackground(new Color(216,191,216));
    quantity_t.setForeground(new Color(250,248,228));
    quantity_t.setFont(new Font("Garamond",Font.BOLD, 18));*/
    remove=new JButton("Remove");
    remove.setBounds(70, 100, 150,40);
    remove.setBackground(new Color(250,250,250));
    remove.setForeground(new Color(216,191,216));
    remove.setFont(new Font("Garamond",Font.BOLD, 28));
    remove.setBorderPainted(false);

    //cp.add(quantity_t);
    cp.add(sku_t);
    cp.add(sku);
    cp.add(home);
    //cp.add(quantity);
    cp.add(remove);
    home.addActionListener(this);
    remove.addActionListener(this);

    }
    public void Finally(){
    this.setSize(310,210);
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 }
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == remove) {
            // ดึง SKU จากผู้ใช้
            String skuInput = sku.getText().trim();

            try {
                // ตรวจสอบข้อมูลนำเข้า
                if (skuInput.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter the SKU to remove.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // เรียกใช้ removeBySku ซึ่งจะลบจากหน่วยความจำและบันทึกเข้า CSV
                // Note: การลบสินค้าทั้งชิ้นใช้แค่ SKU เท่านั้น
                inventoryService.removeBySku(skuInput);

                // แสดงผลสำเร็จและปิดหน้าจอ
                JOptionPane.showMessageDialog(this,
                    "Product with SKU '" + skuInput + "' removed successfully and saved to CSV!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                    parentFrame.loadProductData();
                this.dispose();

            } catch (ProductNotFoundException ex) {
                // จัดการเมื่อไม่พบ SKU
                JOptionPane.showMessageDialog(this, 
                    "Error: Product with SKU '" + skuInput + "' not found.", 
                    "Operation Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // จัดการข้อผิดพลาดอื่นๆ ที่อาจเกิดขึ้น
                 JOptionPane.showMessageDialog(this, 
                    "An unexpected error occurred: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }else if(e.getSource()==home){
        new dashboard();
        dispose();
     
        }
    }
}
