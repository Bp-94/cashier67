import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeScreen implements MouseListener {

    private JFrame frame;
    private HomePanel panel;
    private JLabel btnStart, btnExit;
    private int startW, startH, exitW, exitH;
    private Image oriStartImg;
    private Image oriExitImg;

    public HomeScreen() {

        frame = new JFrame("Home");
        panel = new HomePanel();
        panel.setLayout(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        oriStartImg = new ImageIcon(getClass().getResource("/images/Startbtn.PNG")).getImage();
        oriExitImg = new ImageIcon(getClass().getResource("/images/Exitbtn.PNG")).getImage();

        btnStart = new JLabel(new ImageIcon(oriStartImg));
        btnStart.addMouseListener(this);
        btnStart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnStart);

        btnExit = new JLabel(new ImageIcon(oriExitImg));
        btnExit.addMouseListener(this);
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

    public void resizeComponents() {
        int w = panel.getWidth();
        int h = panel.getHeight();

        startW = (int)(w * 0.18);
        startH = (int)(h * 0.16);
        exitW = startW;
        exitH = startH;

        // scale button
        Image startImg = oriStartImg.getScaledInstance(startW, startH, Image.SCALE_SMOOTH);
        btnStart.setIcon(new ImageIcon(startImg));

        Image exitImg = oriExitImg.getScaledInstance(exitW, exitH, Image.SCALE_SMOOTH);
        btnExit.setIcon(new ImageIcon(exitImg));

        //center
        int centerX = (w - startW) / 2;
        //Start(under logo)
        int startY = (int)(h * 0.60);

        btnStart.setBounds(centerX, startY, startW, startH);
        btnExit.setBounds(centerX, startY + startH + 13, exitW, exitH);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == btnStart) {
            frame.dispose();
            new TutorialScreen();
        }
        else if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
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