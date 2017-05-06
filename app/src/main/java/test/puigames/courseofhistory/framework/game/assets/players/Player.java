package test.puigames.courseofhistory.framework.game.assets.players;

import test.puigames.courseofhistory.framework.game.assets.CardHand;
import test.puigames.courseofhistory.framework.game.assets.Deck;
import test.puigames.courseofhistory.framework.game.assets.Hero;
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

    private static final int STARTING_HAND_SIZE = 3;
    private int playerNumber;
    private PlayerState playerCurrentState;
    private CharacterCard[] testCards;
    public final int MAX_MANA = 10;
    private Mana[] mana = new Mana[MAX_MANA];
    private int currentMana;
    private Deck playerDeck;
    private Board board;
    private StartingHandSelector startingHandSelector;
    private Hero hero;
    private float rotation;
    private CardHand hand;

    public enum PlayerState {
        CREATED, TURN_STARTED, TURN_ACTIVE, WAITING_FOR_TURN, TURN_ENDED, WIN, LOSE,
        WAITING_TO_BEGIN_CREATING_HAND, BEGIN_CREATING_STARTING_HAND, STARTING_HAND_CHOOSING_CARDS_TO_TOSS, FINISHED_CREATING_START_HAND
        //PLAY_ACTIVE refers to when the player is allowed to take active decision in their turn
    }

    public Player(CharacterCard[] playerCards, Board board, Deck deck, CardHand cardHand, Hero hero, int playerNumber) {
        this.playerCurrentState = PlayerState.CREATED;
        this.playerNumber = playerNumber;
        this.board = board;
        this.playerDeck = deck;
        this.currentMana = 0;
        this.rotation = 0;
        this.hero = hero;
        this.hand = cardHand;

      //  this.hero = board.getHero(playerNumber); //give player hero from board now
        setUpPlayerDeck(playerCards);
    }

    public void confirmSelectedCardsFromStartingHandSelector() {
        for (CharacterCard card : startingHandSelector.getCardsToKeep())
            hand.addCardToArea(card);
    }



    public void createNewStartingHand() {
        CharacterCard[] startingHand = new CharacterCard[startingHandSelector.STARTING_HAND_SIZE];
        for (int handIndex = 0; handIndex < startingHandSelector.STARTING_HAND_SIZE; handIndex++)
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
        return (CharacterCard)playerDeck.pop();
    }

    public void finishedCreatingStartHand() {
        //playerCurrentState = PlayerState.FINISHED_CREATING_START_HAND;
    }

    public void placeCardOnBoard(CharacterCard card) {
        board.getPlayArea(playerNumber).addCardToArea(card);
        board.getCardHand(playerNumber).removeCardFromArea(card);
    }

    public void addCardToArea(CharacterCard card) {
        board.getPlayArea(playerNumber).addCardToArea(card);
    }

    public void removeCardFromArea(CharacterCard card) {
        board.getPlayArea(playerNumber).removeCardFromArea(card);
    }

    public void selectCardToKeep(CharacterCard card) {
        //Adds card to keep set, and removes it from to toss set if it is in there
        if (startingHandSelector.getCardsToToss().contains(card))
            startingHandSelector.getCardsToToss().remove(card);
        startingHandSelector.getCardsToKeep().add(card);
    }


    public void selectCardToRemove(CharacterCard card) {
        //Adds card to toss set, and removes it from to keep set if it is there
        if (startingHandSelector.getCardsToKeep().contains(card))
            startingHandSelector.getCardsToKeep().remove(card);
        startingHandSelector.getCardsToToss().add(card);
    }

    public static int getStartingHandSize() {
        return STARTING_HAND_SIZE;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public PlayerState getPlayerCurrentState() {
        return playerCurrentState;
    }

    public void setPlayerCurrentState(PlayerState playerCurrentState) {
        this.playerCurrentState = playerCurrentState;
    }

    public CharacterCard[] getTestCards() {
        return testCards;
    }

    public void setTestCards(CharacterCard[] testCards) {
        this.testCards = testCards;
    }

    public int getMAX_MANA() {
        return MAX_MANA;
    }

    public Mana[] getMana() {
        return mana;
    }

    public void setMana(Mana[] mana) {
        this.mana = mana;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public Deck getPlayerDeck() {
        return playerDeck;
    }

    public void setPlayerDeck(Deck playerDeck) {
        this.playerDeck = playerDeck;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public StartingHandSelector getStartingHandSelector() {
        return startingHandSelector;
    }

    public void setStartingHandSelector(StartingHandSelector startingHandSelector) {
        this.startingHandSelector = startingHandSelector;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return this.rotation;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public CardHand getHand() {
        return hand;
    }

}
