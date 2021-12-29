package gameObjects.Monsters;

import gameObjects.Entities.EntityLiving;

import libraries.Vector2;

public abstract class Monster extends EntityLiving {

    // Constructeur
    public Monster(Vector2 pos, Vector2 size, double speed, int health, String imgPath) {
        super(pos,
                size,
                new Vector2(),
                speed,
                health,
                imgPath);
    }

}
