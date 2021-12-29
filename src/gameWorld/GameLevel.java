package gameWorld;

public abstract class GameLevel {
    GameRoom currentRoom;

    // Constructeur
    public GameLevel(GameRoom startingRoom) {
        this.currentRoom = startingRoom;
    }

}
