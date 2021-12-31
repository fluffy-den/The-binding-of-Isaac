package gameObjects.Doors;

import gameObjects.Entities.EntityDoor;

import libraries.Vector2;

/**
 * 
 */
public class OpenedDoor extends EntityDoor {
    public static final String IMGPATH = "images/OpenedDoor.png";

    /**
     * 
     * @param pos
     */
    public OpenedDoor(Vector2 pos) {
        super(pos, IMGPATH);
    }
}
