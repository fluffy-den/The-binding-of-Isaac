package gameAI;

import gameObjects.Entities.EntityBoss;
import gameObjects.Entities.EntityMonster;
import gameObjects.Hero;

import java.util.List;
import java.util.Random;

import gameWorld.Game;
import gameWorld.GameRoom;

import libraries.Vector2;

import resources.Utils;

public class AI {

    /**
     * Construit l'objet IA. Permet de contrôler le comportement des monstres.
     */
    public AI() {

    }

    /**
     * @brief Génère aléatoirement une position autour du boss de telle façon que
     *        le monstre accompagne celui-ci.
     * 
     * @param boss Le boss.
     * @param ctrl Le monstre.
     * @param room La salle actuelle du jeu.
     * 
     * @return Prochaine position du monstre.
     */
    private Vector2 randomBossPos(EntityBoss boss, EntityMonster ctrl, GameRoom room) {
        Random rand = new Random();
        double dist = boss.getSize().distance(ctrl.getSize());
        double angl = rand.nextDouble();
        Vector2 pos;
        do {
            pos = new Vector2(boss.getPos().getX() + dist * Math.cos(angl),
                    boss.getPos().getY() + dist * Math.sin(angl));
        } while (!GameRoom.isPlaceCorrect(pos, ctrl.getSize(), room.getTerrainList()));
        return pos;
    }

    /**
     * @brief Génère aléatoirement une position dans la carte autour du monstre.
     * 
     * @param ctrl Le monstre.
     * @param room La salle actuelle du jeu.
     * @return Prochaine position du monstre.
     */
    private Vector2 randomPos(EntityMonster ctrl, GameRoom room) {
        Vector2 pos;
        do {
            pos = new Vector2(Utils.randomDouble(GameRoom.MIN_XPOS, GameRoom.MAX_XPOS),
                    Utils.randomDouble(GameRoom.MIN_YPOS, GameRoom.MAX_YPOS));
        } while (!GameRoom.isPlaceCorrect(pos, ctrl.getSize(), room.getTerrainList()));
        return pos;
    }

    /**
     * @brief Le monstre se déplace pour attaquer le joueur.
     * 
     * @param ctrl Le monstre.
     * @param h    Le joueur.
     * @param room La salle actuelle du jeu.
     * @return La position que doit prendre le monstre.
     */
    private Vector2 aggroPos(EntityMonster ctrl, Hero h, GameRoom room) {
        return new Vector2();
    }

    /**
     * @brief IA faisant des deplacement aleatoires. Lorsqu'un deplacement est
     *        termine, en genere un nouveau.
     *        Cette IA accompagne le boss. Lorsque le joueur s'approche trop du
     *        boss, les monstres attaquent le joueur.
     * 
     * @param b Le boss de la salle.
     * @param m Le monstre contrôlé.
     * @param h Le joueur.
     * @param r La salle actuelle du jeu.
     * @return La position que doit prendre le monstre.
     */
    public Vector2 nextDir(EntityBoss b, EntityMonster m, Hero h, GameRoom r) {
        List<Vector2> pL = AIAStar.aStar(
                r,
                GameRoom.getTileXIndex(m.getPos()),
                GameRoom.getTileYIndex(m.getPos()),
                GameRoom.getTileXIndex(h.getPos()),
                GameRoom.getTileYIndex(h.getPos()));

        for (Vector2 p : pL) {
            if (Utils.isAdjacent(
                    p,
                    new Vector2(GameRoom.TILE_SIZE.getX() * 0.75, GameRoom.TILE_SIZE.getY() * 0.75),
                    m.getPos(),
                    m.getSize()))
                continue;
            return p.subVector(m.getPos());
        }

        return h.getPos();
    }

    /**
     * @brief Modifie le comportement de l'IA en aggro lorsque le joueur touche le
     *        monstre.
     */
    public void onHit() {

    }
}
