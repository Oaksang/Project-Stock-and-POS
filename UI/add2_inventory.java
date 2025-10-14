package UI;
import javax.swing.*;

import DataModels.Product;
import Services.InventoryService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class add2_inventory extends JFrame implements ActionListener{
    Container cp;
    JButton add;
    JTextField sku,quantity,name,price;
    JLabel sku_t,quantity_t,name_t,price_t;
    private final InventoryService inventoryService; 
    private final inventory parentFrame;
    ImageIcon home_pic=new ImageIcon("./picture/home.png");
    public add2_inventory(InventoryService inventoryService,inventory parentFrame){
      super("MR.DRY");
      this.parentFrame = parentFrame;
      this.inventoryService = inventoryService; // รับ InventoryService เข้ามา
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
    sku=new JTextField();
    sku.setBounds(50,50,200,25);
    sku_t=new JLabel("SKU");
    sku_t.setBounds(50, 20, 50,20);
    sku_t.setBackground(new Color(216,191,216));
    sku_t.setForeground(new Color(250,248,228));
    sku_t.setFont(new Font("Garamond",Font.BOLD, 18));
    name=new JTextField();
    name.setBounds(50,120,200,25);
    name_t=new JLabel("Name");
    name_t.setBounds(50, 90, 100,20);
    name_t.setBackground(new Color(216,191,216));
    name_t.setForeground(new Color(250,248,228));
    name_t.setFont(new Font("Garamond",Font.BOLD, 18));
    price=new JTextField();
    price.setBounds(50,190,200,25);
    price_t=new JLabel("Price");
    price_t.setBounds(50, 160, 100,20);
    price_t.setBackground(new Color(216,191,216));
    price_t.setForeground(new Color(250,248,228));
    price_t.setFont(new Font("Garamond",Font.BOLD, 18));
    quantity=new JTextField();
    quantity.setBounds(50,260,200,25);
    quantity_t=new JLabel("Quanlity");
    quantity_t.setBounds(50, 230, 100,20);
    quantity_t.setBackground(new Color(216,191,216));
    quantity_t.setForeground(new Color(250,248,228));
    quantity_t.setFont(new Font("Garamond",Font.BOLD, 18));
    add=new JButton("Add");
    add.setBounds(50, 300, 90,40);
    add.setBackground(new Color(250,250,250));
    add.setForeground(new Color(216,191,216));
    add.setFont(new Font("Garamond",Font.BOLD, 28));
    add.setBorderPainted(false);

    cp.add(name);
    cp.add(name_t);
    cp.add(price);
    cp.add(price_t);
    cp.add(quantity_t);
    cp.add(sku_t);
    cp.add(sku);
    cp.add(quantity);
    cp.add(add);
    add.addActionListener(this);
    }
    public void Finally(){
    this.setSize(320,400);
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 }
   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == add) {
            String skuInput = sku.getText().trim();
            String nameInput = name.getText().trim();
            String priceInput = price.getText().trim();
            String qtyInput = quantity.getText().trim();

            try {
               if (skuInput.isEmpty() || nameInput.isEmpty() || priceInput.isEmpty() || qtyInput.isEmpty()) {
                  JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                  return;
               }
               
               double newPrice = Double.parseDouble(priceInput);
               int newStock = Integer.parseInt(qtyInput);
               
               if (newPrice < 0 || newStock < 0) {
                     JOptionPane.showMessageDialog(this, "Price and Quantity must be non-negative numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                     return;
               }

                // เรียกใช้ addProduct ซึ่งจะบันทึกเข้า CSV ด้วย
               Product newProduct = new Product(skuInput, nameInput, newPrice, newStock);
               inventoryService.addProduct(newProduct);

               JOptionPane.showMessageDialog(this, "Product '" + nameInput + "' added successfully and saved to CSV!", "Success", JOptionPane.INFORMATION_MESSAGE);
               this.dispose();

   
                // เรียกเมทอดอัปเดตข้อมูลของหน้าจอหลัก
                parentFrame.loadProductData(); 
                
                this.dispose();

            } catch (NumberFormatException ex) {
               JOptionPane.showMessageDialog(this, "Price and Quantity must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (RuntimeException ex) {
                // RuntimeException ถูกโยนเมื่อ SKU ซ้ำ
               JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Operation Error", JOptionPane.ERROR_MESSAGE);
            }
   }
   }
}
