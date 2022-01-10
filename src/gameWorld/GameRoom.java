package gameWorld;

import gameObjects.Projectiles.MonsterProjectile;
import gameObjects.Projectiles.Tear;
import gameObjects.Terrain.TerrainRock;
import gameObjects.Traps.TrapPikes;
import gameObjects.Hero;
import gameObjects.Doors.OpenedDoor;
import gameObjects.Entities.EntityItem;
import gameObjects.Entities.EntityTerrain;
import gameObjects.Entities.EntityTrap;
import gameObjects.Entities.EntityDoor;
import gameObjects.Items.ItemBloodOfTheMartyr;
import gameObjects.Items.ItemCricketHead;
import gameObjects.Items.ItemHalfRedHeart;
import gameObjects.Items.ItemHeart;
import gameObjects.Items.ItemJesusJuice;
import gameObjects.Items.ItemLunch;
import gameObjects.Items.ItemMagicMushroom;
import gameObjects.Items.ItemNickel;
import gameObjects.Items.ItemPentagram;
import gameObjects.Items.ItemRedHeart;
import gameObjects.Items.ItemStigmata;
import gameObjects.Monsters.MonsterFly;
import gameObjects.Monsters.MonsterSpider;
import gameObjects.Entities.EntityMonster;

import gameWorld.GameLevel;

import libraries.Vector2;
import libraries.StdDraw;

import resources.Controls;

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
    protected LinkedList<EntityTerrain> terrainList;
    protected String imgPath;

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
        this.terrainList = new LinkedList<EntityTerrain>();
        this.imgPath = DEFAULT_BACKGROUND;
        // generateGameRoom(1, 1, 4, 0);
    }

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
        // Items au sol
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
        // On suppose que les pièges ne fonctionnent que sur le Héro
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
        // On suppose que les portes ne fonctionnent que sur le Héro

        for (EntityDoor t : this.doorList) {
            if (monsterList.isEmpty()) {
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
        // Collisions avec monstres et le hero
        for (EntityTerrain t : this.terrainList) {
            if (t.isAdjacent(h)) {
                t.onLivingAdjacency(h);
            }
            for (EntityMonster m : this.monsterList) {
                if (t.isAdjacent(m)) {
                    t.onLivingAdjacency(m);
                }
            }
            t.updateAndDraw();
        }
    }

    /**
     * 
     * @param h
     */
    public void updateHeroBombsActions(Hero h) {
        // Bombes au sol

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
        for (EntityMonster m : this.monsterList) {
            if (m.isAdjacent(h)) {
                m.onMonsterHeroAdjacency(h);
                ArrayList<MonsterProjectile> pList = m.monsterFireProjectiles(h);
                if (pList != null) {
                    this.projListMonster.addAll(pList);
                }
            }
            m.updateAndDraw();
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

    public void generateGameRoom(int difficulty, int type, int xydoor, int nbdoor) {
        Grid map = new Grid();
        map.Generate(difficulty, type, xydoor, nbdoor);
        gridToLinked(map.getGrid());
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
                                    d.setRotation(0);
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
                        case "B": // Boss
                            // TODO Ajouter Boss
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
                                int rdm2 = random.nextInt(7);
                                switch (rdm2) {
                                    case 0, 1:
                                        this.itemList.add(new ItemHalfRedHeart(p)); // Régénération de 0.5 coeur
                                        break;
                                    case 2:
                                        this.itemList.add(new ItemHeart(p)); // Régénération complette + donc de 1 coeur
                                        break;
                                    default:
                                        this.itemList.add(new ItemNickel(p));
                                        break;
                                }
                            } else {
                                int rdm2 = random.nextInt(7);
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
                                    default:
                                        assert (false);
                                        break;

                                }
                            }
                            break;
                        case "O": // Obstacle
                            int rdm2 = random.nextInt(3);
                            if (rdm2 == 0) { // 1 chance sur 3 d'avoir des "pointes"
                                this.trapList.add(new TrapPikes(p));
                            } else {
                                this.terrainList.add(new TerrainRock(p));
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
    // TODO: 1. Une grille (X, Y) où l'on peut placer, monstres, items (ArrayList?)
    // TODO: 2. Fonction qui convertie une grille en une salle
    // TODO: 5. Classe IA qui contient la méthode prochaine direction
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

    // TODO: 6. Classe qui génère un niveau entier, dont la difficulté augmente en
    // fonction des niveaux
    // : -> Retrouve sa maman fin, c'est un objet
    // : -> Sinon lui roule dessus -> c'est la fin aussi
    // : -> Easter Egg, maman morte -> Fin
    // : -> Nombre de salle maximum en fonction de la difficulté
    // TODO: 4. Shop
    // TODO: 3. Portes

    // TODO: Fluffy
    // FAIT: 2. Rochers
    // FAIT: 1. Items
    // ...
    // FAIT: 5. Projectiles des monstres
    // Projectiles légers (++vitesse --dégats)
    // Projectiles lourds (--vitesse ++dégats)
    // TODO: 6. Quelques boss (7 premiers du jeu)
    // : ->
    // TODO: 7. Quelques monstres
    // FAIT: 4. Pièges (Piques (timing / switch de textures), Trous)
    // TODO: 3. Bombes qui détruits les rochers (Animation d'explosions)
    // TODO: Clés
}
