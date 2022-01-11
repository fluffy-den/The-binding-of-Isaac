package gameAI;

import java.util.List;

import gameObjects.Hero;
import gameObjects.Entities.EntityTerrain;

import libraries.Vector2;

public abstract class AI {
    /**
     * 
     * @param h
     * @param isFlying
     * @param avoidList
     * @return
     */
    public Vector2 nextDir(Hero h, boolean isFlying, List<EntityTerrain> avoidList) {
        return new Vector2();
    }
}
