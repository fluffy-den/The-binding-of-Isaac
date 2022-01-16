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
import java.util.LinkedList;
import java.util.Random;
import java.awt.Font;
import java.lang.constant.DynamicCallSiteDesc;

public class GameRoom {
    protected LinkedList<EntityMonster> monsterList;
    protected EntityBoss boss;
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
    private boolean isShop;
    private boolean isSpawn;
    private int difficulty;

    public static final double MIN_XPOS = 0.113;
    public static final double MAX_XPOS = 0.887;
    public static final double MIN_YPOS = 0.168;
    public static final double MAX_YPOS = 0.832;
    public static final int NUM_OF_TILES = 9;
    public static final Vector2 TILE_SIZE = new Vector2(
            (MAX_XPOS - MIN_XPOS) / NUM_OF_TILES,
            (MAX_YPOS - MIN_YPOS) / NUM_OF_TILES);
    public static final Vector2 CENTER_POS = new Vector2(0.5, 0.5);
    public static final double TILE_DIST = Math.sqrt(Math.pow(TILE_SIZE.getX(), 2) + Math.pow(TILE_SIZE.getY(), 2));
    public static final String DEFAULT_BACKGROUND = "images/DefaultBackground.png";

    /// Constructeur
    /**
     * Initialise une GameRoom
     */
    public GameRoom() {
        this.monsterList = new LinkedList<EntityMonster>();
        this.boss = null;
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
        this.boss = null;
    }

    /// Dessin et mise à jour
    /**
     * Affiche les textures au sol
     */
    public void drawBackground() {
        /// Sol
        StdDraw.picture(CENTER_POS.getX(), CENTER_POS.getY(), this.imgPath, 1.0, 1.0, 0); // Le sol de base
        if (isSpawn) {
            StdDraw.picture(CENTER_POS.getX(), CENTER_POS.getY(), "images/DrawnedPentagram.png", 0.1, 0.1, 0);
        } else if (isShop) {
            StdDraw.picture(CENTER_POS.getX(), CENTER_POS.getY() + 0.25, "images/HangingMan.png", 0.1, 0.5, 0);
        }
    }

    /**
     * Met à jours et affiches les projectils par rapport au heros
     * 
     * @param h heros
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
                for (int j = 0; j < this.monsterList.size() + 1; j++) {
                    EntityMonster m;
                    if (j < this.monsterList.size()) {
                        m = this.monsterList.get(j);
                    } else {
                        m = this.boss;
                    }
                    if (m != null && e.isAdjacent(m)) {
                        e.onHitLivingObject(m);
                        this.projListHero.remove(i);
                        --i;
                        if (!m.isLiving()) {
                            dropLoot(m);
                            if (m == this.boss) {
                                this.doorList.add(new ExitDoor(new Vector2(0.5, 0.5)));
                                this.boss = null;
                            } else {
                                this.monsterList.remove(m);
                            }
                        } else {
                            m.updateAndDraw();
                        }
                        j = this.monsterList.size() + 2;
                    }
                }
                e.updateAndDraw();
            }
            ++i;
        }
    }

    /**
     * Affiche et met à jours les projectils des monstres
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
     * Affiche et met à jours les items au sol
     * 
     * @param h
     */
    public void updateAndDrawHeroItems(Hero h) {
        /// Items au sol
        int i = 0;
        while (i < this.itemList.size()) {
            EntityItem e = this.itemList.get(i);
            if (e.isAdjacent(h)) {
                if (isShop) { // item de shop
                    if (StdDraw.isKeyPressed(Controls.enter)) {

                        if (h.remCoins(e.getPrice())) { // Si le retrait à été fait on donne l'item
                            e.onHeroItemAction(h);
                            this.itemList.remove(i);
                            --i;
                        }
                    }
                } else { // item classique
                    e.onHeroItemAction(h);
                    this.itemList.remove(i);
                    --i;
                }
            }
            if (isShop) {
                Font font = new Font("Arial", Font.BOLD, 15);
                StdDraw.setFont(font);
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.text(e.getPos().getX(), e.getPos().getY() - 0.043, Integer.toString(e.getPrice()));
            }
            e.updateAndDraw();
            ++i;
        }
    }

