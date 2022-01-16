package gameObjects.Bosses;

import java.util.LinkedList;
import java.util.List;

import gameAI.AI;

import gameObjects.Entities.EntityTerrain;
import gameObjects.Entities.EntityMonster;
import gameObjects.Monsters.MonsterDeathHead;
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
    public static final int NUM_OF_DEATHHEADS = 3;
    public static final int MELEE_DAMAGE = 8;
    public static final int HP = 250;
    public static final String IMGPATH = "images/Satan.png";

    private LinkedList<EntityMonster> deathheads;
    private GameCounter spawnCounter;

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
    }

    /**
     * 
     */
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
        if (healthratio <= 0.15)
            this.monsterAI.setAggroRange(1.0);
    }
}
