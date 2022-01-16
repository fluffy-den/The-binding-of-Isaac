package gameObjects.Projectiles;

import java.util.List;

import gameObjects.Hero;
import gameObjects.Entities.EntityMoving;

import java.util.LinkedList;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class MonsterLightProjectile extends MonsterProjectile {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.2);
    public static final double SPEED = 0.010;
    public static final double RANGE = 1.0;
    public static final int DAMAGE = 1;
    public static final String IMGPATH = "images/MonsterBall.png";

    /**
     * 
     * @param pos
     * @param dir
     */
    public MonsterLightProjectile(Vector2 pos, Vector2 dir) {

        super(pos, SIZE, dir, SPEED, DAMAGE, RANGE, IMGPATH);
    }

    /**
     * 
     */
    public static List<MonsterProjectile> generateProjectilesInCircle(Vector2 pos, Hero h, int n) {
        int a = 360 / n;
        List<MonsterProjectile> pL = new LinkedList<MonsterProjectile>();
        for (int i = 0; i < n; ++i) {
            pL.add(new MonsterLightProjectile(
                    pos,
                    EntityMoving.generateDir(pos, h.getPos(), a * i)));
        }
        return pL;
    }
}
