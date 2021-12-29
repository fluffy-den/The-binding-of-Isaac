package gameObjects.Monsters;

import resources.ImagePaths;
import resources.FlyInfos;

import libraries.Vector2;

public class Fly extends Monster {

    // Constructeur
    public Fly(Vector2 pos) {
        super(pos,
                FlyInfos.FLY_SIZE,
                FlyInfos.FLY_SPEED,
                FlyInfos.FLY_HP,
                ImagePaths.FLY);
    }
}