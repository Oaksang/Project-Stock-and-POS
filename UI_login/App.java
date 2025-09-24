import javax.swing.ImageIcon;
import javax.swing.JFrame;
import Lib.loginpanel;

public class App {
    public static void main(String[] args) {

        JFrame frame = new JFrame("MR.DRY");
        ImageIcon icon = new ImageIcon(App.class.getResource("/Pic/MRDRY_logo.png"));
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginpanel panel = new loginpanel();
        frame.setContentPane(panel);
        frame.setSize(400, 400); 
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}



