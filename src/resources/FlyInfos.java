package resources;

import libraries.Vector2;

/**
 * Informations imutables d'une mouche
 */
public class FlyInfos {
    // Stats
    public static Vector2 FLY_SIZE = RoomInfos.TILE_SIZE.scalarMultiplication(0.45)
            .vectorMultiplication(new Vector2(1.0, 0.7));
    public static final double FLY_SPEED = 0.0025;
    public static int FLY_HP = 3;
}