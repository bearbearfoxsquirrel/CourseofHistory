package test.puigames.courseofhistory.framework.game.controllers;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameController;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;

/**
 * Created by Michael on 01/03/2017.
 */

public class HumanController extends GameController{
    Pawn pawn;
    public HumanController(Pawn pawn) {
        super();
        pawn = pawn;
    }

    public void update(InputBuddy inputBuddy, float deltaTime) {
        if(pawn.playerCurrentState.equals(Pawn.PawnState.TAKING_TURN)) {
            workOutPlayerIntent();
        }
    }
}
