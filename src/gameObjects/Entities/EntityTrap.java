package gameObjects.Entities;

import gameObjects.Hero;
import gameWorld.Game;
import gameWorld.GameCounter;
import gameWorld.GameRoom;
import libraries.Vector2;

/**
 * 
 */
public abstract class EntityTrap extends Entity {
    private int damage;
    private GameCounter counter;

    /**
     * 
     * @param pos
     * @param size
     * @param damage
     * @param imgPath
     */
    public EntityTrap(Vector2 pos, int damage, String imgPath) {
        super(pos, GameRoom.TILE_SIZE, imgPath);
        this.damage = damage;
        this.counter = new GameCounter(0.025);
    }

    /**
     * 
     * @param h
     */
    public void onHeroAdjacency(Hero h) {
        if (this.counter.isFinished()) {
            h.addDamage(this.damage);

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
        }
    }
}
