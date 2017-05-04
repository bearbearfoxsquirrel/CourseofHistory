package test.puigames.courseofhistory.framework.game.assets.players.controllers;


import android.util.Log;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.controlling.Inputable;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.CourseOfHistory;
import test.puigames.courseofhistory.framework.game.assets.Hero;
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

//    public HumanCardGameController(Screen screen, InputBuddy inputBuddy) {
//        this.inputBuddy = inputBuddy;
//    }

    @Override
    public void update(float deltaTime) {
        switch (player.getPlayerCurrentState()) {
            case TURN_ACTIVE:
                updateCardsInHand(deltaTime);
                updateCardsOnBoardPlayArea(deltaTime);
                updateHero(deltaTime);
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
        //Inter card collision checking and handling
        cardCollisionCheckAndResolve(getPlayersCardsInPlayArea());
        cardCollisionCheckAndResolve(getPlayersCardsInCardHand());
        //Keep cards on board: don't check hand, causes issues
        keepCardsInBoardBounds(getPlayersCardsInPlayArea());

//       if (player.playerCurrentState == Player.PlayerState.TURN_ACTIVE) {
            //For actual thing
//            if (playerEvents.size() == 0) {
//                for (CharacterCard playerCard : getPlayersCardsInPlayArea())
//                    for (CharacterCard opponentCard : player.getBoard().getPlayArea(getOppositePlayerNumber()).getCardsInArea())
//                        if (checkIfThereIsAnAttackOnOpponentCard(playerCard, opponentCard)) {
//                            playerEvents.add(player.createAttack(playerCard, opponentCard));
//                            Log.d("attacker", playerCard.toString());
//                            Log.e("receiver", opponentCard.toString());
//                        }
//            } else {
//                for (Eventable event : playerEvents)
//                    event.update(deltaTime);
//            } //else if (player.getPlayerCurrentState() == Player.PlayerState.CREATING_START_HAND) {
        //    updatePlayersStartingHand();
        //}
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

//            //For actual thing
//
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
//
//    }

    /**
     * Handles updating cards on the board: allows cards to attack if player indicates so (and there is a card to attack)
     * @param deltaTime - would be used for animations, should we have those
     */
    private void updateCardsOnBoardPlayArea(float deltaTime) {
        if(inputBuddy.getTouchEvents().size() > 0) {
            Input.TouchEvent touchEvent = inputBuddy.getTouchEvents().get(0);
            for(CharacterCard card : getPlayersCardsInPlayArea()) {
                if (checkIfCardCanUnleashHell(touchEvent, card)) {
                    card.setOrigin(touchEvent.x, touchEvent.y);
                    player.moveCard(card, card.getOrigin().getOriginX(), card.getOrigin().getOriginY());
                    preventGoddamnCardOriginOverlappingHopefullySuperAwesomeFunMethod(getPlayersCardsInPlayArea());
                    if(youAreTryingToAttackAnotherCard(touchEvent, card)) {
                        attackIfThereIsACardHere(card);
                    } else if (youAreTryingToAttackTheEnemyHero(touchEvent, card, player.getBoard().getHero(getOppositePlayerNumber()))) {
                        attackTheEnemyHero(card, player.getBoard().getHero(getOppositePlayerNumber()));
                    }
                    break;
                }
            }
        } else
            player.getBoard().getPlayArea(player.getPlayerNumber()).positionCardsInArea();
    }

    private void updateCardsInHand(float deltaTime) {
        if(inputBuddy.getTouchEvents().size() > 0) {
            Input.TouchEvent touchEvent = inputBuddy.getTouchEvents().get(0);
            for(CharacterCard card : getPlayersCardsInCardHand()) {
                if(checkIfCardCanBePlayedNow(touchEvent, card)) {
                    card.setOrigin(touchEvent.x, touchEvent.y);
                    player.moveCard(card, card.getOrigin().getOriginX(), card.getOrigin().getOriginY());
                    if(card.getBoundingBox().isEncapsulated(player.getBoard().getPlayArea(player.getPlayerNumber()).getBoundingBox())) {
                        playCard(card);
                    }
                    preventGoddamnCardOriginOverlappingHopefullySuperAwesomeFunMethod(getPlayersCardsInCardHand());
                    break;
                }
            }
        } else
            player.getBoard().getCardHand(player.getPlayerNumber()).positionCardsInArea();
    }

    /**
     * Press the hero, the end turn button will appear
     * SPOILER: the button doesn't appear
     */
    private void updateHero(float deltaTime) {
        if(inputBuddy.getTouchEvents().size() > 0) {
            Input.TouchEvent touchEvent = inputBuddy.getTouchEvents().get(0);
            if(checkIsTouched(touchEvent, player.getHero()) && touchEvent.type == Input.TouchEvent.TOUCH_DOWN) {
                Log.d("hero touched", "turn ended");
                player.setPlayerCurrentState(Player.PlayerState.TURN_ENDED); //TODO: show end turn button - end turn button does this instead
            }
        }
    }

//    /**
//     * checks if there is an attack
//     * @param playerCard - attacking card
//     * @param opponentCard - receiving card
//     * @return true if playerCard's bounding box is overlapping opponentCard's bounding box
//     */
//    private boolean checkIfThereIsAnAttackOnOpponentCard(CharacterCard playerCard, CharacterCard opponentCard) {
//        return playerCard.getBoundingBox().isOverlapping(opponentCard.getBoundingBox());
//    }

    /**
     * collision between cards - only if there are no touch events
     * @param cardList - players cards we are checking for collisions in
     */
    private void cardCollisionCheckAndResolve(ArrayList<CharacterCard> cardList) {
        for (CharacterCard card : cardList) {
            for (CharacterCard card2 : cardList) {
                if (inputBuddy.getTouchEvents().size() <= 0) {
                    if (card.getBoundingBox().getCollisionDetector().checkForCollision(card.getBoundingBox(), card2.getBoundingBox()))
                            card.getBoundingBox().getCollisionDetector().resolveCollision(card, card2, card.getOverlapAllowance());
                }
            }
        }
    }

    /**
     * @param touchEvent - touch event we are checking against card & type of touch event
     * @param card - card we are checking if its touched
     * @return - true if card is touched and touch event type is touch_up, false otherwise
     */
    private boolean youAreTryingToAttackAnotherCard(Input.TouchEvent touchEvent, CharacterCard card) {
        return (checkIsTouched(touchEvent, card)
                && (touchEvent.type == Input.TouchEvent.TOUCH_UP || touchEvent.type == Input.TouchEvent.TOUCH_DOWN));
    }

    /** Card passed in will attack if there is an opposing card's bounding box overlapping with it's bounding box
     * Both cards will take damage
     * @param card - card whose bounding box we are checking for overlaps with opponent's cards
     */
    private void attackIfThereIsACardHere(CharacterCard card) {
        for(CharacterCard opponentCard : player.getBoard().getPlayArea(getOppositePlayerNumber()).getCardsInArea())
            if(card.getBoundingBox().isOverlapping(opponentCard.getBoundingBox())) {
                opponentCard.applyDamage(card.getAttack()); //opponent takes damage
                card.applyDamage(opponentCard.getAttack()); //attacking card also takes damage
                Log.d("attacker health", "" + card.getHealth());
                Log.e("recipient health", "" + opponentCard.getHealth());
                //TODO: add update for stats here man bruh dudeseph
            }
    }

    /**
     *
     * @param touchEvent
     * @param hero - hero we are checking if it's
     * @return - true if card is touched and touch event type is touch_up
     */
    private boolean youAreTryingToAttackTheEnemyHero(Input.TouchEvent touchEvent, CharacterCard card, Hero hero) {
        return (card.getBoundingBox().isOverlapping(hero.getBoundingBox())
                && (touchEvent.type == Input.TouchEvent.TOUCH_UP || touchEvent.type == Input.TouchEvent.TOUCH_DOWN));
    }

    /**
     * Card will attack the enemy hero if their bounding boxes are overlapping
     * @param card - card that is attacking
     * @param hero - hero that is being attacked
     */
    private void attackTheEnemyHero(CharacterCard card, Hero hero) {
        hero.applyDamage(card.getAttack());
    }

    /**
     * **tries** to stop two cards from snapping to one point of touch
     * @param cardList - list of cards we are checking for origin overlapping
     */
    private void preventGoddamnCardOriginOverlappingHopefullySuperAwesomeFunMethod(ArrayList<CharacterCard> cardList) {
        int superFunHappyGreatMultiplier = 2;
        for(CharacterCard card : cardList) {
            for (CharacterCard otherCard : cardList) {
                if (inputBuddy.getTouchEvents().size() > 0) {
                    if(card.getOrigin().equals(otherCard.getOrigin()))
                        card.getBoundingBox().getCollisionDetector().resolveCollision(card, otherCard, card.getOverlapAllowance() * superFunHappyGreatMultiplier);
                }
            }
        }
    }

    /**
     * @return this player's cards in their hand
     */
    private ArrayList<CharacterCard> getPlayersCardsInCardHand() {
        return player.getBoard().getCardHand(player.getPlayerNumber()).getCardsInArea();
    }

    /**
     * @return this player's cards in their play area
     */
    private ArrayList<CharacterCard> getPlayersCardsInPlayArea() {
        return player.getBoard().getPlayArea(player.getPlayerNumber()).getCardsInArea();
    }

    private void keepCardsInBoardBounds(ArrayList<CharacterCard> cardList) {
        for(CharacterCard card : cardList)
            if(!card.getBoundingBox().isEncapsulated(player.getBoard().getBoundingBox()))
                card.getBoundingBox().getCollisionDetector().keepInsideBoundingBox(player.getBoard(), card);
    }

    /**
     * When a player moves a card from their hand to board, this is called
     * Checks if player has enough mana for the action, if they do, card is played
     * and the corresponding mana cost of card is removed from player's
     * currentMana
     * @param card - card that is being played
     */
    public void playCard(CharacterCard card) {
        //player.currentAction = Player.PawnAction.PLACE_CARD_ON_BOARD;
        if(player.getCurrentMana() >= card.getMana()) {
            player.placeCardOnBoard(card);
            removeManaFromPlayer(card.getMana());
        } //else {
            //should probably tell the user that they can't do that
        //}
    }

    /**
     * Checks if card is touched, and if the card has energy to attack
     * @param touchEvent - checking this event against card
     * @param card - card we are checking
     * @return - true if card is touched, the touch event type is dragged, and card has attack energy, false otherwise
     */
    private boolean checkIfCardCanUnleashHell(Input.TouchEvent touchEvent, CharacterCard card) {
        return (checkIsTouched(touchEvent, card)
                && card.hasEnergyToAttack());
    }

    /**
     * Checks if card has mana to be played now nad is touched
     * @param touchEvent - touch event on card
     * @param card - card being touched and card's mana that is being checked
     * @return - true if card is touched and player's mana is >= card's mana
     */
    private boolean checkIfCardCanBePlayedNow(Input.TouchEvent touchEvent, CharacterCard card) {
        return (checkIsTouched(touchEvent, card)
                && player.getCurrentMana() >= card.getMana());
    }

    /**
     * If a player has enough mana, this method is called
     * @param manaCost - amount of mana used by playing card/switched to used state
     */
    private void removeManaFromPlayer(int manaCost) {
        int remainingMana = (player.getCurrentMana() - manaCost) > 0 ? (player.getCurrentMana() - manaCost) : 0;
        player.setCurrentMana(remainingMana);
        for(int i = (player.getMAX_MANA() - 1); i >= (player.getCurrentMana()); i--) {
            player.getMana()[i].setManaState(Mana.ManaState.used);
            player.getMana()[i].setBitmap(player.getMana()[i].getManaType()[1]);
        }
    }

    /**
     * Checks if gameObject is touched
     * @param touchEvent - touch event object we are checking pos(x, y) of
     * @param object - object we are checking
     * @return - true if touch event(x, y) falls within object's bounding box, false otherwise
     */
    private boolean checkIsTouched(Input.TouchEvent touchEvent, GameObject object) {
        return (object.getBoundingBox().isTouchOn(touchEvent));
    }

    /**
     *  Gets opposing player's player number
     * @return - opposing player's number
     */
    private int getOppositePlayerNumber() {
        return ((player.getPlayerNumber() + 1) % 2);
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
