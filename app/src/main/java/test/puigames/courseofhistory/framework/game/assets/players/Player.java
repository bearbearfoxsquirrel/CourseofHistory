package test.puigames.courseofhistory.framework.game.assets.players;

import test.puigames.courseofhistory.framework.game.assets.Deck;
import test.puigames.courseofhistory.framework.game.assets.Mana;
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
    private static final int STARTING_HAND_SIZE = 3;

    public int playerNumber;
    public PlayerState playerCurrentState;
    public CharacterCard[] testCards;
    public final int MAX_MANA = 10;
    public Mana[] mana = new Mana[MAX_MANA];
    public int currentMana;
    public Deck playerDeck;
    public Board board;
    public StartingHandSelector startingHandSelector;

    public enum PlayerState {
        CREATED, TURN_STARTED, TURN_ACTIVE, WAITING_FOR_TURN, TURN_ENDED, WIN, LOSE,
        WAITING_TO_BEGIN_CREATING_HAND, BEGIN_CREATING_STARTING_HAND, STARTING_HAND_CHOOSING_CARDS_TO_TOSS, FINISHED_CREATING_START_HAND
        //PLAY_ACTIVE refers to when the player is allowed to take active decision in their turn
    }

    public Player(CharacterCard[] playerCards, Board board, Deck deck, int playerNumber) {
        this.playerCurrentState = PlayerState.CREATED;
        this.playerNumber = playerNumber;
        this.board = board;
        this.board = board;
        this.playerDeck = deck;
        this.currentMana = 0;

        setUpPlayerDeck(playerCards);

    }

    public void createNewStartingHand() {
        playerCurrentState = Player.PlayerState.BEGIN_CREATING_STARTING_HAND;

        CharacterCard[] startingHand = new CharacterCard[STARTING_HAND_SIZE];
        for (int handIndex = 0; handIndex < STARTING_HAND_SIZE; handIndex++)
            startingHand[handIndex] = drawCardFromDeck(); //Draws the amount of starting cards from the player's deck for each player
        startingHandSelector = new StartingHandSelector(startingHand); //Creates a new instance of starting hand selector for the player to use
    }

    public void setUpPlayerDeck(CharacterCard[] playerCards){
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
        playerCurrentState = PlayerState.TURN_ENDED;
    }

    public void startTurn() {
        playerCurrentState = PlayerState.TURN_STARTED;
    }

    public CharacterCard drawCardFromDeck() {
      //  ((CharacterCard)playerDeck.peek()).place(this.spawnScreen, 200, 300); //TODO give proper spawn places
        return (CharacterCard)playerDeck.pop();
    }

    public void finishedCreatingStartHand() {
        //playerCurrentState = PlayerState.FINISHED_CREATING_START_HAND;
    }

    public void placeCardOnBoard(CharacterCard card) {
        board.playAreas[playerNumber].addCardToArea(card);
//        board.cardHands[playerNumber].removeCardFromArea(card);
    }

    public void addCardToArea(CharacterCard card) {
        board.playAreas[playerNumber].addCardToArea(card);
    }

    public void removeCardFromArea(CharacterCard card) {
        board.playAreas[playerNumber].removeCardFromArea(card);
    }

    public void selectCardToKeep(CharacterCard card) {
        //Adds card to keep set, and removes it from to toss set if it is in there
        if (startingHandSelector.cardsToToss.contains(card))
            startingHandSelector.cardsToToss.remove(card);
        startingHandSelector.cardsToKeep.add(card);
    }


    public void selectCardToRemove(CharacterCard card) {
        //Adds card to toss set, and removes it from to keep set if it is there
        if (startingHandSelector.cardsToKeep.contains(card))
            startingHandSelector.cardsToKeep.remove(card);
        startingHandSelector.cardsToToss.add(card);
    }
}
