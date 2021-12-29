package libraries;

import resources.RoomInfos;

public class Utils {
    public class Hitbox {
        static public boolean adjacent(Vector2 xP, Vector2 xS, Vector2 yP, Vector2 yS) {
            double authorizedOverlap = RoomInfos.TILE_SIZE.getX() / 1000;

            boolean tooFarLeft = xP.getX() + (xS.getX() / 2) < authorizedOverlap + yP.getX() - (yS.getX() / 2);
            boolean tooFarBelow = xP.getY() + (xS.getY() / 2) < authorizedOverlap + yP.getY() - (yS.getY() / 2);
            boolean tooFarRight = xP.getX() - (xS.getX() / 2) + authorizedOverlap > yP.getX() + (yS.getX() / 2);
            boolean tooFarAbove = xP.getY() - (xS.getY() / 2) + authorizedOverlap > yP.getY() + (yS.getY() / 2);

            if (tooFarLeft || tooFarRight || tooFarAbove || tooFarBelow) {
                return false;
            }
            return true;
        }
    }
}