    /**
     * Affiche et met à jours les pièges
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
     * Vérifie si le heros peut changer de GameRoom et change les skins de portes
     * 
     * @param h Le heros
     * @return L'emplacement de la salle si nécessaire, null sinon
     */
    public String updateDoors(Hero h) {
        /// Les portes ne fonctionnent que sur le heor
        boolean tmpS = DoorSkin; // Dis si le skin de la porte à été changé
        for (EntityDoor t : this.doorList) {
            if (monsterList.isEmpty() && this.boss == null) {
                if (tmpS) {
                    if (t.getImgPath() == "images/ClosedDoor.png")
                        t.setImgPath("images/OpenedDoor.png");
                    DoorSkin = false;
                }
                if (t.isAdjacent(h)) {
                    if (t.onHeroAdjacency(h)) {
                        if (t.getImgPath() == "images/KeyLockedDoor.png") {
                            t.setImgPath("images/OpenedDoor.png");
                        }
                        if (t.getImgPath() == "images/TrapDoor.png") {
                            if (StdDraw.isKeyPressed(Controls.space)) {
                                h.setPos(new Vector2(CENTER_POS.getX(), CENTER_POS.getY()));
                                return "exit";
                            }
                        } else {
                            DoorSkin = true;
                            Vector2 vec = new Vector2(t.getPos());
                            if (vec.getX() == 0.5) {
                                if (vec.getY() == 0.86 && StdDraw.isKeyPressed(Controls.goUp)) {
                                    return ("top");
                                } else {// 0.14
                                    if (vec.getY() == 0.14 && StdDraw.isKeyPressed(Controls.goDown)) { // Il est
                                                                                                       // important de
                                                                                                       // mettre 0.14
                                                                                                       // pour éviter
                                                                                                       // les bug de TP
                                        return "bottom";
                                    }
                                }
                            } else {// y = 0.5
                                if (vec.getX() == 0.1 && StdDraw.isKeyPressed(Controls.goLeft)) {
                                    return "left";
                                } else {// 0.9
                                    if (vec.getX() == 0.9 && StdDraw.isKeyPressed(Controls.goRight)) {
                                        return "right";
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // t.updateAndDraw();
        }
        return null;
    }

    /**
     * Affiche les portes
     */
    public void drawDoors() {
        for (EntityDoor t : this.doorList) {
            t.updateAndDraw();
        }
    }

    /**
     * Met à jours et affiches le terrain par rapport au heros
     * 
     * @param h heros
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
     * Met à jours et affiches les bombes par rapport au heros
     * (et monstres / obstacles)
     * 
     * @param h heros
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
            if (b != null) {
                b.updateAndDraw();
            }
            if (b != null && b.isTimerOver()) {
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

                // Adjacence avec le boss ?
                if (this.boss != null) {
                    if (this.boss.isAdjacent(e))
                        b.addDamage(this.boss);
                }

                // Adjacence avec le terrain?
                int j = 0;
                while (j < this.terrainList.size()) {
                    EntityTerrain t = this.terrainList.get(j);
                    if (t.isAdjacent(e)) {
                        dropLoot(t);
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
     * Met à jours la position du heros
     * 
     * @param h heros
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
     * Met à jours et affiches les larmes du heros
     * 
     * @param h heros
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
            for (EntityMonster m : this.monsterList) {
                dropLoot(m);
            }
            if (this.boss != null) {
                dropLoot(this.boss);
                this.doorList.add(new ExitDoor(new Vector2(0.5, 0.5)));
                this.boss = null;
            }
            this.monsterList.clear();
        }
    }

    /**
     * Met à jours et affiches les monstres par rapport au heros
     * 
     * @param h heros
     */
    public void updateAndDrawMonsters(Hero h) {
        /// Monstres
        int i = 0;
        while (i < this.monsterList.size()) {
            EntityMonster m = this.monsterList.get(i);
            if (!m.isLiving()) {
                dropLoot(m);
                this.monsterList.remove(i);
                --i;
            }
            if (m.isAdjacent(h)) {
                m.onMonsterHeroAdjacency(h);
            }
            List<MonsterProjectile> pList = m.fireProjectiles(h);
            if (pList != null) {
                this.projListMonster.addAll(pList);
            }
            m.updateAndDraw();
            m.updateAI(this.boss, h, this);
            ++i;
        }

    }

    /**
     * Met à jours et affiches le boss par rapport au heros
     * 
     * @param h heros
     */
    public void updateAndDrawBoss(Hero h) {
        /// Boss
        if (this.boss == null) {
            return;
        }
        if (!this.boss.isLiving()) {
            dropLoot(this.boss);
            this.boss = null;
            return;
        }
        if (this.boss.isAdjacent(h)) {
            this.boss.onMonsterHeroAdjacency(h);
        }
        List<MonsterProjectile> pList = this.boss.fireProjectiles(h);
        if (pList != null) {
            this.projListMonster.addAll(pList);
        }
        List<EntityMonster> mList = this.boss.spawnMonsters(this.terrainList);
        if (mList != null) {
            this.monsterList.addAll(mList);
        }
        this.boss.updateAndDraw();
        this.boss.updateAI(this.boss, h, this);
    }

    public void updateAndDraw(Hero h) {
        this.updateCheatActions(h);
        h.updateCheatActions();
        this.drawBackground();
        this.drawDoors();
        this.updateAndDrawTerrain(h);
        this.updateHeroMovementActions(h);
        this.updateHeroTearActions(h);
        this.updateAndDrawHeroItems(h);
        this.updateHeroBombsActions(h);
        this.updateAndDrawTraps(h);
        this.updateAndDrawMonsters(h);
        this.updateAndDrawBoss(h);
        h.update();
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
     * Donne la position par rapport à la carte 9 * 9
     * 
     * @param p Position du vecteur
     * @return coordonnée x de la case
     */
    public static int getTileXIndex(Vector2 p) {
        int x = (int) ((p.getX() - MIN_XPOS) / TILE_SIZE.getX());
        if (x < 0)
            x = 0;
        else if (x > 8)
            x = 8;
        return x;
    }

    /**
     * Donne la position par rapport à la carte 9 * 9
     * 
     * @param p Position du vecteur
     * @return coordonnée y de la case
     */
    public static int getTileYIndex(Vector2 p) {
        int y = (int) ((p.getY() - MIN_YPOS) / TILE_SIZE.getY());
        if (y < 0)
            y = 0;
        else if (y > 8)
            y = 8;
        return y;
    }

    /**
     * Permet de savoir si un vecteur est sur la grille de jeu
     * 
     * @param p         vecteur à tester
     * @param s         vecteur de déplacement (si besoin)
     * @param obstacles listes des obstacles
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
     * @brief Cree une GameRoom suivant les paramètes suivants
     * 
     * @param difficulty Difficulté du niveau
     * @param type       0 = Spawn / 1 = Salle normal / 2 = Boss / 3 = Shop
     * @param xydoor     Coordonnées de la porte d'entrée Nord 04, Ouest 36, Est 44,
     *                   Sud 76
     * @param nbdoor     Nombre de portes à ajouter sur une grille
     */
    public void generateGameRoom(int difficulty, int type, int xydoor, int nbdoor) {
        if (type == 3) {
            this.isShop = true;
            this.isSpawn = false;
        } else if (type == 0) { // Si la difficultée est à 0 on affiche les commandes et non un pentagram
            if (difficulty > 0) {
                this.isSpawn = true;
            }
            this.isShop = false;
        } else {
            this.isShop = false;
            this.isSpawn = false;
        }
        GameGrid map = new GameGrid();
        map.Generate(difficulty, type, xydoor, nbdoor);
        gridToLinked(map.getGrid());
        if (type == 3) {
            marketMaker();
        }
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
                this.boss = new BossDukeOfFlies(p);
                break;
            case 1:
                this.boss = new BossMegaFaty(p);
                break;
            case 2:
                this.boss = new BossSatan(p);
                break;
            default:
                break;
        }
    }

    /**
     * Utilise le tableau généré par Grid pour générer une game room
     * 
     * @param Grid Tableau de positionnement des éléments
     */
    public void gridToLinked(String[][] Grid) {
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (Grid[i][j] != null) {
                    Vector2 p = getPositionFromTile(i, j);
                    switch (Grid[i][j]) {
                        case "D": // door
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
                            this.monsterList.add(choixMonstre(p));
                            break;
                        case "I": // Item
                            this.itemList.add(choixItem(p));
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

    /**
     * Remplace un item par une clé
     */
    public void addKey() {
        if (itemList.size() > 0) {
            Vector2 vec = new Vector2(this.itemList.getFirst().getPos());
            this.itemList.addFirst(new ItemKey(vec));
            this.itemList.remove(1);
        }
    }

    /**
     * ¨Pose un item sur le lieu de mort d'un mostre
     * 
     * @param vec Coordonnées de la mort
     * @param nb  Nombre d'items en fonctione du monstre
     */
    public void dropLoot(Entity m) {
        Random random = new Random();
        int nb; // Nombre d'items à poser
        Vector2 vec = new Vector2(m.getPos()); // position du monster
        boolean chance = false; // Si le joueur est chanceux, il gagnera des milliers de coins en tuant un boss

        if (boss == m) { // si l'adrese mémoire est égale
            if (50 == random.nextInt(51)) {
                nb = 30;
                chance = true;
            } else {
                nb = 50;
            }
        } else {
            nb = random.nextInt(3);
        }

        for (int i = 0; i < nb; i++) {
            int x = random.nextInt(11);
            int y = random.nextInt(11);
            Vector2 rdm = new Vector2(vec);
            rdm.addX((x - 5) * 0.02);
            rdm.addY((y - 5) * 0.02);
            while (!GameRoom.isPlaceCorrect(rdm, new Vector2(), this.getTerrainList())) {
                x = random.nextInt(11);
                y = random.nextInt(11);
                rdm.setX(vec.getX());
                rdm.setY(vec.getY());
                rdm.addX((x - 5) * 0.02);
                rdm.addY((y - 5) * 0.02);
            }
            if (chance) {
                switch (random.nextInt(3)) {
                    case 0:
                        itemList.add(new ItemNickel(rdm));
                        break;
                    case 1:
                        itemList.add(new ItemDime(rdm));
                        break;
                    case 2:
                        itemList.add(new ItemPenny(rdm));
                        break;
                }
            } else {
                itemList.add(choixItem(rdm));
            }
        }
    }

    /**
     * CHoix d'un mosntre aléatoire
     * 
     * @param p La position du future monstre
     * @return Le monstre
     */
    public EntityMonster choixMonstre(Vector2 p) {
        Random random = new Random();
        EntityMonster e;
        // TODO version finalerandom.nextInt(2 + (4 * this.difficulty))
        switch (6) {
            case 0:
                e = new MonsterSpider(p); // lvl 0
                break;
            case 1:
                e = new MonsterFly(p); // lvl 0
                break;
            case 2, 3:
                e = new MonsterBlicker(p); // lvl 1
                break;
            case 4, 5:
                e = new MonsterConjoinedFaty(p); // lvl 1
                break;
            case 6, 7:
                e = new MonsterDeathHead(p); // lvl 2
                break;
            case 8, 9:
                e = new MonsterFaty(p); // lvl 2
                break;
            case 10, 11:
                e = new MonsterParabite(p); // lvl 3
                break;
            case 12, 13:
                e = new MonsterGaper(p); // lvl 3
                break;
            case 14, 16:
                e = new MonsterWizoob(p); // lvl 4
                break;
            default:
                e = new MonsterWallCreep(p); // lvl 4
                break;
        }
        return e;
    }

    /**
     * Choisit un item aléatoirement
     * 
     * @param p La position du futur item
     * @return L'item
     */
    public EntityItem choixItem(Vector2 p) {
        EntityItem e = new ItemNickel(p);
        Random random = new Random();
        int prob = random.nextInt(8);
        if (prob != 4) {
            int rdm2 = random.nextInt(11);
            switch (rdm2) {
                case 0, 1:
                    e = new ItemHalfRedHeart(p); // Régénération de 0.5 coeur
                    break;
                case 2, 3, 4:
                    e = new ItemBomb(p);
                    break;
                case 5:
                    e = new ItemRedHeart(p);
                default:
                    e = new ItemNickel(p);
                    break;
            }
        } else {
            int rdm2 = random.nextInt(11);
            switch (rdm2) {
                case 0:
                    e = new ItemBloodOfTheMartyr(p);
                    break;
                case 1:
                    e = new ItemJesusJuice(p);
                    break;
                case 2:
                    e = new ItemLunch(p);
                    break;
                case 3:
                    e = new ItemCricketHead(p);
                    break;
                case 4, 5:
                    e = new ItemHeart(p);
                    break;
                case 6:
                    e = new ItemStigmata(p);
                    break;
                case 7:
                    e = new ItemMagicMushroom(p);
                    break;
                case 8:
                    e = new ItemPentagram(p);
                    break;
                case 9:
                    e = new ItemPenny(p);
                    break;
                case 10:
                    e = new ItemDime(p);
                    break;

            }
        }
        return e;
    }

    /**
     * Ajoute un item achetable sur carte
     * 
     * @param p Coordonnées de l'item
     */
    public void shopableItems(Vector2 p) {
        Random random = new Random();
        EntityItem e = new ItemNickel(p);
        int price = 10 + random.nextInt(16);
        int rdm = random.nextInt(7);
        switch (rdm) {
            case 0:
                e = new ItemBloodOfTheMartyr(p);
                break;
            case 1:
                e = new ItemJesusJuice(p);
                break;
            case 2:
                e = new ItemLunch(p);
                break;
            case 3:
                e = new ItemStigmata(p);
                break;
            case 4:
                e = new ItemMagicMushroom(p);
                break;
            case 5:
                e = new ItemPentagram(p);
                break;
            case 6:
                e = new ItemKey(p);
                break;

        }
        e.setPrice(price);
        this.itemList.add(e);
    }

    /**
     * Place les 3 items à vendre
     */
    public void marketMaker() {
        shopableItems(getPositionFromTile(2, 3));
        shopableItems(getPositionFromTile(4, 3));
        shopableItems(getPositionFromTile(6, 3));

    }

    /**
     * Change la texture de la carte
     * 
     * @param s
     */
    public void setBackground(String s) {
        this.imgPath = s;
    }

    /**
     * Change la difficulté de la carte
     * (Utilise pour les tpyes de monstres)
     * 
     * @param d La difficulté
     */
    public void setDifficulty(int d) {
        this.difficulty = d;
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
    // FAIT: 3. Portes
    // FAIT loots moob
    // FAIT Images
    // FAIT Shop
    // Fait Clés
    // FAIT Trapes Level
    // FAIT HUD Mob => overright du draw ?
    // FAIT Bombe timer
    // Esater egg On écrase la mere à la fin

    /// TODO: Fluffy
    // FAIT: IA (Berserk, Bounding, Random)
    // FAIT: 6. Quelques boss (7 premiers du jeu)
    // FAIT: 7. Quelques monstres
    // FAIT: IA
    // FAIT: Tire des mobs
}
