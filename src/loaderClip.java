/*
     TESTGAME
     A simple demo of a game written in java (8-openjdk)
     (c) 2020 by Felice Murolo, Salerno, Italia
     Email: linuxboy@giove_DOT_tk
     Released under LGPL3 license
     See LICENSE file for info
*/
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

public class loaderClip {

    public Clip load(String path) {
        Clip clip = null;

        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(path)));
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

