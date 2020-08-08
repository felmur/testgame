import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class game extends Canvas implements Runnable, KeyListener, MouseMotionListener {
    private static String prgname="TestGame";
    private static int width=1280;
    private static int height=720;
    private static int realheight;
    BufferedImage background;
    BufferedImage playership;
    BufferedImage playerbullet;
    BufferedImage enemyship;
    BufferedImage enemybullet;
    ArrayList<BufferedImage> explosion = new ArrayList<>();
    Clip pbulletstart;
    Clip ebulletstart;
    Clip shipExplosion;
    private boolean gamerun = true;
    playerShip pship;
    enemyShip eship;
    JFrame w;

    ArrayList<playerBullet> pbullets = new ArrayList<playerBullet>();
    ArrayList<playerBullet> pbulletsaux = new ArrayList<playerBullet>();
    private boolean pbullet_new = false;
    Explosion exp = null;


    public game(){
        log.d("Starting "+prgname+"...");
        loadRes();
        Dimension dimension = new Dimension(width, height);
        JFrame win = new JFrame(prgname);
        w = win;
        win.addKeyListener(this);
        win.setPreferredSize(dimension);
        win.setMaximumSize(dimension);
        win.setMinimumSize(dimension);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setResizable(false);

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        win.getContentPane().setCursor(blankCursor);

        win.add(this);
        win.pack();

        utils.sleep(1000);

        win.setVisible(true);
        realheight = win.getContentPane().getHeight()-5;

        this.addMouseMotionListener(this);
        this.setFocusable(false);   // game is not focusable otherwise a simple mouse click disables the keylistener

        Thread tgame = new Thread(this);
        tgame.start();
    }

    private void loadRes() {
        loaderImg loader = new loaderImg();
        background = loader.load("/images/backgrounds/darkPurple.png");
        playership = loader.load("/images/playerShip.png");
        playerbullet = loader.load("/images/playerBullet.png");
        enemyship = loader.load("/images/enemyShip.png");
        enemybullet = loader.load("/images/enemyBullet.png");
        for (int i=1; i<5; i++) {
            explosion.add(loader.load("/images/explosion/"+i+".png"));
        }
        loaderClip loaderclip = new loaderClip();
        pbulletstart = loaderclip.load("/audio/laserPlayer.wav");
        shipExplosion = loaderclip.load("/audio/shipExplosion.wav");
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
        if (pship.isAlive()) pship.display(g);
        if (eship.isAlive()) eship.display(g);

        pbulletsaux.clear();
        for(playerBullet p : pbullets) {
            if (p.isAlive()) {
                p.display(g);
                if (mCollision.bulletVsEnemy(p,eship)) {
                    log.d("Enemy Destroyed! You Win!");
                    exp = new Explosion(explosion, shipExplosion, eship.getX(), eship.getY());
                    exp.start();
                    eship.setActive(false);
                } else {
                    pbulletsaux.add(p);
                }
            }
        }
        pbullets = (ArrayList<playerBullet>)(pbulletsaux.clone());
        if (pbullet_new) {
            playerBullet p = new playerBullet(playerbullet, 100 + pship.getWidth(), pship.getY() + pship.getHeight() / 2, 54, 13, pbulletstart);
            p.start();
            pbullets.add(p);
            p.display(g);
            pbullet_new = false;
        }
        if (exp != null) {
            if (exp.isAlive()) exp.display(g);
        }
        g.dispose();
        buffer.show();
    }

    @Override
    public void run() {
        log.d("Game started");
        pship = new playerShip(playership,100, (realheight/2)-(99/2), 75,99);
        pship.start();
        eship = new enemyShip(enemyship, width -100, (realheight/2)-(93/2), 84, 93);
        eship.start();
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
            case KeyEvent.VK_SPACE:
                pbullet_new = true;
                break;
        }

    }
    @Override
    public void keyTyped(KeyEvent keyEvent) { }
    @Override
    public void keyReleased(KeyEvent keyEvent) {  }

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
    public void mouseDragged(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        int pos = mouseEvent.getPoint().y;
        if (pos + pship.getHeight() > realheight) pos = realheight-pship.getHeight();
        pship.setY(pos);
    }

}
