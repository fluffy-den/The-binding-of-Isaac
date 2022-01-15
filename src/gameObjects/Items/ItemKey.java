package gameObjects.Items;

import gameWorld.GameRoom;

import gameObjects.Entities.EntityItem;
import gameObjects.Hero;

import libraries.Vector2;

/**
 * 
 */
public class ItemKey extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final String IMGPATH = "images/Key.png";

    /**
     * 
     */
    public ItemKey(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
    }

    /**
     * 
     */
    public void onHeroItemAction(Hero h) {
        h.addKey();
    }
}
