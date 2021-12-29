package gameObjects.Projectiles;

import libraries.Vector2;

public class MonsterProjectile extends Projectile {
    // Constructeur
    public MonsterProjectile(Vector2 pos, Vector2 size, Vector2 dir, double speed, int damage, double range,
            String imgPath) {
        super(pos, size, dir, speed, damage, range, imgPath);
    }
}
