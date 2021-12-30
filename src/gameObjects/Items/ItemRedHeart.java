package gameObjects.Items;

import gameObjects.Hero;
import gameObjects.Entities.EntityItem;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class ItemRedHeart extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final int BUFFOF_HP = 2;
    public static final String IMGPATH = "images/redHeart.png";

    /**
     * 
     */
    public ItemRedHeart(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
    }

    /**
     * 
     */
    public void onHeroItemAction(Hero h) {
        h.addHPs(BUFFOF_HP);
    }
}
