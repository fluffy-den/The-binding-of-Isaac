package gameObjects.Items;

import gameObjects.Hero;
import gameObjects.Entities.EntityItem;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class ItemPentagram extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final double BUFFOF_SPEED = 0.0025;
    public static final double BUFFOF_RELOAD_SPEED = 0.005;
    public static String IMGPATH = "images/Pentagram.png";

    /**
     * 
     * @param pos
     */
    public ItemPentagram(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
    }

    /**
     * 
     * @param h
     */
    public void onHeroItemAction(Hero h) {
        h.addSpeed(BUFFOF_SPEED);
        h.addTearReloadSpeed(BUFFOF_RELOAD_SPEED);
    }
}
