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

/*
 * หน้าต่างจัดการสินค้า ที่จะโชว์ตารางสินค้าทั้งหมด
 * สามารถเพิ่ม ลบ ค้นหา และจัดเรียงสินค้าได้
 * เชื่อมต่อกับ MemmoryInventoryService เพื่อจัดการข้อมูลสินค้า
 * @param inventoryService - บริการจัดการสินค้าทั้งหมด
 */
public class inventory extends JFrame implements ActionListener{
    Container cp;
    JTextField search;
    JRadioButton sortstock,sortprice;
    JTable productTable;
    DefaultTableModel tableModel; 
    JScrollPane tableScrollPane;
    JButton add,remove;
    JComboBox add_product,search_product;
    JButton home,ham;
    JPanel p,p_top;
    JButton inventory,Pos,logout;
    JTextField sku,quantity,name,price;
    JButton search_button;
    boolean check_p=false;
    private final InventoryService inventoryService;
    private List<Product> check_searchTosort;
    ImageIcon home_pic=new ImageIcon("./picture/home.png");
    ImageIcon ham_pic=new ImageIcon("./picture/hamburger.png");
    ImageIcon out_pic=new ImageIcon("./picture/logout.png");

    public inventory(){
       super("MR.DRY");
       // โหลดสินค้าทั้งหมดจาก CSV
       ProductCSVReader csvReader = new ProductCSVReader();
       List<Product> initialProducts = csvReader.readProductsFromCSV();
       check_searchTosort=null;
       // สร้าง InventoryService โดยใช้สินค้าที่โหลดมา
       this.inventoryService = new MemmoryInventoryService(initialProducts);
       Initial();
       setComponent();
       Finally();

       loadProductData(); 
    }

    public void Initial(){
    cp=getContentPane();
    cp.setLayout(null);
    cp.setBackground(new Color(216,191,216));
    }

