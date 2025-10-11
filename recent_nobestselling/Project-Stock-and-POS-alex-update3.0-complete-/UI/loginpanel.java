package UI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class loginpanel extends JFrame implements ActionListener{

    private JTextField txtUser;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    Container cp;
    public loginpanel() {
            initComponents();
    }

    private void initComponents() {
            cp=getContentPane();
            cp.setLayout(null);
            cp.setBackground(new Color(211, 222, 174));

        // ----------------- LOGO -----------------
            ImageIcon centerIcon = new ImageIcon("./picture/MRDRY.png");
            Image centerImg = centerIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            JLabel lblLogo = new JLabel(new ImageIcon(centerImg));
            lblLogo.setBounds(150, 20, 80, 80);
            cp.add(lblLogo);

        // ----------------- Panel ด้านใน ----------------------
            JPanel jPanel2 = new JPanel();
            jPanel2.setBackground(Color.WHITE);
            jPanel2.setLayout(null);
            jPanel2.setBounds(50, 120, 280, 160);  
            jPanel2.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
            cp.add(jPanel2);

        // ------------------ USERNAME ----------------------
            JLabel userIcon = new JLabel("👤");
            userIcon.setBounds(20, 40, 25, 25);
            jPanel2.add(userIcon);

            JLabel l1 = new JLabel("USERNAME");
            l1.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
            l1.setBounds(20, 10, 120, 20);
            jPanel2.add(l1);

        // -------------------- เคลียร์ USER  --------------------
            JLayeredPane userPane = new JLayeredPane();
            userPane.setBounds(50, 40, 200, 25);

            txtUser = new JTextField();
            txtUser.setBackground(new Color(245, 245, 245));
            txtUser.setBounds(0, 0, 200, 25);
            txtUser.setMargin(new Insets(0, 5, 0, 20)); // กันข้อความทับ ❌

            JLabel clearUser = new JLabel("❌", SwingConstants.CENTER);
            clearUser.setBounds(180, 0, 20, 25);
            clearUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtUser.setText("");
            }
            });

            userPane.add(txtUser, Integer.valueOf(0));
            userPane.add(clearUser, Integer.valueOf(1));
            jPanel2.add(userPane);

        // -------------------- PASSWORD ------------------------
            JLabel passIcon = new JLabel("🔒");
            passIcon.setBounds(20, 100, 25, 25);
            jPanel2.add(passIcon);

            JLabel l2 = new JLabel("PASSWORD");
            l2.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
            l2.setBounds(20, 70, 120, 20);
            jPanel2.add(l2);

        // -------------------- เคลียร์ PASSWORD  --------------------
            JLayeredPane passPane = new JLayeredPane();
            passPane.setBounds(50, 100, 200, 25);

            txtPassword = new JPasswordField();
            txtPassword.setBackground(new Color(245, 245, 245));
            txtPassword.setBounds(0, 0, 200, 25);
        
            JLabel clearPass = new JLabel("❌", SwingConstants.CENTER);
            clearPass.setBounds(180, 0, 20, 25);
            clearPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtPassword.setText("");
            }
        });

            passPane.add(txtPassword, Integer.valueOf(0));
            passPane.add(clearPass, Integer.valueOf(1));
            jPanel2.add(passPane);

        // -------------------- ปุ่ม Login ---------------------
        btnLogin = new JButton("LOGIN");
            btnLogin.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
            btnLogin.setBounds(140, 300, 100, 30);
            btnLogin.setBackground(new Color(242, 145, 129));
            btnLogin.setForeground(Color.WHITE);
            btnLogin.setFocusPainted(false);
            cp.add(btnLogin);
        

        // Event check
            btnLogin.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkLogin();  
            }
        });
         btnLogin.addActionListener(this);
        this.setSize(400,400);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean checkLogin() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPassword.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please input Username and Password",
                "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (user.equals("1") && pass.equals("123")) {
            JOptionPane.showMessageDialog(this, "Login successful !",
                "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
        } else {
            JOptionPane.showMessageDialog(this, "Username or Password is incorrect",
                "Error", JOptionPane.ERROR_MESSAGE);
                return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnLogin){
            if(checkLogin()){
                new dashboard();
                dispose();
            }
        }
    }
    
    
}

