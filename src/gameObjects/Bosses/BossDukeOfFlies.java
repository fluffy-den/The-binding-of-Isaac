package gameObjects.Bosses;

import gameAI.AI;

import gameObjects.Hero;
import gameObjects.Entities.EntityBoss;
import gameObjects.Entities.EntityMonster;
import gameObjects.Monsters.MonsterFly;
import gameObjects.Projectiles.MonsterProjectile;
import gameWorld.GameCounter;

import gameWorld.GameRoom;

import java.util.LinkedList;
import java.util.List;

import javax.management.QueryExp;

import libraries.Vector2;

public class BossDukeOfFlies extends EntityBoss {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(1);
    public static final double SPEED = 0.002;
    public static final double MELEE_RELOAD_SPEED = 0.040;
    public static final double MELEE_EFFECT_POWER = 5.;
    public static final double AGGRO_RANGE = 0.00;
    public static final double RELOAD_SPEED1 = 0.035;
    public static final double RELOAD_SPEED2 = 0.022;
    public static final double RAFALE_RELOAD_SPEED = 0.029;
    public static final double MONSTER_SPAWN_SPEED = 0.002; // 15s
    public static final int NUM_OF_MONSTERS1 = 3;
    public static final int NUM_OF_MONSTERS2 = 2;
    public static final int MELEE_DAMAGE = 1;
    public static final int HP = 150;
    public static final String IMGPATH = "images/TheDukeOfFlies.png";

    private List<EntityMonster> monsters;
    private GameCounter fireInCircleCounter;
    private GameCounter fireDirectCounter;
    private GameCounter rafaleCounter;
    private GameCounter mobRespawnCounter; // 15s

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

        this.monsters = new LinkedList<EntityMonster>();
        this.fireInCircleCounter = new GameCounter(RELOAD_SPEED1);
        this.fireDirectCounter = new GameCounter(RELOAD_SPEED2);
        this.rafaleCounter = new GameCounter(RAFALE_RELOAD_SPEED);
        this.mobRespawnCounter = new GameCounter(MONSTER_SPAWN_SPEED);
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
        // <= 75% HP Spawn 3 mouches qui suivent le boss
        if (this.health / HP < 0.75) {
            // Mis à jour de l'état des monstres
            int i = 0;
            while (i < this.monsters.size()) {
                EntityMonster m = this.monsters.get(i);
                if (!m.isLiving()) {
                    this.monsters.remove(i);
                    --i;
                }
                ++i;
            }

            // <= 50% En spawn 2 de plus
            if (this.mobRespawnCounter.isFinished()) {
                int toSpawn = NUM_OF_MONSTERS1;
                if (this.health / HP < 0.50)
                    toSpawn += NUM_OF_MONSTERS2;
                toSpawn -= this.monsters.size();

                LinkedList<EntityMonster> spawned = new LinkedList<EntityMonster>();
                for (int k = 0; k < toSpawn; ++i) {
                    spawned.add(new MonsterFly(this.pos));
                }
                this.monsters.addAll(spawned);
                return spawned;
            }
        }
        return null;

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
