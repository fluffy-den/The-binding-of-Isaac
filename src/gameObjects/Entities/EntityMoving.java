package gameObjects.Entities;

import java.util.LinkedList;
import java.util.List;

import gameWorld.GameRoom;

import gameObjects.Entities.EntityTerrain;

import libraries.Vector2;

/**
 * @brief Caracteristiques des entites mouvantes
 */
public abstract class EntityMoving extends Entity {
    protected Vector2 dir; // Direction seulement pour les intités dynamiques
    protected double speed;

    // Constructeur
    public EntityMoving(Vector2 pos, Vector2 size, Vector2 dir, double speed, String imgPath) {
        super(pos, size, imgPath);
        this.dir = dir;
        this.speed = speed;

    }

    /**
     * @brief Renvoie la direction de l'entite (deplacement)
     * @return Vecteur de direction (deplacement)
     */
    public Vector2 getDir() {
        return this.dir;
    }

    /**
     * @brief Renvoie le vecteur deplacment en fonction de la vitesse
     * @return Le vecteur normalise
     */
    public Vector2 getNormalizedDirection() {
        Vector2 normalizedVector = new Vector2(this.dir);
        normalizedVector.euclidianNormalize(this.speed);
        return normalizedVector;
    }

    /**
     * @brief Change la direction de l'entite
     * @param dir Vecteur difrection
     */
    public void setDir(Vector2 dir) {
        this.dir = dir;
    }

    /**
     * 
     * @return
     */
    public void addDirEffect(Vector2 powerDir, double power) {
        this.dir = this.dir.addVector(powerDir.scalarMultiplication(power));
    }

    /**
     * @brief Renvoie la vitesse de l'entite
     * @return Vitesse (double)
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * @brief Change la vitesse d'une entite
     * @param speed Nouvelle vitesse
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // Déplacement

    /**
     * @brief Deplacement vers le haut
     */
    public void addDirUp() {
        getDir().addY(1);
    }

    /**
     * @brief Deplacment vers le bas
     */
    public void addDirDown() {
        getDir().addY(-1);
    }

    /**
     * @brief Deplacment vers la gauche
     */
    public void addDirLeft() {
        getDir().addX(-1);
    }

    /**
     * @brief Deplacment vers la droite
     */
    public void addDirRight() {
        getDir().addX(1);
    }

    // Mis à jour

    /**
     * @brief Met à jour le déplacement de l'entité.
     */
    public void update() {
        Vector2 normalizedDirection = getNormalizedDirection();
        Vector2 positionAfterMoving = getPos().addVector(normalizedDirection);

        /// Colision avec les murs de la salle
        // Sur X
        if (positionAfterMoving.getX() < GameRoom.MIN_XPOS + this.getSize().getX() / 2.0)
            positionAfterMoving.setX(GameRoom.MIN_XPOS + this.getSize().getX() / 2.0);
        if (positionAfterMoving.getX() > GameRoom.MAX_XPOS - this.getSize().getX() / 2.0)
            positionAfterMoving.setX(GameRoom.MAX_XPOS - this.getSize().getX() / 2.0);

        // Sur Y
        if (positionAfterMoving.getY() < GameRoom.MIN_YPOS + this.getSize().getY() / 2.0)
            positionAfterMoving.setY(GameRoom.MIN_YPOS + this.getSize().getY() / 2.0);
        if (positionAfterMoving.getY() > GameRoom.MAX_YPOS - this.getSize().getX() / 2.0)
            positionAfterMoving.setY(GameRoom.MAX_YPOS - this.getSize().getX() / 2.0);
        this.pos = positionAfterMoving;
        this.dir = new Vector2(); // Remise à 0 de la direction
    }
}
