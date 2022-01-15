package gameObjects.Entities;

import libraries.Vector2;
import libraries.StdDraw;

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
     * Affiche le boss et ses points de vie
     */
    @Override
    public void draw() {
        StdDraw.picture(getPos().getX(), getPos().getY(), getImgPath(), getSize().getX(), getSize().getY(),
                0);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(getPos().getX(), getPos().getY() + 0.05, Integer.toString(this.health));
    }

    /**
     * 
     * @return
     */
    public ArrayList<EntityMonster> spawnMonsters() {
        return null;
    }
};
