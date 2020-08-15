import java.awt.*;

public class utils {

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // PRINTSCORE
    public static void printScore(String text, Graphics g, int height, Color color) {
        int h = main.mygame.getRealheight();
        int w = main.mygame.getWidth();
        Font font = main.mygame.getMyfont();
        int l = text.length();
        int x = (int) ((w - (l*height*0.68))/2);
        int y = height + 5;
        g.setColor(color);
        g.setFont(new Font(font.getFontName(), Font.PLAIN, height));
        g.drawString(text,x,y);
    }
    public static void printScore(String text, Graphics g, Color color) {
        printScore(text,g,48,color);
    }
    public static void printScore(String text, Graphics g, int height) { printScore(text,g,height,Color.white); }
    public static void printScore(String text, Graphics g) { printScore(text,g,48,Color.white); }


    // PRINTCENTER
    public static void printCenter(String text, Graphics g, int height, Color color) {
        int h = main.mygame.getRealheight();
        int w = main.mygame.getWidth();
        Font font = main.mygame.getMyfont();
        int l = text.length();
        int x = (int) ((w - (l*height*0.68))/2);
        int y = (int) ((h - height)/2);
        g.setColor(color);
        g.setFont(new Font(font.getFontName(), Font.PLAIN, height));
        g.drawString(text,x,y);
    }
    public static void printCenter(String text, Graphics g, Color color) {
        printCenter(text,g,48,color);
    }
    public static void printCenter(String text, Graphics g, int height) { printCenter(text,g,height,Color.white); }
    public static void printCenter(String text, Graphics g) { printCenter(text,g,48,Color.white); }

}
