import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class guessPrice extends Minigame implements ActionListener {
    private JDialog dialog;
    private JLayeredPane layerP;
    private JPanel choicePanel;
    private JButton[] btn;
    private JLabel bgImg;
    private DrawPanel canvas;

    private int currentImgindex = 0;
    private int score = 0;
    private final int goods_size = 200;

    private final Map<String, String> gameData;
    private final List<String> imgKeys;
    private String[][] choice;

    private Game game;

    public guessPrice(Game game){
        this.game = game;
        this.setPass(false);

        gameData = new HashMap<>();
        gameData.put("ImageMinigame/กล้วย.PNG", "ImageMinigame/ช้อยเลข44.PNG");
        gameData.put("ImageMinigame/ขนมช็อคโกแลต.PNG", "ImageMinigame/ช้อยเลข20.PNG");
        gameData.put("ImageMinigame/ขนมป้อกกี้.PNG", "ImageMinigame/ช้อยเลข25.PNG");
        gameData.put("ImageMinigame/ขนมเยลลี่.PNG", "ImageMinigame/ช้อยเลข35.PNG");
        gameData.put("ImageMinigame/น้ำเปล่า.PNG", "ImageMinigame/ช้อยเลข50.PNG");
        gameData.put("ImageMinigame/ไข่.PNG", "ImageMinigame/ช้อยเลข60.PNG");
        gameData.put("ImageMinigame/ฟิกเกอร์จารแบงค์.PNG", "ImageMinigame/ช้อยเลข670.PNG");
        gameData.put("ImageMinigame/ส้ม.PNG", "ImageMinigame/ช้อยเลข46.PNG");
        gameData.put("ImageMinigame/เหล้า.PNG", "ImageMinigame/ช้อยเลข120.PNG");
        gameData.put("ImageMinigame/แอปเปิล.PNG", "ImageMinigame/ช้อยเลข49.PNG");

        imgKeys = new ArrayList<>(gameData.keySet());
        Collections.shuffle(imgKeys);

        createChoice();
    }

    private class DrawPanel extends JPanel {
        public DrawPanel(){
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            java.net.URL titleURL = getClass().getResource("ImageMinigame/ป้ายหัวข้อ1.PNG");
            if (titleURL != null) {
                Image tImage = new ImageIcon(titleURL).getImage();
                g2.drawImage(tImage, (getWidth() - 250) / 2, 100, 250, 120, this);
            }
            if (!imgKeys.isEmpty()) {
                String currentPath = imgKeys.get(currentImgindex);
                java.net.URL productURL = getClass().getResource("/" + currentPath);
            
                if (productURL != null) {
                    Image pImg = new ImageIcon(productURL).getImage();
                    g2.drawImage(pImg, (getWidth() - goods_size) / 2, 210, goods_size, goods_size, this);
                }
            }
        }
    }

    @Override
    public void play(){
        dialog = new JDialog(game , true);
        dialog.setUndecorated(true);
        dialog.setModal(true);

        ImageIcon icon = new ImageIcon(getClass().getResource("ImageMinigame/หน้าต่างมินิเกม.png"));
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();

        bgImg = new JLabel(icon);
        bgImg.setBounds(0, 0, w, h);

        canvas = new DrawPanel();
        canvas.setBounds(0, 0, w, h);

        choicePanel = new JPanel();
        choicePanel.setLayout(null);
        choicePanel.setOpaque(false);
        choicePanel.setBounds(0, 0, w, h);

        btn = new JButton[4];

        int btnW = 200;
        int btnH = 70;
        int gap = 30;
        int startX = (w - (btnW * 2 + gap)) / 2;
        int startY = 430;

        for(int i = 0; i < 4; i++){
            btn[i] = new JButton();
            btn[i].setContentAreaFilled(false);
            btn[i].setBorderPainted(false);
            btn[i].setFocusPainted(false);
            btn[i].setOpaque(false);
            
            int row = i / 2, col = i % 2;
            btn[i].setBounds(startX + col * (btnW + gap), startY + row * (btnH + gap), btnW, btnH);
            btn[i].addActionListener(this);
            choicePanel.add(btn[i]);
        }
        updateChoice();

        layerP = new JLayeredPane();
        layerP.setPreferredSize(new Dimension(w, h));

        layerP.add(bgImg, JLayeredPane.DEFAULT_LAYER); //ชั้น0
        layerP.add(canvas, JLayeredPane.PALETTE_LAYER); //ชั้น1
        layerP.add(choicePanel, JLayeredPane.MODAL_LAYER);

        dialog.add(layerP);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setBackground(new Color(0,0,0,0));
        dialog.setVisible(true);
    }

    private ImageIcon getScaledImgIcon(String path, int w, int h){
        java.net.URL imgURL = getClass().getResource("/" + path);
        if (imgURL != null) {
            Image img = new ImageIcon(imgURL).getImage();
            return new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH));
        }
        return null;
    }

    private void createChoice(){
        choice = new String[imgKeys.size()][4];
        List<String> allAns = new ArrayList<>(gameData.values());

        for(int i = 0; i < imgKeys.size(); i++){
            String correctAns = gameData.get(imgKeys.get(i));
            List<String> option = new ArrayList<>();
            option.add(correctAns);

            List<String> wrongAns = new ArrayList<>(allAns);
            wrongAns.remove(correctAns);
            Collections.shuffle(wrongAns);

            option.add(wrongAns.get(0));
            option.add(wrongAns.get(1));
            option.add(wrongAns.get(2));

            Collections.shuffle(option);
            choice[i] = option.toArray(new String[0]);
        }
    }

    private void updateChoice(){
        for(int i = 0; i < 4; i++){
            String path = choice[currentImgindex][i];
            btn[i].setIcon(getScaledImgIcon(path, 200, 60));
            btn[i].setActionCommand(path);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedAns = e.getActionCommand();
        String correctAns = gameData.get(imgKeys.get(currentImgindex));

        if(selectedAns.equals(correctAns)){
            score++;

            if(score >= 3){
                this.setPass(true);
                dialog.dispose();
            } else {
                currentImgindex++;

                if(currentImgindex >= imgKeys.size()){
                    currentImgindex = 0;
                    Collections.shuffle(imgKeys);
                    createChoice();
                }
                updateChoice();
                canvas.repaint();
            }
        }else{
            dialog.dispose();
        }
    }


    // public static void main(String[] args) {
    //     guessPrice g = new guessPrice();
    //     g.play();
    // }
}