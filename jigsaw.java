
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class jigsaw extends Minigame {

    private JDialog dialog;
    private JLayeredPane layerP;
    private JLabel[] pieces;
    private JLabel[] slots;
    private int[] pieceIndex;
    private int[] slotPiece;
    private String selectedPuzzle;

    private static final int W = 1280;
    private static final int H = 720;
    private static final int COLS = 3;
    private static final int ROWS = 3;
    private static final int PIECE_SIZE = 80;
    private static final int SNAP_DISTANCE = 10;

    // ปรับตำแหน่ง
    private static final int SLOT_START_X = 375;
    private static final int SLOT_START_Y = 275;
    private static final int PIECE_START_X = 650;
    private static final int PIECE_START_Y = 275;

    //Constructor
    public jigsaw() {
        String[] puzzles = {"ImageMinigame/IMG_4216.PNG", "ImageMinigame/IMG_4217.PNG", "ImageMinigame/IMG_4218.PNG", "ImageMinigame/IMG_4219.PNG"};
        selectedPuzzle = puzzles[new Random().nextInt(puzzles.length)]; // สุ่มรูป
    }

    @Override
    public void play() {
        this.isPass = false;

        dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setModal(true);
        dialog.setBackground(new Color(0, 0, 0, 0));

        setupUI();

        dialog.add(layerP);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void setupUI() {
        Image bgImg;
        JLabel bgLabel;
        BufferedImage fullImage;
        BufferedImage[] pieceImages;
        JButton submitBtn;

        //Background 
        bgImg = new ImageIcon(getClass().getResource("ImageMinigame/IMG_4221.PNG"))
                .getImage().getScaledInstance(W, H, Image.SCALE_SMOOTH);
        bgLabel = new JLabel(new ImageIcon(bgImg));
        bgLabel.setBounds(0, 0, W, H);

        // ตัดรูปjigsaw
        fullImage = loadPlaceholderImage();
        pieceImages = cutImage(fullImage);

        //init slotPiece
        slotPiece = new int[ROWS * COLS];
        Arrays.fill(slotPiece, -1);

        //setup ช่องวาง
        slots = new JLabel[ROWS * COLS];
        for (int i = 0; i < slots.length; i++) {
            int row = i / COLS, col = i % COLS;
            slots[i] = new JLabel();
            slots[i].setBorder(BorderFactory.createLineBorder(new Color(200, 200, 255), 2));
            slots[i].setBackground(new Color(80, 80, 150, 160));
            slots[i].setOpaque(true);
            slots[i].setBounds(
                    SLOT_START_X + col * PIECE_SIZE,
                    SLOT_START_Y + row * PIECE_SIZE,
                    PIECE_SIZE, PIECE_SIZE
            );
        }

        //สร้างPieces แล้วสุ่มลำดับชิ้น 
        pieces = new JLabel[ROWS * COLS];
        pieceIndex = new int[ROWS * COLS];

        List<Integer> shuffled = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            shuffled.add(i);
        }
        Collections.shuffle(shuffled);

        for (int i = 0; i < pieces.length; i++) {
            int row = i / COLS, col = i % COLS;
            pieceIndex[i] = shuffled.get(i);
            pieces[i] = new JLabel(new ImageIcon(pieceImages[pieceIndex[i]]));
            pieces[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            pieces[i].setBounds(
                    PIECE_START_X + col * PIECE_SIZE,
                    PIECE_START_Y + row * PIECE_SIZE,
                    PIECE_SIZE, PIECE_SIZE
            );
            addDragListener(pieces[i], i);
        }

        //Submit Button
        submitBtn = new JButton();
        submitBtn.setContentAreaFilled(false);
        submitBtn.setBorderPainted(false);
        submitBtn.setFocusPainted(false);
        submitBtn.setBounds(514, 559, 242, 64);
        submitBtn.addActionListener(e -> checkWin());

        //LayeredPane
        layerP = new JLayeredPane();
        layerP.setPreferredSize(new Dimension(W, H));

        layerP.add(bgLabel, JLayeredPane.DEFAULT_LAYER);

        for (JLabel slot : slots) {
            layerP.add(slot, JLayeredPane.PALETTE_LAYER);
        }
        for (JLabel piece : pieces) {
            layerP.add(piece, JLayeredPane.MODAL_LAYER);
        }
        layerP.add(submitBtn, JLayeredPane.POPUP_LAYER); // ← หลัง new layerP เสมอ
    }

    // Drag & Snap
    private void addDragListener(JLabel piece, int pieceIdx) {
        int[] off = new int[2];
        int[] lastHighlightedSlot = new int[]{-1};

        piece.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                off[0] = e.getX();
                off[1] = e.getY();
                layerP.setLayer(piece, JLayeredPane.DRAG_LAYER);

                // ปล่อย slot ที่เคยอยู่ เมื่อหยิบขึ้นมา
                for (int s = 0; s < slots.length; s++) {
                    if (slotPiece[s] == pieceIdx) {
                        slotPiece[s] = -1;
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Reset highlight
                if (lastHighlightedSlot[0] != -1) {
                    slots[lastHighlightedSlot[0]].setBorder(
                            BorderFactory.createLineBorder(new Color(200, 200, 255), 2));
                    lastHighlightedSlot[0] = -1;
                }
                trySnapToNearest(piece, pieceIdx); // snap ไป slot ว่างที่ใกล้สุด
                layerP.setLayer(piece, JLayeredPane.MODAL_LAYER);
            }
        });

        piece.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int newX = piece.getX() + e.getX() - off[0];
                int newY = piece.getY() + e.getY() - off[1];
                piece.setLocation(newX, newY);

                // Highlight slot วางที่ใกล้สุด (เปลี่ยนเฉพาะตอน state เปลี่ยน)
                int nearest = findNearestFreeSlot(newX, newY);
                if (nearest != lastHighlightedSlot[0]) {
                    if (lastHighlightedSlot[0] != -1) {
                        slots[lastHighlightedSlot[0]].setBorder(
                                BorderFactory.createLineBorder(new Color(200, 200, 255), 2));
                    }
                    if (nearest != -1) {
                        slots[nearest].setBorder(
                                BorderFactory.createLineBorder(Color.YELLOW, 3));
                    }
                    lastHighlightedSlot[0] = nearest;
                }
            }
        });
    }

    //หาตำแหน่งช่องว่างที่ใกล้ที่สุด
    private int findNearestFreeSlot(int x, int y) {
        int nearest = -1, minDist = Integer.MAX_VALUE;
        for (int s = 0; s < slots.length; s++) {
            if (slotPiece[s] != -1) {
                continue; // ข้าม slot ที่มีอยู่แล้ว
            }
            int dx = Math.abs(x - slots[s].getX());
            int dy = Math.abs(y - slots[s].getY());
            if (dx < SNAP_DISTANCE && dy < SNAP_DISTANCE && dx + dy < minDist) {
                minDist = dx + dy;
                nearest = s;
            }
        }
        return nearest;
    }

    // ถ้าไม่มี slot ว่างใกล้ๆ piece อยู่ที่เดิม (ไม่ล็อค)
    private void trySnapToNearest(JLabel piece, int pieceIdx) {
        int nearest = findNearestFreeSlot(piece.getX(), piece.getY());
        if (nearest != -1) {
            piece.setLocation(slots[nearest].getX(), slots[nearest].getY());
            slotPiece[nearest] = pieceIdx; // จอง slot
        }
    }

    // checkWin จากปุ่มส่ง
    private void checkWin() {
        for (int i = 0; i < pieces.length; i++) {
            JLabel correctSlot = slots[pieceIndex[i]];
            if (pieces[i].getX() != correctSlot.getX()
                    || pieces[i].getY() != correctSlot.getY()) {
                this.isPass = false;   // ผิด ส่ง false กลับ
                dialog.dispose();
                return;
            }
        }
        this.isPass = true; // ถูกทุกชิ้น ส่ง true กลับ
        dialog.dispose();
    }

    //ปรับขนาดรูปให้พอดีกับตาราง
    private BufferedImage loadPlaceholderImage() {
        try {
            BufferedImage img = javax.imageio.ImageIO.read(
                    getClass().getResource("/" + selectedPuzzle) // ← ใช้ตัวแปรที่สุ่มมา
            );
            BufferedImage resized = new BufferedImage(
                    PIECE_SIZE * COLS, PIECE_SIZE * ROWS, BufferedImage.TYPE_INT_RGB
            );
            Graphics2D g2 = resized.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(img, 0, 0, PIECE_SIZE * COLS, PIECE_SIZE * ROWS, null);
            g2.dispose();
            return resized;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //ตัดชิ้นส่วนjigsaw
    private BufferedImage[] cutImage(BufferedImage full) {
        BufferedImage[] result = new BufferedImage[ROWS * COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                result[row * COLS + col] = full.getSubimage(
                        col * PIECE_SIZE, row * PIECE_SIZE, PIECE_SIZE, PIECE_SIZE
                );
            }
        }
        return result;
    }

    // public static void main(String[] args) throws Exception {
    //     jigsaw g = new jigsaw();
    //     SwingUtilities.invokeAndWait(() -> g.play());
    //     System.out.println("ผ่าน: " + g.getPass());
    // }
    //public static void main(String[] args) throws Exception {
     //   jigsaw g = new jigsaw();
    //    SwingUtilities.invokeAndWait(() -> g.play());
    //    System.out.println("ผ่าน: " + g.getPass());
    //}
}

