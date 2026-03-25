import java.awt.*;
import javax.swing.*;
public class AnimationLevel implements Runnable {
    private LevelCanvas panel;
    private int currentY;
    private float Prongsai;
    public AnimationLevel(LevelCanvas f) {
        panel = f;
    }
    
    @Override
    public void run() {
        currentY = -200;
        Prongsai = 1.0f;

        try {
            while (currentY < -40) {
                currentY += 5;
                panel.updateState(currentY, Prongsai);
                Thread.sleep(15);
            }
            Thread.sleep(2000);

            while (Prongsai > 0.0f) {
                Prongsai -= 0.02f;
                if (Prongsai < 0.0f) {
                    Prongsai = 0.0f;
                }
                panel.updateState(currentY, Prongsai);
                Thread.sleep(30);
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    public int getCurrentY(){
        return currentY;
    }
    public float getProngsai(){
        return Prongsai;
    }

}