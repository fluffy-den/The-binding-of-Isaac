package gameObjects.Bosses;

import gameAI.AI;

import gameObjects.Hero;
import gameObjects.Entities.EntityBoss;
import gameObjects.Entities.EntityMonster;
import gameObjects.Projectiles.MonsterProjectile;
import gameWorld.GameCounter;

import gameWorld.GameRoom;

import java.util.List;

import libraries.Vector2;

public class BossDukeOfFlies extends EntityBoss {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(1);
    public static final double SPEED = 0.002;
    public static final double MELEE_RELOAD_SPEED = 0.040;
    public static final double MELEE_EFFECT_POWER = 5.;
    public static final double AGGRO_RANGE = 0.00;
    public static final int MELEE_DAMAGE = 1;
    public static final int HP = 150;
    public static final String IMGPATH = "images/TheDukeOfFlies.png";

    private GameCounter fireInCircleCounter;
    private GameCounter fireDirectCounter;
    private GameCounter mobRespawnCounter; // 15s
    private int stage;

    /**
     * 
     */
    public BossDukeOfFlies(Vector2 pos) {
        super(
                pos,
                SIZE,
                SPEED,
                true,
                HP,
                MELEE_DAMAGE,
                MELEE_EFFECT_POWER,
                MELEE_RELOAD_SPEED,
                IMGPATH,
                new AI(pos, AGGRO_RANGE));

        this.stage = 0;
    }

    /// Projectiles
    /**
     * 
     */
    public List<MonsterProjectile> fireProjectiles(Hero h) {
        // <= 100% HP Tire en cercle

        // <= 75%-50% < HP Tire 3 gros projectile en arc de cercle

        // <= 50% HP Tire 3 gros projectile en rafale de 3

        return null;
    }

    /// Monsters
    /**
     * 
     * @return
     */
    public List<EntityMonster> spawnMonsters() {
        return null;

        // <= 75% HP Spawn 3 mouches qui suivent le boss
    }

    /**
     * 
     */
    /// Damage Event
    @Override
    public void addDamage(int damage) {
        super.addDamage(damage);

        // <= 5% HP devient aggro
        this.monsterAI.setAggroRange(1.0);
    }
}
