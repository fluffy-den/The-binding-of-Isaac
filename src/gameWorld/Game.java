package gameWorld;

import gameObjects.Entities.Hero;

import resources.Controls;
import resources.RoomInfos;
import resources.DisplaySettings;

import libraries.StdDraw;
import libraries.Timer;

public class Game {
    private GameRoom currentRoom;
    private Hero hero;
    private static boolean gameOver;
    private static long imageNum = 0;

    // Constructeur
    public Game() {
        this.hero = new Hero();
        this.currentRoom = new GameRoom();

        gameOver = false;
        imageNum = 0;

        // Taille du canvas
        StdDraw.setCanvasSize(
                DisplaySettings.CANVAS_X_SIZE,
                DisplaySettings.CANVAS_Y_SIZE);

        // Activation du double-buffering.
        // https://en.wikipedia.org/wiki/Multiple_buffering#Double_buffering_in_computer_graphics
        StdDraw.enableDoubleBuffering();
    }

    // Boucle du jeu
    public boolean loop() {
        Timer.beginTimer();
        StdDraw.clear(StdDraw.WHITE);

        /// Hero
        // Actions de l'utilisateur
        // Déplacement d'Isaac
        // TODO: Collision avec les murs, objets statiques, & object à ramasser
        if (StdDraw.isKeyPressed(Controls.goUp)) {
            hero.goUpNext();
        }
        if (StdDraw.isKeyPressed(Controls.goDown)) {
            hero.goDownNext();
        }
        if (StdDraw.isKeyPressed(Controls.goRight)) {
            hero.goRightNext();
        }
        if (StdDraw.isKeyPressed(Controls.goLeft)) {
            hero.goLeftNext();
        }

        // Tirs d'Isaac
        if (StdDraw.isKeyPressed(Controls.fireUp)) {
            this.currentRoom.addHeroProjectile(hero.fireTearUp());
        }
        if (StdDraw.isKeyPressed(Controls.fireDown)) {
            this.currentRoom.addHeroProjectile(hero.fireTearDown());
        }
        if (StdDraw.isKeyPressed(Controls.fireRight)) {
            this.currentRoom.addHeroProjectile(hero.fireTearRight());
        }
        if (StdDraw.isKeyPressed(Controls.fireLeft)) {
            this.currentRoom.addHeroProjectile(hero.fireTearLeft());
        }

        /// Modes de triches
        // TODO: Touches pour activer les modes de triche

        /// Affichage
        this.currentRoom.updateAndDraw(this.hero);

        /// Hud
        this.hero.updateAndDraw();
        this.hero.drawHUD();

        imageNum++;

        StdDraw.show();
        Timer.waitToMaintainConstantFPS();

        return !gameOver;
    }

    /**
     * @brief Permet de savoir si la partie est finie
     * @return True si la partie est finie, False sinon
     */
    public static boolean isGameOver() {
        return gameOver;
    }

    /**
     * @brief Met fin a la partie
     */
    public static void setGameOver() {
        gameOver = true;
    }

    // Nombre d'images
    public static long getImageNum() {
        return imageNum;
    }
}
