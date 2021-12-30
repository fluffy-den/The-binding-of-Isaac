package gameObjects.Entities;

import gameObjects.Hero;
import libraries.Vector2;

/**
 * @brief Classe représentant l'objet item, consommable par le joueur
 */
public abstract class EntityItem extends Entity {
    /**
     * 
     * @param pos
     * @param size
     * @param imgPath
     */
    public EntityItem(Vector2 pos, Vector2 size, String imgPath) {
        super(pos, size, imgPath);
    }

    /**
     * 
     * @param h
     */
    public void onHeroItemAction(Hero h) {
    }
}
