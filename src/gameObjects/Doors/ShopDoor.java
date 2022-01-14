package gameObjects.Doors;

import gameObjects.Entities.EntityDoor;

import libraries.Vector2;

public class ShopDoor extends EntityDoor {
    public static final String IMGPATH = "images/BossDoor.png";

    /**
     * Créé une porte ouverte (dite classique)
     * 
     * @param pos Coordonnées de la porte
     */
    public ShopDoor(Vector2 pos) {
        super(pos, IMGPATH, true, false, false);
    }
}
