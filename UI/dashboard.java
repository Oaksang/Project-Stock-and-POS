package UI;
import javax.swing.*;
import DataModels.Product;
import DataModels.ProductCSVReader;
import DataModels.Product_soldReader;
import DataModels.SaleCSVRead;
import UI.chart.*;
import java.util.List;
import java.util.Map;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;

public class dashboard extends JPanel implements ActionListener{
 mainframe mainframe;
 JButton ham,home;
 JPanel p_top;
 JButton inventory,Pos,logout;
 JButton totalstock,lowstock,outstock;
 JButton rank_1,rank_2,rank_3;
 boolean check_p=false;
 ImageIcon home_pic=new ImageIcon("./picture/home.png");
 ImageIcon ham_pic=new ImageIcon("./picture/hamburger.png");
 ImageIcon out_pic=new ImageIcon("./picture/logout.png");
 ProductCSVReader csvReader;
 List<Product> initialProducts;
 SaleCSVRead csvReaderSale;
 Product_soldReader productCSVReader;
 List<String[]> kuy,kuy2;
 List<String[]> best;
 String[] rank1,rank2,rank3;
 public dashboard(mainframe mainframe){
  this.mainframe=mainframe;
    csvReader=new ProductCSVReader();
    initialProducts = csvReader.readProductsFromCSV();
    csvReaderSale=new SaleCSVRead();
    kuy=csvReaderSale.readSaleFromCSV();
    productCSVReader=new Product_soldReader();
    kuy2=productCSVReader.readSaleFromCSV();
    best = this.showbest(kuy2);

    if (best != null && best.size() >= 3) {
      rank1 = best.get(0);
      rank2 = best.get(1);
      rank3 = best.get(2);
    } else {
      rank1 = new String[]{"-", "0"};
      rank2 = new String[]{"-", "0"};
      rank3 = new String[]{"-", "0"};     
    }
     setLayout(null);
     setBackground(new Color(216,191,216));
     setSize(550, 650);
     setComponent();   
 }

private void setComponent(){
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
   JLabel dascardboard=new JLabel("DashBoard");
   dascardboard.setHorizontalAlignment(JLabel.LEFT);
   dascardboard.setFont(new Font("Garamond",Font.BOLD, 50));
   dascardboard.setBackground(new Color(216,191,216));
   dascardboard.setForeground(new Color(250, 248, 228));
   dascardboard.setBounds(50,60,300, 50);

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
   outstock.setForeground(new Color(255,105,180));
   outstock.setBounds(65,350,150, 50);
   outstock.setBorderPainted(false);

   JLabel best_label=new JLabel("Best Selling");
   best_label.setHorizontalAlignment(JLabel.LEFT);
   best_label.setFont(new Font("Garamond",Font.BOLD, 30));
   best_label.setBackground(new Color(216,191,216));
   best_label.setForeground(new Color(250, 248, 228));
   best_label.setBounds(320,300,150, 50);
   //เเสดงสินค้าที่มีจำนวนชิ้นการขายมากตามสำดับ
   rank_1=new JButton(rank1[0] + "   " + rank1[1]);
   rank_1.setHorizontalAlignment(JLabel.LEFT);
   rank_1.setFont(new Font("Garamond",Font.BOLD, 24));
   rank_1.setBackground(new Color(216,191,216));
   rank_1.setForeground(new Color(250, 248, 228));
   rank_1.setBounds(300,350,200, 25);
   rank_1.setBorderPainted(false);
   rank_2=new JButton(rank2[0] + "   " + rank2[1]);
   rank_2.setHorizontalAlignment(JLabel.LEFT);
   rank_2.setFont(new Font("Garamond",Font.BOLD, 24));
   rank_2.setBackground(new Color(216,191,216));
   rank_2.setForeground(new Color(250, 248, 228));
   rank_2.setBounds(300,380,200, 25);
   rank_2.setBorderPainted(false);
   rank_3=new JButton(rank3[0] + "   " + rank3[1]);
   rank_3.setHorizontalAlignment(JLabel.LEFT);
   rank_3.setFont(new Font("Garamond",Font.BOLD, 24));
   rank_3.setBackground(new Color(216,191,216));
   rank_3.setForeground(new Color(250, 248, 228));
   rank_3.setBounds(300,410,200, 25);
   rank_3.setBorderPainted(false);
   JLabel sale=new JLabel("Today Sale");
   sale.setHorizontalAlignment(JLabel.LEFT);
   sale.setFont(new Font("Garamond",Font.BOLD, 30));
   sale.setBackground(new Color(216,191,216));
   sale.setForeground(new Color(250, 248, 228));
   sale.setBounds(65,450,180, 50);
   JLabel showsale=new JLabel(String.format("%.2f Baht",showsale(kuy)));
   showsale.setHorizontalAlignment(JLabel.LEFT);
   showsale.setFont(new Font("Garamond",Font.BOLD, 50));
   showsale.setBackground(new Color(216,191,216));
   showsale.setForeground(new Color(250, 248, 228));
   showsale.setBounds(65,500,400, 50);
   p_top=new JPanel();
   p_top.setLayout(null);
   p_top.setBackground(new Color(250,248,228));
   p_top.setBounds(0,0,200,650);
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
   add(home);
   add(ham);
   add(dascardboard);
   add(total);
   add(totalstock);
   add(low);
   add(lowstock);
   add(best_label);
   add(out);
   add(outstock);
   add(rank_1);
   add(rank_2);
   add(rank_3);
   add(sale);
   add(showsale);
   ham.addActionListener(this);
   home.addActionListener(this);
   inventory.addActionListener(this);
   logout.addActionListener(this);
   Pos.addActionListener(this);
   totalstock.addActionListener(this);
   lowstock.addActionListener(this);
   outstock.addActionListener(this);
   rank_1.addActionListener(this);
   rank_2.addActionListener(this);
   rank_3.addActionListener(this);
}
 @Override
 public void actionPerformed(ActionEvent e) {
      List<LabelValue> chartData = new ArrayList<>();
      Object src = e.getSource();
      for (String[] r : best) {
        chartData.add(new LabelValue(r[0], Double.parseDouble(r[1])));
    }

      if (src == rank_1 || src == rank_2 || src == rank_3) {
        PieChartPanel.openPie("Top 3 Best Sellers", chartData);
    }
    if(e.getSource()==ham){
    if (!check_p) {
                remove(home);
                remove(ham);
                home.setBounds(160, 0, 20, 20);
                ham.setBounds(180, 0, 20, 20);
                home.setBackground(new Color(250, 248, 228));
                ham.setBackground(new Color(250, 248, 228));
                p_top.add(home);
                p_top.add(ham);
                mainframe.showside(p_top);
                check_p = true;
            } else {
                home.setBounds(0, 0, 20, 20);
                ham.setBounds(21, 0, 20, 20);
                home.setBackground(new Color(216, 191, 216));
                ham.setBackground(new Color(216, 191, 216));
                add(home);
                add(ham);
                mainframe.hideside();
                check_p = false;
            }
    } else if(e.getSource()==logout){
        new loginpanel();
        mainframe.dispose();
    } else if(e.getSource()==inventory){
        mainframe.showcard("inventory");
        if (check_p) {
                home.setBounds(0, 0, 20, 20);
                ham.setBounds(21, 0, 20, 20);
                home.setBackground(new Color(216, 191, 216));
                ham.setBackground(new Color(216, 191, 216));
                add(home);
                add(ham);
                mainframe.hideside();
                check_p = false;
            }
    }else if(e.getSource()==Pos){
      mainframe.setVisible(false);
      new Jflame_dashboard_order(mainframe);
    } else if(e.getSource()==home){
      if (check_p) {
                home.setBounds(0, 0, 20, 20);
                ham.setBounds(21, 0, 20, 20);
                home.setBackground(new Color(216, 191, 216));
                ham.setBackground(new Color(216, 191, 216));
                add(home);
                add(ham);
                mainframe.hideside();
                check_p = false;
            }
    } else if(e.getSource()==totalstock){
        mainframe.showcard("all");
    }else if(e.getSource()==lowstock){
       mainframe.showcard("low");
    }else if(e.getSource()==outstock){
      mainframe.showcard("out");
    }
 }
private int showlow(List<Product> lowStock,int low){
  List <Product> lowproduct=new ArrayList<>();
  for(Product p: lowStock){
  if((p.stock()<low)&&(p.stock()>0))
  lowproduct.add(p);
  }
  int lenght=lowproduct.size();
  return lenght;
 }
private int showout(List <Product> outStock){
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
private double showsale(List<String[]> recordsale){
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
public List<String[]> showbest(List<String[]> product_sold){
  LocalDate today = LocalDate.now();

    Map<String, Integer> sum = new HashMap<>();
    for (String[] row : product_sold) {
        LocalDate d = LocalDate.parse(row[1].trim());
        if (!d.equals(today)) continue;

        String key = row[2].trim().toLowerCase(); 
        int qty = Integer.parseInt(row[3].trim());
        sum.merge(key, qty, Integer::sum);
    }

    List<String[]> ranked = new ArrayList<>();
    for (var e : sum.entrySet()) {
        ranked.add(new String[]{ e.getKey(), String.valueOf(e.getValue()) });
    }

    ranked.sort((a, b) -> {
        int qa = Integer.parseInt(a[1]);
        int qb = Integer.parseInt(b[1]);
        int cmp = Integer.compare(qb, qa);
        return (cmp != 0) ? cmp : a[0].compareTo(b[0]);
    });
    return ranked;
}
}