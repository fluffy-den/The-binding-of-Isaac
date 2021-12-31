package gameObjects.Items;

import gameWorld.GameRoom;

import gameObjects.Entities.EntityDoor;
import gameObjects.Entities.EntityItem;
import gameObjects.Hero;

import libraries.Vector2;

/**
 * 
 */
public class ItemKey extends EntityItem {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final String IMGPATH = "images/Key.png";

    private EntityDoor door;

    /**
     * 
     */
    public ItemKey(Vector2 pos, EntityDoor door) {
        super(pos, SIZE, IMGPATH);
        this.door = door;
    }

    /**
     * 
     */
    public void onHeroItemAction(Hero h) {
        // TODO: @cypri3 this.door devient une porte ouverte
        // -> à la génération de la clef, la porte devient fermée
    }
}
