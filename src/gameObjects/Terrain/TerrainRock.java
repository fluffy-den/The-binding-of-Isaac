package gameObjects.Terrain;

import gameObjects.Entities.EntityTerrain;

import libraries.Vector2;

/**
 * 
 */
public class TerrainRock extends EntityTerrain {
    public static final String IMGPATH = "images/Rock.png";

    /**
     * 
     * @param pos
     */
    public TerrainRock(Vector2 pos) {
        super(pos, IMGPATH);
    }

}
