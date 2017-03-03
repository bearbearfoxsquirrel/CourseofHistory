package test.puigames.courseofhistory.framework.engine.gameobjects;

import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.game.controllers.Player;

/**
 * Created by Michael on 20/02/2017.
 */


public abstract class GameController {
    public Player pawn;


    //public abstract enum ControllerState{};

    public GameController(Player pawn) {
        this.pawn = pawn;
    //    this.state = ControllerState.MOVING_CARD_IN_HAND;

    }

    public abstract void updateCardsInHand(float deltaTime);

    //TODO set up way of getting pawn action
   // public abstract ControllerAction getControllerAction();

   // public abstract ControllerAction setControllerAction(ControllerAction action);

    public abstract void update(InputBuddy inputBuddy, float deltaTime);
    //public abstract void update();



    public static class ControllerException extends Exception {
        public ControllerException(String message) {
            super(message);
        }
    }
}
