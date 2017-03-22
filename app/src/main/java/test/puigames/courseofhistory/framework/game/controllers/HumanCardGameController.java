package test.puigames.courseofhistory.framework.game.controllers;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

//This class is for allowing the user to interact with a pawn pawn
public class HumanCardGameController extends CardGameController {
    public InputBuddy inputBuddy;

    public HumanCardGameController() {
        super();
    }

    public void update(InputBuddy inputBuddy, float deltaTime) {
        this.inputBuddy = inputBuddy;
        if (player.playerCurrentState == Player.PawnState.TURN_ACTIVE) {
            updateCardsInHand(deltaTime);
            updateCardsOnBoardPlayArea(deltaTime);
        }
    }

    public void updateCardsOnBoardPlayArea(float deltaTime) {
        for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
            for (CharacterCard card : player.testCards) {
                if (checkIsTouched(touchEvent, card)) {
                    currentControllerState = ControllerState.MOVING_CARD_ON_BOARD;
                    player.moveCard(card, touchEvent.x, touchEvent.y);
                }
            }
        }
    }

    public void updateCardsInHand(float deltaTime) {
        for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
            for (CharacterCard card : player.testCards) {
                if (checkIsTouched(touchEvent, card)) {
                    currentControllerState = ControllerState.MOVING_CARD_IN_HAND;
                    //card.translateCard(touchEvent.x, touchEvent.y);
                }
                //if (card.boundingBox.isOverlapping(player.boardPlayerArea)) //TODO: Implement add to board
                  //  addCardToBoardPlayArea(card);
            }
        }
    }

    public void addCardToBoardPlayArea(CharacterCard card) {
        currentControllerState = ControllerState.PLACING_CARD_ON_BOARD;
        //player.currentAction = Player.PawnAction.PLACE_CARD_ON_BOARD;
        player.placeCardOnBoard(card);
    }



    public ControllerState getState(){
        return  this.currentControllerState;
    }


    private boolean checkIsTouched(Input.TouchEvent touchEvent, GameObject object) {
        return (object.boundingBox.isTouchOn(touchEvent));
    }



}