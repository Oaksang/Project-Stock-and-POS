package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class remove_inventory extends JFrame implements ActionListener{
    Container cp;
    JButton home,back,remove;
    JTextField sku,quantity;
    JLabel sku_t,quantity_t;
    ImageIcon home_pic=new ImageIcon("./picture/home.png");
    public remove_inventory(){
      super("MR.DRY");
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
    remove=new JButton("Remove");
    remove.setBounds(70, 170, 150,40);
    remove.setBackground(new Color(250,250,250));
    remove.setForeground(new Color(216,191,216));
    remove.setFont(new Font("Garamond",Font.BOLD, 28));
    remove.setBorderPainted(false);
    back=new JButton("Back");
    back.setBounds(70, 220, 150,40);
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
    cp.add(remove);
    home.addActionListener(this);
    remove.addActionListener(this);
    back.addActionListener(this);
    }
    public void Finally(){
    this.setSize(310,350);
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==home){
        new dashboard();
        dispose();
     }else if(e.getSource()==remove){
        //delete file
        dispose();
     }
     else if(e.getSource()==back){
        dispose();
     }
    }
}
