package gameObjects.Entities;

import libraries.Vector2;

/**
 * Caracteristiques des entites vivantes (Monstres / Boss)
 */
public abstract class EntityLiving extends EntityMoving {
    protected int health;

    // Constructeur
    public EntityLiving(Vector2 pos, Vector2 size, Vector2 dir, double speed, int health, String imgPath) {
        super(pos, size, dir, speed, imgPath);
        this.health = health;
    }

    /**
     * @brief Definit si le monstre est vivant
     * @return True si le monstre est vivant False sinon
     */
    public boolean isLiving() {
        return this.health > 0;
    }

    // Vie
    public int getHealth() {
        return this.health;
    }

    public void setDamage(int damage) {
        this.health -= damage;
        // TODO: Mort
    }
}
