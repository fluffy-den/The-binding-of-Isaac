package gameWorld;

import java.util.Random;

/**
 * 
 */
public class Grid {
    private String[][] Grid;

    /**
     * Création d'une grille de jeu 9 * 9
     */
    public Grid() {
        this.Grid = new String[9][9];
    }

    /**
     * Récupération d'une grille (tableau String)
     * @return La grille
     */
    public String[][] getGrid() {
        return this.Grid;
    }

    /**
     * @brief Génération des portes suplémentaires
     * 
     * @param nbdoor nombre de portes suplémentaires
     */
    public void GenerateDoor(int nbdoor) {
        Random random = new Random();
        int[] co = { 4, 36, 44, 76 };
        for (int i = 0; i < nbdoor; i++) {
            int rdm = random.nextInt(4);
            while (GenerateDoor1(co[rdm]) == false) {
                rdm = random.nextInt(4);
            }

        }
    }

    /**
     * Essai de placer une porte aux coordonnées
     * 
     * @param n coordonnées
     * @return True si la porte à été place false sinon
     */
    public boolean GenerateDoor1(int n) {
        if (this.Grid[n / 9][n % 9] == null) {
            this.Grid[n / 9][n % 9] = "D";
            return true;
        }
        return false;
    }

    /**
     * Génère la sale de départ
     */
    public void Generate() { // Start point
        this.Grid[0][4] = "D";
    }

    /**
     * Cree une un tableau repésentant une grille de jeu
     * 
     * @param difficulty int entre 1 et 10
     * @param type       1 = salle normal / 2 = Boss / 3 = Shop
     * @param xydoor     Coordonnées de la porte d'entrée Nord 04, Ouest 36, Est 44,
     *                   Sud 76
     * @param nbdoor     Nombre de portes suplémentaires
     */
    public void Generate(int difficulty, int type, int xydoor, int nbdoor) {

        Random random = new Random();
        // Porte

        this.Grid[xydoor / 9][xydoor % 9] = "D";

        // Gegerate other door
        if (nbdoor > 0) {
            GenerateDoor(nbdoor);
        }

        // Monstres
        if (type == 1) {
            for (int i = 0; i < difficulty + random.nextInt(difficulty); i++) { // nb de monstres
                int n = random.nextInt(81);
                if (this.Grid[n / 9][n % 9] == null) {
                    this.Grid[n / 9][n % 9] = "M";
                }

            }
        }
        if (type == 2) {
            this.Grid[4][4] = "B";
        }
        // Obstacles
        if (type == 1 || type == 2) {
            int obstacle = random.nextInt(7) + 3; // nombre obstacle
            for (int i = 0; i < obstacle; i++) {
                int co = random.nextInt(81);
                while (this.Grid[co / 9][co % 9] != null) {
                    co = random.nextInt(81);
                }
                this.Grid[co / 9][co % 9] = "O"; // Trap

            }

            // Items
            int rdm = random.nextInt(4);
            for (int i = 0; i < rdm; i++) {
                int co = random.nextInt(81);
                while (this.Grid[co / 9][co % 9] != null) {
                    co = random.nextInt(81);
                }
                this.Grid[co / 9][co % 9] = "I";
            }
            // Bomb
            int bomb = random.nextInt(2);
            for (int i = 0; i < bomb; i++) {
                int co = random.nextInt(81);
                while (this.Grid[co / 9][co % 9] != null) {
                    co = random.nextInt(81);
                }
                this.Grid[co / 9][co % 9] = "B";
            }
        }
        // TODO Verifier qu'aucune case à 4 rocher autour d'elle

    }

    /**
     * Fonction pour développeurs
     * Premet d'afficher la grille dans la console
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.Grid[i][j] == null) {
                    s += "_ ";
                } else {
                    s += this.Grid[i][j] + " ";
                }
            }
            s += "\n";
        }
        return s;
    }

}