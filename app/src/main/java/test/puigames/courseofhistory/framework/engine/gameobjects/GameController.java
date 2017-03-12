package test.puigames.courseofhistory.framework.engine.gameobjects;

import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;

/**
 * Created by Michael on 20/02/2017.
 */


public abstract class GameController {
    public Pawn pawn;

    public interface ControllerStates{}


    public GameController() {

    }

    public abstract void updateCardsInHand(float deltaTime);

    //TODO set up way of getting pawn action
   // public abstract ControllerAction getControllerAction();

   // public abstract ControllerAction setControllerAction(ControllerAction action);

    public abstract void update(InputBuddy inputBuddy, float deltaTime);
    //public abstract void update();

    public abstract void update(Pawn pawn, float deltaTime);




    public static class ControllerException extends Exception {
        public ControllerException(String message) {
            super(message);
        }
    }
}
