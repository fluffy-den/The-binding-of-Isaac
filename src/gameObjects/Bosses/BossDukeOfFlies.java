package gameObjects.Bosses;

import gameAI.AI;

import gameObjects.Entities.EntityBoss;

import gameWorld.GameRoom;

import libraries.Vector2;

public class BossDukeOfFlies extends EntityBoss {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(1);
    public static final double SPEED = 0.002;
    public static final double MELEE_RELOAD_SPEED = 0.040;
    public static final double MELEE_EFFECT_POWER = 5.;
    public static final int MELEE_DAMAGE = 1;
    public static final int HP = 180;
    public static final String IMGPATH = "images/TheDukeOfFlies.png";

    /**
     * 
     */
    public BossDukeOfFlies(Vector2 pos) {
        super(pos, SIZE, SPEED, false, HP, MELEE_DAMAGE, MELEE_EFFECT_POWER, MELEE_RELOAD_SPEED, IMGPATH,
                new AI());
    }
}
