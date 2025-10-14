package UI;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.*;
public class mainframe extends JFrame{
    JFrame frame;
    CardLayout card;
    JPanel panel;
    inventory inven;
    stockAll stockall;
    stockLow stocklow;
    stockOut stockout;
    dashboard dash;
    private final JPanel sidemenu = new JPanel(null);
    public mainframe(){
    setTitle("MR.DRY");
    card=new CardLayout();
    panel=new JPanel(card);
    panel.setBackground(new Color(216,191,216));
    dash=new dashboard(this);
    inven=new inventory(this);
    stockall=new stockAll(this);
    stocklow=new stockLow(this);
    stockout=new stockOut(this);

    panel.add(dash,"dashboard");
    panel.add(inven,"inventory");
    panel.add(stockall,"all");
    panel.add(stocklow,"low");
    panel.add(stockout,"out");
    
    JComponent glass = (JComponent) getGlassPane();
        glass.setLayout(null);
        glass.setOpaque(false);
        glass.setVisible(false);

    setContentPane(panel);
    showcard("dashboard");
    setSize(550,650);
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void showcard(String key){
     card.show(panel,key);
    }
    public void showside(JPanel menu){
        JComponent glass = (JComponent) getGlassPane();
        glass.removeAll();
        sidemenu.removeAll();

        // วางเมนูชิดซ้าย กว้าง 200 สูงเท่าหน้าต่าง
        int w = 200, h = getHeight();
        menu.setBounds(0, 0, w, h);

        sidemenu.setBounds(0, 0, getWidth(), getHeight());
        sidemenu.setOpaque(false);
        sidemenu.add(menu);

        glass.add(sidemenu);
        glass.setVisible(true);
        glass.revalidate();
        glass.repaint();    
    }
    public void hideside(){
        JComponent glass = (JComponent) getGlassPane();
        glass.setVisible(false);
        glass.removeAll();
        glass.revalidate();
        glass.repaint();
    }
    public static void main(String[] args) {
     new loginpanel();
 }
}
