package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import test.puigames.courseofhistory.framework.engine.controlling.Inputable;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.CardArea;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;


//This class is for allowing the user to interact with a pawn pawn
public class HumanCardGameController extends CardGameController implements Inputable {
    private InputBuddy inputBuddy;
    private Screen currentScreen;


    public HumanCardGameController(Screen screen, InputBuddy inputBuddy, Player player) {
        this.inputBuddy = inputBuddy;
        this.currentScreen = screen;
        this.player = player;
    }

    public HumanCardGameController(Screen screen, InputBuddy inputBuddy) {
        this.inputBuddy = inputBuddy;
    }

    @Override
    public void update(float deltaTime) {
        switch (player.getPlayerCurrentState()) {
            case TURN_ACTIVE:
                updateCardsInHand(deltaTime);
                updateCardsOnBoardPlayArea(deltaTime);
                break;
            case BEGIN_CREATING_STARTING_HAND:
              //  showStartingHandCreationUI();
                //player.playerCurrentState = Player.PlayerState.STARTING_HAND_CHOOSING_CARDS_TO_TOSS;
                break;
            case STARTING_HAND_CHOOSING_CARDS_TO_TOSS:
                //updatePlayersStartingHand();
                break;
            case FINISHED_CREATING_START_HAND:
                //hideStartingHandCreationUI();
                break;
        }
        collisionCheckAndResolve(player.getBoard().getPlayAreas()[player.getPlayerNumber()]);
        collisionCheckAndResolve(player.getBoard().getCardHands()[player.getPlayerNumber()]);


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

   /* private void hideStartingHandCreationUI() {
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
    }*/
//            updateCardsOnBoardPlayArea(deltaTime);

            //For test cards
//            if (playerEvents.size() == 0) {
//                for (CharacterCard playerCard : player.board.playAreas[player.playerNumber].cardsInArea)
//                    for (CharacterCard opponentCard : player.board.playAreas[player.playerNumber].cardsInArea)
//                        if (playerCard.boundingBox.isOverlapping(opponentCard.boundingBox)) {
//                            playerEvents.add(player.createAttack(playerCard, opponentCard));
//                        }
//            } else {
//                for (Eventable event : playerEvents)
//                    event.update(deltaTime);
//            }

            //For actual thing

//            if (playerEvents.size() == 0) {
//                for (CharacterCard playerCard : player.board.playAreas[player.playerNumber].cardsInArea)
//                    for (CharacterCard opponentCard : player.board.playAreas[oppositePlayerNumber].cardsInArea)
//                        if (playerCard.boundingBox.isOverlapping(opponentCard.boundingBox))
//                            playerEvents.add(player.createAttack(playerCard, opponentCard));
//            } else {
//                for (Eventable event : playerEvents)
//                    event.update(deltaTime);
//            }
//        }

//    }

    private void updateCardsOnBoardPlayArea(float deltaTime){
        for(int i = 0; i < player.getBoard().getPlayAreas()[player.getPlayerNumber()].getCardsInArea().size(); i++) {
            CharacterCard card = player.getBoard().getPlayAreas()[player.getPlayerNumber()].getCardsInArea().get(i);
            if(inputBuddy.getTouchEvents().size() > 0) {
                Input.TouchEvent touchEvent = inputBuddy.getTouchEvents().get(0);
                if(checkIsTouched(touchEvent, card)) {
                    card.getOrigin().setOrigin(touchEvent.x, touchEvent.y);
                    player.moveCard(card, card.getOrigin().getOriginX(), card.getOrigin().getOriginY());
                }
            } else if(inputBuddy.getTouchEvents().isEmpty()) {
                player.getBoard().getPlayAreas()[player.getPlayerNumber()].positionCardsInArea();
            }
            if(card.isDeaders()) {
                player.getBoard().getPlayAreas()[player.getPlayerNumber()].getCardsInArea().remove(card);
                //TODO: its dead so should be removed from the universe
            }
        }
    }

    private void updateCardsInHand(float deltaTime) {
        for(int i = 0; i < player.getBoard().getCardHands()[player.getPlayerNumber()].getCardsInArea().size(); i++) {
            CharacterCard card = player.getBoard().getCardHands()[player.getPlayerNumber()].getCardsInArea().get(i);
            if(inputBuddy.getTouchEvents().size() > 0) {
                Input.TouchEvent touchEvent = inputBuddy.getTouchEvents().get(0);
                if(checkIsTouched(touchEvent, card)) {
                    card.getOrigin().setOrigin(touchEvent.x, touchEvent.y);
                    player.moveCard(card, card.getOrigin().getOriginX(), card.getOrigin().getOriginY());
                }
            } else if(card.boundingBox.isOverlapping(player.getBoard().getPlayAreas()[player.getPlayerNumber()].boundingBox) && inputBuddy.getTouchEvents().isEmpty()) {
                playCard(card);
            }
        }
    }

    private boolean checkIfThereIsAnAttackOnOpponentCard(CharacterCard playerCard, CharacterCard opponentCard) {
        return playerCard.boundingBox.isOverlapping(opponentCard.boundingBox);
    }

    private void collisionCheckAndResolve(CardArea cardArea)
    {
        for(CharacterCard card : cardArea.getCardsInArea())
        {
            for(CharacterCard card2 : cardArea.getCardsInArea())
            {
                if(card.getOrigin().equals(card2.getOrigin()))
                    card.boundingBox.getCollisionDetector().resolveCollision(card, card2, card.getOverlapAllowance());

                if(card.boundingBox.getCollisionDetector().checkForCollision(card.boundingBox, card2.boundingBox))
                    card.boundingBox.getCollisionDetector().resolveCollision(card, card2, card.getOverlapAllowance());
                if(!card2.boundingBox.isEncapsulated(player.getBoard().boundingBox)) //collision with board
                    player.getBoard().boundingBox.getCollisionDetector().keepInsideBoundingBox(player.getBoard(), card2);
            }
            if(!card.boundingBox.isEncapsulated(player.getBoard().boundingBox))
                card.boundingBox.getCollisionDetector().keepInsideBoundingBox(player.getBoard(), card);
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
        player.setCurrentMana(player.getCurrentMana()- manaCost);
        for(int i = (player.getMAX_MANA() - 1); i >= (player.getCurrentMana() - 1); i++)
        {
            player.getMana()[i].setManaState(Mana.ManaState.used);
            player.getMana()[i].setBitmap(player.getMana()[i].getManaType()[1]);
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

    public InputBuddy getInputBuddy() {
        return inputBuddy;
    }

    public void setInputBuddy(InputBuddy inputBuddy) {
        this.inputBuddy = inputBuddy;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }

}