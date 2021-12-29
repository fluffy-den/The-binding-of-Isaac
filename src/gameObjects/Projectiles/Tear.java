package gameObjects.Projectiles;

import resources.HeroInfos;
import resources.ImagePaths;
import gameObjects.Monsters.Monster;
import libraries.Vector2;

public class Tear extends Projectile {
    // Constructeur
    public Tear(Vector2 pos, Vector2 dir) {
        super(pos,
                HeroInfos.ISAAC_TEAR_SIZE,
                dir,
                HeroInfos.ISAAC_TEAR_SPEED,
                HeroInfos.ISAAC_TEAR_DAMAGE,
                HeroInfos.ISAAC_TEAR_RANGE,
                ImagePaths.TEAR);
    }

    // Lorsque le projectile touche un monstre
    public void monsterHit(Monster m) {
        // TODO: Cheat mode activé
        m.setDamage(this.getDamage());
    }
}
