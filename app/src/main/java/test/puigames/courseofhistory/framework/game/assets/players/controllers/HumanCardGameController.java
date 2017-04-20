package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import test.puigames.courseofhistory.framework.engine.controlling.Inputable;
import test.puigames.courseofhistory.framework.engine.controlling.Possessor;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.game.assets.StartingHandSelectionUI;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;

//This class is for allowing the user to interact with a pawn pawn
public class HumanCardGameController extends CardGameController implements Inputable, Possessor {
    public InputBuddy inputBuddy;
    public StartingHandSelectionUI startingHandSelectionUI;

    public HumanCardGameController(InputBuddy inputBuddy, StartingHandSelectionUI startingHandSelectionUI) {
        this.inputBuddy = inputBuddy;
        this.startingHandSelectionUI = startingHandSelectionUI;
    }

    public void update(float deltaTime) {
        switch (player.playerCurrentState) {
            case TURN_ACTIVE:
                updateCardsInHand();
                updateCardsOnBoardPlayArea(deltaTime);
                break;
            case CREATING_START_HAND:
                updatePlayersStartingHand();
                break;
        }

       /* if (player.playerCurrentState == Player.PawnState.TURN_ACTIVE) {


            //For actual thing

            if (playerEvents.size() == 0) {
                for (CharacterCard playerCard : player.board.playAreas[player.playerNumber].cardsInArea)
                    for (CharacterCard opponentCard : player.board.playAreas[oppositePlayerNumber].cardsInArea)
                        if (playerCard.boundingBox.isOverlapping(opponentCard.boundingBox))
                            playerEvents.add(player.createAttack(playerCard, opponentCard));
            } else {
                for (Eventable event : playerEvents)
                    event.update(deltaTime);

        } else if (player.playerCurrentState == Player.PawnState.CREATING_START_HAND) {
            updatePlayersStartingHand();
        }*/
    }

    public void updatePlayersStartingHand() {
        for (CharacterCard card : player.board.cardHands[player.playerNumber].cardsInArea) {

            for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
                if (card.boundingBox.isTouchOn(touchEvent) && touchEvent.type == Input.TouchEvent.TOUCH_UP){
                    player.selectCardToRemove(card);
                }
            }
        }
    }

    public void updateCardsInHand() {
        for (CharacterCard card : player.board.cardHands[player.playerNumber].cardsInArea) {
            for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
                if(checkIsTouched(touchEvent, card) && touchEvent.type == Input.TouchEvent.TOUCH_DOWN) {
                    player.moveCard(card, touchEvent.x, touchEvent.y);
                } else if (checkIsTouched(touchEvent, card) && touchEvent.type == Input.TouchEvent.TOUCH_UP && card.boundingBox.getCollisionDetector().isCollision(card.boundingBox, player.board.playAreas[player.playerNumber].boundingBox))  {
                    //If player drops card on their board area add card to it
                    player.addCardToArea(card);
                }
            }
        }

    }

    public void updateCardsOnBoardPlayArea(float deltaTime) {
        for(CharacterCard playerCard : player.board.playAreas[player.playerNumber].cardsInArea) {
            for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
                if (checkIsTouched(touchEvent, playerCard)) {
                    player.moveCard(playerCard, touchEvent.x, touchEvent.y);
                    if(player.board.playAreas[player.playerNumber].cardsInArea.contains(playerCard)) {
                        playerCard.setOrigin(new Origin(touchEvent.x, touchEvent.y));
                        player.removeCardFromArea(playerCard);
                    }

                    for (CharacterCard opponentCard : player.board.playAreas[player.playerNumber + 1 & 2].cardsInArea) //TODO make not look bad
                        if (checkIfThereIsAnAttackOnOpponentCard(playerCard, opponentCard))
                            playerEvents.add(player.createAttack(playerCard, opponentCard));

                } else if(playerCard.boundingBox.isOverlapping(player.board.playAreas[player.playerNumber].boundingBox))
                    player.addCardToArea(playerCard);
            }
            if(playerCard.boundingBox.isOverlapping(player.board.playAreas[player.playerNumber].boundingBox) && inputBuddy.touchEvents.isEmpty())
                player.board.playAreas[player.playerNumber].addCardToArea(playerCard);
        }
        collisionCheckAndResolve();
    }

    private boolean checkIfThereIsAnAttackOnOpponentCard(CharacterCard playerCard, CharacterCard opponentCard) {
        return playerCard.boundingBox.isOverlapping(opponentCard.boundingBox);
    }

    private void collisionCheckAndResolve()
    {
        for(CharacterCard card : player.board.playAreas[player.playerNumber].cardsInArea)
        {
            for(CharacterCard card2 : player.board.playAreas[player.playerNumber].cardsInArea)
            {
                if(card.origin.equals(card2.origin))
                    card.boundingBox.getCollisionDetector().resolveCollision(card, card2, card.overlapAllowance);

                if(card.boundingBox.getCollisionDetector().checkForCollision(card, card2))
                    //collision with cards
                    card.boundingBox.getCollisionDetector().resolveCollision(card, card2, card
                            .overlapAllowance);
                if(!card2.boundingBox.isEncapsulated(player.board.boundingBox)) //collision with
                    // board
                    player.board.boundingBox.getCollisionDetector().keepInsideBoundingBox(player.board, card2);
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
