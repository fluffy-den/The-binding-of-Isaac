package resources;

import libraries.Vector2;

/**
 * Informations imutables d'une piece
 */
public class RoomInfos {
	public static final double POSITION_MIN_X = 0.113;
	public static final double POSITION_MAX_X = 0.887;
	public static final double POSITION_MIN_Y = 0.168;
	public static final double POSITION_MAX_Y = 0.832;
	public static final int NUM_OF_TILES = 9;
	public static final Vector2 TILE_SIZE = new Vector2((POSITION_MAX_X - POSITION_MIN_X) / NUM_OF_TILES,
			(POSITION_MAX_Y - POSITION_MIN_Y) / NUM_OF_TILES);
	public static final Vector2 POSITION_CENTER_OF_ROOM = new Vector2(0.5, 0.5);
}
