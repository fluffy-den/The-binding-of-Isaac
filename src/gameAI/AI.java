package gameAI;

import gameObjects.Entities.EntityBoss;
import gameObjects.Entities.EntityMonster;
import gameObjects.Hero;

import gameWorld.GameRoom;

import libraries.Vector2;

public abstract class AI {
    /**
     * 
     * @param boss
     * @param ctrl
     * @param h
     * @param room
     * @return
     */
    public Vector2 nextDir(EntityBoss boss, EntityMonster ctrl, Hero h, GameRoom room) {
        return new Vector2();
    }

    /**
     * 
     */
    public void onHit() {
    }

    /**
     * 
     */
    public void onTargetClose() {
    }
}
