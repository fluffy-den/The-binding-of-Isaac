package gameObjects;

import gameWorld.Game;
import gameWorld.GameRoom;
import gameWorld.GameState;

import java.awt.Font;

import gameObjects.Entities.EntityLiving;
import gameObjects.Projectiles.Tear;

import libraries.StdDraw;
import libraries.Vector2;

public class Hero extends EntityLiving {
    private double tearRange;
    private double tearSpeed;
    private double tearReloadSpeed;
    private long lastTearFrame;
    private int tearDamage;
    private int nBombs;
    private int nCoins;
    private int maxHPs;

    // Isaac
    public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.7);
    public static final double SPEED = 0.01;
    public static final double ON_TEAR_EFFECT_POWER = 0.1;
    public static final double RELOAD_SPEED = 0.05;
    public static final int STARTING_HP = 6;
    public static final String IMGPATH = "images/Isaac.png";

    // Larme
    public static final double TEAR_SPEED = 0.015;
    public static final double TEAR_RANGE = 0.45;
    public static final double TEAR_HIT_POWER = 0.1;
    public static final double TEAR_RELOAD_SPEED = 0.05;
    public static final int TEAR_STARTING_DAMAGE = 1;

    // Constructeur
    public Hero() {
        super(GameRoom.CENTER_POS,
                SIZE,
                new Vector2(),
                SPEED,
                STARTING_HP,
                IMGPATH);
        this.tearRange = TEAR_RANGE;
        this.tearSpeed = TEAR_SPEED;
        this.tearReloadSpeed = TEAR_RELOAD_SPEED;
        this.maxHPs = STARTING_HP;

        // Larme
        this.lastTearFrame = 0;
        this.tearDamage = TEAR_STARTING_DAMAGE;

        // Inventaire
        this.nBombs = 0;
        this.nCoins = 0;
    }

    // HP
    /**
     * 
     * @return
     */
    public int getMaxHPs() {
        return this.maxHPs;
    }

    /**
     * 
     * @param health
     * @return
     */
    public int addMaxHPs(int health) {
        this.maxHPs += health;
        return this.maxHPs;
    }

    /**
     * 
     * @param health
     * @return
     */
    public int addHPs(int health) {
        int newHealth = this.health + health;
        if (newHealth < 0)
            newHealth = this.maxHPs;
        if (newHealth > this.maxHPs)
            newHealth = this.maxHPs;
        this.health = newHealth;
        return this.health;
    }

    // Vitesse
    /**
     * 
     * @param speed
     * @return
     */
    public double addSpeed(double speed) {
        this.speed += speed;
        return this.speed;
    }

    // Projectile
    /**
     * @brief Permet de savoir si on peut retirer une lame (temps)
     * @return True si on peut tirer False sinon
     */
    public boolean isReloaded() {
        long elapsed = Game.getImageNum() - this.lastTearFrame;
        if (elapsed * this.tearReloadSpeed >= 1) {
            this.lastTearFrame = Game.getImageNum();
            return true;
        }
        return false;
    }

    /**
     * 
     * @param damage
     * @return
     */
    public int addTearDamage(int damage) {
        this.tearDamage += damage;
        return this.tearDamage;
    }

    /**
     * 
     * @param range
     * @return
     */
    public double addTearRange(double range) {
        this.tearRange += range;
        return this.tearRange;
    }

    public double addTearReloadSpeed(double reloadSpeed) {
        this.tearReloadSpeed += reloadSpeed;
        return this.tearReloadSpeed;
    }

    /**
     * @brief si on peut tirer, envoie une larme vers le haut
     * @return Larme + Direction imutable
     */
    public Tear fireTearUp() {
        if (!isReloaded())
            return null;
        Vector2 dir = new Vector2();
        dir.addY(1);
        return new Tear(this.getPos(), dir.addVector(this.dir.scalarMultiplication(ON_TEAR_EFFECT_POWER)),
                this.tearSpeed, this.tearDamage, this.tearRange);
    }

    /**
     * @brief si on peut tirer, envoie une larme vers le bas
     * @return Larme + Direction imutable
     */
    public Tear fireTearDown() {
        if (!isReloaded())
            return null;
        Vector2 dir = new Vector2();
        dir.addY(-1);
        return new Tear(this.getPos(), dir.addVector(this.dir.scalarMultiplication(ON_TEAR_EFFECT_POWER)),
                this.tearSpeed, this.tearDamage, this.tearRange);
    }

    /**
     * @brief si on peut tirer, envoie une larme vers la gauche
     * @return Larme + Direction imutable
     */
    public Tear fireTearLeft() {
        if (!isReloaded())
            return null;
        Vector2 dir = new Vector2();
        dir.addX(-1);
        return new Tear(this.getPos(), dir.addVector(this.dir.scalarMultiplication(ON_TEAR_EFFECT_POWER)),
                this.tearSpeed, this.tearDamage, this.tearRange);
    }

    /**
     * @brief si on peut tirer, envoie une larme vers la droite
     * @return Larme + Direction imutable
     */
    public Tear fireTearRight() {
        if (!isReloaded())
            return null;
        Vector2 dir = new Vector2();
        dir.addX(1);
        return new Tear(this.getPos(), dir.addVector(this.dir.scalarMultiplication(ON_TEAR_EFFECT_POWER)),
                this.tearSpeed, this.tearDamage, this.tearRange);
    }

    // HUDs
    public static final String IMGPATH_HUD_HEART_FULL = "images/HUD_heart_red_full.png";
    public static final String IMGPATH_HUD_HEART_HALF = "images/HUD_heart_red_half.png";
    public static final String IMGPATH_HUD_HEART_EMPTY = "images/HUD_heart_red_empty.png";
    public static final String IMGPATH_HUD_BOMB = "images/Bomb.png";
    public static final String IMGPATH_HUD_COINS = "images/Dime.png";

    /**
     * 
     */
    public void drawHealthBar() {
        Vector2 pos = new Vector2(0.040, 1 - 0.033);
        Vector2 size = new Vector2(0.03, 0.03);
        // 1 coeur = 2 pts de vie
        int nCoeurs = this.maxHPs / 2;
        int nCoeursComplets = 0;
        if (this.getHealth() > 0)
            nCoeursComplets = this.getHealth() / 2;

        // Affiche les coeurs complets
        for (int i = 0; i < nCoeursComplets; ++i) {
            StdDraw.picture(pos.getX() + size.getX() * i, pos.getY(),
                    IMGPATH_HUD_HEART_FULL, size.getX(), size.getY(), 0);
        }

        // Affiche les demis-coeurs
        int j = nCoeursComplets;
        if (this.getHealth() % 2 == 1) {
            StdDraw.picture(pos.getX() + size.getX() * j, pos.getY(),
                    IMGPATH_HUD_HEART_HALF, size.getX(), size.getY(), 0);
            j += 1;
        }

        // Affiche les coeurs vides
        for (int i = j; i < nCoeurs; ++i) {
            StdDraw.picture(pos.getX() + size.getX() * i, pos.getY(),
                    IMGPATH_HUD_HEART_EMPTY, size.getX(), size.getY(), 0);
        }
    }

    /**
     * 
     */
    public void drawBombsBar() {
        Vector2 pos = new Vector2(0.040, 1 - 0.066);
        Vector2 size = new Vector2(0.03, 0.03);
        Font font = new Font("Arial", Font.BOLD, 11);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(font);
        StdDraw.picture(pos.getX(), pos.getY(), IMGPATH_HUD_BOMB, size.getX(), size.getY());
        StdDraw.textLeft(pos.getX() + size.getX(), pos.getY(), Integer.toString(this.nBombs));
        StdDraw.setPenColor();

    }

    /**
     * 
     */
    public void drawCoinsBar() {
        Vector2 pos = new Vector2(0.040, 1 - 0.099);
        Vector2 size = new Vector2(0.03, 0.03);
        Font font = new Font("Arial", Font.BOLD, 11);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(font);
        StdDraw.picture(pos.getX(), pos.getY(), IMGPATH_HUD_COINS, size.getX(), size.getY());
        StdDraw.textLeft(pos.getX() + size.getX(), pos.getY(), Integer.toString(this.nCoins));
        StdDraw.setPenColor();
    }

    /**
     * 
     */
    public void drawTearRangeHUD() {

    }

    /**
     * 
     */
    public void drawTearReloadSpeedHUD() {

    }

    /**
     * 
     */
    public void drawTearDamageHUD() {

    }

    /**
     * 
     */
    public void drawSpeedHUD() {

    }

    /**
     * 
     */
    public void drawHUD() {
        this.drawHealthBar();
        this.drawBombsBar();
        this.drawCoinsBar();
    }

    // Damage
    /**
     * 
     */
    public void addDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0)
            Game.updateGameState(GameState.LOST);
    }
}
