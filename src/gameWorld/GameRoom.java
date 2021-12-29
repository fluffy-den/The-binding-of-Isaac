package gameWorld;

import gameObjects.Monsters.*;
import gameObjects.Projectiles.MonsterProjectile;
import gameObjects.Projectiles.Tear;
import gameObjects.Entities.Hero;

import libraries.Vector2;
import libraries.StdDraw;
import libraries.Utils;

import resources.RoomInfos;
import resources.ImagePaths;

import java.util.LinkedList;

public class GameRoom {
    private LinkedList<Monster> monsterList;
    private LinkedList<Tear> projListHero;
    private LinkedList<MonsterProjectile> projListMonster;

    // Constructeur
    public GameRoom() {
        this.monsterList = new LinkedList<Monster>();
        this.projListHero = new LinkedList<Tear>();
        this.projListMonster = new LinkedList<MonsterProjectile>();
        this.monsterList.add(new Spider(RoomInfos.POSITION_CENTER_OF_ROOM.addVector(new Vector2(0.3, 0.3))));
        this.monsterList.add(new Fly(RoomInfos.POSITION_CENTER_OF_ROOM.addVector(new Vector2(-0.3, -0.3))));
        this.monsterList.add(new Fly(RoomInfos.POSITION_CENTER_OF_ROOM.addVector(new Vector2(-0.3, -0.3))));
    }

    // Projectile: Ajoute
    /**
     * @brief Ajoute proj a la liste des projectiles des monstres
     * @param proj projectile lance par un monstre
     */
    public void addMonsterProjectile(MonsterProjectile proj) {
        if (proj == null)
            return;
        this.projListMonster.add(proj);
    }

    /**
     * @brief Ajoute proj a la liste des projectiles du hero
     * @param proj projectile lance par le heros
     */
    public void addHeroProjectile(Tear proj) {
        if (proj == null)
            return;
        this.projListHero.add(proj);
    }

    // Dessin
    /**
     * @brief Affiche la zone carte avec un heros sur dessus
     * @param h Heros a afficher
     */
    public void updateAndDraw(Hero h) {
        /// Sol
        StdDraw.picture(0.5, 0.5, ImagePaths.MAP, 1.0, 1.0, 0);

        /// Monstres
        for (Monster m : this.monsterList) {
            if (m.isLiving()) {
                m.updateAndDraw();
            } else {
                this.monsterList.remove(m);
            }
        }

        /// Projectiles
        // Projectiles des monstres
        for (MonsterProjectile k : this.projListMonster) {
            if (!k.shouldBeDestroyed()) {
                k.updateAndDraw();
                // Hitbox
                if (Utils.Hitbox.adjacent(k.getPos(), k.getSize(), h.getPos(), h.getSize())) {
                    // DommagesS
                }
            } else {
                this.projListMonster.remove(k);
            }
        }

        /// Projectiles du joueur
        for (Tear k : this.projListHero) {
            if (!k.shouldBeDestroyed()) {
                // Hitbox
                for (Monster m : this.monsterList) {
                    if (Utils.Hitbox.adjacent(k.getPos(), k.getSize(), m.getPos(), m.getSize())) {
                        k.monsterHit(m);
                        this.projListHero.remove(k);
                        break;
                    } else {
                        k.updateAndDraw();
                    }
                }
            }
        }

        /// Murs
        // TODO
    }

    // Tile Index to position
    public static Vector2 positionFromTileIndex(int indexX, int indexY) {
        return new Vector2(
                RoomInfos.POSITION_MIN_X + indexX * RoomInfos.TILE_SIZE.getX() * 1.5,
                RoomInfos.POSITION_MIN_Y + indexY * RoomInfos.TILE_SIZE.getY() * 1.5);
    }

    /// TODO: Cyp3
    // TODO: 1. Une grille (X, Y) où l'on peut placer, monstres, items (ArrayList?)
    // TODO: 2. Fonction qui convertie une grille en une salle
    // TODO: 5. Classe IA qui contient la méthode prochaine direction
    // : -> IA: Berserk (suit tout le temps le joueur)
    // : -> IA: Random (direction aléatoire tout le temps)
    // : -> IA:
    // Système de chemins en fonction de la grille (class Pathing?)
    // [I] [M] [I] [I]
    // [I] [R] [R] [I]
    // [I] [I] [X] [I]
    // [ ] [ ] [ ] [ ]
    // - Rapide
    // - Distance M - X dépends de constantes (minimales=1) et (maximales=choix)

    // Vérifier que toutes les portes sont reliés S1(L <=> R)S2

    // TODO: 6. Classe qui génère un niveau entier, dont la difficulté augmente en
    // fonction des niveaux
    // : -> Retrouve sa maman fin, c'est un objet
    // : -> Sinon lui roule dessus -> c'est la fin aussi
    // : -> Easter Egg, maman morte -> Fin
    // : -> Nombre de salle maximum en fonction de la difficulté
    // TODO: 4. Shop
    // TODO: 3. Portes

    /// TODO: Fluffy
    // TODO: 2. Rochers
    // TODO: 1. Items
    // ...
    // TODO: 5. Projectiles des monstres
    // Projectiles légers (++vitesse --dégats)
    // Projectiles lourds (--vitesse ++dégats)
    // TODO: 6. Quelques boss (7 premiers du jeu)
    // : ->
    // TODO: 7. Quelques monstres
    // TODO: 4. Pièges (Piques (timing / switch de textures), Trous)
    // TODO: 3. Bombes qui détruits les rochers (Animation d'explosions)
}
