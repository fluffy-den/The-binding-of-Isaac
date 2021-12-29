package gameObjects.Entities;

import libraries.Vector2;
import resources.RoomInfos;
import libraries.StdDraw;

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
    public void goUpNext() {
        getDir().addY(1);
    }

    /**
     * @brief Deplacment vers le bas
     */
    public void goDownNext() {
        getDir().addY(-1);
    }

    /**
     * @brief Deplacment vers la gauche
     */
    public void goLeftNext() {
        getDir().addX(-1);
    }

    /**
     * @brief Deplacment vers la droite
     */
    public void goRightNext() {
        getDir().addX(1);
    }

    // Mis à jour

    /**
     * @brief Mis a jours des constantes et de l'image d'une entite mouvante
     */
    public void update() {
        Vector2 normalizedDirection = getNormalizedDirection();
        Vector2 positionAfterMoving = getPos().addVector(normalizedDirection);

        // Sur X
        if (positionAfterMoving.getX() < RoomInfos.POSITION_MIN_X + this.getSize().getX() / 2.0)
            positionAfterMoving.setX(RoomInfos.POSITION_MIN_X + this.getSize().getX() / 2.0);
        if (positionAfterMoving.getX() > RoomInfos.POSITION_MAX_X - this.getSize().getX() / 2.0)
            positionAfterMoving.setX(RoomInfos.POSITION_MAX_X - this.getSize().getX() / 2.0);

        // Sur Y
        if (positionAfterMoving.getY() < RoomInfos.POSITION_MIN_Y + this.getSize().getY() / 2.0)
            positionAfterMoving.setY(RoomInfos.POSITION_MIN_Y + this.getSize().getY() / 2.0);
        if (positionAfterMoving.getY() > RoomInfos.POSITION_MAX_Y - this.getSize().getX() / 2.0)
            positionAfterMoving.setY(RoomInfos.POSITION_MAX_Y - this.getSize().getX() / 2.0);

        setPos(positionAfterMoving);
        this.dir = new Vector2(); // Remise à 0 de la direction
    }
}
