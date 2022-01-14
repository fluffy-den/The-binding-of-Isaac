package gameAI;

import java.util.List;

import gameObjects.Entities.EntityBoss;
import gameObjects.Entities.EntityTerrain;
import gameObjects.Entities.EntityMonster;
import gameObjects.Hero;

import gameWorld.GameRoom;

import libraries.Vector2;

public class AIAggro extends AI {
    /**
     * 
     * @param ctrl
     * @param h
     * @param room
     * @return
     */
    public Vector2 nextDir(EntityBoss boss, EntityMonster ctrl, Hero h, GameRoom room) {
        if (ctrl.isFlying())
            return (ctrl.getPos().subVector(h.getPos())); // Une IA aggro fonce sur le joueur

        // Méthode A*

        // Prochain Noeud

        return new Vector2();
    }
}
