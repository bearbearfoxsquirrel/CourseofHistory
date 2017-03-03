package test.puigames.courseofhistory.framework.game.controllers;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;

//This class is for allowing the user to interact with a pawn pawn
public class HumanController extends CardGameController {
    public InputBuddy inputBuddy;
    public HumanController(Player player) {
        super(player);
    }


    public void updateCardsInHand(float deltaTime) {
        for(Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
            for (Card card : pawn.testCards) {
                if (checkIsTouched(touchEvent, card)) {
                    card.translateCard(touchEvent.x, touchEvent.y);
                }
            }
        }
    }

    public ControllerState getState(){
        return  this.currentControllerState;
    }
    private boolean checkIsTouched(Input.TouchEvent touchEvent, GameObject object) {
        return (object.boundingBox.isTouchOn(touchEvent));
    }

    public void update(InputBuddy inputBuddy, float deltaTime) {
        this.inputBuddy = inputBuddy;
       // if(pawn.playerCurrentState.equals(Player.PawnState.TAKING_TURN)) {
            if(inputBuddy.getTouchEvents() != null) {
                updateCardsInHand(deltaTime);

           }
    //    }
    }
}
