package gameObjects.Entities;

import libraries.Vector2;

public abstract class EntityProjectile extends EntityMoving {
    protected int damage;
    protected double range;

    // Constructor
    public EntityProjectile(Vector2 pos, Vector2 size, Vector2 dir, double speed, int damage, double range,
            String imgPath) {
        super(pos, size, dir, speed, true, imgPath);
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
        this.pos = positionAfterMoving;
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

    /**
     * 
     * @return
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * 
     * @return
     */
    public double getRemainingRange() {
        return this.range;
    }

    /**
     * 
     * @param l
     */
    public void onHitLivingObject(EntityLiving l) {
        l.addDamage(this.damage);
    }
}
