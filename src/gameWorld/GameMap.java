package gameWorld;

import libraries.Vector2;

public class GameMap {
    public GameRoom current;
    public Vector2 co;

    /**
     * @brief Cree une GameRoom suivant les paramètes suivants
     * 
     * @param difficulty Difficulté du niveau
     * @param type       0 = Spawn / 1 = Salle normal / 2 = Boss / 3 = Shop
     * @param xydoor     Coordonnées de la porte d'entrée Nord 04, Ouest 36, Est 44,
     *                   Sud 76
     * @param nbdoor     Nombre de portes à ajouter sur une grille
     * @param bossDifficulty Permet d'associer une difficulté à un boss
     */
    public GameMap(int difficulty, int type, int xydoor, int nbdoor, Vector2 co, int bossDifficulty) {
        current = new GameRoom();
        current.setDifficulty(bossDifficulty);
        current.generateGameRoom(difficulty, type, xydoor, nbdoor);
        this.co = co;
    }

    /**
     * @brief Donne la liste des vecteurs positions des portes qui viennent d'être
     *        mises
     * 
     * @param xydoor Coordonnées de la porte d'entrée (case de 1 à 81)
     * @param nbDoor Nombre de portes placées
     */
    public void PositionDoors(int xydoor, int nbDoor) {
        Vector2[] coDoor = new Vector2[nbDoor];
        int tmp = 0;
        for (int i = 0; i < current.doorList.size(); i++) {
            if (current.doorList.get(i).getPos() != GameRoom.getPositionFromTile(xydoor / 9, xydoor % 9)) {
                coDoor[tmp] = current.doorList.get(i).getPos();
                tmp++;
            }
        }
    }

    public Vector2 getCo() {
        return this.co;
    }

    public GameRoom GetRoom() {
        return this.current;
    }
}