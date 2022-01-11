package gameObjects.Entities;

import gameWorld.GameCounter;
import gameWorld.GameRoom;
import libraries.Vector2;

public class EntityBomb extends Entity {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.5);
    public static final Vector2 EXPLOSION_SIZE = GameRoom.TILE_SIZE;
    public static final int EXPLOSION_RANGE = 2;
    public static final int EXPLOSION_DAMAGE = 20;
    public static final double EXPLOSION_DURATION = 0.25; // En images/s
    public static final String IMGPATH = "images/Bomb.png";

    private GameCounter counter; // Le temps que met la bombe à exploser (en images)

    private int damage;
    private int range;

    /**
     * 
     * @param pos
     * @param range
     * @param damage
     * @param damagesOnLiving
     */
    public EntityBomb(Vector2 pos) {
        super(pos, SIZE, IMGPATH);
        this.counter = new GameCounter(0.25); // Drop de bombe toutes les 10 images
        this.range = EXPLOSION_RANGE;
        this.damage = EXPLOSION_DAMAGE;
    }

    /**
     * 
     * @return
     */
    public boolean isTimerOver() {
        return this.counter.isFinished();
    }

    /**
     * 
     * @return
     */
    public EntityExplosion explode() {
        // On cree une nouvelle entite pour pouvoir l'afficher le temps voulu
        return new EntityExplosion(new Vector2(this.pos), GameRoom.TILE_SIZE.scalarMultiplication(range),
                EXPLOSION_DURATION);
    }

    /// Dommages
    /**
     * 
     * @param l
     */
    public void addDamage(EntityLiving l) {
        l.addDamage(this.damage);
    }
}
