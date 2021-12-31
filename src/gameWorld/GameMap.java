package gameWorld;

import java.util.Random;

public class GameMap {
    public static GameRoom curent;
    public static GameMap Nord;
    public static GameMap Sud;
    public static GameMap Est;
    public static GameMap Ouest;

    public GameMap(int difficulty, int type, int xydoor, int nbdoor) {
        curent = new GameRoom();
        curent.generateGameRoom(difficulty, type, xydoor, nbdoor);
        relation(difficulty, type, xydoor, nbdoor);
    }

    public void relation(int difficulty, int type, int xydoor, int nbdoor) {
        if (nbdoor > 0) {
            Random random = new Random();
            int rdm = random.nextInt(4);
            int[] co = { 4, 36, 44, 76 };
            for (int i = 0; i < nbdoor; ++i) {
                if (curent.doorList.get(i).getPos() == GameRoom.getPositionFromTile(0, 4)
                        && (xydoor != co[i])) {
                    Nord = new GameMap(difficulty + random.nextInt(2), type, 4, nbdoor - random.nextInt(nbdoor));
                }
            }
        }
    }
}