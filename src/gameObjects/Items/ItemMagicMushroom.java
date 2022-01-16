package gameObjects.Items;

import gameObjects.Entities.EntityItem;
import gameObjects.Hero;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class ItemMagicMushroom extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final int BUFFOF_MAXHEALTH = 1;
    public static final int BUFFOF_DAMAGE = 1;
    public static final int BUFFOF_HEALTH = Integer.MAX_VALUE;
    public static final double BUFFOF_RANGE = 0.05;
    public static final String IMGPATH = "images/MagicMushroom.png";

    /**
     * 
     */
    public ItemMagicMushroom(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
    }

    /**
     * 
     */
    public void onHeroItemAction(Hero h) {
        h.addMaxHPs(BUFFOF_MAXHEALTH);
        h.addTearDamage(BUFFOF_DAMAGE);
        h.addHPs(BUFFOF_HEALTH);
        h.addTearRange(BUFFOF_RANGE);
    }
}
