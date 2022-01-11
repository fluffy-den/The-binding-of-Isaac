package gameObjects.Entities;

import gameWorld.GameCounter;
import libraries.Vector2;

public class EntityExplosion extends Entity {
    public static final String IMGPATH = "images/Explosion.png";

    private GameCounter duration;

    /**
     * 
     */
    public EntityExplosion(Vector2 pos, Vector2 size, double duration) {
        super(pos, size, IMGPATH);
        this.duration = new GameCounter(duration);
    }

    /**
     * 
     */
    public boolean isFinished() {
        return this.duration.isFinished();
    }
}
