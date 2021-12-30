package gameWorld;

import gameObjects.Hero;

public class GameLevel {
    Hero hero;
    GameRoom currentRoom;

    /**
     * 
     * @param startingRoom
     */
    public GameLevel(GameRoom startingRoom) {
        this.hero = new Hero();
        this.currentRoom = startingRoom;
    }

    /**
     * 
     * @param h
     */
    public void updateAndDraw() {
        this.currentRoom.updateAndDraw(this.hero);
    }
}
