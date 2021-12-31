package gameObjects.Items;

import gameObjects.Hero;
import gameObjects.Entities.EntityItem;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class ItemDime extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final int BUFFOF_COINS = 10;
    public static final String IMGPATH = "images/Dime.png";

    /**
     * 
     */
    public ItemDime(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
    }

    /**
     * 
     */
    public void onHeroItemAction(Hero h) {
        h.addCoins(BUFFOF_COINS);
    }

}
