package gameObjects.Monsters;

import gameAI.AI;

import gameObjects.Entities.EntityMonster;

import gameWorld.GameRoom;

import libraries.Vector2;

public class MonsterFaty extends EntityMonster {
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.75)
            .vectorMultiplication(new Vector2(1.0, 0.7));
    public static final double SPEED = 0.002;
    public static final double MELEE_RELOAD_SPEED = 0.040;
    public static final double MELEE_EFFECT_POWER = 5.;
    public static final double AGGRO_RANGE = 0.50;
    public static final int MELEE_DAMAGE = 4;
    public static final int HP = 12;
    public static final String IMGPATH = "images/Faty.png";

    /**
     * 
     * @param pos
     */
    public MonsterFaty(Vector2 pos) {
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
    }
}
