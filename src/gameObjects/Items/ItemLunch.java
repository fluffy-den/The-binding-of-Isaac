package gameObjects.Items;

import gameObjects.Hero;
import gameObjects.Entities.EntityItem;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class ItemLunch extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final double BUFFOF_RELOAD_SPEED = -0.0005;
    public static final double DEBUFFOF_RANGE = -0.008;
    public static final String IMGPATH = "images/Lunch.png";

    /**
     * 
     * @param pos
     */
    public ItemLunch(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
    }

    /**
     * 
     * @param h
     */
    public void onHeroItemAction(Hero h) {
        h.addTearRange(DEBUFFOF_RANGE);
        h.addTearReloadSpeed(BUFFOF_RELOAD_SPEED);
    }
}
