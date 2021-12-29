package resources;

import libraries.Vector2;

/**
 * Informations imutables d'une mouche
 */
public class SpiderInfos {
    // Stats
    public static Vector2 SPIDER_SIZE = RoomInfos.TILE_SIZE.scalarMultiplication(0.75)
            .vectorMultiplication(new Vector2(1.0, 0.7));
    public static final double SPIDER_SPEED = 0.002;
    public static int SPIDER_HP = 5;
}
