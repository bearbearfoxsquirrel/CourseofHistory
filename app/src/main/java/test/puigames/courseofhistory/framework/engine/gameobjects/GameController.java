package test.puigames.courseofhistory.framework.engine.gameobjects;

/**
 * Created by Michael on 20/02/2017.
 */

public abstract class GameController {

    public GameController() {

    }

    public enum ControllerAction{
        ATTACK, PLACE_CARD_ON_BOARD, END_TURN;
    }


    //TODO set up way of getting player action
    public ControllerAction getControllerAction() {
        return null;
    }

    public PlayerAction setPlayerAction(PlayerAction action) {

    }

}
