package test.puigames.courseofhistory.framework.game.controllers;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameController;

/**
 * Created by Michael on 03/03/2017.
 */

public abstract class CardGameController extends GameController implements PlayerInteraction{
    public ControllerState currentControllerState;
    public Player player;

    public CardGameController(Player player) {
        super(player);
        this.player = player;
        currentControllerState = ControllerState.CREATED;
    }



}