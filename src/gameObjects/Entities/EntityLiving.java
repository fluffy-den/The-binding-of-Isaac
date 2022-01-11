package gameObjects.Entities;

import libraries.Vector2;

/**
 * Caracteristiques des entites vivantes (Monstres / Boss)
 */
public abstract class EntityLiving extends EntityMoving {
    protected int health;

    // Constructeur
    public EntityLiving(Vector2 pos, Vector2 size, Vector2 dir, double speed, boolean flying, int health,
            String imgPath) {
        super(pos, size, dir, speed, flying, imgPath);
        this.health = health;
    }

    // Health
    /**
     * @brief Definit si le monstre est vivant
     * @return True si le monstre est vivant False sinon
     */
    public boolean isLiving() {
        return this.health > 0;
    }

    /**
     * 
     * @return
     */
    public int getHealth() {
        return this.health;
    }

    // Damage
    /**
     * 
     * @param damage
     */
    public void addDamage(int damage) {
        this.health -= damage;
    }
}
