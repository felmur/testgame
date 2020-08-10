/*
     TESTGAME
     A simple demo of a game written in java (8-openjdk)
     (c) 2020 by Felice Murolo, Salerno, Italia
     Email: linuxboy@giove_DOT_tk
     Released under LGPL3 license
     See LICENSE file for info
*/

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    ArrayList<enemyBullet> ebullets = new ArrayList<enemyBullet>();
    ArrayList<enemyBullet> ebulletsaux = new ArrayList<enemyBullet>();
    private boolean pbullet_new = false;
    Explosion exp = null;
    Font myfont = null;
    boolean gamewin = false, gamelose = false;


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
        ebulletstart = loaderclip.load("/audio/laserEnemy.wav");
        shipExplosion = loaderclip.load("/audio/shipExplosion.wav");

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        URL u = getClass().getResource("/fonts/kenvector_future_thin.ttf");
        try {
            myfont = Font.createFont(Font.TRUETYPE_FONT, new File(u.getPath()));
            ge.registerFont(myfont);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    // PLAYER WIN
                    log.d("Enemy Destroyed! You Win!");
                    exp = new Explosion(explosion, shipExplosion, eship.getX(), eship.getY());
                    exp.start();
                    eship.setActive(false);
                    pship.setActive(false);
                    gamewin = true;
                } else {
                    pbulletsaux.add(p);
                }
            }
        }
        pbullets = (ArrayList<playerBullet>)(pbulletsaux.clone());

        ebulletsaux.clear();
        for(enemyBullet p : ebullets) {
            if (p.isAlive()) {
                p.display(g);
                if (mCollision.bulletVsPlayer(p,pship)) {
                    // PLAYER LOSE
                    log.d("Player Destroyed! You Lose.");
                    exp = new Explosion(explosion, shipExplosion, pship.getX(), pship.getY());
                    exp.start();
                    eship.setActive(false);
                    pship.setActive(false);
                    gamelose = true;
                } else {
                    ebulletsaux.add(p);
                }
            }
        }
        ebullets = (ArrayList<enemyBullet>)(ebulletsaux.clone());

        if (pbullet_new) {
            playerBullet p = new playerBullet(playerbullet, 100 + pship.getWidth(), pship.getY() + pship.getHeight() / 2, 54, 13, pbulletstart);
            p.start();
            pbullets.add(p);
            p.display(g);
            pbullet_new = false;
        }
        if (eship.isAlive() && eship.isFiring()) {
            enemyBullet e = new enemyBullet(enemybullet, width -150, eship.getY() + eship.getHeight() / 2, 54, 13, ebulletstart);
            e.start();
            ebullets.add(e);
            e.display(g);
        }

        if (exp != null) {
            if (exp.isAlive()) exp.display(g);
        }
        if (gamewin) utils.printCenter("YOU WIN!", g, Color.orange);
        else if (gamelose) utils.printCenter("YOU LOSE!", g, Color.red);

        g.dispose();
        buffer.show();
    }

    @Override
    public void run() {
        log.d("Game started");
        pship = new playerShip(playership,100, (realheight/2)-(99/2), 75,99);
        pship.start();
        eship = new enemyShip(enemyship, width -150, (realheight/2)-(93/2), 84, 93);
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
                if (eship.isAlive()) pbullet_new = true;
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

    public Font getMyfont() {
        return myfont;
    }
}
