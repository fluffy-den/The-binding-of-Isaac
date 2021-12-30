package gameObjects.Projectiles;

import gameObjects.Entities.EntityProjectile;
import libraries.Vector2;

public class MonsterProjectile extends EntityProjectile {
    /**
     * 
     * @param pos
     * @param size
     * @param dir
     * @param speed
     * @param damage
     * @param range
     * @param imgPath
     */
    public MonsterProjectile(Vector2 pos, Vector2 size, Vector2 dir, double speed, int damage, double range,
            String imgPath) {
        super(pos, size, dir, speed, damage, range, imgPath);
    }
}
