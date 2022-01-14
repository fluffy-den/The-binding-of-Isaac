package gameAI;

import java.util.List;

import gameObjects.Entities.EntityBoss;
import gameObjects.Entities.EntityMonster;
import gameObjects.Hero;
import gameObjects.Entities.EntityTerrain;

import gameWorld.GameRoom;

import libraries.Vector2;

public class AIRandom extends AI {
    /**
     * @brief IA faisant des deplacement aleatoires. Lorsqu'un deplacement est
     *        termine, en genere un nouveau.
     *        Cette IA accompagne le boss. Lorsque le joueur s'approche trop du
     *        boss, les monstres attaquent le joueur.
     * 
     * @return La nouvelle direction du monstre.
     */
    public Vector2 nextDir(EntityBoss boss, EntityMonster ctrl, Hero h, GameRoom room) {
        // Accompagne le boss
        if (boss != null) {
            return new Vector2();
            // TODO
        }

        // Mouvement random
        // TODO
        return new Vector2();
    }
}
