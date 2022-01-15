package gameObjects.Entities;

import java.util.ArrayList;

import gameAI.AI;

import gameObjects.Hero;
import gameObjects.Projectiles.MonsterProjectile;

import gameWorld.Game;
import gameWorld.GameRoom;
import libraries.Vector2;

public abstract class EntityMonster extends EntityLiving {
    protected int meleeDamage;
    protected double meleeEffectPower;
    protected double meleeReloadSpeed;
    private long lastMeleeReloadFrame;
    private AI monsterAI;

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
            double meleeEffectPower, double meleeReloadSpeed, String imgPath, AI ai) {
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
        this.monsterAI = ai;
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
    public ArrayList<MonsterProjectile> fireProjectiles(Hero h) {
        return null;
    }
    // IA
    /**
     * 
     */
    public void updateAI(EntityBoss b, Hero h, GameRoom room) {
        this.dir = this.monsterAI.nextDir(b, this, h, room);
    }

    // Damage
    /**
     * 
     */
    @Override
    public void addDamage(int damage) {
        this.health -= damage;
        this.monsterAI.onHit();
    }
}
