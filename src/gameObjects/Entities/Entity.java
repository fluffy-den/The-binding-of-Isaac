package gameObjects.Entities;

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
    public Entity(Vector2 pos, Vector2 size, String imgPath) {
        this.pos = pos;
        this.size = size;
        this.imgPath = imgPath;
    }

    // Position
    public Vector2 getPos() {
        return this.pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    // Taille
    public Vector2 getSize() {
        return this.size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    // Emplacement de l'image
    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    // Mis à jour
    public void update() {
    }

    // Dessin
    public void draw() {
        StdDraw.picture(getPos().getX(), getPos().getY(), getImgPath(), getSize().getX(), getSize().getY(),
                0);
    }

    public void updateAndDraw() {
        update();
        draw();
    }
}
