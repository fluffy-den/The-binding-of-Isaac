package gameObjects.Doors;

import gameObjects.Entities.EntityDoor;

import libraries.Vector2;

public class KeyLockedDoor extends EntityDoor {
    public static final String IMGPATH = "images/KeyLockedDoor.png";

    /**
     * Créé une porte fermée à clé
     * 
     * @param pos Coordonnées de la porte
     */
    public KeyLockedDoor(Vector2 pos) {
        super(pos, IMGPATH, false, true, false);
    }
}
