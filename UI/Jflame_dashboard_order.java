package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DataModels.Product;
import DataModels.ProductCSVWriter;
import Services.PricingService;


public class Jflame_dashboard_order extends JFrame {
    private DefaultTableModel cartTableModel;
    private DefaultTableModel productTableModel; // Model สำหรับตารางสินค้า
    private List<Product> productList; // รายการสินค้าทั้งหมดที่โหลดจาก CSV
    private JLabel subtotalLabel;
    private JLabel taxLabel;
    private JLabel totalLabel;
    private JTextField searchField;
    private double currentDiscount = 0.0; // ตัวแปรเก็บส่วนลดปัจจุบัน
    private JLabel discountLabel; // Label สำหรับแสดงส่วนลด
    private JTable cartTable; // ตัวแปร JTable สำหรับตะกร้า
    private PricingService pricingService = new PricingService();
    public Jflame_dashboard_order(){
        setTitle("Point of Sale");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // จัดให้อยู่กึ่งกลางหน้าจอ
        setIconImage(new ImageIcon("./picture/MRDRY_logo.png").getImage());
        

        // ใช้ Look and Feel ของระบบปฏิบัติการเพื่อให้ดูเป็นธรรมชาติ
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // กำหนดโทนสีที่ทันสมัยและดูสบายตา
        Color backgroundColor = new Color(240, 242, 245); // สีเทาอ่อนสำหรับพื้นหลัง
        Color panelColor = Color.WHITE; // สีขาวสำหรับแผงหลัก
        Color accentColor = new Color(0, 123, 255); // สีน้ำเงินสำหรับปุ่มและเน้นข้อความ

        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout(15, 15)); // เพิ่มระยะห่างระหว่างส่วนประกอบหลัก
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem dashboardItem = new JMenuItem("Go to Dashboard");
        JMenuItem inventoryItem = new JMenuItem("Go to Inventory");
        dashboardItem.addActionListener(e -> {
            new dashboard();
            dispose();
        });
        inventoryItem.addActionListener(e -> {
            new inventory();
            dispose();
        });
        fileMenu.add(dashboardItem);
        fileMenu.add(inventoryItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        JPanel mainPanel = createMainPanel(panelColor, accentColor);
        add(mainPanel, BorderLayout.CENTER);

        // เพิ่มขอบ (padding) ให้กับเนื้อหาหลัก
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        setVisible(true);
        
    }



