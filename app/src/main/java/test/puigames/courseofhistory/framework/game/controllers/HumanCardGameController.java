package test.puigames.courseofhistory.framework.game.controllers;

import test.puigames.courseofhistory.framework.engine.Controlling.Inputable;
import test.puigames.courseofhistory.framework.engine.Controlling.Possessor;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

//This class is for allowing the user to interact with a pawn pawn
public class HumanCardGameController extends CardGameController implements Inputable, Possessor{
    public InputBuddy inputBuddy;

    public HumanCardGameController(InputBuddy inputBuddy) {
        this.inputBuddy = inputBuddy;
    }

    @Override
    public void update(float deltaTime) {
        if (player.playerCurrentState == Player.PawnState.TURN_ACTIVE) {
            updateCardsInHand(deltaTime);
            updateCardsOnBoardPlayArea(deltaTime);
        }
    }

    public void updateCardsOnBoardPlayArea(float deltaTime) {
        for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
            for (CharacterCard card : player.testCards) {
                if (checkIsTouched(touchEvent, card)) {
                    player.moveCard(card, touchEvent.x, touchEvent.y);
                }
            }
        }
    }

    public void updateCardsInHand(float deltaTime) {
        for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
            for (CharacterCard card : player.testCards) {
                if (checkIsTouched(touchEvent, card)) {
                    //card.translateCard(touchEvent.x, touchEvent.y);
                }
                //if (card.boundingBox.isOverlapping(player.boardPlayerArea)) //TODO: Implement add to board
                  //  addCardToBoardPlayArea(card);
            }
        }
    }

    public void addCardToBoardPlayArea(CharacterCard card) {
        //player.currentAction = Player.PawnAction.PLACE_CARD_ON_BOARD;
        player.placeCardOnBoard(card);
    }

    private boolean checkIsTouched(Input.TouchEvent touchEvent, GameObject object) {
        return (object.boundingBox.isTouchOn(touchEvent));
    }

    @Override
    public InputBuddy getInput() {
        return inputBuddy;
    }

    @Override
    public void possessPlayer(Player player) {
        super.possessPlayer(player);
    }

    @Override
    public Player getPlayer() {
        return super.getPlayer();
    }
}
