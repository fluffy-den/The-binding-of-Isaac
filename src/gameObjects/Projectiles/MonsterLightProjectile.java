package gameObjects.Projectiles;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class MonsterLightProjectile extends MonsterProjectile {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.2);
    public static final double SPEED = 0.018;
    public static final double RANGE = 1.0;
    public static final int DAMAGE = 1;
    public static final String IMGPATH = "images/MonsterBall.png"; // TODO: à trouver

    /**
     * 
     * @param pos
     * @param dir
     */
    public MonsterLightProjectile(Vector2 pos, Vector2 dir) {

        super(pos, SIZE, dir, SPEED, DAMAGE, RANGE, IMGPATH);
    }
}
