package gameObjects.Entities;

import libraries.Vector2;
import resources.Utils;
import libraries.StdDraw;

/**
 * @brief Caracteristiques generles des entites (Position, taille, texture...)
 */
public abstract class Entity {
    protected Vector2 pos;
    protected Vector2 size;
    protected String imgPath;
    private int rotation;

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
        this.rotation = 0;
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

    /**
     * Définit un degré de rotation (Principalement portes)
     * 
     * @param rotation
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * Récupère le degré de rotation d'une entitée
     * 
     * @return degré de rotation
     */
    public int getRotation() {
        return this.rotation;
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
                this.rotation);
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
        return Utils.isAdjacent(this.pos, this.size, e.pos, e.size);
    }
}
