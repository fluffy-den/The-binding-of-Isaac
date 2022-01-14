package gameObjects;

import gameWorld.Game;
import gameWorld.GameRoom;
import gameWorld.GameState;
import gameWorld.GameCounter;

import java.awt.Font;

import gameObjects.Entities.EntityLiving;
import gameObjects.Projectiles.Tear;
import gameObjects.Entities.Entity;
import gameObjects.Entities.EntityBomb;

import libraries.StdDraw;
import libraries.Vector2;
import resources.Controls;

public class Hero extends EntityLiving {
    private double tearRange;
    private double tearSpeed;
    private int tearDamage;
    private int nBombs;
    private int nCoins;
    private int nKeys;
    private int maxHPs;
    private boolean cheatInvincible;
    private boolean cheatSpeed;
    private boolean cheatDamage;
    private GameCounter tearReloadSpeed;
    private GameCounter cheatInvincibleCounter;
    private GameCounter cheatSpeedCounter;
    private GameCounter cheatDamageCounter;
    private GameCounter cheatCoinCounter;

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

    // Key Toggle Ignore step
    public static final double TOGGLE_IGNORE_STEP = 0.05;
    // On peut changer les modes de cheat seulement entre chaques secondes

    /**
     * 
     */
    public Hero() {
        super(GameRoom.CENTER_POS,
                SIZE,
                new Vector2(),
                SPEED,
                false,
                STARTING_HP,
                IMGPATH);
        this.tearRange = TEAR_RANGE;
        this.tearSpeed = TEAR_SPEED;
        this.tearReloadSpeed = new GameCounter(TEAR_RELOAD_SPEED);
        this.maxHPs = STARTING_HP;

        // Larme
        this.tearDamage = TEAR_STARTING_DAMAGE;

        // Inventaire
        this.nBombs = 0;
        this.nCoins = 0;
        this.nKeys = 2;

        // Triche
        this.cheatInvincible = false;
        this.cheatInvincibleCounter = new GameCounter(TOGGLE_IGNORE_STEP);
        this.cheatSpeed = false;
        this.cheatSpeedCounter = new GameCounter(TOGGLE_IGNORE_STEP);
        this.cheatDamage = false;
        this.cheatDamageCounter = new GameCounter(TOGGLE_IGNORE_STEP);
        this.cheatCoinCounter = new GameCounter(TOGGLE_IGNORE_STEP);
    }

    // Keys
    /**
     * 
     * @return
     */
    public int getNumKeys() {
        return this.nKeys;
    }

    /**
     * 
     * @return
     */
    public void addKey() {
        ++this.nKeys;
    }

