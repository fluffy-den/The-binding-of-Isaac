package gameWorld;

import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.Random;

import gameObjects.Hero;
import gameWorld.GameMap;
import libraries.Vector2;

public class GameLevel {
    private Hero hero;
    private GameRoom currentRoom;
    private Vector2 currentCord;
    private ArrayList<GameMap> level;
    private long lastTPFrame;

    /**
     * 
     * @param startingRoom
     */
    public GameLevel(int nbRooms) {
        this.level = new ArrayList<GameMap>();
        this.hero = new Hero();
        level.add(new GameMap(0, 0, 4, 0, new Vector2(0, 0)));
        CreatingClassicRoom(1, 76, 3, level.get(0).getCo());
        this.currentCord = level.get(0).getCo();

        for (int i = 0; i < level.size(); i++) {
            System.out.println("Pièce " + i + " en " + level.get(i).getCo());
        }
        this.currentRoom = getSpeRoom(this.currentCord);
    }

    /**
     * Renvoie la la pièce d'une position donnée de la map
     * 
     * @param vec Position de la pièce
     * @return pièce demandée ou à defaut null
     */
    private GameRoom getSpeRoom(Vector2 vec) {
        for (int i = 0; i < this.level.size(); i++) {
            Vector2 tmp = level.get(i).getCo();
            if (tmp.getX() == vec.getX() && tmp.getY() == vec.getY()) {
                return level.get(i).GetRoom();
            }
        }
        return null;
    }

    /**
     * 
     * @param h
     */
    public void updateAndDraw() {
        this.currentRoom.updateAndDraw(this.hero);

        String s = this.currentRoom.updateAndDrawDoors(this.hero);
        if (s != null) {
            ChangeMap(s);
        }
    }

    public void CreatingClassicRoom(int difficulty, int EntrancePos, int nbRoomRest, Vector2 MotherPos) {
        Random random = new Random();
        int d = random.nextInt(2); // increase difficulty
        int nbD;
        if (nbRoomRest > 3) {
            nbD = 1 + random.nextInt(3); // Door numbers
            nbRoomRest -= nbD;
        } else {
            nbD = nbRoomRest;
            nbRoomRest = 0;
        }
        Vector2 pos = new Vector2(MotherPos);
        switch (EntrancePos) {
            case 4:
                pos.addY(-1);
                break;
            case 36:
                pos.addX(1);
                break;
            case 44:
                pos.addX(-1);
                break;
            case 76:
                pos.addY(1);
                break;

        }
        if (getSpeRoom(pos) != null) {
            System.out.println("Pas de création en" + pos);
            return;
        }
        System.out.println("Création en" + pos);
        GameMap map = new GameMap(difficulty + d, 1, EntrancePos, nbD, pos);
        if (nbD > 0) {
            for (int i = 0; i < nbD; i++) {
                System.out.println(posToInt(map.GetRoom().doorList.get(i).getPos()));
                CreatingClassicRoom(difficulty + d, posToInt(map.GetRoom().doorList.get(i).getPos()), nbRoomRest / nbD,
                        pos);
            }
        }
        level.add(map);
    }

    public int posToInt(Vector2 vec) {
        if (vec.getX() == 0.5) {
            if (vec.getY() == 0.86) {
                return 76;
            } else {// 0.14
                return 4;
            }
        } else {// y = 0.5
            if (vec.getX() == 0.1) {
                return 44;
            } else {// 0.9
                return 36;
            }
        }

    }

    public void ChangeMap(String s) {
        Vector2 vec = new Vector2(this.currentCord);
        switch (s) {
            case "top":
                vec.addY(1);
                if (setRoom(vec)) {
                    hero.setPos(new Vector2(0.5, 0.1));
                }
                break;
            case "bottom":
                vec.addY(-1);
                if (setRoom(vec)) {
                    hero.setPos(new Vector2(0.5, 0.8));
                }
                break;
            case "right":
                vec.addX(1);
                if (setRoom(vec)) {
                    hero.setPos(new Vector2(0.1, 0.5));
                }
                break;
            case "left":
                vec.addX(-1);
                if (setRoom(vec)) {
                    hero.setPos(new Vector2(0.9, 0.5));
                }
                break;
            default:
                assert (false);
        }
    }

    public boolean setRoom(Vector2 vec) {
        long elapsed = Game.getImageNum() - this.lastTPFrame;
        if (elapsed * 0.025 >= 1) {
            this.lastTPFrame = Game.getImageNum();
            GameRoom g = getSpeRoom(vec);
            if (g != null) {
                System.out.println("Téléportation en " + vec);
                this.currentRoom = g;
                this.currentCord = vec;
                return true;
            }
        }
        return false;
    }
}
