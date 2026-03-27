import javax.swing.*;
import java.util.Random;

public class MinigameScheduler {

    private final Game game;
    private final javax.swing.Timer timer;
    private final Random random;
    private boolean minigameActive = false;
    private boolean stopped = false;
    private static final int INTERVAL_MS = 20_000; // ทุก 20 วิ
    private static final double CHANCE    = 0.3;    // โอกาส 50%
    

    public MinigameScheduler(Game game) {
        this.game   = game;
        this.random = new Random();
        timer = new javax.swing.Timer(INTERVAL_MS, e -> tryTrigger());
        timer.setRepeats(true);
    }

    public void start() { 
        stopped = false;
        timer.start();
     }
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
        if (!stopped) {
            if (!mg.getPass()) {
                int newTime = game.getLevelCanvas().getTimer().getTotalSeconds() - 10;
                game.getLevelCanvas().triggerMinusAnim();
                if (newTime < 0) newTime = 0;
                game.getLevelCanvas().getTimer().setTime(newTime);
            }
         
        minigameActive = false;
        timer.start();
        }else {
        minigameActive = false;
    }
}

    private Minigame pickRandom() {
        if (game.getLevel() <= 3) {
        // ด่าน 1-3 สุ่มแค่ 3 อัน (ไม่มี guessGoods)
        switch (random.nextInt(3)) {
            case 0:  return new guessPrice(game);
            case 1:  return new guessBill(game);
            default: return new jigsaw(game);
        }
    } else {
        // ด่าน 4-5 สุ่มครบทั้ง 4 อัน
        switch (random.nextInt(4)) {
            case 0:  return new guessGoods(game);
            case 1:  return new guessPrice(game);
            case 2:  return new guessBill(game);
            default: return new jigsaw(game);
        }
    }
    }
}
