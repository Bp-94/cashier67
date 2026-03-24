import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class MyTimer implements Runnable {
    private int totalSeconds = 150;
    private boolean running = true;
    private String timeString;

    public MyTimer(int time) {
        this.totalSeconds = time; 
    }
    
    @Override
    public void run() {
        while (running) {
            try {
                /*หาค่าตัวแปร */
                int m = (totalSeconds % 3600) / 60;
                int s = totalSeconds % 60;

                /*จัดรูปแบบสตริง */
                String hh, mm, ss;
                if (s < 10) {
                    ss = "0"+s;
                } else {
                    ss = ""+s;
                }

                if (m < 10) {
                    mm = "0"+m;
                } else {
                    mm = ""+m;
                }

                this.timeString = mm+":"+ss;

                /*time update */
                totalSeconds--;
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }

    public String getTimeString() {
        return timeString;
    }

    public void stopTime() {
        this.running = false;
    }
}