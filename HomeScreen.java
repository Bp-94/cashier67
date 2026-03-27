import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeScreen implements MouseListener {

    private JFrame frame;
    private HomePanel panel;
    private JLabel btnStart, btnExit;
    private int startW, startH, exitW, exitH;

    public HomeScreen() {

        frame = new JFrame("Home");
        panel = new HomePanel();
        panel.setLayout(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        ImageIcon startIcon = new ImageIcon(getClass().getResource("/images/Startbtn.PNG"));
        Image startImg = startIcon.getImage().getScaledInstance(600, 500, Image.SCALE_SMOOTH);
        btnStart = new JLabel(new ImageIcon(startImg));
        btnStart.addMouseListener(this);
        btnStart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startW = 600;
        startH = 500;
        panel.add(btnStart);

        ImageIcon exitIcon = new ImageIcon(getClass().getResource("/images/Exitbtn.PNG"));
        Image exitImg = exitIcon.getImage().getScaledInstance(600, 500, Image.SCALE_SMOOTH);
        btnExit = new JLabel(new ImageIcon(exitImg));
        btnExit.addMouseListener(this);
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitW = 600;
        exitH = 500;
        panel.add(btnExit);

        panel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
        });

        frame.setContentPane(panel);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        resizeComponents();
    }

    private void resizeComponents() {
        int w = panel.getWidth();
        int h = panel.getHeight();

        startW = (int)(w * 0.18);
        startH = (int)(h * 0.13);
        exitW = startW;
        exitH = startH;

        // scale button
        Image startImg = new ImageIcon(getClass().getResource("/images/Startbtn.PNG"))
                .getImage().getScaledInstance(startW, startH, Image.SCALE_SMOOTH);
        btnStart.setIcon(new ImageIcon(startImg));

        Image exitImg = new ImageIcon(getClass().getResource("/images/Exitbtn.PNG"))
                .getImage().getScaledInstance(exitW, exitH, Image.SCALE_SMOOTH);
        btnExit.setIcon(new ImageIcon(exitImg));

        //center
        int centerX = (w - startW) / 2;
        //Start(under logo)
        int startY = (int)(h * 0.60);

        btnStart.setBounds(centerX, startY, startW, startH);
        btnExit.setBounds(centerX, startY + startH + 15, exitW, exitH);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == btnStart) {
            frame.dispose();
            new TutorialScreen();
        }
        else if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

    public static void main(String[] args) {
        new HomeScreen();
    }

}

class HomePanel extends JPanel {
    private Image bg;

    public HomePanel() {
        bg = new ImageIcon(getClass().getResource("/images/CashierApp.PNG")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
}
