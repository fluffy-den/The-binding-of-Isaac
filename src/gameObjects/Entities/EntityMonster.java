package gameObjects.Entities;

import java.util.ArrayList;

import gameObjects.Hero;
import gameObjects.Projectiles.MonsterProjectile;
import gameWorld.Game;

import libraries.Vector2;

public abstract class EntityMonster extends EntityLiving {
    protected int meleeDamage;
    protected double meleeEffectPower;
    protected double meleeReloadSpeed;
    private long lastMeleeReloadFrame;

    /**
     * @brief
     * 
     * @param pos
     * @param size
     * @param speed
     * @param health
     * @param meleeDamage
     * @param meleeEffectDamage
     * @param imgPath
     */
    public EntityMonster(Vector2 pos, Vector2 size, double speed, boolean flying, int health, int meleeDamage,
            double meleeEffectPower, double meleeReloadSpeed, String imgPath) {
        super(pos,
                size,
                new Vector2(),
                speed,
                flying,
                health,
                imgPath);
        this.meleeDamage = meleeDamage;
        this.meleeEffectPower = meleeEffectPower;
        this.meleeReloadSpeed = meleeReloadSpeed;
        this.lastMeleeReloadFrame = 0;
    }

    /**
     * 
     * @param h
     */
    public void onMonsterHeroAdjacency(Hero h) {
        long elapsed = Game.getImageNum() - this.lastMeleeReloadFrame;
        if (elapsed * this.meleeReloadSpeed >= 1) {
            h.addDamage(this.meleeDamage);
            h.addDirEffect(h.getPos().subVector(this.pos), this.meleeEffectPower);
            this.lastMeleeReloadFrame = Game.getImageNum();
        }
    }

    /**
     * 
     * @return
     */
    public ArrayList<MonsterProjectile> monsterFireProjectiles(Hero h) {
        return null;
    }
}
