package gameWorld;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import libraries.Vector2;
import gameObjects.Hero;
import gameObjects.Entities.EntityDoor;
import gameObjects.Doors.BossDoor;
import gameObjects.Doors.KeyLockedDoor;
import gameObjects.Doors.ShopDoor;

public class GameLevel {
    private Hero hero;
    private GameRoom currentRoom;
    private Vector2 currentCord;
    private ArrayList<GameMap> level;
    private long lastTPFrame;
    private ArrayList<GameRoom> alreadyExplore;

    /**
     * Création d'un niveau complet
     * 
     * @param nbRooms Nombre de salle classique maximum du niveau
     */
    public GameLevel(int nbRooms, int difficulty, Hero hero) {
        this.hero = hero;
        this.level = new ArrayList<GameMap>();
        level.add(new GameMap(difficulty, 0, 4, 0, new Vector2(0, 0)));
        creatingClassicRoom(1, 76, 15, level.get(0).getCo());
        this.currentCord = level.get(0).getCo();
        delUselessDoors();
        this.currentRoom = getSpeRoom(this.currentCord);
        this.alreadyExplore = new ArrayList<GameRoom>();
        setKeyNDoors(0, 0, new Vector2(0, 0));
        setSpecialRoom(difficulty, 2);
        setSpecialRoom(0, 3);
        if (difficulty == 0) {
            for (GameMap m : this.level) {
                m.GetRoom().setBackground("images/Start.png");
            }
            getSpeRoom(new Vector2(0, 0)).setBackground("images/StartExplain.png");
        }
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
     * Affiche la salle et change de GameMap si besoin
     */
    public boolean updateAndDraw() {
        this.currentRoom.updateAndDraw(this.hero);
        String s = this.currentRoom.updateDoors(this.hero);

        if (s != null) {
            if (s.equals("exit")) {
                return true;
            }
            ChangeMap(s);
        }
        return false;
    }

    /**
     * Création d'une sale de jeu dite "classique"
     * En opposion aux salles de boss / shop / spawn
     * 
     * @param difficulty  difficulté de la salle
     * @param EntrancePos Coordonnées de la porte d'entrée
     * @param nbRoomRest  Nombre maxium de salles qui peuvent être crées
     * @param MotherPos   Coordonnées de la salle précédente sur le level
     */
    public void creatingClassicRoom(int difficulty, int EntrancePos, int nbRoomRest, Vector2 MotherPos) {
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
            return;
        }
        GameMap map = new GameMap(difficulty + d, 1, EntrancePos, nbD, pos);
        level.add(map);
        if (nbD > 0) {
            for (int i = 0; i < nbD; i++) {
                creatingClassicRoom(difficulty + d, posToInt(map.GetRoom().doorList.get(i).getPos()), nbRoomRest / nbD,
                        pos);
            }
        }
    }

    /**
     * Suprimes du level les portes générées qui ne mènent à rien
     */
    public void delUselessDoors() {
        for (int i = 1; i < this.level.size(); i++) {
            GameMap map = this.level.get(i);
            Vector2 mapPos = map.getCo();
            LinkedList<EntityDoor> doorL = map.GetRoom().doorList;
            for (int d = 0; d < doorL.size(); d++) {
                int xy = posToInt(doorL.get(d).getPos());
                Vector2 tmp = new Vector2(mapPos);
                switch (xy) {
                    case 4:
                        tmp.addY(-1);
                        break;
                    case 36:
                        tmp.addX(1);
                        break;
                    case 44:
                        tmp.addX(-1);
                        break;
                    case 76:
                        tmp.addY(1);
                        break;
                }
                if (getSpeRoom(tmp) == null) {
                    doorL.remove(d);
                    d--;
                }
            }
        }
    }

    /**
     * @brief Converti les coordonnées Vectoriels d'une portes en coordonnées
     *        "linéaires"
     * @param vec Vecteur coordonnées
     * @return Coordonnées linéaires
     */
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

    /**
     * Charge une GameMap après le passage d'un héros par une porte
     * 
     * @param s Emplacement de la GameMpa par rapport à la précédente
     */
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

    /**
     * Verifie si le heros peut passer une porte et change de GameRoom
     * 
     * @param vec Emplacement de la GameRoom sur un level
     * @return True si le heros à changé de GameRoom, false sinon
     */
    public boolean setRoom(Vector2 vec) {
        long elapsed = Game.getImageNum() - this.lastTPFrame;
        if (elapsed * 0.25 >= 1) {
            this.lastTPFrame = Game.getImageNum();
            GameRoom g = getSpeRoom(vec);
            if (g != null) {
                this.currentRoom = g;
                this.currentCord = vec;
                return true;
            }
        }
        return false;
    }

