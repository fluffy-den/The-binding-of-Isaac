package gameAI;

import gameObjects.Entities.EntityBoss;
import gameObjects.Entities.EntityMonster;
import gameObjects.Hero;

import java.util.List;

import gameWorld.GameCounter;
import gameWorld.GameRoom;

import libraries.Vector2;

import resources.Utils;

public class AI {
    private GameCounter escortCounter;
    private Vector2 nextPos;
    private double aggroRange;

    /**
     * Construit l'objet IA. Permet de contrôler le comportement des monstres.
     */
    public AI(Vector2 p, double r) {
        this.escortCounter = new GameCounter(0.025);
        this.nextPos = p;
        this.aggroRange = r;
    }

    /**
     * @brief Fonction qui génère le cheminment utilisant l'algorithme A* si le
     *        monstre ne volle pas.
     * 
     * @param m Le monstre à déplacer.
     * @param t La position ciblée.
     * @param r La salle actuelle du jeu.
     * @return Renvoie la prochaine direction de telle façon que le monstre ne serra
     *         pas bloqué lors de son déplacement.
     */
    private Vector2 generateDir(EntityMonster m, Vector2 t, GameRoom r) {
        // Le monstre ne vole pas, il faut éviter les murs
        if (!m.isFlying()) {

            // Liste du cheminement (utilisant A*)
            List<Vector2> pL = AIAStar.aStar(
                    r,
                    GameRoom.getTileXIndex(m.getPos()),
                    GameRoom.getTileYIndex(m.getPos()),
                    GameRoom.getTileXIndex(t),
                    GameRoom.getTileYIndex(t));

            // On ignore les positions trop proches du monstre dans la liste de cheminement
            for (Vector2 p : pL) {
                if (Utils.isAdjacent(
                        p,
                        new Vector2(GameRoom.TILE_SIZE.getX() * 0.75, GameRoom.TILE_SIZE.getY() * 0.75),
                        m.getPos(),
                        m.getSize()))
                    continue;
                return p.subVector(m.getPos());
            }
        }

        // Pas besoin d'éviter les murs, on fonce sur le joueur
        return t.subVector(m.getPos());
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
    private Vector2 escortDir(EntityBoss b, EntityMonster m, GameRoom r) {
        // On veut que les monstres tournent autour du monstre pour l'escorter
        // On force une régénération des positions toutes les secondes
        if (this.escortCounter.isFinished() || Utils.isAdjacent(
                m.getPos(),
                m.getSize(),
                this.nextPos,
                new Vector2(GameRoom.TILE_SIZE.getX() * 0.75, GameRoom.TILE_SIZE.getY() * 0.75))) {

            // Génération d'une nouvelle position aléatoire autour du boss
            do {
                // Angle
                double angle = Utils.randomDouble(0.0, 1.0);
                double range = b.getSize().distance(m.getSize());
                this.nextPos = new Vector2(
                        b.getPos().getX() + Math.cos(angle) * range,
                        b.getPos().getY() + Math.sin(angle) * range);
                this.nextPos = GameRoom.getPositionFromTile(
                        GameRoom.getTileXIndex(this.nextPos),
                        GameRoom.getTileYIndex(this.nextPos));
            } while (GameRoom.isPlaceCorrect(
                    this.nextPos,
                    m.getSize(),
                    r.getTerrainList()));
        }

        // Fin
        return generateDir(m, this.nextPos, r);
    }

    /**
     * @brief Génère aléatoirement une position dans la carte autour du monstre.
     * 
     * @param ctrl Le monstre.
     * @param room La salle actuelle du jeu.
     * @return Prochaine position du monstre.
     */
    private Vector2 randomDir(EntityMonster m, Hero h, GameRoom r) {
        // Si la dernière position n'a pas été atteinte on ne regénère pas de nouvelle
        // position
        if (Utils.isAdjacent(
                m.getPos(),
                m.getSize(),
                this.nextPos,
                new Vector2(GameRoom.TILE_SIZE.getX() * 0.75, GameRoom.TILE_SIZE.getY() * 0.75))) {

            // Génération d'une nouvelle position aléatoire

            do {
                this.nextPos = new Vector2(
                        Utils.randomDouble(GameRoom.MIN_XPOS, GameRoom.MAX_XPOS),
                        Utils.randomDouble(GameRoom.MIN_YPOS, GameRoom.MAX_YPOS));
                this.nextPos = GameRoom.getPositionFromTile(
                        GameRoom.getTileXIndex(this.nextPos),
                        GameRoom.getTileYIndex(this.nextPos));
            } while (GameRoom.isPlaceCorrect(
                    this.nextPos,
                    m.getSize(),
                    r.getTerrainList()));
        }

        // Calcul de la direction utilisant l'algorithme A*
        return generateDir(m, this.nextPos, r);
    }

    /**
     * @brief Le monstre se déplace pour attaquer le joueur.
     * 
     * @param ctrl Le monstre.
     * @param h    Le joueur.
     * @param room La salle actuelle du jeu.
     * @return La position que doit prendre le monstre.
     */
    private Vector2 aggroDir(EntityMonster m, Hero h, GameRoom r) {
        // On fonce sur le joueur
        return generateDir(m, h.getPos(), r);
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
        // IA: Aggro
        if (m.getPos().distance(h.getPos()) <= this.aggroRange) {
            this.aggroRange = 1.0; // On met une valeur élevée pour toujours garder l'aggro du monstre
            return aggroDir(m, h, r);
        }

        // IA: Accompagnement (Un boss est présent)
        if (b != null) {
            return escortDir(b, m, r);
        }

        // IA: Random
        return randomDir(m, h, r);
    }

    /**
     * @brief Modifie le comportement de l'IA en aggro lorsque le joueur touche le
     *        monstre.
     */
    public void onHit() {
        if (this.aggroRange != 0.0)
            this.aggroRange = 1.0; // Une valeur élevée force l'aggro
    }

    /// Range
    /**
     * @brief Fonction get de la variable aggroRange
     * 
     * @return Renvoie aggroRange
     */
    public double getAggroRange() {
        return this.aggroRange;
    }

    /**
     * @brief Fonction set de la variable aggroRange
     * 
     * @param r La nouvelle valeur de aggroRange
     */
    public void setAggroRange(double r) {
        this.aggroRange = r;
    }
}
