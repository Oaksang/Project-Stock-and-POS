package UI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DataModels.Product;
import DataModels.ProductCSVReader;
import Services.InventoryService;
import Services.MemmoryInventoryService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
public class stockout extends JFrame implements ActionListener{
    Container cp;
    JRadioButton sortstock,sortprice;
    JTable productTable;
    DefaultTableModel tableModel; 
    JScrollPane tableScrollPane;
    JButton home,ham;
    JPanel p,p_top;
    JButton inventory,Pos,logout;
    JTextField sku,quantity,name,price;
    boolean check_p=false;
    private final InventoryService inventoryService; 
    List<Product> initialProducts;
    ImageIcon home_pic=new ImageIcon("./picture/home.png");
    ImageIcon ham_pic=new ImageIcon("./picture/hamburger.png");
    ImageIcon out_pic=new ImageIcon("./picture/logout.png");

    public stockout(){
       super("MR.DRY");
       // โหลดสินค้าทั้งหมดจาก CSV
       ProductCSVReader csvReader = new ProductCSVReader();
       initialProducts = csvReader.readProductsFromCSV();

       this.inventoryService = new MemmoryInventoryService(initialProducts);
       Initial();
       setComponent();
       Finally();

       loadProductData(this.showout(initialProducts)); 
    }
     public List<Product> showout(List <Product> outStock){
     List <Product> outproduct=new ArrayList<>();
     for(Product p: outStock){
         if(p.stock()==0)
         outproduct.add(p);
    }
    return outproduct;
    }
    public void Initial(){
    cp=getContentPane();
    cp.setLayout(null);
    cp.setBackground(new Color(216,191,216));
    }

    public void setComponent(){

    // กดเลือกการจัดเรียง
    sortprice=new JRadioButton("sort by price",false);
    sortprice.setForeground(new Color(250,248,228));
    sortprice.setFont(new Font("Garamond",Font.BOLD, 18));
    sortprice.setBounds(65, 40, 170, 30);
    sortprice.setBackground(new Color(216,191,216));

    sortstock=new JRadioButton("sort by out stock",false);
    sortstock.setForeground(new Color(250,248,228));
    sortstock.setFont(new Font("Garamond",Font.BOLD, 18));
    sortstock.setBounds(220, 40, 170, 30);
    sortstock.setBackground(new Color(216,191,216));

    // กลุ่มปุ่มเลือก
    ButtonGroup group=new ButtonGroup();
    group.add(sortprice);
    group.add(sortstock);
    // 1. ตั้งค่า Model และ Header
        String[] columnNames = {"SKU", "Name", "Price", "Stock"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            // ทำให้ตารางแก้ไขข้อมูลไม่ได้
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        // 2. สร้าง JTable และ JScrollPane
        productTable = new JTable(tableModel);
        productTable.setFont(new Font("Garamond", Font.PLAIN, 14));
        productTable.setRowHeight(25);
        productTable.getTableHeader().setFont(new Font("Garamond", Font.BOLD, 16));
        productTable.getTableHeader().setBackground(new Color(192, 192, 192));
        
        tableScrollPane = new JScrollPane(productTable);
        
        tableScrollPane.setBounds(65, 70, 400, 400);
        
        // 3. เพิ่ม JScrollPane (ซึ่งมี JTable อยู่ข้างใน) เข้าสู่ Container
        cp.add(tableScrollPane);

    // ปุ่ม home
    home=new JButton();
    home.setIcon(home_pic);
    home.setBackground(new Color(216,191,216));
    home.setSize(20, 20);
    home.setBorderPainted(false);
    home.setBounds(0,0,20,20);

    // ปุ่ม ham
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
    cp.add(sortstock);
    cp.add(sortprice);
    ham.addActionListener(this);
    home.addActionListener(this);
    inventory.addActionListener(this);
    logout.addActionListener(this);
    Pos.addActionListener(this);
    sortprice.addActionListener(this);
    sortstock.addActionListener(this);
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
        }else if(e.getSource()==inventory){
                new inventory();
                dispose();
        } else if(e.getSource()==logout){
            new loginpanel();
            dispose();
        } else if(e.getSource()==Pos){
            new Jflame_dashboard_order();
            dispose();
        }else if(sortprice.isSelected()){
           this.sortProductData(true);
        }else if(sortstock.isSelected()){
           this.sortProductData(false);
        }
    }
    public void loadProductData(List <Product> product_out) {
        tableModel.setRowCount(0);

        for (Product product : product_out) {
            Vector<Object> row = new Vector<>();
            row.add(product.sku());
            row.add(product.name());
            row.add(String.format("%.2f", product.price())); // จัดรูปแบบราคา
            row.add(product.stock());
            tableModel.addRow(row);
        }
    }
    public void sortProductData(boolean sort) {
        tableModel.setRowCount(0);
        List<Product> products;
        if(sort)
        products = inventoryService.sortByPrice(false,this.showout(initialProducts));
        else products=inventoryService.sortByStock(true,this.showout(initialProducts));
        for (Product product : products) {
            Vector<Object> row = new Vector<>();
            row.add(product.sku());
            row.add(product.name());
            row.add(String.format("%.2f", product.price()));
            row.add(product.stock());
            tableModel.addRow(row);
        }
    }
      
}
  