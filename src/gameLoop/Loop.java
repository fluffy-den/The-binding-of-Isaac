package gameLoop;

import gameWorld.Game;
import libraries.StdDraw;
import resources.ImagePaths;

public class Loop {
    // Lancement du jeu
    public static void main(String[] args) {
        Game isaac = new Game();
        while (isaac.loop())
            ;
    }
}
