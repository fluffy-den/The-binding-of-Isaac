package gameAI;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
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
        public Node parent;
        public double g = Double.MAX_VALUE;
        public double f = Double.MAX_VALUE;
        public int x;
        public int y;

        /// Constructeur
        public Node(int x, int y, double g, double f, Node p) {
            this.parent = p;
            this.g = g;
            this.f = f;
            this.x = x;
            this.y = y;
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
         * @brief Fonction qui convertit la position (x, y) de this en vecteur.
         * 
         * @return GameRoom.getPositionFromTile(x, y)
         */
        public Vector2 toVector2() {
            return GameRoom.getPositionFromTile(this.x, this.y);
        }
    }

    private static class Edge {
        public int x;
        public int y;

        /// Constructeur
        /**
         * 
         * @param x
         * @param y
         */
        public Edge(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * 
         * @return
         */
        public Vector2 toVector2() {
            return new Vector2((double) this.x, (double) this.y);
        }

    }

    private static double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math
                .abs(Math.sqrt((Math.pow(x1, 2) + Math.pow(y1, 2))) - Math.sqrt((Math.pow(x2, 2)) + Math.pow(y2, 2)));
    }

    private static List<Edge> neighorsOf(boolean[][] g, Node n) {
        LinkedList<Edge> l = new LinkedList<Edge>();

        int imin = n.x - 1;
        if (imin - 1 < 0)
            imin = 0;
        int imax = n.x + 1;
        if (imax + 1 > GameRoom.NUM_OF_TILES - 1)
            imax = GameRoom.NUM_OF_TILES - 1;
        int jmin = n.y - 1;
        if (jmin - 1 < 0)
            jmin = 0;
        int jmax = n.y + 1;
        if (jmax + 1 > GameRoom.NUM_OF_TILES - 1)
            jmax = GameRoom.NUM_OF_TILES - 1;
        for (int i = imin; i <= imax; ++i) {
            for (int j = jmin; j <= jmax; ++j) {
                if ((g[i][j] == false) && (i != n.x || j != n.y))
                    l.add(new Edge(i, j));
            }
        }

        return l;
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

        //

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
    private static List<Vector2> shortestPath(boolean[][] grid, Entity from, Entity to, GameRoom room) {
        // https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode
        // Initialisation
        PriorityQueue<Node> o = new PriorityQueue<Node>();
        LinkedList<Node> c = new LinkedList<Node>();
        o.add(new Node(
                GameRoom.getTileXIndex(from.getPos()),
                GameRoom.getTileYIndex(from.getPos()),
                0,
                calculateDistance(GameRoom.getTileXIndex(from.getPos()), GameRoom.getTileYIndex(from.getPos()),
                        GameRoom.getTileXIndex(to.getPos()), GameRoom.getTileYIndex(to.getPos())),
                null));
        while (!o.isEmpty()) {
            Node m = o.peek();
            if (m.x == GameRoom.getTileXIndex(to.getPos()) || m.y == GameRoom.getTileYIndex(to.getPos())) {
                return reconstructPath(m);
            }
            o.remove(m);
            c.add(m);

            // Generate Neighbors
            List<Edge> neighbors = neighorsOf(grid, m);
            for (Edge n : neighbors) {
                Node nc = null;
                for (Node k : c) {
                    if (k.x == n.x && k.y == n.y) {
                        nc = k;
                        break;
                    }
                }

                double cost = m.g + calculateDistance(m.x, m.y, n.x, n.y);

                Node no = null;
                for (Node k : o) {
                    if (k.x == n.x && k.y == n.y) {
                        no = k;
                        break;
                    }
                }

                if (no != null && cost < no.g) {
                    o.remove(no);
                }

                if (nc != null && cost < nc.g) {
                    c.remove(nc);
                }

                if (no == null && nc == null) {
                    double f = cost + calculateDistance(n.x, n.y, // f = g + h
                            GameRoom.getTileXIndex(to.getPos()),
                            GameRoom.getTileYIndex(to.getPos()));
                    o.add(new Node(n.x, n.y, cost, f, m));

                }
            }
        }

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
        return shortestPath(grid, from, to, room);
    }
}
