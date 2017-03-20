package test.puigames.courseofhistory.framework.game.controllers;

import android.util.Log;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

//This class is for allowing the user to interact with a pawn pawn
public class HumanController extends CardGameController {
    public InputBuddy inputBuddy;

    public HumanController(Player player) {
        super(player);

    }
    public void updateCardsInHand(float deltaTime) {
        for(CharacterCard card : player.testCards) {
            for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
                if (checkIsTouched(touchEvent, card)) {
                    card.translateCard(touchEvent.x, touchEvent.y);

                    if(player.playArea.cardsInArea.contains(card)) {
                        card.setOrigin(new Origin(touchEvent.x, touchEvent.y));
                        player.removeCardFromArea(card);
                    }
                }
                else
                    if(card.boundingBox.isOverlapping(player.playArea.boundingBox))
                        player.addCardToArea(card);
            }
            if(card.boundingBox.isOverlapping(player.playArea.boundingBox) && inputBuddy
                    .touchEvents.isEmpty())
                player.playArea.addCardToArea(card);
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
        updateCardsInHand(deltaTime);
    }
}
