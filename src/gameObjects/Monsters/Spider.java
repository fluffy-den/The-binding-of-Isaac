package gameObjects.Monsters;

import resources.ImagePaths;
import resources.SpiderInfos;

import libraries.Vector2;

public class Spider extends Monster {

    /**
     * Creation d'une areignee
     * @param pos Coordonnees
     */
    public Spider(Vector2 pos) {
        super(pos,
                SpiderInfos.SPIDER_SIZE,
                SpiderInfos.SPIDER_SPEED,
                SpiderInfos.SPIDER_HP,
                ImagePaths.SPIDER);
    }
}
