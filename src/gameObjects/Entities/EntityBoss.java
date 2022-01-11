package gameObjects.Entities;

import libraries.Vector2;

import java.util.ArrayList;

/**
 * 
 */
public class EntityBoss extends EntityMonster {

    /**
     * 
     */
    public EntityBoss(Vector2 pos, Vector2 size, double speed, boolean flying, int health, int melee, double meleePower,
            double meleeSpeed,
            String imgPath) {
        super(pos, size, speed, flying, health, melee, meleePower, meleeSpeed, imgPath);
    }

    /**
     * 
     * @return
     */
    public ArrayList<EntityMonster> spawnMonsters() {
        return null;
    }
};
