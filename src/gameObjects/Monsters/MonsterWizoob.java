package gameObjects.Monsters;

import gameAI.AI;

import java.util.List;
import java.util.LinkedList;

import gameObjects.Hero;
import gameObjects.Entities.EntityMonster;
import gameObjects.Projectiles.MonsterHeavyProjectile;
import gameObjects.Projectiles.MonsterLightProjectile;
import gameObjects.Projectiles.MonsterProjectile;
import gameWorld.GameCounter;
import gameWorld.GameRoom;

import libraries.Vector2;
import resources.Utils;

/**
 * 
 */
public class MonsterWizoob extends EntityMonster {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.75)
            .vectorMultiplication(new Vector2(1.0, 0.7));
    public static final double SPEED = 0.0035;
    public static final double RELOAD_SPEED = 0.01;
    public static final double MELEE_RELOAD_SPEED = 0.025;
    public static final double MELEE_EFFECT_POWER = 7.5;
    public static final double AGGRO_RANGE = GameRoom.TILE_DIST * 5.0;
    public static final int MELEE_DAMAGE = 1;
    public static final int HP = 8;
    public static final String IMGPATH = "images/Wizoob.png";

    private GameCounter reloadCounter;

    /**
     * 
     * @param pos
     */
    public MonsterWizoob(Vector2 pos) {
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
    }

    /**
     * 
     */
    public List<MonsterProjectile> fireProjectiles(Hero h) {
        if (this.reloadCounter.isFinished()) {
            List<MonsterProjectile> pL = new LinkedList<MonsterProjectile>();
            pL.add(new MonsterHeavyProjectile(
                    this.pos,
                    h.getPos().subVector(this.pos)));
            return pL;
        }
        return null;
    }
};
