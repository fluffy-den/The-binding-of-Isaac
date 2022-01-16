package gameObjects.Items;

import gameObjects.Hero;
import gameObjects.Entities.EntityItem;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class ItemStigmata extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final int BUFFOF_DAMAGE = 1;
    public static final int BUFFOF_MAXHEALTH = 1;
    public static String IMGPATH = "images/Stigmata.png";

    /**
     * 
     * @param pos
     */
    public ItemStigmata(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
    }

    /**
     * 
     */
    public void onHeroItemAction(Hero h) {
        h.addMaxHPs(BUFFOF_MAXHEALTH);
        h.addTearDamage(BUFFOF_DAMAGE);
    }
}
