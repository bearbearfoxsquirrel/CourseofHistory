package test.puigames.courseofhistory.framework.game.controllers;

import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.game.assets.Pawn;

/**
 * Created by Michael on 20/02/2017.
 */


public abstract class GameController {
    public Pawn pawn;

    public GameController(Pawn pawn) {
        this.pawn = pawn;
    }

    //TODO set up way of getting player action
   // public abstract ControllerAction getControllerAction();

   // public abstract ControllerAction setControllerAction(ControllerAction action);

    public abstract void update(InputBuddy inputBuddy, float deltaTime);



    public static class ControllerException extends Exception {
        public ControllerException(String message) {
            super(message);
        }
    }
}
