package gameObjects.Entities;

import gameWorld.Game;
import libraries.Vector2;
import resources.DisplaySettings;

public class EntityBomb extends Entity {
    public static final String IMGPATH = "images/Bomb.png";

    private int range; // En tiles (touche horizontalement et verticalement les rochers et entités
                       // vivantes)
    private int damage;
    private long timer; // En cycles par secondes
    private long lasttp;

    /**
     * 
     * @param pos
     * @param size
     * @param range
     * @param damagesOnLiving
     */
    public EntityBomb(Vector2 pos, Vector2 size, int range, int damages, long timer) {
        super(pos, size, IMGPATH);
        this.range = range;
        this.damage = damages;
        this.timer = timer * DisplaySettings.FRAME_PER_SECOND;
        this.lasttp = Game.getImageNum();
    }

    /**
     * 
     * @return
     */
    public boolean isTimerOver() {
        long tp = Game.getImageNum(); // TODO: GameCounter
        long elapsed = tp - this.lasttp;
        this.timer -= elapsed;
        if (this.timer > 0)
            return true;
        this.lasttp = tp;
        return false;
    }
}
