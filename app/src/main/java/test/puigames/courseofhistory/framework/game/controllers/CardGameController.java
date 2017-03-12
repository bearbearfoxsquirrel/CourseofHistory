package test.puigames.courseofhistory.framework.game.controllers;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameController;


/**
 * Created by Michael on 03/03/2017.
 */

public abstract class CardGameController extends GameController implements HumanControllerInteraction {
    public ControllerState currentControllerState;
    public Player player;

    public enum ControllerState implements ControllerStates {
        CREATED, MOVING_CARD_IN_HAND, PLACING_CARD_ON_BOARD, MOVING_CARD_ON_BOARD, ATTACKING, THROWING_OUT_HAND, DRAWING_CAR, IDLE;
    }

    public CardGameController() {
        currentControllerState = ControllerState.CREATED;
    }



}
