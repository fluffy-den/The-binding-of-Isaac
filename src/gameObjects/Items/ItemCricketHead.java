package gameObjects.Items;

import gameObjects.Entities.EntityItem;

import gameWorld.GameRoom;
import gameObjects.Hero;

import libraries.Vector2;

/**
 * 
 */
public class ItemCricketHead extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final int BUFFOF_DAMAGE = 1;
    public static final String IMGPATH = "images/CricketsHead.png";

    /**
     * 
     * @param pos
     */
    public ItemCricketHead(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
    }

    /**
     * 
     */
    public void onHeroItemAction(Hero h) {
        h.addDamage(BUFFOF_DAMAGE);
    }
}
