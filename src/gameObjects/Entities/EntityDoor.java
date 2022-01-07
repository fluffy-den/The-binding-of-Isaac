package gameObjects.Entities;

import gameObjects.Hero;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public abstract class EntityDoor extends Entity {
    // Adjacent & bouton vers le haut
    private boolean isOpened;
    private boolean isKeyLocked;
    private boolean isSecret;

    /**
     * 
     * @param pos         Position de la porte
     * @param imgPath     Image de la porte
     * @param isOpened    True si ouverte false sinon
     * @param isKeyLocked True si fermée false sinon
     * @param isSecret    True si sectète false sinon
     */
    public EntityDoor(Vector2 pos, String imgPath, boolean isOpened, boolean isKeyLocked,
            boolean isSecret) {
        super(pos, GameRoom.TILE_SIZE, imgPath);
        this.isOpened = isOpened;
        this.isKeyLocked = isKeyLocked;
        this.isSecret = isSecret;
    }

    /**
     * 
     * @param h
     */
    public boolean onHeroAdjacency(Hero h) {
        if (this.isKeyLocked) {
            // TODO si le heros a une clé ouvrir et en retirer une
        }
        if (this.isOpened) {
            return true;
        }
        return false;
    }
}
