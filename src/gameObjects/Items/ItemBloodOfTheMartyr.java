package gameObjects.Items;

import gameObjects.Hero;
import gameObjects.Entities.EntityItem;
import gameWorld.GameRoom;
import libraries.Vector2;

public class ItemBloodOfTheMartyr extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final int BUFFOF_TEAR_DAMAGE = 1;
    public static final String IMGPATH = "images/BloodOfTheMartyr.png";

    /**
     * 
     * @param pos
     * @param size
     */
    public ItemBloodOfTheMartyr(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
    }

    /**
     * 
     */
    public void onHeroItemAction(Hero h) {
        h.addTearDamage(BUFFOF_TEAR_DAMAGE);
    }
}
