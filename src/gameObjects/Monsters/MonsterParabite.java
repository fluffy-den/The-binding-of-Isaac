package gameObjects.Monsters;

import gameAI.AI;

import gameObjects.Entities.EntityMonster;

import gameObjects.Hero;
import gameWorld.GameRoom;
import gameWorld.GameCounter;

import libraries.Vector2;

public class MonsterParabite extends EntityMonster {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.75)
            .vectorMultiplication(new Vector2(1.0, 0.7));
    public static final double SPEED = 0.;
    public static final double MELEE_RELOAD_SPEED = 0.04;
    public static final double MELEE_EFFECT_POWER = 5.;
    public static final double AGGRO_RANGE = 0.;
    public static final int MELEE_DAMAGE = 1;
    public static final int HP = 12;
    public static final String IMGPATH = "images/Parabite.png";
    public static final double RELOAD_SPEED = 0.01;
    private GameCounter reloadAttaque;
    private GameCounter reloadCounter;
    private boolean isVisible;
    private boolean isPeaceful;

    /**
     * 
     * @param pos
     */
    public MonsterParabite(Vector2 pos) {
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
        this.reloadAttaque = new GameCounter(MELEE_RELOAD_SPEED);
        this.reloadCounter = new GameCounter(RELOAD_SPEED);
        this.isVisible = true;
        this.isPeaceful = false;
    }

    /**
     * Le parabite passe par ses 3 états
     */
    public void updateParabite(Hero h) {
        if (this.reloadCounter.isFinished()) {
            if (isVisible && isPeaceful) {
                isVisible = false;
                isPeaceful = false;
                this.imgPath = IMGPATH;

            } else if (!isVisible && !isPeaceful) { // Ne fais pas de dégat
                this.size = GameRoom.TILE_SIZE.scalarMultiplication(0.5)
                        .vectorMultiplication(new Vector2(1.0, 0.7));
                this.isPeaceful = true;
                this.isVisible = false;
                this.imgPath = "images/Invisible.png";

            } else { // Ne fais pas de dégat et spawn sur le joueur
                this.size = SIZE;
                isPeaceful = true;
                isVisible = true;
                this.imgPath = "images/PeacefulParabite.png";
                super.setPos(h.getPos());
            }
        }
    }

    @Override
    public void onMonsterHeroAdjacency(Hero h) {
        if (this.reloadAttaque.isFinished() && (isVisible == false) && (isPeaceful == false)) {
            System.out.println("ok");
            h.addDamage(this.meleeDamage);
            h.addDirEffect(h.getPos().subVector(this.pos), this.meleeEffectPower);
        }
    }
}
