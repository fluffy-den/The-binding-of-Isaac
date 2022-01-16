package gameObjects.Bosses;

import gameAI.AI;

import gameObjects.Hero;
import gameObjects.Entities.EntityBoss;
import gameObjects.Entities.EntityMonster;
import gameObjects.Entities.EntityTerrain;
import gameObjects.Monsters.MonsterFly;
import gameObjects.Projectiles.MonsterHeavyProjectile;
import gameObjects.Projectiles.MonsterLightProjectile;
import gameObjects.Projectiles.MonsterProjectile;
import gameWorld.GameCounter;

import gameWorld.GameRoom;

import java.util.LinkedList;
import java.util.List;

import libraries.Vector2;
import resources.DisplaySettings;
import resources.Utils;

public class BossDukeOfFlies extends EntityBoss {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(1);
    public static final double SPEED = 0.002;
    public static final double MELEE_RELOAD_SPEED = 0.040;
    public static final double MELEE_EFFECT_POWER = 5.;
    public static final double AGGRO_RANGE = 0.00;
    public static final double RELOAD_SPEED1 = 0.025;
    public static final double RELOAD_SPEED2 = 0.003;
    public static final double RAFALE_RELOAD_SPEED = 0.029;
    public static final double MONSTER_SPAWN_SPEED = 0.001; // 15s
    public static final int RAFALE_COUNT = 3;
    public static final int RAFALE_SIZE = 3;
    public static final int MAX_FIRE_ANGLE = 20;
    public static final int NUM_OF_MONSTERS1 = 3;
    public static final int NUM_OF_MONSTERS2 = 2;
    public static final int MELEE_DAMAGE = 4;
    public static final int HP = 150;
    public static final String IMGPATH = "images/TheDukeOfFlies.png";

    private List<EntityMonster> monsters;
    private GameCounter fireInCircleCounter;
    private GameCounter fireDirectCounter;
    private GameCounter rafaleCounter;
    private int rafaleNum;
    private GameCounter mobRespawnCounter; // 15s

    /**
     * 
     */
    public BossDukeOfFlies(Vector2 pos) {
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

        this.monsters = new LinkedList<EntityMonster>();
        this.fireInCircleCounter = new GameCounter(RELOAD_SPEED1);
        this.fireDirectCounter = new GameCounter(RELOAD_SPEED2);
        this.rafaleCounter = new GameCounter(RAFALE_RELOAD_SPEED);
        this.rafaleNum = 0;
        this.mobRespawnCounter = new GameCounter(MONSTER_SPAWN_SPEED);
    }

    /// Projectiles
    /**
     * 
     */
    @Override
    public List<MonsterProjectile> fireProjectiles(Hero h) {
        // <= 100% HP Tire en cercle
        LinkedList<MonsterProjectile> pL = new LinkedList<MonsterProjectile>();
        if (this.fireInCircleCounter.isFinished())
            pL.addAll(MonsterLightProjectile.generateProjectilesInCircle(this.pos, h, 12));

        double healthratio = (double) this.health / (double) HP;

        // <= 75%-50% < HP Tire 3 gros projectile en arc de cercle
        if (healthratio < 0.75 && healthratio >= 0.50 && this.fireDirectCounter.isFinished()) {
            for (int i = 0; i < RAFALE_SIZE; ++i) {
                pL.add(new MonsterHeavyProjectile(
                        this.pos,
                        MonsterHeavyProjectile.generateDir(
                                this.pos,
                                h.getPos(),
                                Math.toRadians(Utils.randomInt(-MAX_FIRE_ANGLE, MAX_FIRE_ANGLE)))));
            }
        }

        // <= 50% HP Tire 3 gros projectile en rafale de 3
        if (healthratio < 0.50) {
            if (this.fireDirectCounter.isFinished() || (this.rafaleNum != 0 && this.rafaleCounter.isFinished())) {
                for (int i = 0; i < RAFALE_SIZE; ++i) {
                    pL.add(new MonsterHeavyProjectile(
                            this.pos,
                            MonsterHeavyProjectile.generateDir(
                                    this.pos,
                                    h.getPos(),
                                    Math.toRadians(Utils.randomInt(-MAX_FIRE_ANGLE, MAX_FIRE_ANGLE)))));
                }
                this.rafaleNum++;
                if (this.rafaleNum > RAFALE_COUNT)
                    this.rafaleNum = 0;
            }
        }

        return pL;
    }

    /// Monsters
    /**
     * 
     * @return
     */
    @Override
    public List<EntityMonster> spawnMonsters(List<EntityTerrain> terrainList) {
        double healthratio = (double) this.health / (double) HP;
        // <= 75% HP Spawn 3 mouches qui suivent le boss
        if (this.health / HP < healthratio) {
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

            if (this.mobRespawnCounter.isFinished()) {
                int toSpawn = NUM_OF_MONSTERS1;

                // <= 50% En spawn 2 de plus
                if (this.health / HP < healthratio)
                    toSpawn += NUM_OF_MONSTERS2;
                toSpawn -= this.monsters.size();

                LinkedList<EntityMonster> spawned = new LinkedList<EntityMonster>();
                for (int k = 0; k < toSpawn; ++k) {
                    // On spawn autour du boss
                    double dist = MonsterFly.SIZE.distance(SIZE);
                    double angle = Math.toRadians(Utils.randomInt(0, 359));
                    spawned.add(new MonsterFly(new Vector2(
                            this.getPos().getX() + dist * Math.cos(angle),
                            this.getPos().getY() + dist * Math.sin(angle))));
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
        double healthratio = (double) this.health / (double) HP;
        if (healthratio < 0.05)
            this.monsterAI.setAggroRange(1.0);
    }
}
