import java.io.File;
import javax.sound.sampled.*;

public class Sound {
    
    private Clip clip;

    
    public void play(String filePath) {
        try {
            stop(); // หยุดเพลงเก่าก่อน ถ้ามี

            File file = new File(filePath);
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // หยุดเพลง
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}

