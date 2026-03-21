
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class guessGoods extends Minigame implements ActionListener {
    private JDialog dialog;
    private JLayeredPane layerP;
    private JPanel panel, choicePanel;
    private JButton[] btn;
    private JLabel bgImg, productImg;
    private int currentImgindex = 0;
    private int score = 0;

    private Map<String, String> gameData;
    private List<String> imgKeys;
    private String[][] choice;

    public guessGoods(){
        this.isFinish = false;
        this.isPass = false;

        gameData = new HashMap<String, String>();
        gameData.put("ImageMinigame/เงากล้วย.PNG", "Banana");
        gameData.put("ImageMinigame/เงาขนมช็อคโกแลต.PNG", "Chocolate");
        gameData.put("ImageMinigame/เงาขนมป้อกกี้.PNG", "ขนมป็อกกี้");
        gameData.put("ImageMinigame/เงาขนมเยลลี่.PNG", "Jelly");
        gameData.put("ImageMinigame/เงาขวดน้ำ.PNG", "Water");
        gameData.put("ImageMinigame/เงาไข่.PNG", "Egg");
        gameData.put("ImageMinigame/เงาฟิกเกอร์จารแบงค์.PNG", "ฟิกเกอร์อาจารย์เเบงค์");
        gameData.put("ImageMinigame/เงาส้ม.PNG", "Orange");
        gameData.put("ImageMinigame/เงาเหล้า.PNG", "Alcohol");
        gameData.put("ImageMinigame/เงาแอปเปิล.PNG", "Apple");

        imgKeys = new ArrayList<>(gameData.keySet());
        Collections.shuffle(imgKeys);

        createChoice();
    }
    @Override
    public void play(){
        dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setModal(true); //ล็อคหน้าต่างหลัก

        ImageIcon icon = new ImageIcon("ImageMinigame/หน้าต่างมินิเกม.png");
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();

        bgImg = new JLabel(icon);
        bgImg.setBounds(0, 0, w, h);

        int imgSize = 250;
        ImageIcon pImg = getScaledImgIcon(imgKeys.get(currentImgindex), imgSize, imgSize);
        productImg = new JLabel(pImg);

        choicePanel = new JPanel();
        choicePanel.setOpaque(false); //ให้ปุ่มไม่บังพท้นหลัง
        choicePanel.setLayout(new GridLayout(2,2, 7, 7)); //ระยะห่างปุ่ม 2row 2col

        btn = new JButton[4];
        for(int i = 0; i < 4; i++){
            btn[i] = new JButton();
            btn[i].addActionListener(this);
            choicePanel.add(btn[i]);
        }
        updateChoice();

        panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(0,20));
        panel.add(productImg, BorderLayout.CENTER);
        panel.add(choicePanel, BorderLayout.SOUTH);
        int panelW = (int)(w * 0.5);
        int panelH = (int)(h * 0.5);
        int panelX = (w - panelW) / 2;
        int panelY = (int)(h * 0.2); //จากบอบบน
        panel.setBounds(panelX, panelY, panelW, panelH); //ให้อยู่ในกรอบรูป

        layerP = new JLayeredPane();
        layerP.add(bgImg, JLayeredPane.DEFAULT_LAYER);
        layerP.add(panel, JLayeredPane.PALETTE_LAYER);

        dialog.setSize(w, h);
        dialog.setLocationRelativeTo(null);
        dialog.setBackground(new Color(0,0,0,0));
        dialog.add(layerP);
        dialog.setVisible(true);
    }

    private ImageIcon getScaledImgIcon(String path, int w, int h){
        ImageIcon icon = new ImageIcon(path);

        if(icon.getIconWidth() <= 0){
            return icon;
        }
        Image img = icon.getImage();
        Image scalesImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scalesImg);
    }

    private void createChoice(){
        choice = new String[imgKeys.size()][4];
        String [] allAnswer = {"Banana", "Chocolate", "ขนมป็อกกี้", "Jelly", "Water", "Egg",
                    "ฟิกเกอร์อาจารย์แบงค์", "Orange", "Aocohol", "Apple"};

        for(int i = 0; i < imgKeys.size(); i++){
            String correctAns = gameData.get(imgKeys.get(i));
            List<String> option = new ArrayList<>();
            option.add(correctAns);

            List<String> wrongAns = new ArrayList<>(Arrays.asList(allAnswer));
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
            btn[i].setText(choice[currentImgindex][i]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < btn.length; i++){
            if(e.getSource() == btn[i]){
                String selectAns = btn[i].getText();
                String correctAns = gameData.get(imgKeys.get(currentImgindex));

                if(selectAns.equals(correctAns)){
                    score++;
                }
                checkWincondition();
                break;
            //     if(score >= 5){
            //         this.isPass = true;
            //         this.isFinish = true;
            //         dialog.dispose();
            //     }else{
            //         currentImgindex++;

            //         if(currentImgindex >= imgKeys.size()){
            //             currentImgindex = 0;
            //             Collections.shuffle(imgKeys);
            //             createChoice();
            //         }
            //         int imgSize = 250;
            //         ImageIcon pImg = getScaledImgIcon(imgKeys.get(currentImgindex), imgSize, imgSize);
            //         productImg.setIcon(pImg);
            //         updateChoice();
            //     }
            // }
            }
        }
    }

    @Override
    public boolean checkWincondition() {
        if(score >= 5){
            this.isPass = true;
            this.isFinish = true;
            dialog.dispose();
        }else{
            currentImgindex++;

            if(currentImgindex >= imgKeys.size()){
                currentImgindex = 0;
                Collections.shuffle(imgKeys);
                createChoice();
            }
            int imgSize = 250;
            ImageIcon pImg = getScaledImgIcon(imgKeys.get(currentImgindex), imgSize, imgSize);
            productImg.setIcon(pImg);
            updateChoice();
        }
        return isPass;
    }

    public static void main(String[] args) {
        guessGoods g = new guessGoods();
        g.play();
    }
}