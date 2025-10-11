package UI;
import javax.swing.*;
import DataModels.Product;
import DataModels.ProductCSVReader;
import DataModels.SaleCSVRead;

import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.time.LocalDate;
public class dashboard extends JFrame implements ActionListener{
 Container cp;
 JButton ham,home;
 JPanel p,p_top;
 JButton inventory,Pos,logout;
 JButton totalstock,lowstock,outstock,bestsale;
 boolean check_p=false;
 ImageIcon home_pic=new ImageIcon("./picture/home.png");
 ImageIcon ham_pic=new ImageIcon("./picture/hamburger.png");
 ImageIcon out_pic=new ImageIcon("./picture/logout.png");
 ProductCSVReader csvReader;
 List<Product> initialProducts;
 SaleCSVRead csvReaderSale;
 List<String[]> kuy;
 public dashboard(){
    super("MR.DRY");
    csvReader=new ProductCSVReader();
    initialProducts = csvReader.readProductsFromCSV();
    csvReaderSale=new SaleCSVRead();
    kuy=csvReaderSale.readSaleFromCSV();
    Initial();
    setComponent();
    Finally();
 }
 public int showlow(List<Product> lowStock,int low){
  List <Product> lowproduct=new ArrayList<>();
  for(Product p: lowStock){
  if((p.stock()<low)&&(p.stock()>0))
  lowproduct.add(p);
  }
  int lenght=lowproduct.size();
  return lenght;
 }
 public int showout(List <Product> outStock){
 List <Product> outproduct=new ArrayList<>();
 for(Product p: outStock){
  if(p.stock()==0)
  outproduct.add(p);
 }
 int lenght=outproduct.size();
 return lenght;
 }
 public int showstock(List<Product> Stock){
     int lenght=Stock.size();
     return lenght;
 }
public double showsale(List <String[]> recordsale){
  LocalDate Today=LocalDate.now();
  double price_total=0;
  for(String[] s: recordsale){
    //String orderid = s[0].trim();
    double price = Double.parseDouble(s[1].trim());
    LocalDate date = LocalDate.parse(s[2].trim());
    if(Today.equals(date)){
    price_total+=price;
    }
}
 return price_total;
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
   ham=new JButton();
   ham.setIcon(ham_pic);
   ham.setBackground(new Color(216,191,216));
   ham.setSize(20, 20);
   ham.setBorderPainted(false);
   ham.setBounds(21,0,20,20);
   JLabel Dashboard=new JLabel("DashBoard");
   Dashboard.setHorizontalAlignment(JLabel.LEFT);
   Dashboard.setFont(new Font("Garamond",Font.BOLD, 50));
   Dashboard.setBackground(new Color(216,191,216));
   Dashboard.setForeground(new Color(250, 248, 228));
   Dashboard.setBounds(50,60,300, 50);

   JLabel total=new JLabel("Total Stock");
   total.setHorizontalAlignment(JLabel.LEFT);
   total.setFont(new Font("Garamond",Font.BOLD, 30));
   total.setBackground(new Color(216,191,216));
   total.setForeground(new Color(250, 248, 228));
   total.setBounds(60,130,150, 50);
   //แสดงจำนวนสินค้าทั้งหมดในStock
   totalstock=new JButton(""+showstock(initialProducts));
   totalstock.setHorizontalAlignment(JLabel.LEFT);
   totalstock.setFont(new Font("Garamond",Font.BOLD, 50));
   totalstock.setBackground(new Color(216,191,216));
   totalstock.setForeground(new Color(250, 248, 228));
   totalstock.setBounds(60,190,150, 50);
   totalstock.setBorderPainted(false);

   JLabel low=new JLabel("Low Stock");
   low.setHorizontalAlignment(JLabel.LEFT);
   low.setFont(new Font("Garamond",Font.BOLD, 30));
   low.setBackground(new Color(216,191,216));
   low.setForeground(new Color(250, 248, 228));
   low.setBounds(320,130,150, 50);
   //แสดงจำนวนสินค้าที่มีจำนวนน้อยกว่าที่กำหนดในStock
   lowstock=new JButton(""+showlow(initialProducts,10));
   lowstock.setHorizontalAlignment(JLabel.LEFT);
   lowstock.setFont(new Font("Garamond",Font.BOLD, 50));
   lowstock.setBackground(new Color(216,191,216));
   lowstock.setForeground(new Color(255, 218, 185));
   lowstock.setBounds(320,190,150, 50);
   lowstock.setBorderPainted(false);
   
   JLabel out=new JLabel("Out Stock");
   out.setHorizontalAlignment(JLabel.LEFT);
   out.setFont(new Font("Garamond",Font.BOLD, 30));
   out.setBackground(new Color(216,191,216));
   out.setForeground(new Color(250, 248, 228));
   out.setBounds(65,300,150, 50);
   //แสดงจำนวนสินค้าที่หมดStockที่กำหนดในStock
   outstock=new JButton(""+showout(initialProducts));
   outstock.setHorizontalAlignment(JLabel.LEFT);
   outstock.setFont(new Font("Garamond",Font.BOLD, 50));
   outstock.setBackground(new Color(216,191,216));
   outstock.setForeground(new Color(220, 49, 100));
   outstock.setBounds(65,350,150, 50);
   outstock.setBorderPainted(false);

   JLabel best=new JLabel("Best Selling");
   best.setHorizontalAlignment(JLabel.LEFT);
   best.setFont(new Font("Garamond",Font.BOLD, 30));
   best.setBackground(new Color(216,191,216));
   best.setForeground(new Color(250, 248, 228));
   best.setBounds(320,300,150, 50);
   //เเสดงสินค้าที่มีจำนวนชิ้นการขายมากตามสำดับ
   bestsale=new JButton(""+showout(initialProducts));
   bestsale.setHorizontalAlignment(JLabel.LEFT);
   bestsale.setFont(new Font("Garamond",Font.BOLD, 50));
   bestsale.setBackground(new Color(216,191,216));
   bestsale.setForeground(new Color(220, 49, 100));
   bestsale.setBounds(320,350,150, 50);
   bestsale.setBorderPainted(false);

   JLabel sale=new JLabel("Today Sale");
   sale.setHorizontalAlignment(JLabel.LEFT);
   sale.setFont(new Font("Garamond",Font.BOLD, 30));
   sale.setBackground(new Color(216,191,216));
   sale.setForeground(new Color(250, 248, 228));
   sale.setBounds(65,450,180, 50);
   JLabel showsale=new JLabel(showsale(kuy)+"Baht");
   showsale.setHorizontalAlignment(JLabel.LEFT);
   showsale.setFont(new Font("Garamond",Font.BOLD, 50));
   showsale.setBackground(new Color(216,191,216));
   showsale.setForeground(new Color(250, 248, 228));
   showsale.setBounds(65,500,400, 50);
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

   //เพิ่มค่าลงในcontainer
   cp.add(home);
   cp.add(ham);
   cp.add(Dashboard);
   cp.add(total);
   cp.add(totalstock);
   cp.add(low);
   cp.add(lowstock);
   cp.add(best);
   cp.add(out);
   cp.add(outstock);
   cp.add(best);
   cp.add(bestsale);
   cp.add(sale);
   cp.add(showsale);
   ham.addActionListener(this);
   home.addActionListener(this);
   inventory.addActionListener(this);
   logout.addActionListener(this);
   Pos.addActionListener(this);
   totalstock.addActionListener(this);
   lowstock.addActionListener(this);
   outstock.addActionListener(this);
   //
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
    } else if(e.getSource()==logout){
        new loginpanel();
        dispose();
    } else if(e.getSource()==inventory){
        new inventory();
        dispose();
    }else if(e.getSource()==Pos){
      new Jflame_dashboard_order();
      dispose();
    } else if(e.getSource()==home){
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
    } else if(e.getSource()==totalstock){
      new stockAll();
      dispose();
    }else if(e.getSource()==lowstock){
      new stockLow();
      dispose(); 
    }else if(e.getSource()==outstock){
      new stockout();
      dispose();
    }
 }
}