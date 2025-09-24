package UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class add2_inventory extends JFrame implements ActionListener{
    Container cp;
    JButton home,back,add;
    JTextField sku,quantity,name,price;
    JLabel sku_t,quantity_t,name_t,price_t;
    ImageIcon home_pic=new ImageIcon("./picture/home.png");
    public add2_inventory(){
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
    back=new JButton("Back");
    back.setBounds(150, 300, 98,40);
    back.setBackground(new Color(250,250,250));
    back.setForeground(new Color(216,191,216));
    back.setFont(new Font("Garamond",Font.BOLD, 28));
    back.setBorderPainted(false);
    cp.add(name);
    cp.add(name_t);
    cp.add(price);
    cp.add(price_t);
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
    this.setSize(320,400);
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
    @Override
    public void actionPerformed(ActionEvent e) {
    if(e.getSource()==home){
        new dashboard();
        dispose();
     }else if(e.getSource()==add){
        //write file
        new inventory();
        dispose();
     }
     else if(e.getSource()==back){
        new inventory();
        dispose();
     }
    }
}
