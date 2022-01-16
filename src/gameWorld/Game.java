package gameWorld;

import resources.DisplaySettings;

import libraries.StdDraw;
import libraries.Timer;

import gameObjects.Hero;

public class Game {
    private static GameLevel LEVEL;
    private static GameState STATE;
    private static long IMAGE_NUM;

    public static final boolean ALLOW_CHEAT = false;

    /**
     * 
     */
    public static void setupAndLoop() {
        STATE = GameState.RUNNING;
        IMAGE_NUM = 0;

        // Taille du canvas
        StdDraw.setCanvasSize(
                DisplaySettings.CANVAS_X_SIZE,
                DisplaySettings.CANVAS_Y_SIZE);

        // Activation du double-buffering.
        // https://en.wikipedia.org/wiki/Multiple_buffering#Double_buffering_in_computer_graphics
        StdDraw.enableDoubleBuffering();

        Hero hero = new Hero();
        LEVEL = new GameLevel(10, 0, hero);
        int lvl = 1; // 3 niveau (car 3 boss)
        // Boucle du jeu
        while (STATE == GameState.RUNNING) {
            Timer.beginTimer();
            StdDraw.clear();

            /// Affichage et changement de niveau
            if (LEVEL.updateAndDraw()) {
                LEVEL = new GameLevel(10, lvl, hero);
                lvl++;
            }
            if(lvl == 4){
                STATE = GameState.WIN;
            }

            IMAGE_NUM++;

            StdDraw.show();
            Timer.waitToMaintainConstantFPS();
        }

        // Fin du jeu
        // Le joueur a gagné?
        String imgPath = "";
        if (STATE == GameState.WIN) {
            imgPath = "images/Win.jpg";
        }

        // Le joueur a perdu?
        else {
            imgPath = "Images/Lose.png";
        }

        // On affiche l'image de fin du jeu pendant 5 secondes
        long btp = System.currentTimeMillis();
        long etp = btp;
        while (etp - btp < 5000) {
            // On simule une boucle de jeu, afin que l'utilisateur puisse fermer
            // la fenetre lors de ces 5 secondes
            Timer.beginTimer();
            StdDraw.clear(StdDraw.BLACK);
            StdDraw.picture(0.5, 0.5, imgPath, 1.0, 1.0, 0);
            StdDraw.show();
            Timer.waitToMaintainConstantFPS();
            etp = System.currentTimeMillis();
        }

        // On supprime tout
        LEVEL = null;
        STATE = null;
        IMAGE_NUM = 0;
    }

    /**
     * 
     * @return
     */
    public static long getImageNum() {
        return IMAGE_NUM;
    }

    /**
     * 
     */
    public static GameState getGameState() {
        return STATE;
    }

    /**
     * 
     */
    public static void updateGameState(GameState state) {
        STATE = state;
    }
}
