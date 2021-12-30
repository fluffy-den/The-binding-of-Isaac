package gameObjects.Entities;

import gameWorld.GameRoom;
import libraries.Vector2;

public abstract class EntityTerrain extends Entity {
    /**
     * 
     */
    public EntityTerrain(Vector2 pos, String imgPath) {
        super(pos, GameRoom.TILE_SIZE, imgPath);
    }

    /**
     * 
     */
    public void onLivingAdjacency(EntityLiving living) {
        double authorizedOverlap = GameRoom.TILE_SIZE.getX() / 1000;

        // Deplacement sur X
        double newX = living.getPos().getX();
        boolean yAdjac = living.getPos().getY() < this.getPos().getY() + this.getSize().getY() / 2.0
                && living.getPos().getY() > this.getPos().getY() - this.getSize().getY() / 2.0;
        if ((this.getPos().getX() + authorizedOverlap - this.getSize().getX() / 2.0 < living.getPos().getX()
                + living.getSize().getX())
                && (this.getPos().getX() > living.getPos().getX())
                && yAdjac)
            newX = this.getPos().getX() + authorizedOverlap - (this.getSize().getX() + living.getSize().getX()) / 2.0;
        if ((this.getPos().getX() - authorizedOverlap + this.getSize().getX() / 2.0 > living.getPos().getX()
                - living.getSize().getX())
                && (this.getPos().getX() < living.getPos().getX())
                && yAdjac)
            newX = this.getPos().getX() - authorizedOverlap + (this.getSize().getX() + living.getSize().getX()) / 2.0;

        // Deplacement sur Y
        double newY = living.getPos().getY();
        boolean xAdjac = living.getPos().getX() < this.getPos().getX() + this.getSize().getX() / 2.0
                && living.getPos().getX() > this.getPos().getX() - this.getSize().getX() / 2.0;
        if ((this.getPos().getY() - this.getSize().getY() / 2.0 < living.getPos().getY()
                + living.getSize().getY())
                && (this.getPos().getY() > living.getPos().getY())
                && xAdjac)
            newY = this.getPos().getY() - (this.getSize().getY() + living.getSize().getY()) / 2.0;
        if ((this.getPos().getY() + this.getSize().getY() / 2.0 > living.getPos().getY()
                - living.getSize().getY())
                && (this.getPos().getY() < living.getPos().getY())
                && xAdjac)
            newY = this.getPos().getY() + (this.getSize().getY() + living.getSize().getY()) / 2.0;

        // Nouvelle position
        living.setPos(new Vector2(newX, newY));

    }
}
