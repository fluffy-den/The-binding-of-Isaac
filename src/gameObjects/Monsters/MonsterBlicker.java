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

/**
 * 
 */
public class MonsterBlicker extends EntityMonster {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.75)
            .vectorMultiplication(new Vector2(1.0, 0.7));
    public static final double SPEED = 0.002;
    public static final double MELEE_RELOAD_SPEED = 0.040;
    public static final double RELOAD_SPEED = 0.040;
    public static final double FIRING_MAX_ANGLE = 5;
    public static final double FIRING_MIN_ANGLE = 5;
    public static final double MELEE_EFFECT_POWER = 5.;
    public static final double AGGRO_RANGE = GameRoom.TILE_DIST * 3.00;
    public static final int MELEE_DAMAGE = 1;
    public static final int HP = 7;
    public static final String IMGPATH = "images/Blicker.png";

    private GameCounter reloadCounter;

    /**
     * 
     */
    public MonsterBlicker(Vector2 pos) {
        super(
                pos,
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
            double angle = Utils.randomDouble(FIRING_MIN_ANGLE, FIRING_MAX_ANGLE);
            Vector2 dir = h.getPos().subVector(this.getPos());
            pL.add(new MonsterLightProjectile(
                    this.pos,
                    new Vector2(
                            dir.getX() * Math.cos(angle),
                            dir.getY() * Math.sin(angle))));
            return pL;
        }

        return null;
    }
}
