package gameObjects.Projectiles;

import gameObjects.Entities.EntityProjectile;
import gameWorld.GameRoom;
import libraries.Vector2;

/**
 * 
 */
public class Tear extends EntityProjectile {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.25);
    public static final String IMGPATH = "images/Tear.png";

    /**
     * Créé un larme
     * @param pos Position de départ
     * @param dir Direction de la larme
     * @param speed Vitesse de la larme
     * @param damage Degats de la larme
     * @param range Portée de la larme
     */
    public Tear(Vector2 pos, Vector2 dir, double speed, int damage, double range) {
        super(pos,
                SIZE,
                dir,
                speed,
                damage,
                range,
                IMGPATH);
    }
}
