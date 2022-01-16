package gameObjects.Projectiles;

import gameObjects.Entities.EntityProjectile;
import libraries.Vector2;

public class MonsterProjectile extends EntityProjectile {
    /**
     * Créé un projectile
     * 
     * @param pos Position de depart
     * @param size Taille du projectile
     * @param dir Direction du projectile
     * @param speed Vitesse du projectile
     * @param damage Dégats du projectile
     * @param range Portée du projectile
     * @param imgPath Texture du projectile
     */
    public MonsterProjectile(Vector2 pos, Vector2 size, Vector2 dir, double speed, int damage, double range,
            String imgPath) {
        super(pos, size, dir, speed, damage, range, imgPath);
    }
}
