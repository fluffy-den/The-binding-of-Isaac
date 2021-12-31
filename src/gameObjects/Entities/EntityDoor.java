package gameObjects.Entities;

import gameObjects.Hero;
import gameWorld.Game;
import gameWorld.GameRoom;
import libraries.Vector2;

/**
 * 
 */
public abstract class EntityDoor extends Entity {
    private double reloadSpeed;
    private long lastHitFrame;

    /**
     * 
     * @param pos
     * @param size
     * @param imgPath
     */
    public EntityDoor(Vector2 pos, String imgPath) {
        super(pos, GameRoom.TILE_SIZE, imgPath);
        this.reloadSpeed = 0.025;
        this.lastHitFrame = 0;
    }

    /**
     * 
     * @param h
     */
    public void onHeroAdjacency(Hero h) {
        long elapsed = Game.getImageNum() - this.lastHitFrame;
        if (elapsed * this.reloadSpeed >= 1) {

            Vector2 hpos = h.getPos();
            double minX = this.pos.getX() - this.size.getX();
            double maxX = this.pos.getX() + this.size.getX();
            double minY = this.pos.getY() - this.size.getY();
            double maxY = this.pos.getY() + this.size.getY();

            // Le hero est-il dans cet objet?
            if (hpos.getX() >= minX && hpos.getX() <= maxX
                    && hpos.getY() >= minY && hpos.getY() <= maxY) {
                Vector2 newPos = this.pos.subVector(hpos).scalarMultiplication(0.5);
                h.setPos(hpos.subVector(newPos));
            }

            this.lastHitFrame = Game.getImageNum();
        }
    }
}
