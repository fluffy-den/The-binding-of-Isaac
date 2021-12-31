package gameObjects.Items;

import gameWorld.GameRoom;
import gameObjects.Hero;
import gameObjects.Entities.EntityItem;

import libraries.Vector2;

/**
 * 
 */
public class ItemBomb extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final String IMGPATH = "images/Bomb.png";

    /**
     * 
     * @param pos
     */
    public ItemBomb(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
    }

    /**
     * 
     */
    public void onHeroItemAction(Hero h) {
        h.addBomb();
    }
}
