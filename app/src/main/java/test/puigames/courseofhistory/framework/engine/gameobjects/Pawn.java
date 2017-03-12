package test.puigames.courseofhistory.framework.engine.gameobjects;

/**
 * Created by Michael on 03/03/2017.
 */

//A pawn is an object that manipulates GameObjects within the game.
//It contains a controller which is used to decide what the pawn does
public abstract class Pawn {
    protected GameController controller;

    public Pawn(GameController controller) {
        this.controller = controller;
    }
}
