package gameObjects.Monsters;

import gameAI.AI;

import java.util.List;
import java.util.LinkedList;

import gameObjects.Hero;
import gameObjects.Entities.EntityMonster;
import gameObjects.Projectiles.MonsterLightProjectile;
import gameObjects.Projectiles.MonsterProjectile;
import gameWorld.GameCounter;
import gameWorld.GameRoom;

import libraries.Vector2;
import resources.Utils;

public class MonsterGaper extends EntityMonster {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.75)
            .vectorMultiplication(new Vector2(1.0, 0.7));
    public static final double SPEED = 0.002;
    public static final double MELEE_RELOAD_SPEED = 0.040;
    public static final double RELOAD_SPEED = 0.005;
    public static final double RAFALE_RELOAD_SPEED = 0.05;
    public static final double MELEE_EFFECT_POWER = 5.;
    public static final double AGGRO_RANGE = 0.00;
    public static final int FIRING_MAX_ANGLE = 10;
    public static final int FIRING_MIN_ANGLE = -10;
    public static final int RAFALE_SIZE = 3;
    public static final int RAFALE_COUNT = 3;
    public static final int MELEE_DAMAGE = 4;
    public static final int HP = 12;
    public static final String IMGPATH = "images/Gaper.png";

    private GameCounter reloadCounter;
    private GameCounter rafaleCounter;
    int rafaleNum = 0;

    /**
     * 
     * @param pos
     */
    public MonsterGaper(Vector2 pos) {
        super(pos,
                SIZE,
                SPEED,
                false,
                HP,
                MELEE_DAMAGE,
                MELEE_EFFECT_POWER,
                MELEE_RELOAD_SPEED,
                IMGPATH,
                new AI(pos, AGGRO_RANGE));

        this.reloadCounter = new GameCounter(RELOAD_SPEED);
        this.rafaleCounter = new GameCounter(RAFALE_RELOAD_SPEED);

    }

    /**
     * 
     */
    public List<MonsterProjectile> fireProjectiles(Hero h) {
        // Tirre 3 balles en rafale
        if (this.reloadCounter.isFinished() || (this.rafaleNum != 0 && this.rafaleCounter.isFinished())) {
            LinkedList<MonsterProjectile> pL = new LinkedList<MonsterProjectile>();
            for (int i = 0; i < RAFALE_SIZE; ++i) {
                pL.add(new MonsterLightProjectile(
                        this.pos,
                        generateDir(this.pos, h.getPos(), Utils.randomInt(FIRING_MIN_ANGLE, FIRING_MAX_ANGLE))));
            }
            this.rafaleNum++;
            if (this.rafaleNum > RAFALE_COUNT)
                this.rafaleNum = 0;
            return pL;
        }

        return null;
    }
}
