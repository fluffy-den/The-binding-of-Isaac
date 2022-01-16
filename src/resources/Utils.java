package resources;

import java.util.Random;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class Utils {

        /**
         * @brief Dis si les vecteurs son adjacents
         * 
         * @param xp
         * @param xs
         * @param yp
         * @param ys
         * @return True si adjacent, false sinon
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

        /**
         * @brief Renvoie un double aléatoire entre
         *        deux bornes
         * 
         * @param lower borne inférieur
         * @param upper borne supérieur
         * @return double aléatoire
         */
        public static double randomDouble(double lower, double upper) {
                Random random = new Random();
                return random.nextDouble(upper + 1.0 - lower) + lower;
        }

        /**
         * @brief Renvoie un entier aléatoire entre
         *        deux bornes
         * 
         * @param lower borne inférieur
         * @param upperborne supérieur
         * @return entier aléatoire
         */
        public static int randomInt(int lower, int upper) {
                Random random = new Random();
                return random.nextInt(upper + 1 - lower) + lower;
        }
}
