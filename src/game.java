import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class game extends Canvas implements Runnable, KeyListener, MouseMotionListener {
    private static String prgname="TestGame";
    private static int width=1280;
    private static int height=720;
    private static int realheight;
    BufferedImage background;
    BufferedImage playership;
    private boolean gamerun = true;
    playerShip pship;

    public game(){
        log.d("Starting "+prgname+"...");
        loadRes();
        Dimension dimension = new Dimension(width, height);
        JFrame win = new JFrame(prgname);
        win.addKeyListener(this);
        win.setPreferredSize(dimension);
        win.setMaximumSize(dimension);
        win.setMinimumSize(dimension);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setResizable(false);
        win.add(this);
        win.pack();
        win.setVisible(true);
        realheight = win.getContentPane().getHeight()-5;

        this.addMouseMotionListener(this);
        Thread tgame = new Thread(this);
        tgame.start();
    }

    private void loadRes() {
        loaderImg loader = new loaderImg();
        background = loader.load("/images/backgrounds/darkPurple.png");
        playership = loader.load("/images/playerShip.png");
    }

    private void display() {
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            createBufferStrategy(2);
            return;
        }
        Graphics g = buffer.getDrawGraphics();
        for (int h=0; h<height; h+=256) {
            for (int w=0; w<width; w+=256) {
                g.drawImage(background, w, h, 256, 256, this);
            }
        }
        pship.display(g);
        g.dispose();
        buffer.show();
    }

    @Override
    public void run() {
        log.d("Game started");
        pship = new playerShip(playership,100, (realheight/2)-(99/2), 75,99);
        pship.start();
        while(gamerun) {
            display();
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_W:
                pship.moveUp();
                break;
            case KeyEvent.VK_S:
                pship.moveDown();
                break;
        }

    }
    @Override
    public void keyTyped(KeyEvent keyEvent) { }
    @Override
    public void keyReleased(KeyEvent keyEvent) { }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getRealheight() {
        return realheight;
    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {}

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        int pos = mouseEvent.getPoint().y;
        if (pos - pship.getHeight() < 0 ) pos = 0;
        else if (pos + pship.getHeight() > realheight) pos = realheight-pship.getHeight();
        pship.setY(pos);
    }
}
