package gameObjects.Entities;

import gameWorld.GameRoom;

import libraries.Vector2;
import libraries.StdDraw;

/**
 * @brief Caracteristiques generles des entites (Position, taille, texture...)
 */
public abstract class Entity {
    protected Vector2 pos;
    protected Vector2 size;
    protected String imgPath;

    // Constructeur
    /**
     * 
     * @param pos
     * @param size
     * @param imgPath
     */
    public Entity(Vector2 pos, Vector2 size, String imgPath) {
        this.pos = pos;
        this.size = size;
        this.imgPath = imgPath;
    }

    // Position
    /**
     * 
     * @return
     */
    public Vector2 getPos() {
        return this.pos;
    }

    /**
     * 
     * @param newPos
     */
    public void setPos(Vector2 newPos) {
        this.pos = newPos;
    }

    // Taille
    /**
     * 
     * @return
     */
    public Vector2 getSize() {
        return this.size;
    }

    // Emplacement de l'image
    /**
     * 
     * @return
     */
    public String getImgPath() {
        return this.imgPath;
    }

    /**
     * 
     * @param imgPath
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    // Mis à jour
    /**
     * 
     */
    public void update() {
    }

    // Dessin
    /**
     * 
     */
    public void draw() {
        StdDraw.picture(getPos().getX(), getPos().getY(), getImgPath(), getSize().getX(), getSize().getY(),
                0);
    }

    /**
     * 
     */
    public void updateAndDraw() {
        update();
        draw();
    }

    // Hitbox
    /**
     * 
     * @param e
     * @return
     */
    public boolean isAdjacent(Entity e) {
        double authorizedOverlap = GameRoom.TILE_SIZE.getX() / 1000;

        boolean tooFarL = this.getPos().getX() + (this.getSize().getX() / 2) < authorizedOverlap + e.getPos().getX()
                - (e.getSize().getX() / 2);
        boolean tooFarB = this.getPos().getY() + (this.getSize().getY() / 2) < authorizedOverlap + e.getPos().getY()
                - (e.getSize().getY() / 2);
        boolean tooFarR = this.getPos().getX() - (this.getSize().getX() / 2) + authorizedOverlap > e.getPos().getX()
                + (e.getSize().getX() / 2);
        boolean tooFarA = this.getPos().getY() - (this.getSize().getY() / 2) + authorizedOverlap > e.getPos().getY()
                + (e.getSize().getY() / 2);

        if (tooFarL || tooFarR || tooFarA || tooFarB) {
            return false;
        }
        return true;
    }
}
