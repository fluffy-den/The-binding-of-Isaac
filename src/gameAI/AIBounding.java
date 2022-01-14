package gameAI;

import java.util.List;

import gameObjects.Entities.EntityBoss;
import gameObjects.Entities.EntityMonster;
import gameObjects.Hero;
import gameObjects.Entities.EntityTerrain;

import gameWorld.GameRoom;

import libraries.Vector2;

public class AIBounding extends AI {

    /**
     * @brief IA qui a chaques colision avec un mur tente de se déplacer en
     *        direction du joueur en fonction d'un angle maximum alpha.
     * 
     * @return La nouvelle direction du monstre.
     */
    public Vector2 nextDir(EntityBoss boss, EntityMonster ctrl, Hero h, GameRoom room) {
        return new Vector2();
    }
}