    public void setComponent(){
    // ค้นหาสินค้า
    search=new JTextField("search product");
    search.setFont(new Font("Garamond",Font.BOLD, 16));
    search.setBounds(220, 10, 200,20);
    search.setBackground(new Color(250,250,250));
    // Label search
    search_button=new JButton("Search");
    search_button.setForeground(new Color(216,191,216));
    search_button.setFont(new Font("Garamond",Font.BOLD, 16));
    search_button.setBackground(new Color(250,250,250));
    search_button.setBounds(430, 10, 90, 20);
    search_button.setBorderPainted(false);

    // กดเลือกการจัดเรียง
    sortprice=new JRadioButton("sort by price",false);
    sortprice.setForeground(new Color(250,248,228));
    sortprice.setFont(new Font("Garamond",Font.BOLD, 18));
    sortprice.setBounds(15, 40, 200, 30);
    sortprice.setBackground(new Color(216,191,216));
    sortstock=new JRadioButton("sort by out stock",false);
    sortstock.setForeground(new Color(250,248,228));
    sortstock.setFont(new Font("Garamond",Font.BOLD, 18));
    sortstock.setBounds(180, 40, 200, 30);
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
    // ปุ่มเพิ่มสินค้า
    add=new JButton("Add");
    add.setForeground(new Color(216,191,216));
    add.setFont(new Font("Garamond",Font.BOLD, 30));
    add.setBounds(10,480,150,40);
    add.setBackground(new Color(250,250,250));
    add.setBorderPainted(false);
    // ปุ่มลบสินค้า
    remove=new JButton("Delete");
    remove.setForeground(new Color(216,191,216));
    remove.setFont(new Font("Garamond",Font.BOLD, 30));
    remove.setBounds(10,530,150,40);
    remove.setBackground(new Color(250,250,250));
    remove.setBorderPainted(false);

    // เลือกเพิ่มสินค้าแบบไหน
    add_product=new JComboBox<String>();
    add_product.addItem("add new product");
    add_product.addItem("add quantity product");
    add_product.setForeground(new Color(216,191,216));
    add_product.setFont(new Font("Garamond",Font.BOLD, 18));
    add_product.setBounds(170,480,200,20);
    add_product.setBackground(new Color(250,250,250));

    search_product=new JComboBox<String>();
    search_product.addItem("search by SKU");
    search_product.addItem("search by Name");
    search_product.setForeground(new Color(216,191,216));
    search_product.setFont(new Font("Garamond",Font.BOLD,16));
    search_product.setBounds(60,10,155,20);
    search_product.setBackground(new Color(250,250,250));

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
    cp.add(add_product);
    cp.add(remove);
    cp.add(add);
    cp.add(sortstock);
    cp.add(sortprice);
    cp.add(search);
    cp.add(search_button);
    cp.add(search_product);
    ham.addActionListener(this);
    home.addActionListener(this);
    inventory.addActionListener(this);
    remove.addActionListener(this);
    add.addActionListener(this);
    logout.addActionListener(this);
    Pos.addActionListener(this);
    sortprice.addActionListener(this);
    sortstock.addActionListener(this);
    search_button.addActionListener(this);
    search_product.addActionListener(this);
    search.addActionListener(this);
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
            // เพิ่มสินค้าแบบไหน
            if(select.equals("add new product")){ 
                new add2_inventory(this.inventoryService, this); 
            }else if(select.equals("add quantity product")){ 
                new add_inventory(this.inventoryService, this); 
            }
        }else if(e.getSource()==remove){
            new remove_inventory(this.inventoryService, this); 

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
        } else if(e.getSource()==logout){
            new loginpanel();
            dispose();
        } else if(e.getSource()==Pos){
            new Jflame_dashboard_order();
            dispose();
        } // จัดเรียงสินค้า
        else if(sortprice.isSelected()){
           String select=(String)search_product.getSelectedItem();
           if(check_searchTosort==null)
           this.Show_new(this.sortProductData(true,inventoryService.getAll()));
           else this.Show_new(this.sortProductData(true,this.searchProductData(select,search.getText())));
        }else if(sortstock.isSelected()){
            String select=(String)search_product.getSelectedItem();
            if(check_searchTosort==null)
            this.Show_new(this.sortProductData(false, inventoryService.getAll()));
            else this.Show_new(this.sortProductData(false,this.searchProductData(select,search.getText())));
        } else if(e.getSource()==search_button){
            String select=(String)search_product.getSelectedItem();
            this.Show_new(this.searchProductData(select,search.getText()));
        }
    }
    // แสดงข้อมูลสินค้าใหม่ในตาราง
    public void Show_new(List<Product> products){
        tableModel.setRowCount(0);
        for (Product product : products) {
            Vector<Object> row = new Vector<>();
            row.add(product.sku());
            row.add(product.name());
            row.add(String.format("%.2f", product.price()));
            row.add(product.stock());
            tableModel.addRow(row);
        }
    }

    // โหลดข้อมูลสินค้าและแสดงในตาราง
    // เรียกใช้เมธอดนี้เมื่อมีการเพิ่ม ลบ หรือแก้ไขสินค้า
    public void loadProductData() {
        // ล้างข้อมูลเก่าทั้งหมดในตาราง
        tableModel.setRowCount(0);

        // ดึงข้อมูลสินค้าล่าสุดจาก MemoryInventoryService
        List<Product> products = inventoryService.getAll();

        // วนลูปเพื่อเพิ่มแต่ละรายการสินค้าเข้าสู่ tableModel
        for (Product product : products) {
            Vector<Object> row = new Vector<>();
            row.add(product.sku());
            row.add(product.name());
            row.add(String.format("%.2f", product.price())); // จัดรูปแบบราคา
            row.add(product.stock());
            tableModel.addRow(row);
        }
    }
    // จัดเรียงหรือค้นหาสินค้าแล้วแสดงผล
    // ถ้า sort เป็น true จะจัดเรียงตามราคา
    // ถ้า sort เป็น false จะจัดเรียงตามสต็อกที่น้อยที่สุด
    public List<Product> sortProductData(boolean sort,List<Product> select) {
        tableModel.setRowCount(0);
        List<Product> products;
        if(sort)
        products = inventoryService.sortByPrice(false,select);
        else products=inventoryService.sortByStock(true,select);
        return products;
    }
    // ค้นหาสินค้า
    // ถ้าไม่ค้นหาให้ส่ง null มา
    // ถ้าค้นหาแล้วให้ส่งรายชื่อที่ค้นหาได้มา
    // ถ้าไม่เจอสินค้าให้ส่งค่าว่างมา
    public List<Product> searchProductData(String s,String search) {
        List <Product> products=new ArrayList<>();
        if("search by Name".equalsIgnoreCase(s)){
           products=inventoryService.searchByName(search);
        }else if("search by SKU".equalsIgnoreCase(s)){
           try {
            products=inventoryService.searchBySku(search); 
           } catch (Exception e) {
            System.out.println(e);
        } 
      }
    check_searchTosort=products;
    return check_searchTosort;
}
}
  