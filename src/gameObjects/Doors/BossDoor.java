package gameObjects.Doors;

import gameObjects.Entities.EntityDoor;

import libraries.Vector2;

public class BossDoor extends EntityDoor {
    public static final String IMGPATH = "images/BossDoor.png";

    /**
     * Créé une porte ouverte (dite classique)
     * 
     * @param pos Coordonnées de la porte
     */
    public BossDoor(Vector2 pos) {
        super(pos, IMGPATH, true, false);
    }
}
