/*
     TESTGAME
     A simple demo of a game written in java (8-openjdk)
     (c) 2020 by Felice Murolo, Salerno, Italia
     Email: linuxboy@giove_DOT_tk
     Released under LGPL3 license
     See LICENSE file for info
*/
public class mCollision {

    public static boolean bulletVsEnemy(playerBullet pbullet, enemyShip eship) {
        boolean ret = false;
        if (eship.isAlive() && pbullet.isAlive()) {
            ret = pbullet.getEdges().intersects(eship.getEdges());
        }
        return ret;
    }

}
