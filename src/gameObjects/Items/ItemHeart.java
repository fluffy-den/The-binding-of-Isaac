package gameObjects.Items;

import gameObjects.Hero;
import gameObjects.Entities.EntityItem;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class ItemHeart extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final int BUFFOF_MAXHP = 2;
    public static final int BUFFOF_HP = Integer.MAX_VALUE;
    public static final String IMGPATH = "images/Heart.png";

    /**
     * 
     * @param pos
     * @param size
     */
    public ItemHeart(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
    }

    /**
     * 
     */
    public void onHeroItemAction(Hero h) {
        h.addMaxHPs(BUFFOF_MAXHP);
        h.addHPs(BUFFOF_HP);
    }
}
