package gameObjects.Entities;

import gameWorld.Game;

import java.awt.Font;

import gameObjects.Projectiles.Tear;
import libraries.StdDraw;
import libraries.Vector2;

import resources.HeroInfos;
import resources.ImagePaths;
import resources.RoomInfos;

public class Hero extends EntityLiving {
    private long lastTearFrame;
    private int nBombs;
    private int nCoins;

    // Constructeur
    public Hero() {
        super(RoomInfos.POSITION_CENTER_OF_ROOM,
                HeroInfos.ISAAC_SIZE,
                new Vector2(),
                HeroInfos.ISAAC_SPEED,
                HeroInfos.ISAAC_HP,
                ImagePaths.ISAAC);

        // Temps de rechargement
        this.lastTearFrame = 0;

        // Inventaire
        this.nBombs = 0;
        this.nCoins = 0;
    }

    // Projectile

    /**
     * @brief Permet de savoir si on peut retirer une lame (temps)
     * @return True si on peut tirer False sinon
     */
    public boolean isReloaded() {
        long elapsed = Game.getImageNum() - this.lastTearFrame;
        if (elapsed * HeroInfos.ISAAC_TEAR_RELOAD_SPEED >= 1) {
            this.lastTearFrame = Game.getImageNum();
            return true;
        }
        return false;
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
        return new Tear(this.getPos(), dir.addVector(this.dir.scalarMultiplication(HeroInfos.ISAAC_TEAR_SPEED_EFFECT)));
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
        return new Tear(this.getPos(), dir.addVector(this.dir.scalarMultiplication(HeroInfos.ISAAC_TEAR_SPEED_EFFECT)));
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
        return new Tear(this.getPos(), dir.addVector(this.dir.scalarMultiplication(HeroInfos.ISAAC_TEAR_SPEED_EFFECT)));
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
        return new Tear(this.getPos(), dir.addVector(this.dir.scalarMultiplication(HeroInfos.ISAAC_TEAR_SPEED_EFFECT)));
    }

    // HUD
    public void drawHealthBar() {
        Vector2 pos = new Vector2(0.040, 1 - 0.033);
        Vector2 size = new Vector2(0.03, 0.03);
        // 1 coeur = 2 pts de vie
        int nCoeurs = HeroInfos.ISAAC_HP / 2;
        int nCoeursComplets = 0;
        if (this.getHealth() > 0)
            nCoeursComplets = this.getHealth() / 2;

        // Affiche les coeurs complets
        for (int i = 0; i < nCoeursComplets; ++i) {
            StdDraw.picture(pos.getX() + size.getX() * i, pos.getY(),
                    ImagePaths.HEART_HUD, size.getX(), size.getY(), 0);
        }

        // Affiche les demis-coeurs
        int j = nCoeursComplets;
        if (this.getHealth() % 2 == 1) {
            StdDraw.picture(pos.getX() + size.getX() * j, pos.getY(), ImagePaths.HALF_HEART_HUD, size.getY(),
                    size.getX(), 0);
            j += 1;
        }

        // Affiche les coeurs vides
        for (int i = j; i < nCoeurs; ++i) {
            StdDraw.picture(pos.getX() + size.getX() * i, pos.getY(),
                    ImagePaths.EMPTY_HEART_HUD, size.getX(), size.getY(), 0);
        }
    }

    public void drawBombsBar() {
        Vector2 pos = new Vector2(0.040, 1 - 0.066);
        Vector2 size = new Vector2(0.03, 0.03);
        Font font = new Font("Arial", Font.BOLD, 11);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(font);
        StdDraw.picture(pos.getX(), pos.getY(), ImagePaths.BOMB, size.getX(), size.getY());
        StdDraw.textLeft(pos.getX() + size.getX(), pos.getY(), Integer.toString(this.nBombs));
        StdDraw.setPenColor();

    }

    public void drawCoinsBar() {
        Vector2 pos = new Vector2(0.040, 1 - 0.099);
        Vector2 size = new Vector2(0.03, 0.03);
        Font font = new Font("Arial", Font.BOLD, 11);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(font);
        StdDraw.picture(pos.getX(), pos.getY(), ImagePaths.COIN, size.getX(), size.getY());
        StdDraw.textLeft(pos.getX() + size.getX(), pos.getY(), Integer.toString(this.nCoins));
        StdDraw.setPenColor();
    }

    public void drawHUD() {
        this.drawHealthBar();
        this.drawBombsBar();
        this.drawCoinsBar();
    }
}
