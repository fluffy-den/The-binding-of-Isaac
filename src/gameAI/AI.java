package gameAI;

import gameObjects.Entities.EntityBoss;
import gameObjects.Entities.EntityMonster;
import gameObjects.Entities.EntityTerrain;
import gameObjects.Hero;

import java.util.List;
import java.util.Random;

import gameWorld.GameRoom;

import libraries.Vector2;

import resources.Utils;

public class AI {
    private Vector2 nextPos;
    private double aggroRange;
    private boolean enableAggro;
    private boolean aggro;

    /**
     * Construit l'objet IA. Permet de contrôler le comportement des monstres.
     */
    public AI(Vector2 startPos, double aggroRange, boolean enableAggro) {
        this.nextPos = startPos;
        this.aggroRange = aggroRange;
        this.enableAggro = enableAggro;
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
        if (ctrl.isFlying())
            return (ctrl.getPos().subVector(h.getPos())); // Une IA aggro fonce sur le joueur

        // Méthode A*
        List<Vector2> n = AIPathing.generatePath(ctrl, h, room);
        if (n == null || n.isEmpty())
            return new Vector2();
        else
            return (h.getPos().subVector(n.get(0)));
    }

    /**
     * @brief IA faisant des deplacement aleatoires. Lorsqu'un deplacement est
     *        termine, en genere un nouveau.
     *        Cette IA accompagne le boss. Lorsque le joueur s'approche trop du
     *        boss, les monstres attaquent le joueur.
     * 
     * @param boss Le boss de la salle.
     * @param ctrl Le monstre contrôlé.
     * @param h    Le joueur.
     * @param room La salle actuelle du jeu.
     * @return La position que doit prendre le monstre.
     */
    public Vector2 nextDir(EntityBoss boss, EntityMonster ctrl, Hero h, GameRoom room) {
        this.nextPos = aggroPos(ctrl, h, room);
        return nextPos;

        /*
         * // Le monstre a-t-il atteint la dernière case calculée?
         * Vector2 overlapVector = new Vector2(GameRoom.TILE_SIZE.getX() / 1000,
         * GameRoom.TILE_SIZE.getY() / 1000);
         * if (!Utils.isAdjacent(ctrl.getPos(), ctrl.getSize(), this.nextPos,
         * overlapVector)) {
         * return this.nextPos.subVector(ctrl.getPos());
         * }
         * 
         * // L'IA fonce sur le joueur si celui-ci est trop proche, où a été touché par
         * le
         * // joueur.
         * if (enableAggro && !aggro && ctrl.getPos().distance(h.getPos()) <=
         * this.aggroRange) {
         * aggro = true;
         * }
         * if (aggro) {
         * this.nextPos = aggroPos(ctrl, h, room);
         * return nextPos;
         * }
         * 
         * // Accompagne le boss
         * if (boss != null && boss != ctrl) {
         * if (ctrl.isFlying()) {
         * this.nextPos = ctrl.getPos().subVector(this.randomBossPos(boss, ctrl, room));
         * } else {
         * boolean isAdjacent;
         * do {
         * isAdjacent = false;
         * this.nextPos = this.randomBossPos(boss, ctrl, room);
         * List<EntityTerrain> terrainList = room.getTerrainList();
         * for (EntityTerrain t : terrainList) {
         * if (Utils.isAdjacent(t.getPos(), t.getSize(), this.nextPos, overlapVector))
         * isAdjacent = true;
         * }
         * } while (isAdjacent);
         * return this.nextPos;
         * }
         * }
         * 
         * // Mouvement random
         * if (ctrl.isFlying()) {
         * return this.randomPos(ctrl, room);
         * }
         * boolean isAdjacent;
         * do {
         * isAdjacent = false;
         * this.nextPos = this.randomPos(ctrl, room);
         * List<EntityTerrain> terrainList = room.getTerrainList();
         * for (EntityTerrain t : terrainList) {
         * if (Utils.isAdjacent(t.getPos(), t.getSize(), this.nextPos, overlapVector))
         * isAdjacent = true;
         * }
         * } while (isAdjacent);
         * return this.nextPos;
         */
    }

    /**
     * @brief Modifie le comportement de l'IA en aggro lorsque le joueur touche le
     *        monstre.
     */
    public void onHit() {
        if (this.enableAggro)
            this.aggro = true;
    }
}
