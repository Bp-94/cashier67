import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private MyTimer timerLogic;

    public GamePanel() {
        // 1. สร้างเครื่องจักรนับเวลา
        timerLogic = new MyTimer(150);

        // 2. สร้าง Thread แยกเพื่อให้นาฬิกาวิ่งเลนขนาน (หน้าจอจะได้ไม่ค้าง)
        Thread t = new Thread(timerLogic);
        t.start();

        // 3. สร้าง Timer ตัวเล็กๆ เพื่อสั่งให้หน้าจอ "วาดใหม่" (Repaint) ทุกๆ 0.1 วินาที
        // เพื่อให้เลขนาฬิกาที่อัปเดตใน Thread โชว์บนหน้าจอทันที
        Timer refresh = new Timer(100, e -> repaint());
        refresh.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // ตกแต่งพื้นหลังนิดหน่อย
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        // ดึง String จาก MyTimer มาวาด
        String timeToShow = timerLogic.getTimeString();

        // ตั้งค่าฟอนต์และสีที่จะวาดเลขนาฬิกา
        g.setFont(new Font("Tahoma", Font.BOLD, 50));
        g.setColor(Color.CYAN);
        
        // วาดลงบนหน้าจอ (ตำแหน่ง x=80, y=100)
        g.drawString(timeToShow, 80, 100);
        
        g.setFont(new Font("Tahoma", Font.PLAIN, 15));
        g.drawString("นาฬิกากำลังทำงานใน Thread แยก...", 70, 150);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ทดสอบนาฬิกาใน GamePanel");
        GamePanel panel = new GamePanel();

        frame.add(panel);
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // วางหน้าต่างไว้กลางจอ
        frame.setVisible(true);
    }
}