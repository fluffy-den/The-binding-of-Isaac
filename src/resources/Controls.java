package resources;

import libraries.Keybinding;

public class Controls {
	// Touches: Déplacement d'ISAAC / passer une porte
	public static int goUp = Keybinding.keycodeOf('z');
	public static int goDown = Keybinding.keycodeOf('s');
	public static int goRight = Keybinding.keycodeOf('d');
	public static int goLeft = Keybinding.keycodeOf('q');

	// Touche: Changer de niveau
	public static int space = Keybinding.keycodeOf(Keybinding.SpecialKeys.SPACE);

	// Touche: Acheter un objet
	public static int enter = Keybinding.keycodeOf(Keybinding.SpecialKeys.RETURN);

	// Touches: Tirs des larmes d'ISAAC
	public static int fireUp = Keybinding.keycodeOf(Keybinding.SpecialKeys.UP);
	public static int fireDown = Keybinding.keycodeOf(Keybinding.SpecialKeys.DOWN);
	public static int fireRight = Keybinding.keycodeOf(Keybinding.SpecialKeys.RIGHT);
	public static int fireLeft = Keybinding.keycodeOf(Keybinding.SpecialKeys.LEFT);

	// Touches: Bombe
	public static int putBomb1 = Keybinding.keycodeOf(Keybinding.SpecialKeys.MAJ);
	public static int putBomb2 = Keybinding.keycodeOf('e');

	// Touches: Codes de triches
	public static int cheatInvincibility = Keybinding.keycodeOf('i');
	public static int cheatSpeed = Keybinding.keycodeOf('l');
	public static int cheatKillAll = Keybinding.keycodeOf('k');
	public static int cheatOneShot = Keybinding.keycodeOf('p');
	public static int cheatGold = Keybinding.keycodeOf('o');
	public static int cheatGiveAll = Keybinding.keycodeOf(Keybinding.SpecialKeys.TAB);

}
