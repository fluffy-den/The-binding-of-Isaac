package gameObjects.Doors;

import gameObjects.Entities.EntityDoor;

import libraries.Vector2;

public class SecretDoor extends EntityDoor {
    /**
     * Créé une porte secrète
     * 
     * @param pos Coordonnées de la porte
     */
    public SecretDoor(Vector2 pos) {
        super(pos, null, false, true, true);
    }
}
