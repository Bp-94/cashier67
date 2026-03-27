import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class guessBill extends Minigame {

    private JDialog dialog;
    private JLayeredPane layerP;
    private JTextField inputField;
    private JButton greenButton;
    private JLabel billImgLabel;
    private Game game;
    private Map<String, String> gameData;
    private List<String> keys;
    private String currentAnswer;
    private String selectedImg;

    private static final int W = 1280;
    private static final int H = 720;

    // constructor
    public guessBill(Game game) {
        this.game = game;
        // รูป -> คำตอบที่ถูกต้อง
        gameData = new HashMap<>();
        gameData.put("ImageMinigame/1.PNG", "672894");
        gameData.put("ImageMinigame/2.PNG", "003825");
        gameData.put("ImageMinigame/3.PNG", "146729");
        gameData.put("ImageMinigame/4.PNG", "981436");

        // สุ่มรูป
        keys = new ArrayList<>(gameData.keySet());
        Collections.shuffle(keys);
        selectedImg = keys.get(0);
        currentAnswer = gameData.get(selectedImg);
    }

    // play()
    @Override
    public void play() {
        this.isPass = false;

        dialog = new JDialog(game , true);
        dialog.setUndecorated(true);
        dialog.setModal(true);
        dialog.setBackground(new Color(0, 0, 0, 0));

        setupUI();

        dialog.add(layerP);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    // UI Setup
    private void setupUI() {
        Image bgImg;
        JLabel bgLabel;
        Image billImg;
        javax.swing.Timer timer;
        java.net.URL imgURL;

        // Background
        bgImg = new ImageIcon(getClass().getResource("ImageMinigame/IMG_4199.PNG")).getImage().getScaledInstance(W, H, Image.SCALE_SMOOTH);
        bgLabel = new JLabel(new ImageIcon(bgImg));
        bgLabel.setBounds(0, 0, W, H);

        // รูปบิล
        billImgLabel = new JLabel();
        imgURL = getClass().getResource("/" + selectedImg);
        if (imgURL != null) {
            billImg = new ImageIcon(imgURL).getImage().getScaledInstance(430, 430, Image.SCALE_SMOOTH);
            billImgLabel.setIcon(new ImageIcon(billImg));
        }
        billImgLabel.setBounds((W - 430) / 2, 170, 430, 430);

        // ซ่อนรูปหลัง 2 วินาที
        timer = new javax.swing.Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                billImgLabel.setIcon(null);
            }
        });
        timer.setRepeats(false);
        timer.start();

        // ช่องพิมพ์คำตอบ
        inputField = new JTextField();
        inputField.setFont(new Font("Tahoma", Font.BOLD, 30));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setOpaque(false);
        inputField.setBorder(null);
        inputField.setBounds(540, 560, 200, 60);
        inputField.addActionListener(e -> {
            this.isPass = checkInput();
            dialog.dispose();
        });

        // ปุ่มใสทับปุ่มสีเขียว
        greenButton = new JButton();
        greenButton.setContentAreaFilled(false);
        greenButton.setBorderPainted(false);
        greenButton.setFocusPainted(false);
        greenButton.setBounds(770, 555, 70, 70);
        greenButton.addActionListener(e -> {
            this.isPass = checkInput();
            dialog.dispose();
        });

        layerP = new JLayeredPane();
        layerP.setPreferredSize(new Dimension(W, H));

        layerP.add(bgLabel, JLayeredPane.DEFAULT_LAYER); // พื้นหลัง
        layerP.add(billImgLabel, JLayeredPane.PALETTE_LAYER); //รูปบิล
        layerP.add(inputField, JLayeredPane.MODAL_LAYER);   // กล่องคำตอบ
        layerP.add(greenButton, JLayeredPane.POPUP_LAYER);   //ปุ่มส่ง
    }


    public boolean checkInput() {
        if (currentAnswer == null || currentAnswer.isEmpty()) {
        return false; 
    }
        return inputField.getText().trim().equals(currentAnswer);
    }

    //public static void main(String[] args) throws Exception {
    //    guessBill g = new guessBill();
    //    SwingUtilities.invokeAndWait(() -> g.play());
    //    System.out.println("ผ่าน: " + g.getPass());
    //}
    // public static void main(String[] args) throws Exception {
    //     guessBill g = new guessBill();
    //     SwingUtilities.invokeAndWait(() -> g.play());
    //     System.out.println("ผ่าน: " + g.getPass());
    // }
}