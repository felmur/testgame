/*
     TESTGAME
     A simple demo of a game written in java (8-openjdk)
     (c) 2020 by Felice Murolo, Salerno, Italia
     Email: linuxboy@giove_DOT_tk
     Released under LGPL3 license
     See LICENSE file for info
*/
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class enemyShip extends Thread {
    private int x,y,width,height;
    BufferedImage image;
    private boolean active;
    private int speed = 2;
    private int wait = 500;
    Random rnd;

    public enemyShip(BufferedImage image, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        this.active = true;
    }

    @Override
    public void run() {
        active = true;
        while(active) {
            rnd = new Random();
            int np = rnd.nextInt(main.mygame.getRealheight()-height);
            if (y < np) {
                for (int i=y; i<np; i+=speed) {
                    if (!active) break;
                    utils.sleep(10);
                    setY(i);
                }
            }
            if (y > np) {
                for (int i=y; i>np; i-=speed) {
                    if (!active) break;
                    utils.sleep(10);
                    setY(i);
                }
            }
        }
    }

    public void display(Graphics g){
        g.drawImage(image, x, y, width, height, null);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Rectangle getEdges() {
        return new Rectangle(x, y, width, height);
    }

}
