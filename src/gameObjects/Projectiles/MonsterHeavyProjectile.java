package gameObjects.Projectiles;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class MonsterHeavyProjectile extends MonsterProjectile {

    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.55);
    public static final double SPEED = 0.006;
    public static final double RANGE = 1.0;
    public static final int DAMAGE = 2;
    public static final String IMGPATH = ""; // TODO: à trouver

    /**
     * 
     * @param pos
     * @param dir
     */
    public MonsterHeavyProjectile(Vector2 pos, Vector2 dir) {
        super(pos, SIZE, dir, SPEED, DAMAGE, RANGE, IMGPATH);
    }
}
