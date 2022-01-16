package gameObjects.Monsters;

import gameAI.AI;

import gameObjects.Entities.EntityMonster;
import gameWorld.GameRoom;

import libraries.Vector2;

public class MonsterFly extends EntityMonster {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.45);
    public static final double SPEED = 0.0025;
    public static final double MELEE_RELOAD_SPEED = 0.035;
    public static final double MELEE_EFFECT_POWER = 5.;
    public static final double AGGRO_RANGE = GameRoom.TILE_DIST * 2.00;
    public static final int MELEE_DAMAGE = 1;
    public static final int HP = 3;
    public static final String IMGPATH = "images/Fly.png";

    /**
     * @brief
     * 
     * @param pos
     */
    public MonsterFly(Vector2 pos) {
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
    }
}