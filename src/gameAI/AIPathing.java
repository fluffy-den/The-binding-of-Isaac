package gameAI;

import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedList;

import gameObjects.Entities.Entity;
import gameObjects.Entities.EntityTerrain;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * @brief Basé sur
 *        https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode
 */
public class AIPathing {
    /**
     * 
     */
    private class Node implements Comparable<Node> {
        /**
         * 
         */
        public LinkedList<Node> successors;
        public Node parent;
        public double g;
        public double f;
        public int x;
        public int y;

        /// Constructeur
        /**
         * 
         */
        public Node(int x, int y, double h, Node parent) {
            this.successors = new LinkedList<Node>();
            this.x = x;
            this.y = y;
            if (parent != null) {
                this.g = distance(this.toVector2(), parent.toVector2()) + parent.g;
                this.f = h + g;
            } else {
                this.g = Double.MAX_VALUE;
                this.f = Double.MAX_VALUE;
            }
            this.f = h + g;
            this.parent = parent;
        }

        /**
         * 
         */
        @Override
        public int compareTo(Node n) {
            return Double.compare(this.f, n.f);
        }

        /**
         * 
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
                                distance(GameRoom.getPositionFromTile(i, j), to.getPos()), this));
                    }
                }
            }
        }

        /**
         * 
         * @return
         */
        public Vector2 toVector2() {
            return new Vector2(this.x, this.y);
        }
    }

    /**
     * 
     * @param from
     * @param curr
     * @return
     */
    private List<Node> reconstructPath(Node end) {
        LinkedList<Node> list = new LinkedList<Node>();
        Node p = end;
        while (p != null) {
            list.add(p);
            p = p.parent;
        }
        return list;
    }

    /**
     * 
     * @return
     */
    private double distance(Vector2 p1, Vector2 p2) {
        // Chebyshev distance
        return Math.sqrt(
                Math.pow(p1.getX() - p2.getX(), 2)
                        + Math.pow(p1.getY() - p2.getY(), 2));
    }

    /**
     * 
     * @param grid
     * @param from
     * @param to
     * @param room
     * @return
     */
    private Node shortestPath(boolean[][] grid, Entity from, Entity to, GameRoom room) {
        // https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode
        // Initialisation
        PriorityQueue<Node> o = new PriorityQueue<Node>();
        PriorityQueue<Node> c = new PriorityQueue<Node>();
        o.add(new Node(GameRoom.getTileXIndex(from.getPos()),
                GameRoom.getTileYIndex(from.getPos()),
                distance(from.getPos(), to.getPos()),
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
     * 
     */
    public List<Node> generatePath(Entity from, Entity to, GameRoom room) {
        // Génération de la grille (true si la case ne peut pas être accédé)
        boolean[][] grid = new boolean[GameRoom.NUM_OF_TILES][GameRoom.NUM_OF_TILES];
        List<EntityTerrain> terrainList = room.getTerrainList();
        for (EntityTerrain t : terrainList) {
            grid[GameRoom.getTileXIndex(t.getPos())][GameRoom.getTileYIndex(t.getPos())] = true;
        }

        // Construction du chemin le plus court
        Node end = this.shortestPath(grid, from, to, room);
        if (end == null)
            return null;

        // Conversion en une liste
        return this.reconstructPath(end);
    }
}
