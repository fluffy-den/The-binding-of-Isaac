package resources;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class Utils {

    /**
     * 
     * @param xp
     * @param xs
     * @param yp
     * @param ys
     * @return
     */
    public static boolean isAdjacent(Vector2 xp, Vector2 xs, Vector2 yp, Vector2 ys) {
        double authorizedOverlap = GameRoom.TILE_SIZE.getX() / 1000;

        boolean tooFarL = xp.getX() + (xs.getX() / 2) < authorizedOverlap + yp.getX()
                - (ys.getX() / 2);
        boolean tooFarB = xp.getY() + (xs.getY() / 2) < authorizedOverlap + yp.getY()
                - (ys.getY() / 2);
        boolean tooFarR = xp.getX() - (xs.getX() / 2) + authorizedOverlap > yp.getX()
                + (ys.getX() / 2);
        boolean tooFarA = xp.getY() - (xs.getY() / 2) + authorizedOverlap > yp.getY()
                + (ys.getY() / 2);

        if (tooFarL || tooFarR || tooFarA || tooFarB) {
            return false;
        }
        return true;
    }
}
