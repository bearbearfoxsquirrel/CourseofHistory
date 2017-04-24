package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.controlling.Inputable;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.StartingHandSelectionUI;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;

//This class is for allowing the user to interact with a pawn pawn
public class HumanCardGameController extends CardGameController implements Inputable {
    public InputBuddy inputBuddy;
    public StartingHandSelectionUI startingHandSelectionUI;
    private Screen currentScreen;

    public HumanCardGameController(InputBuddy inputBuddy, Screen screen, Player player, Bitmap startingHandSelectionUIBackgroundBitmap, Bitmap confirmationButtonBitmap) {
        this.inputBuddy = inputBuddy;
        this.currentScreen = screen;
        this.player = player;
        this.startingHandSelectionUI = new StartingHandSelectionUI(this.currentScreen, player, startingHandSelectionUIBackgroundBitmap, confirmationButtonBitmap);
    }

    public HumanCardGameController(InputBuddy inputBuddy, StartingHandSelectionUI startingHandSelectionUI) {
        this.inputBuddy = inputBuddy;
        this.startingHandSelectionUI = startingHandSelectionUI;
    }

    @Override
    public void update(float deltaTime) {
        switch (player.playerCurrentState) {
            case TURN_ACTIVE:
                updateCardsInHand();
                updateCardsOnBoardPlayArea(deltaTime);
                break;
            case BEGIN_CREATING_STARTING_HAND:
                showStartingHandCreationUI();
                player.playerCurrentState = Player.PlayerState.STARTING_HAND_CHOOSING_CARDS_TO_TOSS;
                break;
            case STARTING_HAND_CHOOSING_CARDS_TO_TOSS:
                updatePlayersStartingHand();
                break;
            case FINISHED_CREATING_START_HAND:
                hideStartingHandCreationUI();
                break;
        }


       /* if (player.playerCurrentState == Player.PlayerState.TURN_ACTIVE) {


            //For actual thing

            if (playerEvents.size() == 0) {
                for (CharacterCard playerCard : player.board.playAreas[player.playerNumber].cardsInArea)
                    for (CharacterCard opponentCard : player.board.playAreas[oppositePlayerNumber].cardsInArea)
                        if (playerCard.boundingBox.isOverlapping(opponentCard.boundingBox))
                            playerEvents.add(player.createAttack(playerCard, opponentCard));
            } else {
                for (Eventable event : playerEvents)
                    event.update(deltaTime);

        } else if (player.playerCurrentState == Player.PlayerState.CREATING_START_HAND) {
            updatePlayersStartingHand();
        }*/
    }

    private void hideStartingHandCreationUI() {
        startingHandSelectionUI.remove(this.currentScreen);
    }

    private void showStartingHandCreationUI() {
        startingHandSelectionUI.place(this.currentScreen ,startingHandSelectionUI.UI_POS_X, startingHandSelectionUI.UI_POS_Y);
    }

    public void updatePlayersStartingHand() {
        for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
            for (CharacterCard card : player.board.cardHands[player.playerNumber].cardsInArea) {
                if (card.boundingBox.isTouchOn(touchEvent) && touchEvent.type == Input.TouchEvent.TOUCH_UP && player.startingHandSelector.cardsToKeep.contains(card)){ //check if card is selected or deselected
                    player.selectCardToRemove(card);
                } else if (card.boundingBox.isTouchOn(touchEvent) && touchEvent.type == Input.TouchEvent.TOUCH_UP && player.startingHandSelector.cardsToKeep.contains(card)) {
                    player.selectCardToKeep(card);
                }
            }

            if (startingHandSelectionUI.confirmationButton.boundingBox.isTouchOn(touchEvent)) {
                startingHandSelectionUI.confirmationButton.applyAction();
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
