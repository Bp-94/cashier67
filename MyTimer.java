import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class MyTimer implements Runnable {
    private int totalSeconds;
    private boolean running = true;
    private String timeString = "00:00";

    public MyTimer(int time) {
        this.totalSeconds = time; 
    }
    
    @Override
    public void run() {
        while ( totalSeconds >= 0) {
            if (running) {
                
                try {
                    /*หาค่าตัวแปร */
                    int m = (totalSeconds % 3600) / 60;
                    int s = totalSeconds % 60;

                        timeString = String.format("%02d:%02d", m, s);

                    /*time update */
                    Thread.sleep(1000);
                    totalSeconds--;
                } catch (InterruptedException ex) {
                    break;
                }
            }
        }
    }

    public String getTimeString() {
        return timeString;
    }

    public void stopTime() {
        this.running = false;
    }
    public int getTotalSeconds() {
    return totalSeconds;
}
    public void setTime(int totalSeconds) {
    this.totalSeconds = totalSeconds;
    
    
    int m = (totalSeconds % 3600) / 60;
    int s = totalSeconds % 60;
    this.timeString = String.format("%02d:%02d", m, s);
}

    public void start() {

        running = true;
        new Thread(this).start();
}
    
}