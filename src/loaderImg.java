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
