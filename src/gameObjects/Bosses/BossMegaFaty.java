package gameObjects.Bosses;

import java.util.List;
import java.util.LinkedList;

import gameAI.AI;

import gameObjects.Entities.EntityMonster;
import gameObjects.Entities.EntityTerrain;
import gameObjects.Monsters.MonsterConjoinedFaty;
import gameObjects.Monsters.MonsterFaty;
import gameObjects.Entities.EntityBoss;

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
    public static final int NUM_OF_FATIES = 2;
    public static final int NUM_OF_CONJOINED = 1;
    public static final int MELEE_DAMAGE = 1;
    public static final int HP = 200;
    public static final String IMGPATH = "images/MegaFaty.png";

    private LinkedList<EntityMonster> faties;
    private EntityMonster conjoined;
    private GameCounter spawnCounter;

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
    }

    /// Monsters
    /**
     * 
     */
    public List<EntityMonster> spawnMonsters(List<EntityTerrain> terrainList) {
        boolean timerFinished = this.spawnCounter.isFinished();
        LinkedList<EntityMonster> spawned = null;

        // <= 75% HP Spawn 2 Faty
        if (this.health / HP < 0.75) {
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
                    do {
                        double angle = Math.toRadians(Utils.randomInt(0, 359));
                        mpos = new Vector2(
                                this.getPos().getX() + dist * Math.cos(angle),
                                this.getPos().getY() + dist * Math.sin(angle));
                    } while (GameRoom.isPlaceCorrect(mpos, MonsterFaty.SIZE, terrainList));
                    spawned.add(new MonsterFaty(mpos));
                }
                this.faties.addAll(spawned);
            }
        }

        // <= 50% HP Spawn a conjoined Faty
        if (this.health / HP < 0.50) {
            if (this.conjoined != null && !this.conjoined.isLiving())
                this.conjoined = null;

            // Le temps de respawn est finit?
            if (timerFinished) {
                if (this.conjoined == null) {
                    double dist = MonsterConjoinedFaty.SIZE.distance(SIZE);
                    Vector2 mpos;
                    do {
                        double angle = Math.toRadians(Utils.randomInt(0, 359));
                        mpos = new Vector2(
                                this.getPos().getX() + dist * Math.cos(angle),
                                this.getPos().getY() + dist * Math.sin(angle));
                    } while (GameRoom.isPlaceCorrect(mpos, MonsterConjoinedFaty.SIZE, terrainList));
                    this.conjoined = new MonsterConjoinedFaty(this.pos);
                    spawned.add(this.conjoined);
                }
            }
        }

        return spawned;
    }
}
