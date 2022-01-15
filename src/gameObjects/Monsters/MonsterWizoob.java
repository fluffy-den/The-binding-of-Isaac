package gameObjects.Monsters;

import gameAI.AI;

import gameObjects.Entities.EntityMonster;
import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class MonsterWizoob extends EntityMonster {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.75)
            .vectorMultiplication(new Vector2(1.0, 0.7));
    public static final double SPEED = 0.0035;
    public static final double MELEE_RELOAD_SPEED = 0.025;
    public static final double MELEE_EFFECT_POWER = 7.5;
    public static final double AGGRO_RANGE = 0.60;
    public static final int MELEE_DAMAGE = 1;
    public static final int HP = 8;
    public static final String IMGPATH = "images/Wizoob.png";

    /**
     * 
     * @param pos
     */
    public MonsterWizoob(Vector2 pos) {
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
    }
};
