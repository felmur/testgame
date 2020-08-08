import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Explosion extends Thread {
    private int x,y;
    ArrayList<BufferedImage> imgs;
    BufferedImage image;
    private int wait = 250;
    private boolean active=false;
    Clip explosion;

    public Explosion(ArrayList<BufferedImage> imgs, Clip explosion, int x, int y) {
        this.x = x;
        this.y = y;
        this.imgs = (ArrayList<BufferedImage>) (imgs.clone());
        this. explosion = explosion;
        image = imgs.get(0);
    }

    @Override
    public void run() {
        active = true;
        playSnd();
        for(BufferedImage img : imgs) {
            image = img;
            utils.sleep(wait);
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
