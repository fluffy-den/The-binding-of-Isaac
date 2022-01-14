package gameWorld;

import gameObjects.*;
import gameObjects.Entities.*;
import gameObjects.Items.*;
import gameObjects.Projectiles.*;
import gameObjects.Bosses.*;
import gameObjects.Doors.*;
import gameObjects.Terrain.*;
import gameObjects.Traps.*;
import gameObjects.Monsters.*;

import libraries.Vector2;
import libraries.StdDraw;

import resources.Controls;
import resources.Utils;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class GameRoom {
    protected LinkedList<EntityMonster> monsterList;
    protected LinkedList<Tear> projListHero;
    protected LinkedList<MonsterProjectile> projListMonster;
    protected LinkedList<EntityItem> itemList;
    protected LinkedList<EntityTrap> trapList;
    protected LinkedList<EntityDoor> doorList;
    protected LinkedList<EntityBomb> bombList;
    private LinkedList<EntityExplosion> explList;
    protected LinkedList<EntityTerrain> terrainList;
    protected String imgPath;
    private GameCounter bombReloadSpeed;
    private boolean DoorSkin = true; // Dit le un changement de skin à été fait

    public static final double MIN_XPOS = 0.113;
    public static final double MAX_XPOS = 0.887;
    public static final double MIN_YPOS = 0.168;
    public static final double MAX_YPOS = 0.832;
    public static final int NUM_OF_TILES = 9;
    public static final Vector2 TILE_SIZE = new Vector2(
            (MAX_XPOS - MIN_XPOS) / NUM_OF_TILES,
            (MAX_YPOS - MIN_YPOS) / NUM_OF_TILES);
    public static final Vector2 CENTER_POS = new Vector2(0.5, 0.5);
    public static final String DEFAULT_BACKGROUND = "images/DefaultBackground.png";

    /// Constructeur
    /**
     * 
     */
    public GameRoom() {
        this.monsterList = new LinkedList<EntityMonster>();
        this.projListHero = new LinkedList<Tear>();
        this.projListMonster = new LinkedList<MonsterProjectile>();
        this.itemList = new LinkedList<EntityItem>();
        this.trapList = new LinkedList<EntityTrap>();
        this.doorList = new LinkedList<EntityDoor>();
        this.bombList = new LinkedList<EntityBomb>();
        this.explList = new LinkedList<EntityExplosion>();
        this.terrainList = new LinkedList<EntityTerrain>();
        this.imgPath = DEFAULT_BACKGROUND;
        this.bombReloadSpeed = new GameCounter(0.25);
        // generateGameRoom(1, 1, 4, 0);
    }

    /// Dessin et mise à jour
    /**
     * 
     */
    public void drawBackground() {
        /// Sol
        StdDraw.picture(CENTER_POS.getX(), CENTER_POS.getY(), this.imgPath, 1.0, 1.0, 0); // Le sol de base
    }

    /**
     * 
     */
    public void updateAndDrawHeroProjectiles() {
        /// Projectiles du joueur
        int i = 0;
        while (i < this.projListHero.size()) {
            Tear e = this.projListHero.get(i);
            if (e.shouldBeDestroyed()) {
                this.projListHero.remove(i);
                --i;
            } else {
                for (EntityMonster m : this.monsterList) {
                    if (e.isAdjacent(m)) {
                        e.onHitLivingObject(m);
                        this.projListHero.remove(i);
                        --i;
                        if (!m.isLiving()) {
                            this.monsterList.remove(m);
                            break;
                        } else {
                            m.updateAndDraw();
                        }
                    }
                }
                e.updateAndDraw();
            }
            ++i;
        }
    }

    /**
     * 
     * @param h
     */
    public void updateAndDrawMonsterProjectiles(Hero h) {
        /// Projectiles des monstres
        int i = 0;
        while (i < this.projListMonster.size()) {
            MonsterProjectile e = this.projListMonster.get(i);
            if (e.shouldBeDestroyed()) {
                this.projListMonster.remove(i);
                --i;
            } else {
                if (e.isAdjacent(h)) {
                    e.onHitLivingObject(h);
                    this.projListMonster.remove(i);
                    --i;
                }
                e.updateAndDraw();
            }
            ++i;
        }
    }

    /**
     * 
     * @param h
     */
    public void updateAndDrawHeroItems(Hero h) {
        /// Items au sol
        int i = 0;
        while (i < this.itemList.size()) {
            EntityItem e = this.itemList.get(i);
            if (e.isAdjacent(h)) {
                e.onHeroItemAction(h);
                this.itemList.remove(i);
                --i;
            } else {
                e.updateAndDraw();
            }
            ++i;
        }
    }

    /**
     * 
     * @param h
     */
    public void updateAndDrawTraps(Hero h) {
        /// Trap
        for (EntityTrap t : this.trapList) {
            if (t.isAdjacent(h)) {
                t.onHeroAdjacency(h);
            }
            t.updateAndDraw();
        }
    }

    /**
     * Affiche les portes et vérifie si le heros peut changer de GameRoom
     * 
     * @param h Le heros
     * @return L'emplacement de la salle si nécessaire, null sinon
     */
    public String updateAndDrawDoors(Hero h) {
        /// Les portes ne fonctionnent que sur le heor
        boolean tmpS = DoorSkin;
        for (EntityDoor t : this.doorList) {
            if (monsterList.isEmpty()) {
                if (tmpS) {
                    if (t.getImgPath() == "images/ClosedDoor.png")
                        t.setImgPath("images/OpenedDoor.png");
                    DoorSkin = false;
                }
                if (t.isAdjacent(h)) {
                    if (t.onHeroAdjacency(h)) {
                        Vector2 vec = new Vector2(t.getPos());
                        if (vec.getX() == 0.5) {
                            if (vec.getY() == 0.86 && StdDraw.isKeyPressed(Controls.goUp)) {
                                return ("top");
                            } else {// 0.14
                                if (StdDraw.isKeyPressed(Controls.goDown)) {
                                    return "bottom";
                                }
                            }
                        } else {// y = 0.5
                            if (vec.getX() == 0.1 && StdDraw.isKeyPressed(Controls.goLeft)) {
                                return "left";
                            } else {// 0.9
                                if (StdDraw.isKeyPressed(Controls.goRight)) {
                                    return "right";
                                }
                            }
                        }
                    }
                }
            }
            t.updateAndDraw();
        }
        return null;
    }

    /**
     * 
     * @param h
     */
    public void updateAndDrawTerrain(Hero h) {
        /// Collision
        for (EntityTerrain t : this.terrainList) {
            // Avec le hero
            if (t.isAdjacent(h)) {
                t.onLivingAdjacency(h);
            }

            // Avec les monstres
            for (EntityMonster m : this.monsterList) {
                if (t.isAdjacent(m)) {
                    t.onLivingAdjacency(m);
                }
            }

            // Mise à jour et dessin
            t.updateAndDraw();
        }
    }

    /**
     * 
     * @param h
     */
    public void updateHeroBombsActions(Hero h) {
        // Drop bomb
        if ((StdDraw.isKeyPressed(Controls.putBomb1) || StdDraw.isKeyPressed(Controls.putBomb2))
                && this.bombReloadSpeed.isFinished()) {
            this.bombList.add(h.deployBomb());
        }

        // Bombes
        int i = 0;
        while (i < this.bombList.size()) {
            EntityBomb b = this.bombList.get(i);
            if (b.isTimerOver()) {
                // Creation de l'explosion
                EntityExplosion e = b.explode();
                this.explList.add(e);

                // Adjacence avec le hero?
                if (h.isAdjacent(e)) {
                    b.addDamage(h);
                }

                // Adjacence avec les monstres?
                for (EntityMonster m : this.monsterList) {
                    if (m.isAdjacent(e))
                        b.addDamage(m);
                }

                // Adjacence avec le terrain?
                int j = 0;
                while (j < this.terrainList.size()) {
                    EntityTerrain t = this.terrainList.get(j);
                    if (t.isAdjacent(e)) {
                        this.itemList.addAll(t.dropLoot());
                        this.terrainList.remove(j);
                        --j;
                    }
                    ++j;
                }

                // Suppression de la bombe
                this.bombList.remove(i);
                --i;
            }
            ++i;
        }

        // Explosions
        i = 0;
        while (i < this.explList.size()) {
            EntityExplosion e = this.explList.get(i);
            if (e.isFinished()) {
                this.explList.remove(i);
                --i;
            } else {
                e.updateAndDraw();
            }
            ++i;
        }
    }

    /**
     * 
     * @param h
     */
    public void updateHeroMovementActions(Hero h) {
        // Déplacement d'Isaac
        if (StdDraw.isKeyPressed(Controls.goUp)) {
            h.addDirUp();
        }
        if (StdDraw.isKeyPressed(Controls.goDown)) {
            h.addDirDown();
        }
        if (StdDraw.isKeyPressed(Controls.goRight)) {
            h.addDirRight();
        }
        if (StdDraw.isKeyPressed(Controls.goLeft)) {
            h.addDirLeft();
        }
    }

    /**
     * 
     */
    public void updateHeroTearActions(Hero h) {
        // Tirs d'Isaac
        if (StdDraw.isKeyPressed(Controls.fireUp)) {
            Tear t = h.fireTearUp();
            if (t != null)
                this.projListHero.add(t);
        }
        if (StdDraw.isKeyPressed(Controls.fireDown)) {
            Tear t = h.fireTearDown();
            if (t != null)
                this.projListHero.add(t);
        }
        if (StdDraw.isKeyPressed(Controls.fireRight)) {
            Tear t = h.fireTearRight();
            if (t != null)
                this.projListHero.add(t);
        }
        if (StdDraw.isKeyPressed(Controls.fireLeft)) {
            Tear t = h.fireTearLeft();
            if (t != null)
                this.projListHero.add(t);
        }
    }

    /**
     * 
     */
    public void updateCheatActions(Hero h) {
        /// Tue tous les monstres
        if (StdDraw.isKeyPressed(Controls.cheatKillAll)) {
            this.monsterList.clear();
        }
    }

    /**
     * 
     * @param h
     *
     */
    public void updateAndDrawMonsters(Hero h) {
        /// Monstres
        int i = 0;
        while (i < this.monsterList.size()) {
            EntityMonster m = this.monsterList.get(i);
            if (!m.isLiving()) {
                this.monsterList.remove(i);
                this.itemList.addAll(m.dropLoot());
                --i;
            }
            if (m.isAdjacent(h)) {
                m.onMonsterHeroAdjacency(h);
                ArrayList<MonsterProjectile> pList = m.fireProjectiles(h);
                if (pList != null) {
                    this.projListMonster.addAll(pList);
                }
            }
            m.updateAndDraw();
            ++i;
        }
    }

    public void updateAndDraw(Hero h) {
        this.updateCheatActions(h);
        h.updateCheatActions();
        this.drawBackground();
        this.updateHeroMovementActions(h);
        this.updateHeroTearActions(h);
        this.updateAndDrawHeroItems(h);
        this.updateHeroBombsActions(h);
        this.updateAndDrawTraps(h);
        this.updateAndDrawMonsters(h);
        h.update();
        this.updateAndDrawTerrain(h);
        this.updateAndDrawHeroProjectiles();
        this.updateAndDrawMonsterProjectiles(h);
        h.draw();
        h.drawHUD();

    }

    /// Tile <=> Position
    /**
     * 
     * @param indexX
     * @param indexY
     * @return
     */
    public static Vector2 getPositionFromTile(int indexX, int indexY) {
        return new Vector2(
                MIN_XPOS + (indexX + 0.5) * TILE_SIZE.getX(),
                MIN_YPOS + (indexY + 0.5) * TILE_SIZE.getY());
    }

    /**
     * 
     */
    public static int getTileXIndex(Vector2 p) {
        double x = p.getX() - MIN_XPOS;
        return (int) Math.ceil(x / TILE_SIZE.getX());
    }

    /**
     * 
     * @param e
     * @return
     */
    public static int getTileYIndex(Vector2 p) {
        double y = p.getY() - MIN_YPOS;
        return (int) Math.ceil(y / TILE_SIZE.getY());
    }

    /**
     * 
     * @param e
     * @return
     */
    public static boolean isPlaceCorrect(Vector2 p, Vector2 s, List<EntityTerrain> obstacles) {
        if (p.getX() - s.getX() < MIN_XPOS)
            return false;
        if (p.getX() + s.getX() > MAX_XPOS)
            return false;
        if (p.getY() - s.getY() < MIN_YPOS)
            return false;
        if (p.getY() + s.getY() > MAX_YPOS)
            return false;
        for (Entity t : obstacles) {
            if (Utils.isAdjacent(p, s, t.getPos(), t.getSize()))
                return false;
        }
        return true;
    }

    /// Terrain
    /**
     * 
     * @return
     */
    public List<EntityTerrain> getTerrainList() {
        return this.terrainList;
    }

    /**
     * 
     * @param difficulty
     * @param type
     * @param xydoor
     * @param nbdoor
     */
    public void generateGameRoom(int difficulty, int type, int xydoor, int nbdoor) {
        GameGrid map = new GameGrid();
        map.Generate(difficulty, type, xydoor, nbdoor);
        gridToLinked(map.getGrid());
    }

    /**
     * Ajoute une boss au milieu de la salle
     * 
     * @param BossLevel Niveau du boss (0 à 4)
     */
    public void addBoss(int BossLevel) {
        Vector2 p = getPositionFromTile(4, 4);
        switch (BossLevel) {
            case 0:
                this.monsterList.add(new BossDarkOne(p));
                break;
            case 1:
                this.monsterList.add(new BossDukeOfFlies(p));
                break;
            case 2:
                this.monsterList.add(new BossMegaFaty(p));
                break;
            case 3:
                this.monsterList.add(new BossTheHusk(p));
                break;
            case 4:
                this.monsterList.add(new BossSatan(p));
                break;
            default:
                break;
        }
    }

    /**
     * Utilise le tableau généré par Grid pour générer une game room
     * 
     * @param Grid Tableau de positionnement des "objets"
     */
    public void gridToLinked(String[][] Grid) {
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (Grid[i][j] != null) {
                    Vector2 p = getPositionFromTile(i, j);
                    switch (Grid[i][j]) {
                        case "D": // door
                            // TODO Portes Complexes
                            if (j == 4) {
                                if (i == 0) {
                                    OpenedDoor d = new OpenedDoor(new Vector2(0.5, 0.86));
                                    this.doorList.add(d);
                                } else {
                                    OpenedDoor d = new OpenedDoor(new Vector2(0.5, 0.14));
                                    d.setRotation(180);
                                    this.doorList.add(d);
                                }
                            } else {
                                if (j == 0) {
                                    OpenedDoor d = new OpenedDoor(new Vector2(0.1, 0.5));
                                    d.setRotation(90);
                                    this.doorList.add(d);
                                } else {
                                    OpenedDoor d = new OpenedDoor(new Vector2(0.9, 0.5));
                                    d.setRotation(270);
                                    this.doorList.add(d);
                                }
                            }

                            break;
                        case "B": // Emplacement réservé
                            // On ne fais pas apparaitre le boss ici pour laisser le choix du boss au
                            // developpeur
                            break;
                        case "M": // Fly or Spider
                            int rdm = random.nextInt(2);
                            if (rdm == 0) {
                                this.monsterList.add(new MonsterSpider(p));
                            } else {
                                this.monsterList.add(new MonsterFly(p));
                            }
                            break;
                        case "I": // Item

                            int prob = random.nextInt(5);

                            if (prob != 4) {
                                int rdm2 = random.nextInt(11);
                                switch (rdm2) {
                                    case 0, 1, 2:
                                        this.itemList.add(new ItemHalfRedHeart(p)); // Régénération de 0.5 coeur
                                        break;
                                    case 3:
                                        this.itemList.add(new ItemHeart(p)); // Régénération complette + don de 1 coeur
                                        break;
                                    default:
                                        this.itemList.add(new ItemNickel(p));
                                        break;
                                }
                            } else {
                                int rdm2 = random.nextInt(8);
                                switch (rdm2) {

                                    case 0:
                                        this.itemList.add(new ItemBloodOfTheMartyr(p));
                                        break;
                                    case 1:
                                        this.itemList.add(new ItemJesusJuice(p));
                                        break;
                                    case 2:
                                        this.itemList.add(new ItemLunch(p));
                                        break;
                                    case 3:
                                        this.itemList.add(new ItemRedHeart(p));
                                        break;
                                    case 4:
                                        this.itemList.add(new ItemCricketHead(p));
                                        break;
                                    case 5:
                                        this.itemList.add(new ItemStigmata(p));
                                        break;
                                    case 6:
                                        this.itemList.add(new ItemMagicMushroom(p));
                                        break;
                                    case 7:
                                        this.itemList.add(new ItemPentagram(p));
                                        break;

                                }
                            }
                            break;
                        case "O": // Obstacle
                            if ((j != 4 || (i != 0 && i != 8)) && (i != 4 || (j != 8 && j != 0))) {

                                int rdm2 = random.nextInt(3);
                                if (rdm2 == 0) { // 1 chance sur 3 d'avoir des "pointes"
                                    this.trapList.add(new TrapPikes(p));
                                } else {
                                    this.terrainList.add(new TerrainRock(p));
                                }
                            }
                            break;
                    }
                }
            }
        }
    }

    /**
     * Retire une port à une position donnée
     * 
     * @param vec Position de la porte à retirer
     */
    public void removeDoor(Vector2 vec) {
        for (int i = 0; i < doorList.size(); i++) {
            if (doorList.get(i).getPos() == vec) {
                doorList.remove(i);
                return;
            }
        }
    }

    /// TODO: Cyp3
    // FAIT: 1. Une grille (X, Y) où l'on peut placer, monstres, items (ArrayList?)
    // FAIT: 2. Fonction qui convertie une grille en une salle
    // : -> IA: Berserk (suit tout le temps le joueur)
    // : -> IA: Random (direction aléatoire tout le temps)
    // : -> IA:
    // Système de chemins en fonction de la grille (class Pathing?)
    // [I] [M] [I] [I]
    // [I] [R] [R] [I]
    // [I] [I] [X] [I]
    // [ ] [ ] [ ] [ ]
    // - Rapide
    // - Distance M - X dépends de constantes (minimales=1) et (maximales=choix)

    // Vérifier que toutes les portes sont reliés S1(L <=> R)S2

    // FAIT: 6. Classe qui génère un niveau entier, dont la difficulté augmente en
    // fonction des niveaux
    // : -> Retrouve sa maman fin, c'est un objet
    // : -> Sinon lui roule dessus -> c'est la fin aussi
    // : -> Easter Egg, maman morte -> Fin
    // : -> Nombre de salle maximum en fonction de la difficulté
    // TODO: 4. Shop
    // FAIT: 3. Portes

    // FAIT Images
    // TODO Shop
    // TODO Clés

    /// TODO: Fluffy
    // TODO: IA (Berserk, Bounding, Random)
    // TODO: 6. Quelques boss (7 premiers du jeu)
    // TODO: 7. Quelques monstres
    // TODO: IA
}
