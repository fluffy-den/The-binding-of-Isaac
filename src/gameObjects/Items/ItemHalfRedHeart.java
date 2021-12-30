package gameObjects.Items;

import gameObjects.Hero;
import gameObjects.Entities.EntityItem;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class ItemHalfRedHeart extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final int BUFFOF_HP = 1;
    public static final String IMGPATH = "images/HalfRedHeart.png";

    /**
     * 
     * @param pos
     */
    public ItemHalfRedHeart(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
    }

    /**
     * 
     */
    public void onHeroItemAction(Hero h) {
        h.addHPs(BUFFOF_HP);
    }
}
