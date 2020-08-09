/*
     TESTGAME
     A simple demo of a game written in java (8-openjdk)
     (c) 2020 by Felice Murolo, Salerno, Italia
     Email: linuxboy@giove_DOT_tk
     Released under LGPL3 license
     See LICENSE file for info
*/
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Explosion extends Thread {
    private int x,y;
    ArrayList<BufferedImage> imgs;
    BufferedImage image, previous;
    private int wait = 200;
    private boolean active=false;
    Clip explosion;

    public Explosion(ArrayList<BufferedImage> imgs, Clip explosion, int x, int y) {
        this.x = x;
        this.y = y;
        this.imgs = (ArrayList<BufferedImage>) (imgs.clone());
        this. explosion = explosion;
        image = imgs.get(0);
        previous = image;
    }

    @Override
    public void run() {
        active = true;
        playSnd();
        for(BufferedImage img : imgs) {
            image = img;
            int sp = (image.getHeight()-previous.getHeight())/2;
            y = y - sp;
            x = x - sp;
            utils.sleep(wait);
            previous = image;
        }
    }

    public void playSnd(){
        explosion.stop();
        explosion.flush();
        explosion.setMicrosecondPosition(0);
        explosion.start();
    }


    public void display(Graphics g) {
            if (active) {
                g.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
            }
    }
}
