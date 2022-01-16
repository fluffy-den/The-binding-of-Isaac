package gameObjects.Bosses;

import java.util.List;
import java.util.LinkedList;

import gameAI.AI;

import gameObjects.Entities.EntityMonster;
import gameObjects.Entities.EntityTerrain;
import gameObjects.Monsters.MonsterConjoinedFaty;
import gameObjects.Monsters.MonsterFaty;
import gameObjects.Monsters.MonsterParabite;
import gameObjects.Projectiles.MonsterHeavyProjectile;
import gameObjects.Projectiles.MonsterLightProjectile;
import gameObjects.Projectiles.MonsterProjectile;
import gameObjects.Entities.EntityBoss;
import gameObjects.Hero;

import gameWorld.GameCounter;
import gameWorld.GameRoom;

import libraries.Vector2;
import resources.Utils;

/**
 * 
 */
public class BossMegaFaty extends EntityBoss {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(1);
    public static final double SPEED = 0.002;
    public static final double MELEE_RELOAD_SPEED = 0.040;
    public static final double MELEE_EFFECT_POWER = 5.;
    public static final double AGGRO_RANGE = 0.00;
    public static final double MONSTER_SPAWN_SPEED = 0.001;
    public static final double RELOAD_SPEED1 = 0.004;
    public static final double RELOAD_SPEED2 = 0.08;
    public static final double RAFALE_RELOAD_SPEED = 0.01;
    public static final int NUM_OF_FATIES = 2;
    public static final int NUM_OF_CONJOINED = 1;
    public static final int RAFALE1_SIZE = 6;
    public static final int RAFALE1_COUNT = 4;
    public static final int RAFALE2_SIZE = 2;
    public static final int FIRING_ANGLE = 5;
    public static final int MELEE_DAMAGE = 6;
    public static final int HP = 200;
    public static final String IMGPATH = "images/MegaFaty.png";

    private LinkedList<EntityMonster> faties;
    private EntityMonster conjoined;
    private GameCounter spawnCounter;
    private GameCounter fire1Counter;
    private GameCounter rafale1Counter;
    private GameCounter fire2Counter;
    private int rafale1Num;

    /**
     * 
     * @param pos
     */
    public BossMegaFaty(Vector2 pos) {
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

        this.faties = new LinkedList<EntityMonster>();
        this.conjoined = null;
        this.spawnCounter = new GameCounter(MONSTER_SPAWN_SPEED);
        this.rafale1Num = 0;
        this.fire1Counter = new GameCounter(RELOAD_SPEED1);
        this.rafale1Counter = new GameCounter(RAFALE_RELOAD_SPEED);
        this.fire2Counter = new GameCounter(RELOAD_SPEED2);
    }

    /// Projectiles
    /**
     * 
     */
    public List<MonsterProjectile> fireProjectiles(Hero h) {
        double healthratio = (double) this.health / (double) HP;

        // <= 100% HP Tire en cercle sur le joueur
        LinkedList<MonsterProjectile> pL = new LinkedList<MonsterProjectile>();
        if (this.fire1Counter.isFinished() || (rafale1Num != 0 && this.rafale1Counter.isFinished())) {
            for (int i = -RAFALE1_SIZE / 2; i < RAFALE1_SIZE / 2; ++i) {
                pL.add(new MonsterHeavyProjectile(
                        this.pos,
                        MonsterHeavyProjectile.generateDir(
                                this.pos,
                                h.getPos(),
                                Math.toRadians(Utils.randomInt(
                                        (i - 1) * FIRING_ANGLE, (i + 1) * FIRING_ANGLE)))));
            }
            ++rafale1Num;
            if (this.rafale1Num > RAFALE1_COUNT)
                this.rafale1Num = 0;
        }

        // <= 35% Tirre de manière aléatoire des heaby balls
        if (healthratio < 0.35) {
            if (this.fire2Counter.isFinished()) {
                for (int i = 0; i < RAFALE2_SIZE; ++i) {
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

    /// Monsters
    /**
     * 
     */
    @Override
    public List<EntityMonster> spawnMonsters(List<EntityTerrain> terrainList) {
        boolean timerFinished = this.spawnCounter.isFinished();
        LinkedList<EntityMonster> spawned = null;

        // <= 75% HP Spawn 2 Faty
        double healthratio = (double) this.health / (double) HP;
        if (healthratio < 0.75) {
            // Mis à jour de l'état des monstres
            int i = 0;
            while (i < this.faties.size()) {
                EntityMonster m = this.faties.get(i);
                if (!m.isLiving()) {
                    this.faties.remove(i);
                    --i;
                }
                ++i;
            }

            // Le temps de respawn est finit?
            if (timerFinished) {
                spawned = new LinkedList<EntityMonster>();
                int toSpawn = NUM_OF_FATIES - this.faties.size();
                for (int k = 0; k < toSpawn; ++k) {
                    // On spawn autour du boss
                    double dist = MonsterFaty.SIZE.distance(SIZE);
                    Vector2 mpos;
                    double angle = Math.toRadians(Utils.randomInt(0, 359));
                    mpos = new Vector2(
                            this.getPos().getX() + dist * Math.cos(angle),
                            this.getPos().getY() + dist * Math.sin(angle));
                    spawned.add(new MonsterFaty(mpos));
                }
                this.faties.addAll(spawned);
            }
        }

        // <= 50% HP Spawn a conjoined Faty
        if (healthratio < 0.50) {
            if (this.conjoined != null && !this.conjoined.isLiving())
                this.conjoined = null;

            // Le temps de respawn est finit?
            if (timerFinished) {
                if (this.conjoined == null) {
                    double dist = MonsterConjoinedFaty.SIZE.distance(SIZE);
                    double angle = Math.toRadians(Utils.randomInt(0, 359));
                    Vector2 mpos = new Vector2(
                            this.getPos().getX() + dist * Math.cos(angle),
                            this.getPos().getY() + dist * Math.sin(angle));
                    this.conjoined = new MonsterConjoinedFaty(this.pos);
                    spawned.add(this.conjoined);
                }
            }
        }

        return spawned;
    }

    /**
     * 
     */
    /// Damage Event
    @Override
    public void addDamage(int damage) {
        super.addDamage(damage);

        // <= 10% HP devient aggro
        double healthratio = (double) this.health / (double) HP;
        if (healthratio < 0.10)
            this.monsterAI.setAggroRange(1.0);
    }
}
