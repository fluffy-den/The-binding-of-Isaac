package gameObjects.Monsters;

import gameAI.AI;

import java.util.List;
import java.util.LinkedList;

import gameObjects.Hero;
import gameObjects.Entities.EntityMonster;
import gameObjects.Projectiles.MonsterLightProjectile;
import gameObjects.Projectiles.MonsterProjectile;
import gameWorld.GameCounter;
import gameWorld.GameRoom;

import libraries.Vector2;
import resources.Utils;

public class MonsterDeathHead extends EntityMonster {
        public static final Vector2 SIZE = GameRoom.TILE_SIZE.scalarMultiplication(0.75)
                        .vectorMultiplication(new Vector2(1.0, 0.7));
        public static final double SPEED = 0.002;
        public static final double MELEE_RELOAD_SPEED = 0.040;
        public static final double RELOAD_SPEED = 0.01;
        public static final double MELEE_EFFECT_POWER = 5.;
        public static final double AGGRO_RANGE = GameRoom.TILE_DIST * 0.00;
        public static final int FIRING_MAX_ANGLE = 7;
        public static final int FIRING_MIN_ANGLE = 3;
        public static final int MELEE_DAMAGE = 1;
        public static final int HP = 12;
        public static final String IMGPATH = "images/DeathHead.png";

        private GameCounter reloadCounter;

        /**
         * 
         * @param pos
         */
        public MonsterDeathHead(Vector2 pos) {
                super(
                                pos,
                                SIZE,
                                SPEED,
                                true,
                                HP,
                                MELEE_DAMAGE,
                                MELEE_EFFECT_POWER,
                                MELEE_RELOAD_SPEED,
                                IMGPATH,
                                new AI(pos, AGGRO_RANGE));

                this.reloadCounter = new GameCounter(RELOAD_SPEED);
        }

        /**
         * 
         */
        public List<MonsterProjectile> fireProjectiles(Hero h) {
                if (this.reloadCounter.isFinished()) {
                        List<MonsterProjectile> pL = new LinkedList<MonsterProjectile>();

                        // #1. Tir plus à gauche
                        double angle = Math.toRadians(Utils.randomInt(FIRING_MIN_ANGLE, FIRING_MAX_ANGLE));
                        pL.add(new MonsterLightProjectile(
                                        this.pos,
                                        generateDir(this.getPos(), h.getPos(), angle)));

                        // #2. Tir milieu
                        angle = Math.toRadians(Utils.randomInt(-FIRING_MIN_ANGLE, FIRING_MIN_ANGLE));
                        pL.add(new MonsterLightProjectile(
                                        this.pos,
                                        generateDir(this.getPos(), h.getPos(), angle)));

                        // #3. Tir droite
                        angle = Math.toRadians(Utils.randomInt(-FIRING_MAX_ANGLE, -FIRING_MIN_ANGLE));
                        pL.add(new MonsterLightProjectile(
                                        this.pos,
                                        generateDir(this.getPos(), h.getPos(), angle)));

                        return pL;
                }
                return null;
        }
}
