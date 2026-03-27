import javax.swing.*;
import java.util.Random;

public class MinigameScheduler {

    private final Game game;
    private final javax.swing.Timer timer;
    private final Random random;
    private boolean minigameActive = false;

    private static final int INTERVAL_MS = 5_000; // ทุก 20 วิ
    private static final double CHANCE    = 0.7;    // โอกาส 50%
    private static final int PENALTY_SEC = 10;      // แพ้ → -10 วิ

    public MinigameScheduler(Game game) {
        this.game   = game;
        this.random = new Random();
        timer = new javax.swing.Timer(INTERVAL_MS, e -> tryTrigger());
        timer.setRepeats(true);
    }

    public void start() { timer.start(); }
    public void stop()  { timer.stop();  }
    public boolean isActive() { return minigameActive; }

    private void tryTrigger() {
        if (random.nextDouble() >= CHANCE) return;

        timer.stop();
        minigameActive = true;

        // โผล่ทับเกมหลักเลย ไม่มีแจ้งเตือน
        Minigame mg = pickRandom();
        mg.play(); // Modal dialog — MyTimer ยังเดินต่อ ✅

        // ตัดสินผล
        if (!mg.getPass()) {
            int newTime = game.getLevelCanvas().getTimer().getTotalSeconds() - 10;
            if (newTime < 0) newTime = 0;
            game.getLevelCanvas().getTimer().setTime(newTime);
        }

        minigameActive = false;
        timer.start();
    }

    private Minigame pickRandom() {
        switch (random.nextInt(4)) {
            case 0:  return new guessGoods();
            case 1:  return new guessPrice();
            case 2:  return new guessBill();
            default: return new jigsaw();
        }
    }
}
