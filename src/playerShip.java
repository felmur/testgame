import java.awt.*;
import java.awt.image.BufferedImage;

public class playerShip extends Thread {
    private int x,y,width,height;
    BufferedImage image;
    private boolean active;
    private int speed = 10;

    public playerShip(BufferedImage image, int x, int y, int width, int height) {
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
            utils.sleep(100);
        }
    }

    public void moveUp(){
        y-=speed;
        if (y < 0) y=0;
    }
    public void moveDown(){
        y+=speed;
        int max = main.mygame.getRealheight()-height;
        if (y > (max)) y=max;
    }

    public void display(Graphics g){
        g.drawImage(image, x, y, width, height, null);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