    // สร้างหน้าต่าง Panel หลักทั้งหมด
    private JPanel createMainPanel(Color panelColor, Color accentColor) {
        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 15, 0)); // เพิ่มระยะห่างระหว่างคอลัมน์
        
        JPanel productPanel = createProductPanel(panelColor, accentColor);
        JPanel shoppingCartPanel = createShoppingCartPanel(panelColor);
        JPanel totalPanel = createTotalPanel(panelColor, accentColor);
        
        mainPanel.add(productPanel);
        mainPanel.add(shoppingCartPanel);
        mainPanel.add(totalPanel);
        
        return mainPanel;
    }


    private JPanel createProductPanel(Color panelColor, Color accentColor) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(panelColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Product Information", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Tahoma", Font.BOLD, 14), 
                Color.DARK_GRAY)));
        
        
        // สร้างส่วนค้นหาสินค้า (Search Panel)
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBackground(panelColor);
        searchPanel.setBorder(new EmptyBorder(10, 10, 5, 10));

        searchField = new JTextField(); 
        searchField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        searchPanel.add(new JLabel("Search Product or Scan Barcode"), BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // สร้างตารางแสดงสินค้า (Product List Table)
        String[] productColumnNames = {"SKU", "Name", "Price"};
        productTableModel = new DefaultTableModel(productColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                 return false; // ห้ามแก้ไขข้อมูลในตารางสินค้า
            }
        };
        JTable productTable = new JTable(productTableModel);
        productTable.setFillsViewportHeight(true);
        productTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        
        JScrollPane productScrollPane = new JScrollPane(productTable);
        productScrollPane.setBorder(new EmptyBorder(0, 10, 0, 10)); // เพิ่มช่องว่างด้านข้าง

        // เพิ่ม Listener ให้กับตารางเพื่อเพิ่มสินค้าเมื่อดับเบิ้ลคลิก
        productTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // ดับเบิ้ลคลิก
                    int row = productTable.getSelectedRow();
                    if (row != -1) {
                        String productID = (String) productTableModel.getValueAt(row, 0); // SKU คือคอลัมน์แรก
                        addProductToCart(productID);
                    }
                }
            }
        });


        // สร้าง Action Panel (ปุ่ม Add/Delete)
        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 10, 10)); 
        actionPanel.setBackground(panelColor);
        actionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    
        JButton manualEnterButton = createStyledButton("Add Product by ID", accentColor, Color.WHITE);
        manualEnterButton.addActionListener(e -> {
            String productID = searchField.getText().trim(); 
            if (!productID.isEmpty()) {
                addProductToCart(productID);
                searchField.setText(""); // ล้างช่องค้นหา
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a Product ID or scan a barcode.", "Input Required", JOptionPane.WARNING_MESSAGE);
            }
        });
        actionPanel.add(manualEnterButton);
        JButton deleteButton = createStyledButton("Delete Item from Cart", new Color(220, 53, 69), Color.WHITE);
        deleteButton.addActionListener(e -> {
            // โค้ดสำหรับลบสินค้าในตะกร้า (Cart)
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow != -1) {
                cartTableModel.removeRow(selectedRow);
                currentDiscount = 0.0; // รีเซ็ตส่วนลดเมื่อมีการแก้ไขตะกร้า
                updateTotals(); // อัปเดตยอดรวมหลังลบ
                JOptionPane.showMessageDialog(panel, "Item deleted from cart.");
            } else {
                JOptionPane.showMessageDialog(panel, "Please select an item in the cart to delete.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });
        actionPanel.add(deleteButton);
        actionPanel.add(deleteButton);
        
        // จัดเรียงส่วนประกอบ
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(productScrollPane, BorderLayout.CENTER); // ตารางอยู่ตรงกลาง
        panel.add(actionPanel, BorderLayout.SOUTH); // ปุ่มอยู่ด้านล่าง
        
        // โหลดข้อมูลสินค้าเมื่อสร้าง Panel เสร็จ
        loadProductsFromCSV();
        
        return panel;
    }
    

    // สร้างส่วนตรงกลาง ลิสต์สินค้าที่เพิ่มในตะกร้า
    private JPanel createShoppingCartPanel(Color panelColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(panelColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Shopping carts", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Tahoma", Font.BOLD, 14), 
                Color.DARK_GRAY)));

        String[] columnNames = {"List", "Quantity", "Total"};
        cartTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return Integer.class;
                if (columnIndex == 2) return Double.class;
                return String.class;
            }
        };
        cartTable = new JTable(cartTableModel);
        
        // ปรับปรุงรูปลักษณ์ของตาราง
        cartTable.setFillsViewportHeight(true);
        cartTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
        cartTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        cartTable.getTableHeader().setBackground(new Color(245, 245, 245));
        
        JScrollPane scrollPane = new JScrollPane(cartTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    

    // สร้างส่วนขวาสุด ราคาสินค้าและการชำระเงิน
    private JPanel createTotalPanel(Color panelColor, Color accentColor) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(panelColor);
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
        new EmptyBorder(10, 10, 10, 10)));

    
    // สร้าง Panel สำหรับแสดงยอดรวม (Totals Panel) โดยใช้ GridBagLayout
    JPanel totalsPanel = new JPanel(new GridBagLayout());
    totalsPanel.setBackground(panelColor);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5); // ช่องว่างระหว่างแถว
    
    // ตั้งค่าตัวจัดเรียงข้อความ
    Font labelFont = new Font("Tahoma", Font.BOLD, 14);
    Font valueFont = new Font("Tahoma", Font.BOLD, 16);
    
    // Helper function สำหรับเพิ่มแถวใน GridBagLayout
    autoAddRow(totalsPanel, gbc, "Subtotal:", labelFont, valueFont, Color.DARK_GRAY, subtotalLabel = new JLabel("฿0.00"));
    autoAddRow(totalsPanel, gbc, "Discount:", labelFont, valueFont, new Color(220, 53, 69), discountLabel = new JLabel("฿0.00")); // แถวใหม่สำหรับส่วนลด
    autoAddRow(totalsPanel, gbc, "Tax (7%):", labelFont, valueFont, Color.DARK_GRAY, taxLabel = new JLabel("฿0.00"));

    // เส้นคั่นก่อน Total
    JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 2;
    totalsPanel.add(separator, gbc);

    // Total
    gbc.gridx = 0;
    gbc.gridy++;
    JLabel totalTitle = new JLabel("TOTAL:");
    totalTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
    totalTitle.setForeground(accentColor);
    gbc.gridwidth = 1; // ต้องตั้งกลับไปเป็น 1 เพื่อให้ช่องขวาจัดชิดขวา
    totalsPanel.add(totalTitle, gbc); 

    gbc.gridx = 1;
    totalLabel = new JLabel("฿0.00");
    totalLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
    totalLabel.setForeground(accentColor);
    totalLabel.setHorizontalAlignment(SwingConstants.RIGHT); // จัดชิดขวา
    totalsPanel.add(totalLabel, gbc);
    
    // จัดให้ totalsPanel อยู่ด้านบนของ BorderLayout.CENTER
    JPanel centerWrap = new JPanel(new BorderLayout());
    centerWrap.setBackground(panelColor);
    centerWrap.add(totalsPanel, BorderLayout.NORTH);
    
    // สร้าง Button Panel
    JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2x2
    buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
    buttonPanel.setBackground(panelColor);
    
    // ปุ่มต่างๆ
    JButton cashButton = createStyledButton("Cash", accentColor, Color.WHITE);
    cashButton.addActionListener(e -> processPayment("Cash"));
    buttonPanel.add(cashButton);

    JButton codeButton = createStyledButton("Use Code", accentColor, Color.WHITE);
    codeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cartTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(panel, "Shopping cart is empty. Please add products first.", "Action Failed", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String discountCode = JOptionPane.showInputDialog(panel, 
                "Enter Discount Code:", 
                "Apply Discount Code", 
                JOptionPane.PLAIN_MESSAGE);
            
            if (discountCode != null && !discountCode.trim().isEmpty()) {
                applyDiscountCode(discountCode.trim());
            } else if (discountCode != null) {
                // หากผู้ใช้กด OK โดยไม่กรอกโค้ด ให้แจ้งเตือน
                JOptionPane.showMessageDialog(panel, "Discount code cannot be empty.", "Invalid Code", JOptionPane.WARNING_MESSAGE);
            }
        }
    });
    buttonPanel.add(codeButton);
    
    // จัดเรียง Panel หลัก
    panel.add(centerWrap, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);
    
    // อัปเดตยอดรวมเมื่อสร้าง Panel เสร็จ
    updateTotals();

    return panel;
}

    // ฟังก์ชันช่วยในการสร้างปุ่มที่มีสไตล์
    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
    
    // เมทอดสำหรับเพิ่มสินค้าลงในตะกร้า
    private void addProductToCart(String productID) {
    
    DataModels.Product selectedProduct = null;
    if (productList != null) {
        for (DataModels.Product product : productList) {
            // ต้องเปรียบเทียบ SKU ด้วย equals
            if (product.sku().equals(productID)) {
                selectedProduct = product;
                break;
            }
        }
    }
    
    if (selectedProduct == null) {
        JOptionPane.showMessageDialog(this, "Product ID not found: " + productID, "Not Found", JOptionPane.WARNING_MESSAGE);
        return;
    }

        // ตรวจสอบสต็อก
    
    if (selectedProduct.stock() <= 0) {
        JOptionPane.showMessageDialog(this, 
            "สินค้าหมด! (Stock: 0) ไม่สามารถเพิ่มสินค้าลงในตะกร้าได้.", 
            "Out of Stock", 
            JOptionPane.WARNING_MESSAGE);
        return; // หยุดการทำงานถ้าสินค้าหมด
    }
    
    String productName = selectedProduct.name();
    double price = selectedProduct.price();
    // ดึงสต็อกปัจจุบันของสินค้าเพื่อใช้ตรวจสอบก่อนเพิ่ม (เผื่อมีการเรียกซ้ำ)
    int availableStock = selectedProduct.stock();
    
    int quantity = 1;
    double total = quantity * price;

    // ตรวจสอบว่าของในตะกร้าไม่ซ้ำกับที่มีอยู่
    boolean productFound = false;
    for (int i = 0; i < cartTableModel.getRowCount(); i++) {
        // ตรวจสอบจากชื่อสินค้าในตะกร้า
        if (cartTableModel.getValueAt(i, 0).equals(productName)) {
            
            // ... (โค้ดดึง currentQuantity เหมือนเดิม) ...
            int currentQuantity = 0;
            try {
                currentQuantity = (int) cartTableModel.getValueAt(i, 1);
            } catch (ClassCastException e) {
                Object qValue = cartTableModel.getValueAt(i, 1);
                if (qValue instanceof String) {
                    try {
                        currentQuantity = Integer.parseInt((String) qValue);
                    } catch (NumberFormatException ignored) {}
                }
            }
            
            // =======================================================
            // *** 2. เพิ่มโค้ดตรวจสอบสต็อกเมื่อมีการเพิ่มซ้ำ ***
            // =======================================================
            if (currentQuantity >= availableStock) {
                 JOptionPane.showMessageDialog(this, 
                    "เพิ่มสินค้าไม่ได้! จำนวนในตะกร้า (" + currentQuantity + ") เกินสต็อกที่มี (" + availableStock + ").", 
                    "Stock Limit Reached", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            // =======================================================
            
            double currentTotal = (double) cartTableModel.getValueAt(i, 2); 
            
            cartTableModel.setValueAt(currentQuantity + 1, i, 1);
            cartTableModel.setValueAt(currentTotal + price, i, 2);
            productFound = true;
            break;
        }
    }

    if (!productFound) {
        // ... (โค้ดเพิ่มแถวใหม่เหมือนเดิม) ...
        cartTableModel.addRow(new Object[]{productName, quantity, total});
    }
    
    // เมื่อเพิ่มสินค้าใหม่หรืออัปเดต ต้องรีเซ็ตส่วนลด
    currentDiscount = 0.0;
    
    updateTotals();
    searchField.setText(""); // ล้างช่องค้นหา
}

// เมทอดสำหรับอัปเดตยอดรวมทั้งหมด
private void updateTotals() {
    double subtotal = 0.0;
    
    // ตรวจสอบว่า cartTableModel ไม่ใช่ null ก่อนใช้งาน
    if (cartTableModel == null) {
        return;
    }

    for (int i = 0; i < cartTableModel.getRowCount(); i++) {
        // คอลัมน์ใน cartTableModel: 0=List, 1=Quantity, 2=Total
        
        try {
             subtotal += (double) cartTableModel.getValueAt(i, 2); 
        } catch (ClassCastException | NullPointerException e) {
             System.err.println("Error reading total amount from row " + i + ". Data might not be a double. Value: " + cartTableModel.getValueAt(i, 2));
             
             Object totalValue = cartTableModel.getValueAt(i, 2);
             if (totalValue instanceof String) {
                 try {
                     subtotal += Double.parseDouble((String) totalValue);
                 } catch (NumberFormatException ignored) {}
             }
        }
    }
    
    double discountApplied = currentDiscount;
    
    double effectiveSubtotal = subtotal - discountApplied;
    
    // ป้องกันยอดรวมติดลบ และปรับส่วนลดที่ใช้จริงให้เท่ากับ subtotal หากส่วนลดมากกว่า
    if (effectiveSubtotal < 0) {
        effectiveSubtotal = 0;
        discountApplied = subtotal; // ส่วนลดที่ใช้จริง
    }

    double taxRate = 0.07;
    double tax = effectiveSubtotal * taxRate;
    double total = effectiveSubtotal + tax;
    
    // อัปเดต Label
    subtotalLabel.setText(String.format("฿%.2f", subtotal));
    discountLabel.setText(String.format("-฿%.2f", discountApplied)); // แสดงส่วนลดที่ใช้จริง
    taxLabel.setText(String.format("฿%.2f", tax));
    totalLabel.setText(String.format("฿%.2f", total));
}

    // เมทอดสำหรับประมวลผลการชำระเงิน
    private void processPayment(String paymentMethod) {
        if (cartTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Shopping cart is empty. Please add products first.", "Payment Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ดึงยอดรวมสุดท้ายก่อนทำการชำระ
        String totalText = totalLabel.getText().replace("฿", "").replace(",", "");
        double totalAmount = 0.0;
        try {
            totalAmount = Double.parseDouble(totalText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error processing total amount. Please try again.", "Payment Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // ยืนยันการชำระเงิน
        int response = JOptionPane.showConfirmDialog(this,
                "Confirm payment of " + totalLabel.getText() + " with " + paymentMethod + "?",
                "Confirm Payment",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            
            // ลดสต็อกสินค้าใน productList 
            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                String productName = (String) cartTableModel.getValueAt(i, 0);
                int quantitySold = 0;
                try {
                    quantitySold = (int) cartTableModel.getValueAt(i, 1); 
                    this.saveproduct_sold(productName, quantitySold);
                } catch (Exception ex) {
                    Object qValue = cartTableModel.getValueAt(i, 1);
                    if (qValue instanceof String) {
                        try {
                            quantitySold = Integer.parseInt((String) qValue);
                        } catch (NumberFormatException ignored) {}
                    }
                
                }
                
                // ค้นหาสินค้าและลดสต็อก
                for (int j = 0; j < productList.size(); j++) {
                    DataModels.Product p = productList.get(j);
                    if (p.name().equals(productName)) {
                        
                        int newStock = p.stock() - quantitySold;
                        if (newStock < 0) newStock = 0; 
                        
                        // สร้าง Product ใหม่และอัปเดต List
                        productList.set(j, new DataModels.Product(p.sku(), p.name(), p.price(), newStock));
                        break;
                    }
                }
                
            }
            
            // บันทึกการเปลี่ยนแปลงสต็อกกลับไปที่ CSV
            saveProductsToCSV();
            
            // บันทึกรายการขาย (Transaction) ลงใน daily_report.csv
            saveSaleTransactionToCSV(totalAmount);

            JOptionPane.showMessageDialog(this, "Payment successful! The transaction has been completed, stock updated, and sale recorded.", "Payment Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear the cart and reset totals
            cartTableModel.setRowCount(0);
            currentDiscount = 0.0;
            updateTotals();
            
            // โหลดข้อมูลสินค้าใหม่ในตารางสินค้าเพื่อแสดงสต็อกที่อัปเดตแล้ว ***
            loadProductsFromCSV();
        }
    }
    // โหลดสินค้าจาก CSV และเติมลงในตาราง
    private void loadProductsFromCSV() {
        
        DataModels.ProductCSVReader reader = new DataModels.ProductCSVReader();
        productList = reader.readProductsFromCSV(); // อ่านข้อมูลทั้งหมด
        
        // เติมข้อมูลลงในตารางสินค้า
        productTableModel.setRowCount(0); // ล้างข้อมูลเก่า
        
        if (productList != null) {
            for (DataModels.Product product : productList) {
                // SKU, Name, Price
                // ใช้ String.format("฿%.2f", product.price()) เพื่อจัดรูปแบบราคาเป็นสกุลเงิน
                productTableModel.addRow(new Object[]{
                    product.sku(), 
                    product.name(), 
                    String.format("฿%.2f", product.price())
                });
            }
        }
    }

private void applyDiscountCode(String code) {
    // คำนวณ Subtotal ปัจจุบัน
    double subtotal = 0.0;
    for (int i = 0; i < cartTableModel.getRowCount(); i++) {
        // ดัชนี 2 คือ Total
        try {
            subtotal += (double) cartTableModel.getValueAt(i, 2); 
        } catch (ClassCastException e) {
             Object totalValue = cartTableModel.getValueAt(i, 2);
             if (totalValue instanceof String) {
                 try {
                     subtotal += Double.parseDouble((String) totalValue);
                 } catch (NumberFormatException ignored) {}
             }
        }
    }
    
    // รีเซ็ตส่วนลดก่อน
    double oldDiscount = currentDiscount;
    currentDiscount = 0.0; // สมมติว่าโค้ดใหม่จะล้างโค้ดเก่าเสมอ
    
    // คำนวณส่วนลดโดยใช้ PricingService
    double calculatedDiscount = pricingService.calDiscount(subtotal, code);

    if (calculatedDiscount > 0.0) {
        // โค้ดส่วนลดถูกต้อง
        currentDiscount = calculatedDiscount;
        
        // ตรวจสอบชนิดของส่วนลดเพื่อแสดงข้อความที่เหมาะสม (อิงตาม logic ของ PricingService)
        String discountMessage;
        if (code.equalsIgnoreCase("SALE20")) {
            discountMessage = " (20% off on subtotal)";
        } else if (code.equalsIgnoreCase("TENOFF")) {
            discountMessage = " (฿10.00 off)";
        } else {
            discountMessage = ""; // ใช้ในกรณีที่มีโค้ดอื่นๆ เพิ่มเติม
        }

        JOptionPane.showMessageDialog(this,
            "Discount code '" + code + "' applied successfully!" + discountMessage,
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
            
        updateTotals(); // อัปเดตยอดรวม

    } else {
        // โค้ดไม่ถูกต้อง (calDiscount คืนค่า 0.0)
        currentDiscount = oldDiscount; // คืนค่าส่วนลดเดิม
        JOptionPane.showMessageDialog(this, 
            "Discount code '" + code + "' is invalid or expired.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}

private void autoAddRow(JPanel parent, GridBagConstraints gbc, String title, Font titleFont, Font valueFont, Color valueColor, JLabel valueLabel) {
    gbc.gridy++;
    
    // Title (ชิดซ้าย)
    gbc.gridx = 0;
    gbc.weightx = 0.5;
    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(titleFont);
    titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
    parent.add(titleLabel, gbc);

    // Value (ชิดขวา)
    gbc.gridx = 1;
    gbc.weightx = 0.5;
    valueLabel.setFont(valueFont);
    valueLabel.setForeground(valueColor);
    valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    parent.add(valueLabel, gbc);
}

/**
     * เมทอดสำหรับบันทึกสินค้ากลับไปยัง CSV
     * ต้องมีการสร้างคลาส ProductCSVWriter ที่มีเมทอด writeProductsToCSV(List<Product>)
     */
    private void saveProductsToCSV() {
        if (productList != null) {
            try {
                ProductCSVWriter writer = new ProductCSVWriter(); 
                writer.writeAllProductsToCSV(productList);
            } catch (Exception e) {
                // จัดการข้อผิดพลาดในการบันทึกไฟล์
                JOptionPane.showMessageDialog(this, "Error saving product data to CSV: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    /**
     * เมทอดสำหรับบันทึกรายการขายใหม่ต่อท้ายไฟล์ daily_report.csv
     * รูปแบบที่บันทึกคือ: orderId,cost,date
     */
    private void saveSaleTransactionToCSV(double totalAmount) {
        String filePath = "./FileCSV/daily_report.csv";
        
        try (FileWriter fw = new FileWriter(filePath, true); // true คือโหมด append (เขียนต่อท้าย)
            PrintWriter pw = new PrintWriter(fw)) {
            // สร้าง Order ID และ Date
            String orderId = "O" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss"));
            String date = LocalDateTime.now().toLocalDate().toString();
            String line = String.format("%s,%.2f,%s", orderId, totalAmount, date);
            
            // เขียนลงไฟล์
            pw.println(line);
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error saving sale transaction to daily report CSV: " + e.getMessage(), 
                "Save Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void saveproduct_sold(String product_Name,int quantity){
        String CSV_FILE = "./FileCSV/Product_sold.csv"; 
        String CSV_HEADER = "OrderID,Date,Name,Quantity";
         File file = new File(CSV_FILE);
          FileWriter fw=null;
          BufferedWriter bw=null;
          boolean checkHeader=!file.exists()||file.length() == 0;
        try{
            fw=new FileWriter(file,true);
            bw=new BufferedWriter(fw);
            String orderID="O"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss"));
            String date=LocalDateTime.now().toLocalDate().toString();
            String line=String.format("%s,%s,%s,%d", orderID, date, product_Name, quantity);
            if(checkHeader){
                bw.write(CSV_HEADER);
                bw.newLine();
            }
            
            bw.write(line);
            bw.newLine();
            
        } catch (IOException e) {
            System.err.println("Error writing all products to CSV file: " + e.getMessage());
        }finally{
            try {
                bw.close();
                fw.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


}
