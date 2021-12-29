package gameObjects.Projectiles;

import gameObjects.Entities.EntityMoving;

import libraries.Vector2;
import libraries.StdDraw;

public abstract class Projectile extends EntityMoving {
    protected int damage;
    protected double range;

    // Constructor
    public Projectile(Vector2 pos, Vector2 size, Vector2 dir, double speed, int damage, double range,
            String imgPath) {
        super(pos, size, dir, speed, imgPath);
        this.damage = damage;
        this.range = range;
    }

    // Mis à jour
    /**
     * @brief Deplacement du projectile (Sans chagement de direction)
     */
    public void update() {
        Vector2 normalizedDirection = getNormalizedDirection();
        Vector2 positionAfterMoving = getPos().addVector(normalizedDirection);
        this.range -= positionAfterMoving.distance(this.getPos());
        setPos(positionAfterMoving);
        // Pas de reset de la direction!
    }

    /**
     * @brief Verifie si le projectile doit etre detruit
     * @return True si le pojectile doit etre detruit, False sinon
     */
    public boolean shouldBeDestroyed() {
        if (this.range >= 0)
            return false;
        return true;
    }

    // Dommages
    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    // Portée
    public double getRange() {
        return this.range;
    }

    /**
     * @brief Change la portee restante d'un projectile
     * @param range nouvelle portee
     */
    public void setRange(double range) {
        this.range = range;
    }
}
