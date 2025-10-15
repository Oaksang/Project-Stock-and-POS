package UI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class App {
    loginpanel panel;
    public App() {

        JFrame frame = new JFrame("MR.DRY");
        ImageIcon centerIcon = new ImageIcon(App.class.getResource("./picture/mr_DRY_logo_ver2.png"));
        ImageIcon icon = new ImageIcon("./picture/mr_DRY_logo_ver2.png");
        frame.setIconImage(icon.getImage()); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new loginpanel();
        frame.setContentPane(panel);
        
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}