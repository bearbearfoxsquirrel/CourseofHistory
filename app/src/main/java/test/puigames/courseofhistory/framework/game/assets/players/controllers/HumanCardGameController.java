package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;

//This class is for allowing the user to interact with a pawn pawn
public class HumanCardGameController extends CardGameController {
    public InputBuddy inputBuddy;
    public int oppositePlayerNumber;

    public HumanCardGameController() {
        super();
    }

    @Override
    public void possessPlayer(Player player) {
        super.possessPlayer(player);
        this.oppositePlayerNumber = player.playerNumber + 1 % 2;

    }

    public void update(InputBuddy inputBuddy, float deltaTime) {
        this.inputBuddy = inputBuddy;
        if (player.playerCurrentState == Player.PawnState.TURN_ACTIVE) {
         //   updateCardsInHand(deltaTime);
            updateCardsOnBoardPlayArea(deltaTime);


            for (CharacterCard playerCard : player.board.playAreas[player.playerNumber].cardsInArea) {
                for (CharacterCard opponentCard : player.board.playAreas[oppositePlayerNumber].cardsInArea) {
                    if (playerCard.boundingBox.isOverlapping(opponentCard.boundingBox))
                        player.createAttack(playerCard, opponentCard);
                }
            }
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
        for(CharacterCard card : player.testCards) {
            for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
                if (checkIsTouched(touchEvent, card)) {
                    card.translateCard(touchEvent.x, touchEvent.y);

                    if(player.playArea.cardsInArea.contains(card)) {
                        card.setOrigin(new Origin(touchEvent.x, touchEvent.y));
                        player.removeCardFromArea(card);
                    }
                } else if(card.boundingBox.isOverlapping(player.playArea.boundingBox))
                    player.addCardToArea(card);
            }
            if(card.boundingBox.isOverlapping(player.playArea.boundingBox) && inputBuddy
                    .touchEvents.isEmpty())
                player.playArea.addCardToArea(card);
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
