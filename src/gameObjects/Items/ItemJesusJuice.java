package gameObjects.Items;

import gameObjects.Hero;
import gameObjects.Entities.EntityItem;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class ItemJesusJuice extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final int BUFFOF_DAMAGE = 1;
    public static final double BUFFOF_RANGE = 0.015;
    public static final String IMGPATH = "images/JesusJuice.png";

    /**
     * 
     */
    public ItemJesusJuice(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
    }

    /**
     * 
     * @param h
     */
    public void onHeroItemAction(Hero h) {
        h.addTearDamage(BUFFOF_DAMAGE);
        h.addTearRange(BUFFOF_RANGE);
    }
}
