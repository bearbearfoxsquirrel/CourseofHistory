package test.puigames.courseofhistory.framework.game.assets.players;

import test.puigames.courseofhistory.framework.game.assets.Deck;
import test.puigames.courseofhistory.framework.game.assets.StartingHandSelector;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.events.CardAttack;
import test.puigames.courseofhistory.framework.game.assets.players.events.Damageable;

/**
 * Created by Michael on 20/02/2017.
 */

public class Player {
    //TODO: Add hero
    public int playerNumber;
    //public CharacterCard[] testCards;
    public PawnState playerCurrentState;
    public Deck playerDeck;
    public Board board;
    public StartingHandSelector startingHandSelector;

    public enum PawnState {
        TURN_STARTED, TURN_ACTIVE, CREATED, WAITING_FOR_TURN, TURN_ENDED, WIN, LOSE, CREATING_START_HAND, FINISHED_CREATING_START_HAND
        //PLAY_ACTIVE refers to when the player is allowed to take active decision in their turn
    }

    public Player(CharacterCard[] playerCards, Board board, int playerNumber) {
        this.playerCurrentState = PawnState.CREATED;
        this.playerNumber = playerNumber;
        this.board = board;
        //this.testCards = playerCards;
        this.board = board;
        setUpPlayerDeck(playerCards);
    }

    public void setUpPlayerDeck(CharacterCard[] playerCards){
        this.playerDeck = new Deck();
        playerDeck.setUpDeck(playerCards);
    }

    public void moveCard(Card card, float posX, float posY) {
        card.translateCard(posX, posY);
    }

    public CardAttack createAttack(CharacterCard theAttacker, Damageable.Attackable recipientOfMyFatalBlow) {
        //TODO add thing to make sure card can only attack once
        return new CardAttack(theAttacker, recipientOfMyFatalBlow, 5, null);
    }

    private void attackHero() {

    }

    public void endTurn() {
        playerCurrentState = PawnState.TURN_ENDED;
    }

    public void startTurn() {
        playerCurrentState = PawnState.TURN_STARTED;
    }

    public CharacterCard drawCardFromDeck() {
        ((CharacterCard)playerDeck.peek()).spawnObject(0, 0);
        return (CharacterCard)playerDeck.pop();
    }

    public void finishedCreatingStartHand() {
        playerCurrentState = PawnState.FINISHED_CREATING_START_HAND;
    }

    public void placeCardOnBoard(CharacterCard card) {

    }

    public void addCardToArea(CharacterCard card) {
        board.playAreas[playerNumber].addCardToArea(card);
    }

    public void removeCardFromArea(CharacterCard card) {
        board.playAreas[playerNumber].removeCardFromArea(card);
    }

    public void selectCardToRemove(CharacterCard card) {

    }
}
