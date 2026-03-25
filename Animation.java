import java.awt.*;
import javax.swing.*;
public class Animation implements Runnable {
    private LevelCanvas panel;
    
    public Animation(LevelCanvas f) {
        panel = f;
    }
    
    @Override
    public void run() {
        int currentY = -100;
        float Prongsai = 1.0f;

        try {
            while (currentY < 200) {
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
    
}
