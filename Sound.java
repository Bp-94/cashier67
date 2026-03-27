import java.io.File;
import javax.sound.sampled.*;

public class Sound {

    public static void main(String[] args) {

        try {

            File file = new File("bgm.wav");
            System.out.println("Looking for file at: " + file.getAbsolutePath());

            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();

            clip.open(audio);

            clip.loop(Clip.LOOP_CONTINUOUSLY); // เล่นวนตลอด

            System.out.println("Playing sound...");

            Thread.sleep(1000000); // กันโปรแกรมปิด

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

//import java.io.File;
//import javax.sound.sampled.*;

//public class Sound {

    //private static Clip clip;

    //public static void startBGM() {
        //try {

            //File file = new File("bgm.wav");
            //AudioInputStream audio = AudioSystem.getAudioInputStream(file);

            //clip = AudioSystem.getClip();
            //clip.open(audio);

            //clip.loop(Clip.LOOP_CONTINUOUSLY); // เล่นวนจนกว่าโปรแกรมจะปิด

        //} catch (Exception e) {
            //e.printStackTrace();
        //}
    //}

    //public static void stopBGM() {
        //if (clip != null) {
            /////clip.stop();
        //}
    //}
//}

//Sound.startBGM(); ในapp.java

