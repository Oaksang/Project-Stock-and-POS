package UI;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class inventory extends JFrame implements ActionListener{
    Container cp;
    JTextField search;
    JRadioButton New,out,old;
    JList item;
    JButton add,remove;
    JComboBox add_product;
    JButton home,ham;
    JPanel p,p_top;
    JButton inventory,Pos,logout;
    boolean check_p=false;
    ImageIcon home_pic=new ImageIcon("./picture/home.png");
    ImageIcon ham_pic=new ImageIcon("./picture/hamburger.png");
    ImageIcon out_pic=new ImageIcon("./picture/logout.png");
    public inventory(){
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
    search=new JTextField("search product");
    search.setFont(new Font("Garamond",Font.BOLD, 16));
    search.setBounds(260, 10, 200,20);
    JLabel search_t=new JLabel("Search");
    search_t.setForeground(new Color(250,248,228));
    search_t.setFont(new Font("Garamond",Font.BOLD, 16));
    search_t.setBackground(new Color(216,191,216));
    search_t.setBounds(470, 10, 50, 20);
    New=new JRadioButton("sort by recent",false);
    New.setForeground(new Color(250,248,228));
    New.setFont(new Font("Garamond",Font.BOLD, 18));
    New.setBounds(15, 40, 150, 30);
    New.setBackground(new Color(216,191,216));
    old=new JRadioButton("sort by stale",false);
    old.setForeground(new Color(250,248,228));
    old.setFont(new Font("Garamond",Font.BOLD, 18));
    old.setBounds(180, 40, 150, 30);
    old.setBackground(new Color(216,191,216));
    out=new JRadioButton("sort by out stock",false);
    out.setForeground(new Color(250,248,228));
    out.setFont(new Font("Garamond",Font.BOLD, 18));
    out.setBounds(340, 40, 200, 30);
    out.setBackground(new Color(216,191,216));

    ButtonGroup group=new ButtonGroup();
    group.add(New);
    group.add(old);
    group.add(out);
    item=new JList<String>();//product class
    item.setForeground(new Color(216,191,216));
    item.setFont(new Font("Garamond",Font.BOLD, 18));
    item.setBounds(10, 70, 485, 400);
    item.setBackground(new Color(250,250,250));
    add=new JButton("Add");
    add.setForeground(new Color(216,191,216));
    add.setFont(new Font("Garamond",Font.BOLD, 30));
    add.setBounds(10,480,150,40);
    add.setBackground(new Color(250,250,250));
    add.setBorderPainted(false);
    remove=new JButton("Delete");
    remove.setForeground(new Color(216,191,216));
    remove.setFont(new Font("Garamond",Font.BOLD, 30));
    remove.setBounds(10,530,150,40);
    remove.setBackground(new Color(250,250,250));
    remove.setBorderPainted(false);
    add_product=new JComboBox<String>();
    add_product.addItem("add new product");
    add_product.addItem("add quantity product");
    add_product.setForeground(new Color(216,191,216));
    add_product.setFont(new Font("Garamond",Font.BOLD, 18));
    add_product.setBounds(170,480,200,20);
    add_product.setBackground(new Color(250,250,250));
    home=new JButton();
    home.setIcon(home_pic);
    home.setBackground(new Color(216,191,216));
    home.setSize(20, 20);
    home.setBorderPainted(false);
    home.setBounds(0,0,20,20);
    ham=new JButton();
    ham.setIcon(ham_pic);
    ham.setBackground(new Color(216,191,216));
    ham.setSize(20, 20);
    ham.setBorderPainted(false);
    ham.setBounds(21,0,20,20);
   p_top=new JPanel();
   p_top.setLayout(null);
   p_top.setBackground(new Color(250,248,228));
   p_top.setBounds(0,0,200,650);
   p=new JPanel();
   p.setLayout(null);
   p.setBackground(new Color(0,0,0,0));
   p.setBounds(0, 0, 200,650);
   inventory=new JButton("Inventory");
   inventory.setBackground(new Color(250,248,228));
   inventory.setBorderPainted(false);
   inventory.setFont(new Font("Garamond",Font.BOLD, 30));
   inventory.setForeground(new Color(216,191,216));
   inventory.setBounds(0, 60,200, 50);
   Pos=new JButton("POS");
   Pos.setBorderPainted(false);
   Pos.setFont(new Font("Garamond",Font.BOLD, 30));
   Pos.setBackground(new Color(250,248,228));
   Pos.setBorderPainted(false);
   Pos.setForeground(new Color(216,191,216));
   Pos.setBounds(0, 110,200, 50);
   logout =new JButton();
   logout.setIcon(out_pic);
   logout.setBackground(new Color(250,248,228));
   logout.setBounds(0,590,20,20);
   logout.setSize(20, 20);
   logout.setBorderPainted(false);
   JLabel logout_t=new JLabel("logout");
   logout_t.setHorizontalAlignment(JLabel.LEFT);
   logout_t.setFont(new Font("Garamond",Font.BOLD, 14));
   logout_t.setBackground(new Color(250,248,228));
   logout_t.setForeground(new Color(216,191,216));
   logout_t.setBounds(30,590,50, 20);
   p_top.add(logout_t);
   p_top.add(logout);
   p_top.add(inventory);
   p_top.add(Pos);
    cp.add(home);
    cp.add(ham);
    cp.add(add_product);
    cp.add(remove);
    cp.add(add);
    cp.add(item);
    cp.add(out);
    cp.add(old);
    cp.add(New);
    cp.add(search);
    cp.add(search_t);
    ham.addActionListener(this);
    home.addActionListener(this);
    inventory.addActionListener(this);
    remove.addActionListener(this);
    add.addActionListener(this);
   p.add(p_top);
   this.setGlassPane(p);
   p.setVisible(false);
   p.setOpaque(false);
    }
    public void Finally(){
    this.setSize(550,650);
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ham){
        if (!check_p) {
                cp.remove(home);
                cp.remove(ham);
                home.setBounds(160, 0, 20, 20);
                ham.setBounds(180, 0, 20, 20);
                home.setBackground(new Color(250, 248, 228));
                ham.setBackground(new Color(250, 248, 228));
                p_top.add(home);
                p_top.add(ham);
                p.setVisible(true);
                //cp.add(p_top);
                check_p = true;
            } else {
                home.setBounds(0, 0, 20, 20);
                ham.setBounds(21, 0, 20, 20);
                home.setBackground(new Color(216, 191, 216));
                ham.setBackground(new Color(216, 191, 216));
                cp.add(home);
                cp.add(ham);
                p.setVisible(false);
                check_p = false;
            }
            cp.revalidate();
            cp.repaint();
        }else if(e.getSource()==home){
                new dashboard();
                dispose();
        }else if(e.getSource()==add){
            String select=(String)add_product.getSelectedItem();
            if(select=="add new product"){
                new add2_inventory();
                //dispose();
            }else if(select=="add quantity product"){
                new add_inventory();
                //dispose();
            }
        }else if(e.getSource()==remove){
                new remove_inventory();
                //dispose();
        }else if(e.getSource()==inventory){
             if (check_p) {
                home.setBounds(0, 0, 20, 20);
                ham.setBounds(21, 0, 20, 20);
                home.setBackground(new Color(216, 191, 216));
                ham.setBackground(new Color(216, 191, 216));
                cp.add(home);
                cp.add(ham);
                p.setVisible(false);
                check_p = false;
            }
            cp.revalidate();
            cp.repaint();
        }
    }
}
