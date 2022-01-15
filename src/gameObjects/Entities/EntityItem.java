package gameObjects.Entities;

import gameObjects.Hero;
import libraries.Vector2;

/**
 * @brief Classe représentant l'objet item, consommable par le joueur
 */
public abstract class EntityItem extends Entity {
    private int price;
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

    /**
     * Définit le prix d'un objet
     * @param price = prix de l'objet
     */
    public void setPrice(int price){
        this.price = price;
    }

    /**
     * Donne le prix d'un objet
     * @return le prix
     */
    public int getPrice(){
        return this.price;
    }

}
