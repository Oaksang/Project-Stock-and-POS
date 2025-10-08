package UI;

import javax.swing.*;

import Exception.InvalidOperationException;
import Exception.ProductNotFoundException;
import Services.InventoryService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;;
public class add_inventory extends JFrame implements ActionListener{
   Container cp;
   JButton home,back,add;
   JTextField sku,quantity;
   JLabel sku_t,quantity_t;
   private final InventoryService inventoryService;
   private final inventory parentFrame; 
   ImageIcon home_pic=new ImageIcon("./picture/home.png");
   public add_inventory(InventoryService inventoryService,inventory parentFrame){
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
   quantity=new JTextField();
   quantity.setBounds(50,120,200,25);
   quantity_t=new JLabel("Quanlity");
   quantity_t.setBounds(50, 90, 100,20);
   quantity_t.setBackground(new Color(216,191,216));
   quantity_t.setForeground(new Color(250,248,228));
   quantity_t.setFont(new Font("Garamond",Font.BOLD, 18));
   add=new JButton("Add");
   add.setBounds(50, 170, 90,40);
   add.setBackground(new Color(250,250,250));
   add.setForeground(new Color(216,191,216));
   add.setFont(new Font("Garamond",Font.BOLD, 28));
   add.setBorderPainted(false);
   back=new JButton("Back");
   back.setBounds(150, 170, 98,40);
   back.setBackground(new Color(250,250,250));
   back.setForeground(new Color(216,191,216));
   back.setFont(new Font("Garamond",Font.BOLD, 28));
   back.setBorderPainted(false);
   cp.add(back);
   cp.add(quantity_t);
   cp.add(sku_t);
   cp.add(sku);
   cp.add(home);
   cp.add(quantity);
   cp.add(add);
   home.addActionListener(this);
   add.addActionListener(this);
   back.addActionListener(this);
   }
   public void Finally(){
   this.setSize(310,300);
   this.setVisible(true);
   this.setLocationRelativeTo(null);
   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == add) {
            String skuInput = sku.getText().trim();
            String qtyInput = quantity.getText().trim();

            try {
               if (skuInput.isEmpty() || qtyInput.isEmpty()) {
                  JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                  return;
               }
               
               int qtyToAdd = Integer.parseInt(qtyInput);
               
               if (qtyToAdd <= 0) {
                     JOptionPane.showMessageDialog(this, "Quantity to add must be greater than 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                     return;
               }

                // เรียกใช้ increase ซึ่งจะอัปเดตสต็อกในหน่วยความจำและบันทึกเข้า CSV
               inventoryService.increase(skuInput, qtyToAdd);

               JOptionPane.showMessageDialog(this, 
                  "Stock for SKU '" + skuInput + "' increased by " + qtyToAdd + " successfully! Saved to CSV.", 
                  "Success", 
                  JOptionPane.INFORMATION_MESSAGE);
                  parentFrame.loadProductData();
               this.dispose();

            } catch (NumberFormatException ex) {
               JOptionPane.showMessageDialog(this, "Quantity must be a valid whole number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (ProductNotFoundException ex) {
               JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Operation Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidOperationException ex) {
               JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Operation Error", JOptionPane.ERROR_MESSAGE);
            }
      } else if (e.getSource() == back) {
            this.dispose();
      }
   if(e.getSource()==home){
      new dashboard();
      dispose();
   }
   }
}
