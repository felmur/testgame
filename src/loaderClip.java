import javax.sound.sampled.*;
import java.io.IOException;

public class loaderClip {

    public Clip load(String path) {
        Clip clip = null;

        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));
            clip = AudioSystem.getClip();
            clip.open(audio);
            log.d("Audio: '"+path+"' loaded successfully");

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;
    }
}

