package gameObjects.Entities;

import libraries.Vector2;

import java.util.ArrayList;

import gameAI.AI;

/**
 * 
 */
public class EntityBoss extends EntityMonster {

    /**
     * 
     */
    public EntityBoss(Vector2 pos, Vector2 size, double speed, boolean flying, int health, int melee, double meleePower,
            double meleeSpeed,
            String imgPath, AI ai) {
        super(pos, size, speed, flying, health, melee, meleePower, meleeSpeed, imgPath, ai);
    }

    /**
     * 
     * @return
     */
    public ArrayList<EntityMonster> spawnMonsters() {
        return null;
    }
};
