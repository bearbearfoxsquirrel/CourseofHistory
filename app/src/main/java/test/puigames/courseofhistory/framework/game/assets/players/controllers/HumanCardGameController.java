package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.controlling.Inputable;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.game.assets.CardArea;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;


//This class is for allowing the user to interact with a pawn pawn
public class HumanCardGameController extends CardGameController implements Inputable {
    public InputBuddy inputBuddy;
    HumanCardGameUIContainer controllerUI;

    public HumanCardGameController(Screen screen, InputBuddy inputBuddy, Player player, Bitmap startingHandSelectorBackgroundBitmap, Bitmap confirmationButtonBitmap, Bitmap endTurnButtonBitmap) {
        this.inputBuddy = inputBuddy;
        this.player = player;
        this.controllerUI= new HumanCardGameUIContainer(screen, this.player, startingHandSelectorBackgroundBitmap, confirmationButtonBitmap, endTurnButtonBitmap);
    }

    public HumanCardGameController(InputBuddy inputBuddy, Player player) {
        this.inputBuddy = inputBuddy;
        this.player = player;
    }

    @Override
    public void update(float deltaTime) {
        for (MenuButton menuButton : controllerUI.getShownMenuButtons())
            if (menuButton.checkForInput(inputBuddy))
                menuButton.applyAction();

        switch (player.playerCurrentState) {
            case TURN_ACTIVE:
                updateCardsInHand(deltaTime);
                updateCardsOnBoardPlayArea(deltaTime);
                break;

            case TURN_ENDED:

            case BEGIN_CREATING_STARTING_HAND:

                break;
            case STARTING_HAND_CHOOSING_CARDS_TO_TOSS:

                break;
            case FINISHED_CREATING_START_HAND:

                break;
        }
        collisionCheckAndResolve(player.board.playAreas[player.playerNumber]);
        collisionCheckAndResolve(player.board.cardHands[player.playerNumber]);
    }



    private void updateCardsOnBoardPlayArea(float deltaTime){
//        for(CharacterCard card : player.board.playAreas[player.playerNumber].cardsInArea) {
//            for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
//                if (checkIsTouched(touchEvent, card)) {
//                    player.moveCard(card, touchEvent.x, touchEvent.y);
//                    if(player.board.playAreas[player.playerNumber].cardsInArea.contains(card)) {
//                        //card.origin.setOrigin(touchEvent.x, touchEvent.y);
//                        player.removeCardFromArea(card);
//                    }
//                } //else if(card.boundingBox.isOverlapping(player.board.playAreas[player.playerNumber].boundingBox))
////                    player.addCardToArea(card);
//            }
//            if(card.boundingBox.isOverlapping(player.board.playAreas[player.playerNumber].boundingBox) && inputBuddy.touchEvents.isEmpty())
//                player.board.playAreas[player.playerNumber].addCardToArea(card);
//        }
//    }
        }

    private void updateCardsInHand(float deltaTime) {
        for(int i = 0; i < player.board.cardHands[player.playerNumber].cardsInArea.size(); i++) {
            CharacterCard card = player.board.cardHands[player.playerNumber].cardsInArea.get(i);
            if(inputBuddy.touchEvents.size() > 0) {
                Input.TouchEvent touchEvent = inputBuddy.touchEvents.get(0);
                if(checkIsTouched(touchEvent, card)) {
                    card.origin.setOrigin(touchEvent.x, touchEvent.y);
                    player.moveCard(card, card.origin.x, card.origin.y);
                }
            } else if(card.boundingBox.isOverlapping(player.board.playAreas[player.playerNumber].boundingBox) && inputBuddy.touchEvents.isEmpty()) {
                playCard(card);
            }
        }
    }

    private boolean checkIfThereIsAnAttackOnOpponentCard(CharacterCard playerCard, CharacterCard opponentCard) {
        return playerCard.boundingBox.isOverlapping(opponentCard.boundingBox);
    }

    private void collisionCheckAndResolve(CardArea cardArea)
    {
        if (cardArea.cardsInArea.size() > 0) {
            for (CharacterCard card : cardArea.cardsInArea) {
                for (CharacterCard card2 : cardArea.cardsInArea) {
                    if (card.origin.equals(card2.origin))
                        card.boundingBox.getCollisionDetector().resolveCollision(card, card2, card.overlapAllowance);

                    if (card.boundingBox.getCollisionDetector().checkForCollision(card.boundingBox, card2.boundingBox))
                        card.boundingBox.getCollisionDetector().resolveCollision(card, card2, card.overlapAllowance);
                    if (!card2.boundingBox.isEncapsulated(player.board.boundingBox)) //collision with board
                        player.board.boundingBox.getCollisionDetector().keepInsideBoundingBox(player.board, card2);
                }
                if (!card.boundingBox.isEncapsulated(player.board.boundingBox))
                    card.boundingBox.getCollisionDetector().keepInsideBoundingBox(player.board, card);
            }
        }
    }

    /**
     * TODO: inform user of not having enough mana for action??
     * When a player moves a card from their hand to board, this is called
     * Checks if player has enough mana for the action,
     *      if they do, card is played
     *          and the corresponding mana cost of card is removed from player's
     *          currentMana
     * @param card - card that is being played
     */
    public void playCard(CharacterCard card)
    {
        //player.currentAction = Player.PawnAction.PLACE_CARD_ON_BOARD;
//        if(player.currentMana >= card.mana)
//        {
            player.placeCardOnBoard(card);
//            removeManaFromPlayer(card.mana);
//        }
//        else
//        {
            //should probably tell the user that they can't do that
//        }
    }

    /**
     * If a player has enough mana, this method is called
     * @param manaCost - amount of mana used by playing card/switched to used state
     */
    private void removeManaFromPlayer(int manaCost)
    {
        player.currentMana -= manaCost;
        for(int i = (player.MAX_MANA - 1); i >= (player.currentMana - 1); i++)
        {
            player.mana[i].manaState = Mana.ManaState.used;
            player.mana[i].setBitmap(player.mana[i].manaType[1]);
        }
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

    @Override
    public void startTicking(Screen screen) {
        super.startTicking(screen);
       // controllerUI.setUpGamePiecePositions(screen, 480/2, 320/2 );
    }

    @Override
    public void stopTicking(Screen screen) {
       super.stopTicking(screen);
        controllerUI.stopTicking(screen);
    }
}
