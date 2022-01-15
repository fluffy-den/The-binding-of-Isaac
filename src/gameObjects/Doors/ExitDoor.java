package gameObjects.Doors;

import gameObjects.Entities.EntityDoor;

import libraries.Vector2;

public class ExitDoor extends EntityDoor {
    public static final String IMGPATH = "images/ExitDoor.png";

    /**
     * Créé une porte ouverte (dite classique)
     * 
     * @param pos Coordonnées de la porte
     */
    public ExitDoor(Vector2 pos) {
        super(pos, IMGPATH, true, false);
    }
}
