package test.puigames.courseofhistory.framework.game.controllers;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.game.assets.Pawn;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;

//This class is for allowing the user to interact with a player pawn
public class HumanController extends GameController{
    public HumanController(Pawn pawn) {
        super(pawn);
    }


  /*  private void updateCardPosition(Card card, float x, float y) {
        card.origin.x -= ((card.origin.x - x) / 2);
        card.origin.y -= ((card.origin.y - y) / 2);
    }*/

    public void checkCardsInHand(InputBuddy inputBuddy, float deltaTime) {
        for(Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
            for (Card card : pawn.testCards) {
                if (checkIsTouched(touchEvent, card)) {
                    card.translateCard(touchEvent.x, touchEvent.y);
                }
            }
        }
    }

    private boolean checkIsTouched(Input.TouchEvent touchEvent, GameObject object) {
        return (object.boundingBox.isTouchOn(touchEvent));
    }

    public void update(InputBuddy inputBuddy, float deltaTime) {
        if(pawn.playerCurrentState.equals(Pawn.PawnState.TAKING_TURN)) {
            if(inputBuddy.getTouchEvents() != null) {
                checkCardsInHand(inputBuddy, deltaTime);
            }
        }
    }
}
