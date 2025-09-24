import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class Jflame_dashboard_order extends JFrame {

    private DefaultTableModel cartTableModel;
    private JLabel subtotalLabel;
    private JLabel taxLabel;
    private JLabel totalLabel;
    private JTextField searchField; // ทำให้ searchField เป็นตัวแปรของคลาส

    public Jflame_dashboard_order(){

        setTitle("ระบบ POS ");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // จัดให้อยู่กึ่งกลางหน้าจอ
        setIconImage(new ImageIcon("MRDRY_logo.png").getImage());
        

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


    // สร้างส่วนค้นหาสินค้าทางซ้ายสุด
     private JPanel createProductPanel(Color panelColor, Color accentColor) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(panelColor);
        // เพิ่มเงาและขอบที่ดูสะอาดตา
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Product Information", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Tahoma", Font.BOLD, 14), 
                Color.DARK_GRAY)));
        
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBackground(panelColor);
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // เพิ่มช่องว่างด้านใน
        
        searchField = new JTextField(); // เปลี่ยนเป็น JTextField
        searchField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        searchPanel.add(new JLabel("Search Product or Scan Barcode"), BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);
        
        JPanel keypadPanel = new JPanel(new GridLayout(4, 3, 10, 10)); // เพิ่มระยะห่างระหว่างปุ่ม
        keypadPanel.setBackground(panelColor);

        // สร้าง loop ตัวเลขปุ่มกด
        for (int i = 1; i <= 9; i++) {
            JButton numButton = createStyledButton(String.valueOf(i), accentColor, Color.WHITE);
            // ActionListener สำหรับปุ่มตัวเลข
            numButton.addActionListener(e -> {
                String digit = e.getActionCommand();
                searchField.setText(searchField.getText() + digit); // เพิ่มตัวเลขไปในช่องค้นหา
            });
            keypadPanel.add(numButton);
        }
        
        JButton manualEnterButton = createStyledButton("Manual Enter", accentColor, Color.WHITE);
        manualEnterButton.addActionListener(e -> {
            String productID = JOptionPane.showInputDialog(this, "Enter Product ID:");
            if (productID != null && !productID.isEmpty()) {
                addProductToCart(productID);
            }
        });
        keypadPanel.add(manualEnterButton);
        
        JButton zeroButton = createStyledButton("0", accentColor, Color.WHITE);
        zeroButton.addActionListener(e -> {
            String digit = e.getActionCommand();
            searchField.setText(searchField.getText() + digit); // เพิ่มตัวเลข 0 ไปในช่องค้นหา
        });
        keypadPanel.add(zeroButton);
        
        JButton dashboardButton = createStyledButton("Delete Product", new Color(220, 53, 69), Color.WHITE);
        dashboardButton.addActionListener(e -> {
            // Implement logic for navigating to the dashboard
            JOptionPane.showMessageDialog(null, "Delete Successful.");
        });
        keypadPanel.add(dashboardButton);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(keypadPanel, BorderLayout.CENTER);
    
        
        
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
        cartTableModel = new DefaultTableModel(columnNames, 0);
        JTable cartTable = new JTable(cartTableModel);
        
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
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(panelColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Total Price", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                new Font("Tahoma", Font.BOLD, 14), 
                Color.DARK_GRAY)));

        JPanel summaryPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        summaryPanel.setBackground(panelColor);
        summaryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        summaryPanel.add(createStyledLabel("Subtotal:"));
        subtotalLabel = createStyledValueLabel("฿0.00");
        summaryPanel.add(subtotalLabel);
        
        summaryPanel.add(createStyledLabel("Tax:"));
        taxLabel = createStyledValueLabel("฿0.00");
        summaryPanel.add(taxLabel);
        
        summaryPanel.add(createStyledLabel("Total:", true));
        totalLabel = createStyledValueLabel("฿0.00", true);
        totalLabel.setFont(new Font("Tahoma", Font.BOLD, 24)); // ขยายขนาดตัวอักษรของราคารวม
        totalLabel.setForeground(accentColor); // ใช้สีเน้นสำหรับราคารวม
        summaryPanel.add(totalLabel);
        
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        buttonPanel.setBackground(panelColor);
        buttonPanel.setBorder(new EmptyBorder(0, 10, 10, 10)); // เพิ่มช่องว่าง
        
        JButton cashButton = createStyledButton("Cash", accentColor, Color.WHITE);
        cashButton.addActionListener(e -> processPayment("Cash"));
        buttonPanel.add(cashButton);

        JButton codeButton = createStyledButton("Use Code", accentColor, Color.WHITE);
        codeButton.addActionListener(e -> processPayment("Use Code"));
        buttonPanel.add(codeButton);
        
        
        panel.add(summaryPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
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

    // ฟังก์ชันช่วยในการสร้าง JLabel ที่มีสไตล์
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 14));
        return label;
    }
    
    // ฟังก์ชันช่วยในการสร้าง JLabel สำหรับแสดงค่า
    private JLabel createStyledValueLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.RIGHT);
        label.setFont(new Font("Tahoma", Font.BOLD, 14));
        return label;
    }
    
    private JLabel createStyledLabel(String text, boolean bold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", bold ? Font.BOLD : Font.PLAIN, 16));
        return label;
    }
    
    private JLabel createStyledValueLabel(String text, boolean bold) {
        JLabel label = new JLabel(text, SwingConstants.RIGHT);
        label.setFont(new Font("Tahoma", bold ? Font.BOLD : Font.PLAIN, 16));
        return label;
    }
    
    // เมทอดสำหรับเพิ่มสินค้าลงในตะกร้า
    private void addProductToCart(String productID) {
        // This is a placeholder. In a real application, you would fetch product details from a database.
        String productName = "Product " + productID;
        int quantity = 1;
        double price = 100.00; // Example price
        double total = quantity * price;

        // Check if the product already exists in the cart to update quantity instead of adding a new row
        boolean productFound = false;
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            if (cartTableModel.getValueAt(i, 0).equals(productName)) {
                int currentQuantity = (int) cartTableModel.getValueAt(i, 1);
                double currentTotal = (double) cartTableModel.getValueAt(i, 2);
                cartTableModel.setValueAt(currentQuantity + 1, i, 1);
                cartTableModel.setValueAt(currentTotal + price, i, 2);
                productFound = true;
                break;
            }
        }

        if (!productFound) {
            cartTableModel.addRow(new Object[]{productName, quantity, total});
        }
        
        updateTotals();
    }

    // เมทอดสำหรับอัปเดตยอดรวมทั้งหมด
    private void updateTotals() {
        double subtotal = 0.0;
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            subtotal += (double) cartTableModel.getValueAt(i, 2);
        }
        
        // Tax is a placeholder, assuming a fixed percentage
        double taxRate = 0.07; // Example tax rate
        double tax = subtotal * taxRate;
        double total = subtotal + tax;
        
        subtotalLabel.setText(String.format("฿%.2f", subtotal));
        taxLabel.setText(String.format("฿%.2f", tax));
        totalLabel.setText(String.format("฿%.2f", total));
    }

    // เมทอดสำหรับประมวลผลการชำระเงิน
    private void processPayment(String paymentMethod) {
        if (cartTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Shopping cart is empty. Please add products first.", "Payment Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Display a confirmation dialog and reset the cart upon successful payment
        int response = JOptionPane.showConfirmDialog(this,
                "Confirm payment of " + totalLabel.getText() + " with " + paymentMethod + "?",
                "Confirm Payment",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Payment successful! The transaction has been completed.", "Payment Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear the cart and reset totals
            cartTableModel.setRowCount(0);
            updateTotals();
        }
    }


}