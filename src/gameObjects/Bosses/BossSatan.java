package gameObjects.Bosses;

import java.util.LinkedList;
import java.util.List;

import gameAI.AI;

import gameObjects.Hero;

import gameObjects.Entities.EntityTerrain;
import gameObjects.Entities.EntityMonster;
import gameObjects.Monsters.MonsterDeathHead;
import gameObjects.Projectiles.MonsterHeavyProjectile;
import gameObjects.Projectiles.MonsterLightProjectile;
import gameObjects.Projectiles.MonsterProjectile;
import gameObjects.Entities.EntityBoss;

import gameWorld.GameCounter;
import gameWorld.GameRoom;

import libraries.Vector2;
import resources.Utils;

/**
 * 
 */
public class BossSatan extends EntityBoss {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(1);
    public static final double SPEED = 0.002;
    public static final double MELEE_RELOAD_SPEED = 0.040;
    public static final double MELEE_EFFECT_POWER = 5.;
    public static final double AGGRO_RANGE = 0.00;
    public static final double MONSTER_SPAWN_SPEED = 0.001;
    public static final double RELOAD_SPEED1 = 0.020;
    public static final double RELOAD_SPEED2 = 0.005;
    public static final double RAFALE_SPEED2 = 0.020;
    public static final double RELOAD_SPEED3 = 0.080;
    public static final int NUM_OF_DEATHHEADS = 3;
    public static final int SIZE_OF_RAFALE2 = 3;
    public static final int COUNT_OF_RAFALE2 = 3;
    public static final int ANGLE_OF_SHOTS2 = 10;
    public static final int MELEE_DAMAGE = 8;
    public static final int NUM_OF_SHOTS3 = 4;
    public static final int HP = 250;
    public static final String IMGPATH = "images/Satan.png";

    private LinkedList<EntityMonster> deathheads;
    private GameCounter spawnCounter;
    private GameCounter reloadCounter1;

    private GameCounter reloadCounter2;
    private GameCounter rafaleCounter2;
    int rafale2;

    private GameCounter reloadCounter3;

    /**
     * 
     */
    public BossSatan(Vector2 pos) {
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

        this.deathheads = new LinkedList<EntityMonster>();
        this.spawnCounter = new GameCounter(MONSTER_SPAWN_SPEED);
        this.reloadCounter1 = new GameCounter(RELOAD_SPEED1);
        this.reloadCounter2 = new GameCounter(RELOAD_SPEED2);
        this.rafaleCounter2 = new GameCounter(RAFALE_SPEED2);
        this.rafale2 = 0;
        this.reloadCounter3 = new GameCounter(RELOAD_SPEED3);
    }

    /**
     * 
     */
    @Override
    public List<MonsterProjectile> fireProjectiles(Hero h) {
        LinkedList<MonsterProjectile> pL = new LinkedList<MonsterProjectile>();
        double healthratio = (double) this.health / (double) HP;

        // 1. Fire circle (of light projectiles)
        if (this.reloadCounter1.isFinished()) {
            pL.addAll(MonsterLightProjectile.generateProjectilesInCircle(
                    this.pos,
                    h,
                    16));
        }

        // 2. Fire 3 big balls of 3
        if (healthratio <= 0.75 && healthratio > 0.45) {
            if (this.reloadCounter2.isFinished() || (this.rafale2 != 0 && this.rafaleCounter2.isFinished())) {
                for (int i = 0; i < SIZE_OF_RAFALE2; ++i) {
                    pL.add(new MonsterHeavyProjectile(
                            this.pos,
                            MonsterHeavyProjectile.generateDir(
                                    this.pos,
                                    h.getPos(),
                                    Math.toRadians(Utils.randomInt(-ANGLE_OF_SHOTS2 / 2, ANGLE_OF_SHOTS2 / 2)))));
                }
                ++this.rafale2;
                if (this.rafale2 > COUNT_OF_RAFALE2)
                    this.rafale2 = 0;
            }
        }

        // 3. Fire 4 big balls in random position
        if (healthratio <= 0.45) {
            if (this.reloadCounter3.isFinished()) {
                for (int i = 0; i < NUM_OF_SHOTS3; ++i) {
                    double angle = Math.toRadians(Utils.randomInt(0, 359));
                    pL.add(new MonsterHeavyProjectile(
                            this.pos,
                            new Vector2(
                                    Math.cos(angle),
                                    Math.sin(angle))));
                }
            }
        }

        return pL;
    }

    /**
     * 
     */
    @Override
    public List<EntityMonster> spawnMonsters(List<EntityTerrain> terrainList) {
        // <= 75% HP Spawn 3 têtes
        double healthratio = (double) this.health / (double) HP;
        if (healthratio / HP < 0.75) {
            // Mis à jour de l'état des monstres
            int i = 0;
            while (i < this.deathheads.size()) {
                EntityMonster m = this.deathheads.get(i);
                if (!m.isLiving()) {
                    this.deathheads.remove(i);
                    --i;
                }
                ++i;
            }

            // Le temps de respawn est finit?
            LinkedList<EntityMonster> spawned = new LinkedList<EntityMonster>();
            if (this.spawnCounter.isFinished()) {
                int toSpawn = NUM_OF_DEATHHEADS - this.deathheads.size();
                for (int k = 0; k < toSpawn; ++k) {
                    // On spawn autour du boss
                    double dist = MonsterDeathHead.SIZE.distance(SIZE);
                    double angle = Math.toRadians(Utils.randomInt(0, 359));
                    spawned.add(new MonsterDeathHead(new Vector2(
                            this.getPos().getX() + dist * Math.cos(angle),
                            this.getPos().getY() + dist * Math.sin(angle))));
                }
                this.deathheads.addAll(spawned);
                return spawned;
            }
        }
        return null;
    }

    /**
     * 
     */
    /// Damage Event
    public void addDamage(int damage) {
        super.addDamage(damage);

        // <= 10% HP devient aggro
        double healthratio = (double) this.health / (double) HP;
        if (healthratio <= 0.10)
            this.monsterAI.setAggroRange(1.0);
    }
}
