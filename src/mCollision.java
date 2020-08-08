public class mCollision {

    public static boolean bulletVsEnemy(playerBullet pbullet, enemyShip eship) {
        boolean ret = false;
        if (eship.isAlive() && pbullet.isAlive()) {
            ret = pbullet.getEdges().intersects(eship.getEdges());
        }
        return ret;
    }

}
