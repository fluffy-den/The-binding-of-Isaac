package gameWorld;

import resources.DisplaySettings;
import libraries.StdDraw;
import libraries.Timer;

public class Game {
    private static GameLevel currentLevel;
    private static GameState gameState;
    private static long imageNum = 0;

    /**
     * 
     */
    public static void setupAndLoop() {
        gameState = GameState.RUNNING;
        imageNum = 0;

        // Taille du canvas
        StdDraw.setCanvasSize(
                DisplaySettings.CANVAS_X_SIZE,
                DisplaySettings.CANVAS_Y_SIZE);

        // Activation du double-buffering.
        // https://en.wikipedia.org/wiki/Multiple_buffering#Double_buffering_in_computer_graphics
        StdDraw.enableDoubleBuffering();

        // TODO: @cyp3 à changer
        currentLevel = new GameLevel(new GameRoom());

        // Boucle du jeu
        while (gameState == GameState.RUNNING) {
            Timer.beginTimer();
            StdDraw.clear();

            /// Affichage
            currentLevel.updateAndDraw();

            imageNum++;

            StdDraw.show();
            Timer.waitToMaintainConstantFPS();
        }

        // Fin du jeu
        // Le joueur a gagné?
        String imgPath = "";
        if (gameState == GameState.WIN) {
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
        currentLevel = null;
        gameState = null;
        imageNum = 0;
    }

    /**
     * 
     * @return
     */
    public static long getImageNum() {
        return imageNum;
    }

    /**
     * 
     */
    public static GameState getGameState() {
        return gameState;
    }

    /**
     * 
     */
    public static void updateGameState(GameState state) {
        gameState = state;
    }
}
