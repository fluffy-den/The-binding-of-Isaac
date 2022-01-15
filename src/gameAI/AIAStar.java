package gameAI;

import gameObjects.Entities.Entity;
import gameObjects.Entities.EntityTerrain;

import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;

import gameWorld.GameRoom;

import libraries.Vector2;

/**
 * 
 */
public class AIAStar {
    /**
     * 
     */
    static private class Node implements Comparable<Node> {
        public Node p;
        public int x;
        public int y;
        public double g;
        public double f; // = g + h

        /**
         * @brief
         * 
         * @param p
         * @param x
         * @param y
         * @param g
         * @param f
         */
        public Node(Node p, int x, int y, double g, double f) {
            this.p = p;
            this.x = x;
            this.y = y;
            this.g = g;
            this.f = f;
        }

        /**
         * 
         */
        @Override
        public int compareTo(Node o) {
            return Double.compare(this.f, o.f);
        }
    }

    /**
     * 
     */
    static private class Edge {
        public int x;
        public int y;

        /**
         * 
         */
        public Edge(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * 
     */
    static private double euclidianDistance(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * @brief
     * 
     * @param n
     * @return
     */
    static private List<Edge> constructNeighbors(boolean[][] g, int x, int y) {
        // i
        int imin = x - 1;
        if (imin < 0)
            imin = 0;
        int imax = x + 1;
        if (imax > GameRoom.NUM_OF_TILES - 1)
            imax = GameRoom.NUM_OF_TILES - 1;

        // j
        int jmin = y - 1;
        if (jmin < 0)
            jmin = 0;
        int jmax = y + 1;
        if (jmax > GameRoom.NUM_OF_TILES - 1)
            jmax = GameRoom.NUM_OF_TILES - 1;

        // List
        LinkedList<Edge> l = new LinkedList<Edge>();
        for (int i = imin; i <= imax; ++i) {
            for (int j = jmin; j <= jmax; ++j) {
                if (!g[i][j] && (i != x || j != y)) {
                    // Cas case [i,j] se situe derrière une diagonale
                    if (i == x - 1 && j == y - 1 && (g[i + 1][j] || g[i][j + 1]))
                        continue;
                    if (i == x - 1 && j == y + 1 && (g[i + 1][j] || g[i][j - 1]))
                        continue;
                    if (i == x + 1 && j == y - 1 && (g[i - 1][j] || g[i][j + 1]))
                        continue;
                    if (i == x + 1 && j == y + 1 && (g[i - 1][j] || g[i][j - 1]))
                        continue;

                    l.add(new Edge(i, j));

                }
            }
        }

        return l;
    }

    /**
     * @brief
     * 
     * @param e
     * @return
     */
    static private List<Vector2> constructPath(Node e) {
        Node c = e;
        LinkedList<Vector2> pL = new LinkedList<Vector2>();
        while (c != null) {
            pL.addFirst(GameRoom.getPositionFromTile(c.x, c.y));
            c = c.p;
        }
        return pL;
    }

    /**
     * 
     */
    static public List<Vector2> aStar(GameRoom r, int sx, int sy, int ex, int ey) {
        // 1. Initialisation
        PriorityQueue<Node> cL = new PriorityQueue<Node>();
        PriorityQueue<Node> oL = new PriorityQueue<Node>();
        oL.add(new Node(
                null,
                sx,
                sy,
                0.0,
                euclidianDistance(sx, sy, ex, ey)));

        // 2. Construction d'une grille de boolean représentant les cases non
        // accessibles de r (true = obstacle)
        boolean[][] g = new boolean[GameRoom.NUM_OF_TILES][GameRoom.NUM_OF_TILES];
        List<EntityTerrain> tL = r.getTerrainList();
        for (EntityTerrain t : tL)
            g[GameRoom.getTileXIndex(t.getPos())][GameRoom.getTileYIndex(t.getPos())] = true;

        // 3. Recherche du chemin le plus court (en évitant les obstacles)
        while (!oL.isEmpty()) {
            Node n = oL.poll();
            cL.add(n);
            if (n.x == ex && n.y == ey)
                return constructPath(n);
            List<Edge> neighbors = constructNeighbors(g, n.x, n.y);
            for (Edge e : neighbors) {
                double eg = euclidianDistance(e.x, e.y, n.x, n.y);
                double ef = euclidianDistance(e.x, e.y, ex, ey) + eg;
                boolean inO = false;
                for (Node i : oL) {
                    if (i.x == e.x && i.y == e.y) {
                        inO = true;
                        break;
                    }
                }
                boolean inC = false;
                for (Node i : cL) {
                    if (i.x == e.x && i.y == e.y) {
                        inC = true;
                        break;
                    }
                }
                if (!inO && !inC) {
                    oL.add(new Node(
                            n,
                            e.x,
                            e.y,
                            eg,
                            ef));
                }
            }
        }

        return null;
    }
}
