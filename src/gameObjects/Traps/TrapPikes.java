package gameObjects.Traps;

import gameObjects.Entities.EntityTrap;

import libraries.Vector2;

/**
 * 
 */
public class TrapPikes extends EntityTrap {
    public static final int DAMAGE = 1;
    public static final String IMGPATH = "images/Spikes.png";

    /**
     * Pose un piège à l'emplacement donné
     * 
     * @param pos Vecteur position
     */
    public TrapPikes(Vector2 pos) {
        super(pos, DAMAGE, IMGPATH);
    }
}