    /**
     * @brief Fonction qui verifie si le joueur possede au moins une clef et la
     *        supprime si c'est le cas
     * 
     * @return True si le joueur possedait une clef (Autorise l'ouverture d'une
     *         porte fermee)
     *         False sinon
     */
    public boolean remKey() {
        if (this.nKeys <= 0)
            return false;
        --this.nKeys;
        return true;
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
        return this.tearReloadSpeed.isFinished();
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
        return this.tearReloadSpeed.addStep(reloadSpeed);
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

    // Coins
    /**
     * 
     * @param count
     * @return
     */
    public int addCoins(int count) {
        this.nCoins += count;
        return this.nCoins;
    }

    /**
     * 
     * @param count
     * @return
     */
    public int remCoins(int count) {
        this.nCoins -= count;
        return this.nCoins;
    }

    // Bombs
    /**
     * 
     * @return
     */
    public int getBombCount() {
        return this.nBombs;
    }

    /**
     * 
     */
    public int addBomb() {
        this.nBombs++;
        return this.nBombs;
    }

    /**
     * 
     * @return
     */
    public EntityBomb deployBomb() {
        if (this.nBombs <= 0)
            return null;
        --this.nBombs;
        return new EntityBomb(this.pos);
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
        Vector2 pos = new Vector2(0.040, 1 - 0.033 * 1);
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
        Vector2 pos = new Vector2(0.040, 1 - 0.033 * 2);
        Vector2 size = new Vector2(0.03, 0.03);
        StdDraw.picture(pos.getX(), pos.getY(), IMGPATH_HUD_BOMB, size.getX(), size.getY());
        StdDraw.textLeft(pos.getX() + size.getX(), pos.getY(), Integer.toString(this.nBombs));

    }

    /**
     * 
     */
    public void drawCoinsBar() {
        Vector2 pos = new Vector2(0.040, 1 - 0.033 * 3);
        Vector2 size = new Vector2(0.03, 0.03);
        StdDraw.picture(pos.getX(), pos.getY(), IMGPATH_HUD_COINS, size.getX(), size.getY());
        StdDraw.textLeft(pos.getX() + size.getX(), pos.getY(), Integer.toString(this.nCoins));
    }

    /**
     * 
     */
    public void drawTearRangeHUD() {
        Vector2 pos = new Vector2(0.040, 1 - 0.033 * 4);
        StdDraw.textLeft(pos.getX(), pos.getY(), "Range: " + Double.toString(this.tearRange));
    }

    /**
     * 
     */
    public void drawTearReloadSpeedHUD() {
        Vector2 pos = new Vector2(0.040, 1 - 0.033 * 5);
        StdDraw.textLeft(pos.getX(), pos.getY(), "Reload: " + Double.toString(this.tearReloadSpeed.getStep()));
    }

    /**
     * 
     */
    public void drawTearDamageHUD() {
        Vector2 pos = new Vector2(0.040, 1 - 0.033 * 6);
        StdDraw.textLeft(pos.getX(), pos.getY(), "Damage: " + Double.toString(this.tearDamage));
    }

    /**
     * 
     */
    public void drawSpeedHUD() {
        Vector2 pos = new Vector2(0.040, 1 - 0.033 * 7);
        StdDraw.textLeft(pos.getX(), pos.getY(), "Speed: " + Double.toString(this.speed));
    }

    /**
     * 
     */
    public void drawCheatInvincibilityHUD() {
        if (this.cheatInvincible) {
            Vector2 pos = new Vector2(1 - 0.040, 1 - 0.033 * 5);
            StdDraw.textRight(pos.getX(), pos.getY(), "Invincibility: Cheat");
        }
    }

    /**
     * 
     */
    public void drawCheatSpeedHUD() {
        if (this.cheatSpeed) {
            Vector2 pos = new Vector2(1 - 0.040, 1 - 0.033 * 6);
            StdDraw.textRight(pos.getX(), pos.getY(), "Speed: Cheat");
        }
    }

    /**
     * 
     */
    public void drawCheatDamageHUD() {
        if (this.cheatDamage) {
            Vector2 pos = new Vector2(1 - 0.040, 1 - 0.033 * 7);
            StdDraw.textRight(pos.getX(), pos.getY(), "Damage: Cheat");
        }
    }

    /**
     * 
     */
    public void drawHUD() {
        Font font = new Font("Arial", Font.BOLD, 11);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        this.drawHealthBar();
        this.drawBombsBar();
        this.drawCoinsBar();
        this.drawTearRangeHUD();
        this.drawTearReloadSpeedHUD();
        this.drawTearDamageHUD();
        this.drawSpeedHUD();
        this.drawCheatInvincibilityHUD();
        this.drawCheatSpeedHUD();
        this.drawCheatDamageHUD();
        StdDraw.setPenColor();
    }

    // Triche
    public static double CHEAT_OF_SPEED = 0.025;
    public static int CHEAT_OF_DAMAGE = Integer.MAX_VALUE / 2;

    /**
     * 
     */
    public void updateCheatActions() {
        // Invincible
        if (StdDraw.isKeyPressed(Controls.cheatInvincibility) && this.cheatInvincibleCounter.isFinished())
            this.cheatInvincible ^= true;

        // Rapidite
        if (StdDraw.isKeyPressed(Controls.cheatSpeed) && this.cheatSpeedCounter.isFinished()) {
            if (this.cheatSpeed == false) {
                this.speed += CHEAT_OF_SPEED;
                this.cheatSpeed = true;
            } else {
                this.speed -= CHEAT_OF_SPEED;
                this.cheatSpeed = false;
            }
        }

        // Puissance
        if (StdDraw.isKeyPressed(Controls.cheatOneShot) && this.cheatDamageCounter.isFinished()) {
            if (this.cheatDamage == false) {
                this.tearDamage += CHEAT_OF_DAMAGE;
                this.cheatDamage = true;
            } else {
                this.tearDamage -= CHEAT_OF_DAMAGE;
                this.cheatDamage = false;
            }
        }

        // Pièces
        if (StdDraw.isKeyPressed(Controls.cheatGold) && this.cheatCoinCounter.isFinished()) {
            this.addCoins(10);
        }
    }

    /**
     * 
     * @return
     */
    public boolean isCheatInvincibleEnabled() {
        return this.cheatInvincible;
    }

    /**
     * 
     * @return
     */
    public boolean isCheatSpeedEnabled() {
        return this.cheatSpeed;
    }

    /**
     * 
     * @return
     */
    public boolean isCheatPowerEnabled() {
        return this.cheatDamage;
    }

    // Damage
    /**
     * 
     */
    public void addDamage(int damage) {
        if (!this.cheatInvincible) {
            this.health -= damage;
            if (this.health <= 0)
                Game.updateGameState(GameState.LOST);
        }
    }
}
