package gameObjects.Entities;

import gameObjects.Hero;
import gameObjects.Doors.DoorPlacement;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public abstract class EntityDoor extends Entity {
    // Adjacent & bouton vers le haut
    private DoorPlacement placement;
    private GameRoom room;
    private boolean isOpened;

    /**
     * 
     * @param pos
     * @param imgPath
     * @param doorPlacement
     */
    public EntityDoor(Vector2 pos, String imgPath, DoorPlacement doorPlacement) {
        super(pos, GameRoom.TILE_SIZE, imgPath);
    }

    /**
     * 
     * @return
     */
    public GameRoom getRoom() {
        return this.room;
    }

    /**
     * 
     * @param h
     */
    public void onHeroAdjacency(Hero h) {
        if (this.isOpened) {
            switch (this.placement) {
                case DOOR_UP: {

                    break;
                }

                case DOOR_DOWN: {

                    break;
                }

                case DOOR_LEFT: {

                    break;
                }

                case DOOR_RIGHT: {

                    break;
                }

                default:
                    assert (false);
            }
        }
    }
}
