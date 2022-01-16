package gameObjects.Terrain;

import gameObjects.Entities.EntityTerrain;

import libraries.Vector2;

/**
 * 
 */
public class TerrainRock extends EntityTerrain {
    public static final String IMGPATH = "images/Rock.png";

    /**
     * Pose une roche à l'emplacement donné
     * 
     * @param pos Vecteur position
     */
    public TerrainRock(Vector2 pos) {
        super(pos, IMGPATH);
    }

}
