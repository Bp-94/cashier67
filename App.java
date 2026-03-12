import java.awt.*;
import javax.swing.*;

public class App extends JPanel {
    Image bg = new ImageIcon("bg.jpg").getImage();
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("My Game");
        frame.setSize(1920, 1080);

     
        JLabel image;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        App panel = new App();
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(panel);
        frame.setVisible(true);
    }
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
}
}