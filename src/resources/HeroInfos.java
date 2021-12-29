package resources;

import libraries.Vector2;

/**
 * Informations imutables d'un heros et de son attaque
 */
public class HeroInfos {
	// Isaac
	public static final Vector2 ISAAC_SIZE = RoomInfos.TILE_SIZE.scalarMultiplication(0.7);
	public static final double ISAAC_SPEED = 0.01;
	public static final int ISAAC_HP = 6;

	// Larme
	public static final Vector2 ISAAC_TEAR_SIZE = RoomInfos.TILE_SIZE.scalarMultiplication(0.25);
	public static final double ISAAC_TEAR_SPEED = 0.015;
	public static final double ISAAC_TEAR_SPEED_EFFECT = 0.1;
	public static final int ISAAC_TEAR_DAMAGE = 1;
	public static final double ISAAC_TEAR_RANGE = 0.45;
	public static final double ISAAC_TEAR_RELOAD_SPEED = 0.10; // En tirs/cycles

}
