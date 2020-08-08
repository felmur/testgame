/*
     TESTGAME
     A simple demo of a game written in java (8-openjdk)
     (c) 2020 by Felice Murolo, Salerno, Italia
     Email: linuxboy@giove_DOT_tk
     Released under LGPL3 license
     See LICENSE file for info
*/
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class loaderImg {

    public BufferedImage load(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource(path));
            log.d("Image: '"+path+"' loaded successfully");
        } catch (IOException e) {
            log.d("Image: '"+path+"' error!");
            e.printStackTrace();
        }
        return img;
    }
}
