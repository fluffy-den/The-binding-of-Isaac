package gameObjects.Doors;

import gameObjects.Entities.EntityDoor;

import libraries.Vector2;

public class OpenedDoor extends EntityDoor {
    public static final String IMGPATH = "images/OpenedDoor.png";

    /**
     * Créé une porte ouverte (dite classique)
     * 
     * @param pos Coordonnées de la porte
     */
    public OpenedDoor(Vector2 pos) {
        super(pos, IMGPATH, true, false, false);
    }
}
