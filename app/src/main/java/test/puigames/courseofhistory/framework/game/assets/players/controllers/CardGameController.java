package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameController;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.game.assets.players.events.Eventable;


/**
 * Created by Michael on 03/03/2017.
 */

public abstract class CardGameController extends GameController {
    public ControllerState currentControllerState;
    public Player player;
    public ArrayList<Eventable> playerEvents;

    public enum ControllerState implements ControllerStates {
        CREATED, MOVING_CARD_IN_HAND, PLACING_CARD_ON_BOARD, MOVING_CARD_ON_BOARD, ATTACKING, THROWING_OUT_HAND, DRAWING_CAR, IDLE;
    }

    public CardGameController() {
        this.currentControllerState = ControllerState.CREATED;
        this.playerEvents = new ArrayList<Eventable>();
    }

    public void possessPlayer(Player player) {
        this.player = player;
    }
}
