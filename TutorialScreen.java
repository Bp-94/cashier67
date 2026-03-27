import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TutorialScreen implements MouseListener {

    private JFrame frame;
    private TutorialPanel tutorialPanel;
    private ImagePanel displayPanel;
    private int currentPage = 0;

    private String[] pages = {
            "/images/Letter_close.PNG",
            "/images/Letter_open.PNG",
            "/images/StoryPage.PNG",
            "/images/TutorialPage1.PNG",
            "/images/TutorialPage2.PNG",
            "/images/TutorialPage3.PNG",
            "/images/TutorialPage4.PNG",
            "/images/TutorialPage5.PNG",
            "/images/TutorialPage6.PNG",
            "/images/TutorialPage7.PNG",
            "/images/TutorialPage8.PNG",
            "/images/TutorialPage9.PNG"
    };

    public TutorialScreen() {
        frame = new JFrame("Tutorial");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        tutorialPanel = new TutorialPanel();
        tutorialPanel.setLayout(null);

        displayPanel = new ImagePanel(pages[currentPage]);
        displayPanel.addMouseListener(this);
        tutorialPanel.add(displayPanel);

        frame.setContentPane(tutorialPanel);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        tutorialPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                displayPanel.setBounds(0, 0, tutorialPanel.getWidth(), tutorialPanel.getHeight());
                tutorialPanel.repaint();
            }
        });

        updatePage();
    }

    private void updatePage() {
        displayPanel.setImage(pages[currentPage]);
        if (currentPage == 0 || currentPage == 1) {
            displayPanel.setScaleMultiplier(0.6);
        }
        else {
            displayPanel.setScaleMultiplier(0.9);
        }
        tutorialPanel.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() != displayPanel) return;

        Point p = e.getPoint();
        System.out.println("clicked: " + p + ", currentPage = " + currentPage);

        if (currentPage == 0) {
            if (displayPanel.isInsideLetterArea(p)) {
                System.out.println("go to letter_open");
                currentPage = 1;
                updatePage();
            }
            return;
        }
        if (currentPage == 1) {
            if (displayPanel.isInsideLetterArea(p)) {
                System.out.println("go to storyPage");
                currentPage = 2;
                updatePage();
            }
            return;
        }
        if (currentPage == 2) {
            if (displayPanel.isInsidePrevArea(p)) {
                System.out.println("story prev -> HomeScreen");
                frame.dispose();
                new HomeScreen();
            } else if (displayPanel.isInsideNextArea(p)) {
                System.out.println("story next -> tutorial1");
                currentPage = 3;
                updatePage();
            }
            return;
        }

        if (currentPage == pages.length - 1) {
            if (displayPanel.isInsidePrevArea(p)) {
                System.out.println("last tutorial prev");
                currentPage--;
                updatePage();
            } else if (displayPanel.isInsideStartArea(p)) {
                System.out.println("start game");
                frame.dispose();
                // new GameScreen();
            }
            return;
        }
        if (displayPanel.isInsidePrevArea(p)) {
            System.out.println("tutorial prev");
            currentPage--;
            updatePage();
        } else if (displayPanel.isInsideNextArea(p)) {
            System.out.println("tutorial next");
            currentPage++;
            updatePage();
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        new TutorialScreen();
    }
}
class TutorialPanel extends JPanel {
    private Image bg;

    public TutorialPanel() {
        bg = new ImageIcon(getClass().getResource("/images/BlurTutorialScreen.PNG")).getImage();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
}
class ImagePanel extends JPanel {
    private Image img;
    private int drawX, drawY, drawW, drawH;
    private double scaleMultiplier = 0.6;

    public ImagePanel(String path) {
        setImage(path);
        setOpaque(false);
    }

    public void setImage(String path) {
        img = new ImageIcon(getClass().getResource(path)).getImage();
        repaint();
    }

    public void setScaleMultiplier(double scaleMultiplier) {
        this.scaleMultiplier = scaleMultiplier;
        repaint();
    }

    private Rectangle getRelativeRect(double xPercent, double yPercent, double wPercent, double hPercent) {
        int x = drawX + (int) (drawW * xPercent);
        int y = drawY + (int) (drawH * yPercent);
        int w = (int) (drawW * wPercent);
        int h = (int) (drawH * hPercent);
        return new Rectangle(x, y, w, h);
    }

    //Letter Area
    public boolean isInsideLetterArea(Point p) {
        Rectangle area = getRelativeRect(0.011, 0.01, 0.98, 0.99);
        return area.contains(p);
    }

    //Prev Area
    public boolean isInsidePrevArea(Point p) {
        Rectangle area = getRelativeRect(0.09, 0.735, 0.10, 0.18);
        return area.contains(p);
    }

    //Next Area
    public boolean isInsideNextArea(Point p) {
        Rectangle area = getRelativeRect(0.80, 0.735, 0.105, 0.18);
        return area.contains(p);
    }

    //Start Area
    public boolean isInsideStartArea(Point p) {
        Rectangle area = getRelativeRect(0.80, 0.735, 0.105, 0.18);
        return area.contains(p);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (img == null) return;

        int panelW = getWidth();
        int panelH = getHeight();

        int imgW = img.getWidth(this);
        int imgH = img.getHeight(this);

        if (imgW <= 0 || imgH <= 0) return;

        double scale = Math.min((double) panelW / imgW, (double) panelH / imgH) * scaleMultiplier;

        drawW = (int) (imgW * scale);
        drawH = (int) (imgH * scale);
        drawX = (panelW - drawW) / 2;
        drawY = (panelH - drawH) / 2;

        g.drawImage(img, drawX, drawY, drawW, drawH, this);
    }
}