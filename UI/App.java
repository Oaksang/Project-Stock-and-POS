package UI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class App {
    loginpanel panel;
    public App() {

        JFrame frame = new JFrame("MR.DRY");
        ImageIcon icon = new ImageIcon(App.class.getResource("/Pic/MRDRY_logo.png"));
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



