package gameAI;

import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedList;

import gameObjects.Entities.Entity;
import gameObjects.Entities.EntityTerrain;
import gameWorld.Game;
import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * @brief Définit les fonctions permettant à un objet controllé par l'IA de se
 *        déplacer d'un point A à un point B, en supposant que la taille X est
 *        plus petite que la taille X d'une tuile, même chose pour Y.
 *        Basé sur
 *        https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode
 */
public class AIPathing {
    /**
     * @brief Noeud permettant de traverser toute la grille, et d'éviter les murs.
     *        Voir l'algorithme pour plus de détails.
     *        Note: Ici, on a choisit la valeur heuristique comme étant la distance
     *        euclidienne entre deux vecteurs.
     * 
     * @a g Distance entre this et le successeur.
     * @a h Distance entre this et la destination.
     * @a f = h + g est la valeur du noeud permettant de comparer les objets Node
     *    entre eux.
     */
    private static class Node implements Comparable<Node> {
        public LinkedList<Node> successors;
        public Node parent;
        public double g;
        public double f;
        public int x;
        public int y;

        /// Constructeur
        /**
         * @brief Contient toutes les informations nécessaires à la
         *
         * @param x      La position x de l'objet sur la grille de jeu.
         * @param y      La position y de l'objet sur la grille de jeu.
         * @param h      La valeur heuristique de ce noeud.
         * @param parent Le parent du noeud (peut-être nul)
         */
        public Node(int x, int y, double h, Node parent) {
            this.successors = new LinkedList<Node>();
            this.x = x;
            this.y = y;
            if (parent != null) {
                this.g = this.toVector2().distance(parent.toVector2()) + parent.g;
                this.f = h + g;
            } else {
                this.g = Double.MAX_VALUE;
                this.f = Double.MAX_VALUE;
            }
            this.f = h + g;
            this.parent = parent;
        }

        /**
         * @brief Fonction de comparaison surchargé de la classe Comparable<Node>
         *        (permet l'utilisation de ce noeud dans la PriorityQueue).
         *        Compare les valeurs heuristiques.
         * 
         * @param n Le noeud à comparer avec this.
         * @return @see Double.compare
         */
        @Override
        public int compareTo(Node n) {
            return Double.compare(this.f, n.f);
        }

        /**
         * @brief Fonction de génération des "successeurs" de this. On appelle
         *        successeur tout noeud adjacent qui n'est pas un obstacle.
         * 
         * @param grid   Une grille representant les cases où this peut se déplacer.
         *               (true
         *               si obstacle, false sinon)
         * @param Entity Vers quel objet on veut se déplacer.
         */
        public void generateSuccessors(boolean[][] grid, Entity to) {
            // Génération des voisins de q
            // i
            int imin = this.x - 1;
            int imax = this.x + 1;
            if (imin < 0) {
                imin = this.x;
            } else if (imax > 9) {
                imax = this.x;
            }

            // j
            int jmin = this.y - 1;
            int jmax = this.y + 1;
            if (jmin < 0) {
                jmin = this.y;
            } else if (jmax > 9) {
                jmax = this.y;
            }

            // Itération
            for (int i = imin; i < imax; ++i) {
                for (int j = jmin; j < jmax; ++j) {
                    if (!grid[i][j] && i != this.x && j != this.y) {
                        this.successors.add(new Node(i, j,
                                GameRoom.getPositionFromTile(i, j).distance(to.getPos()), this));
                    }
                }
            }
        }

        /**
         * @brief Fonction qui convertit la position (x, y) de this en vecteur.
         * 
         * @return GameRoom.getPositionFromTile(x, y)
         */
        public Vector2 toVector2() {
            return GameRoom.getPositionFromTile(this.x, this.y);
        }
    }

    /**
     * @brief Reconstruit le cheminement des noeuds depuis end.
     * 
     * @param end Le dernier noeud construit (celui de l'objet destination).
     * @return Liste contenant le cheminement.
     */
    private static List<Vector2> reconstructPath(Node end) {
        LinkedList<Vector2> list = new LinkedList<Vector2>();
        Node p = end;
        while (p != null) {
            list.addFirst(p.toVector2());
            p = p.parent;
        }
        return list;
    }

    /**
     * @brief Fonction qui retourne le dernier noeud construit de l'algorithme A*.
     *        Permet de reconstruire à l'envers le cheminement des noeuds.
     * 
     * @param grid Contient les cases non praticables.
     * @param from L'entité de départ.
     * @param to   L'entité d'arrivée.
     * @param room La salle actuelle du jeu.
     * @return Le dernier noeud.
     */
    private static Node shortestPath(boolean[][] grid, Entity from, Entity to, GameRoom room) {
        // https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode
        // Initialisation
        PriorityQueue<Node> o = new PriorityQueue<Node>();
        PriorityQueue<Node> c = new PriorityQueue<Node>();
        o.add(new Node(GameRoom.getTileXIndex(from.getPos()),
                GameRoom.getTileYIndex(from.getPos()),
                from.getPos().distance(to.getPos()),
                null));

        // Iteration
        while (!o.isEmpty()) {
            Node q = o.peek();
            o.remove(q);
            q.generateSuccessors(grid, to);
            for (Node n : q.successors) {
                if (n.x == to.getPos().getX() && n.y == to.getPos().getY()) {
                    return n;
                }

                boolean skipped = false;
                for (Node k : o) {
                    if (k.x == n.x && k.y == n.y && k.f < n.f) {
                        skipped = true;
                        break;
                    }
                }
                if (!skipped) {
                    for (Node k : c) {
                        if (k.x == n.y && k.y == n.y && k.f < n.f) {
                            break;
                        } else {
                            o.add(k);
                        }
                    }
                }
            }
            c.add(q);
        }

        // Fin
        return null;
    }

    /**
     * @brief Génère la liste de noeuds.
     * 
     * @param from L'entité de départ.
     * @param to   L'entité d'arrivée.
     * @param room La salle actuelle du jeu.
     * @return Le cheminement des noeuds.
     */
    public static List<Vector2> generatePath(Entity from, Entity to, GameRoom room) {
        // Génération de la grille (true si la case ne peut pas être accédé)
        boolean[][] grid = new boolean[GameRoom.NUM_OF_TILES][GameRoom.NUM_OF_TILES];
        List<EntityTerrain> terrainList = room.getTerrainList();
        for (EntityTerrain t : terrainList) {
            grid[GameRoom.getTileXIndex(t.getPos())][GameRoom.getTileYIndex(t.getPos())] = true;
        }

        // Construction du chemin le plus court
        Node end = shortestPath(grid, from, to, room);
        if (end == null)
            return null;

        // Conversion en une liste
        return reconstructPath(end);
    }
}