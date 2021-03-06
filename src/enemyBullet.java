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

public class enemyBullet extends Thread {
    private int x,y,width,height;
    BufferedImage image;
    private boolean active;
    private int speed = 3;
    private int k=0;
    private static Clip startSound;

    public enemyBullet(BufferedImage image, int x, int y, int width, int height, Clip startSound) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.startSound = startSound;
        this.active = true;
        start();
    }

    @Override
    public void run() {
        playSnd();
        active = true;
        while(active) {
            x-=speed;
            if (x<0) active = false;
            utils.sleep(2);
        }
    }

    public void display(Graphics g){
        g.drawImage(image, x, y, width, height, null);
    }

    public void playSnd(){
        startSound.stop();
        startSound.flush();
        startSound.setMicrosecondPosition(0);
        startSound.start();
    }

    public Rectangle getEdges() {
        return new Rectangle(x, y, width, height);
    }

}