    /**
     * Permet de générer une salle speciale aléatoirement sur le level
     * 
     * @param bossLevel (0 à 5) Si un boss est présent
     * @param type      2 Boss / 3 Shop
     */
    public void setSpecialRoom(int bossLevel, int type) {
        Boolean b = false;
        Random random = new Random();
        int i = random.nextInt(this.level.size());
        EntityDoor d;
        if (type == 2) {
            d = new BossDoor(new Vector2(0.5, 0.86));
        } else {
            d = new ShopDoor(new Vector2(0.5, 0.86));
        }
        while (b == false) {
            Vector2 pos = new Vector2(this.level.get(i).getCo());
            pos.addX(1);
            if (getSpeRoom(pos) == null) {
                this.level.add(new GameMap(bossLevel, type, 36, 0, pos));
                d.setPos(new Vector2(0.9, 0.5));
                d.setRotation(270);
                this.level.get(i).GetRoom().doorList.add(d);
                b = true;
            } else {
                pos.addX(-2);
                if (getSpeRoom(pos) == null) {
                    this.level.add(new GameMap(bossLevel, type, 44, 0, pos));
                    d.setPos(new Vector2(0.1, 0.5));
                    d.setRotation(90);
                    this.level.get(i).GetRoom().doorList.add(d);
                    b = true;
                } else {
                    pos.addX(1);
                    pos.addY(1);
                    if (getSpeRoom(pos) == null) {
                        this.level.add(new GameMap(bossLevel, type, 76, 0, pos));
                        this.level.get(i).GetRoom().doorList.add(d);
                        b = true;
                    } else {
                        pos.addY(-2);
                        if (getSpeRoom(pos) == null) {
                            this.level.add(new GameMap(bossLevel, type, 4, 0, pos));
                            d.setPos(new Vector2(0.5, 0.14));
                            d.setRotation(180);
                            this.level.get(i).GetRoom().doorList.add(d);
                            b = true;
                        }
                    }
                }
            }
            if (b) {
                if (type == 2)
                    getSpeRoom(pos).addBoss(bossLevel);
                return;
            }
            i = random.nextInt(this.level.size());
        }
    }

    /**
     * 
     * @param nbK    Nombre de clés à placer
     * @param nbDSet Nombre de portes fermées déjà placées
     */
    public void setKeyNDoors(int nbK, int nbDSet, Vector2 currentC) {
        GameRoom current = getSpeRoom(currentC);
        if (this.alreadyExplore.contains(current)) {
            return;
        } else {
            this.alreadyExplore.add(current);
            Random random = new Random();
            if (nbK < 3) {
                int rdm = random.nextInt(6);
                if (rdm < 3) {
                    current.addKey();
                    nbK++;
                }
            }
            if (nbK > 0 && (current.doorList.size() > 1)) {
                int rdm = random.nextInt(3);
                if (rdm < 1) {
                    for (int i = 0; i < current.doorList.size(); i++) {
                        EntityDoor d = current.doorList.get(i);
                        Vector2 vec = d.getPos();
                        Vector2 tmp = new Vector2(currentC);
                        if (vec.getX() == 0.5) {
                            if (vec.getY() == 0.86) {
                                tmp.addY(1);
                            } else { // 0.14
                                tmp.addY(-1);
                            }
                        } else {// y = 0.5
                            if (vec.getX() == 0.1) {
                                tmp.addX(-1);
                            } else {// 0.9
                                tmp.addX(1);

                            }
                        }
                        if (alreadyExplore.contains(getSpeRoom(tmp)) == false) {
                            EntityDoor newd = new KeyLockedDoor(current.doorList.get(i).getPos());
                            newd.setRotation(current.doorList.get(i).getRotation());
                            current.doorList.add(newd);
                            current.doorList.remove(i);
                            nbDSet++;
                            i = 5;
                        }
                    }
                }
            }
            for (EntityDoor d : current.doorList) {
                Vector2 vec = d.getPos();
                Vector2 tmp = new Vector2(currentC);
                if (vec.getX() == 0.5) {
                    if (vec.getY() == 0.86) {
                        tmp.addY(1);
                    } else { // 0.14
                        tmp.addY(-1);
                    }
                } else {// y = 0.5
                    if (vec.getX() == 0.1) {
                        tmp.addX(-1);
                    } else {// 0.9
                        tmp.addX(1);

                    }
                }
                if (alreadyExplore.contains(getSpeRoom(tmp)) == false && nbDSet < 3) {
                    Vector2 vec2 = new Vector2(tmp);
                    setKeyNDoors(nbK, nbDSet, vec2);
                }
            }
        }
    }
}
