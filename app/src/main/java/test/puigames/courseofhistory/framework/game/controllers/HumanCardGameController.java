package test.puigames.courseofhistory.framework.game.controllers;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
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
            updateCardsOnBoardPlayArea(deltaTime);
        }
    }

    public void updateCardsOnBoardPlayArea(float deltaTime) {
      for(CharacterCard card : player.testCards) {
            for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
                if (checkIsTouched(touchEvent, card)) {
                   player.moveCard(card, touchEvent.x, touchEvent.y);

                    if(player.playArea.cardsInArea.contains(card)) {
                        card.setOrigin(new Origin(touchEvent.x, touchEvent.y));
                        player.removeCardFromArea(card);
                    }
                }
                else if(card.boundingBox.isOverlapping(player.playArea.boundingBox))
                    player.addCardToArea(card);
            }
            if(card.boundingBox.isOverlapping(player.playArea.boundingBox) && inputBuddy
                    .touchEvents.isEmpty())
                player.playArea.addCardToArea(card);
        }
        collisionCheckAndResolve();
    }

    private void collisionCheckAndResolve()
    {
        for(CharacterCard card : player.testCards)
        {
            for(CharacterCard card2 : player.testCards)
            {
                if(card.checkForCollision(card2)) //collision with cards
                    card.resolveCollision(card2, card.overlapAllowance);

                if(!card.boundingBox.isEncapsulated(player.board.boundingBox)) //collision with board
                    player.board.boundingBox.getCollisionDetector().keepInsideBoundingBox(player.board, card2);
            }
        }
    }

//TODO: Implement add to board
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
