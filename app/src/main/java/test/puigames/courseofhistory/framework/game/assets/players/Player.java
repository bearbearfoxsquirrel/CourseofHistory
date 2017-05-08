package test.puigames.courseofhistory.framework.game.assets.players;

import android.util.Log;

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
    private static final int MAX_TIMES_PLAYER_CAN_ATTACK_ENEMY_HERO_PER_TURN = 1;

    private int playerNumber;
    private PlayerState playerCurrentState;
    public final int MAX_MANA = 10;
    private Mana[] mana = new Mana[MAX_MANA];
    private int currentMana;
    private Deck playerDeck;
    private Board board;
    private StartingHandSelector startingHandSelector;
    private Hero hero;
    private float rotation;
    private CardHand hand;

    //ONLY TEMPORARY UNTIL ABILITIES ARE ADDED
    private int currentAttacksLeftOnEnemyHero;

    public boolean hasCardsThatCanAttack() {
        for (CharacterCard card : board.getPlayArea(playerNumber).getCardsInArea())
            if (card.hasEnergyToAttack())
                return true;
        return false;
    }

    public enum PlayerState {
        CREATED, TURN_STARTED, WAITING_FOR_FIRST_TURN, TURN_ACTIVE, WAITING_FOR_TURN, TURN_ENDED, WIN, LOSE,
        WAITING_TO_BEGIN_CREATING_HAND, BEGIN_CREATING_STARTING_HAND, STARTING_HAND_CHOOSING_CARDS_TO_TOSS, FINISHED_CREATING_START_HAND
        //PLAY_ACTIVE refers to when the player is allowed to take active decision in their turn
    }

    public Player(CharacterCard[] playerCards, Board board, Deck deck, CardHand cardHand, int playerNumber) {
        this.playerCurrentState = PlayerState.CREATED;
        this.playerNumber = playerNumber;
        this.board = board;
        this.playerDeck = deck;
        this.currentMana = 0;
        this.rotation = 0;
        this.hand = cardHand;
        this.currentAttacksLeftOnEnemyHero = MAX_TIMES_PLAYER_CAN_ATTACK_ENEMY_HERO_PER_TURN;

        this.hero = board.getHero(playerNumber); //give player hero from board now
        setUpPlayerDeck(playerCards);
    }

    public void setUpPlayerDeck(CharacterCard[] playerCards){
        playerDeck.setUpDeck(playerCards);
    }

    public void confirmSelectedCardsFromStartingHandSelector() {
        //Cards choosen to keep are added to hand
        for (CharacterCard card : startingHandSelector.getCardsToKeep())
            hand.addCardToArea(card);

        //Drawing cards from the deck till the player has the right amount of cards
        //Used if player chooses to toss cards
        while (hand.getCardsInArea().size() < startingHandSelector.STARTING_HAND_SIZE)
            hand.addCardToArea(playerDeck.pop());

        //Placing cards chosen to toss back in the deck at random areas (shuffling kinda)˚
        for (CharacterCard card : startingHandSelector.getCardsToToss())
            playerDeck.add( (int) Math.random() % playerDeck.size(), card );

        playerCurrentState = PlayerState.FINISHED_CREATING_START_HAND;
    }

    public void createNewStartingHand() {
        CharacterCard[] startingHand = new CharacterCard[startingHandSelector.STARTING_HAND_SIZE];
        for (int handIndex = 0; handIndex < startingHandSelector.STARTING_HAND_SIZE; handIndex++)
            startingHand[handIndex] = drawCardFromDeck(); //Draws the amount of starting cards from the player's deck for each player
        startingHandSelector = new StartingHandSelector(startingHand); //Creates a new instance of starting hand selector for the player to use
    }

    public void moveCard(Card card, float posX, float posY) {
        card.translateCard(posX, posY);
    }

    public CardAttack createAttack(CharacterCard theAttacker, Damageable.Attackable recipientOfMyFatalBlow) {
        //TODO add thing to make sure card can only attack once
        return new CardAttack(theAttacker, recipientOfMyFatalBlow, 5, null);
    }

    public int getCurrentAttacksLeftOnEnemyHero() {
        return currentAttacksLeftOnEnemyHero;
    }

    //TODO make
    private void attackHero() {

    }

    public void endTurn() {
        playerCurrentState = PlayerState.TURN_ENDED;
    }

    public void startTurn() {
        currentAttacksLeftOnEnemyHero = MAX_TIMES_PLAYER_CAN_ATTACK_ENEMY_HERO_PER_TURN;
        if (playerDeck.size() > 0) {
            hand.addCardToArea(drawCardFromDeck());
        }
    }

    public void startActivePartOfTurn() {
        playerCurrentState = PlayerState.TURN_ACTIVE;
        currentAttacksLeftOnEnemyHero = MAX_TIMES_PLAYER_CAN_ATTACK_ENEMY_HERO_PER_TURN;
    }

    public CharacterCard drawCardFromDeck() {
        return playerDeck.pop();
    }

    public void finishedCreatingStartHand() {
        playerCurrentState = PlayerState.FINISHED_CREATING_START_HAND;
    }

    public boolean hasCardThatCanBeDrawn() {
        for (CharacterCard card : hand.getCardsInArea())
            if (card.getMana() <= getCurrentMana())
                return true;
        return false;
    }

    public void addCardToArea(CharacterCard card) {
        board.getPlayArea(playerNumber).addCardToArea(card);
    }

    /**
     * Checks if card is touched, and if the card has energy to attack
     *
     * @param card - card we are checking
     * @return - true if card is touched, the touch event type is dragged, and card has attack energy, false otherwise
     */
    public boolean canCardBePlacedOnPlayArea(CharacterCard card) {
        return (currentMana >= card.getMana());
    }

    /**
     * When a player moves a card from their hand to board, this is called
     * Checks if player has enough mana for the action, if they do, card is played
     * and the corresponding mana cost of card is removed from player's currentMana
     *
     * @param card - card that is being played
     */
    public void playCard(CharacterCard card) {
        placeCardOnBoard(card);
        removeManaFromPlayer(card.getMana());
    }

    private void placeCardOnBoard(CharacterCard card) {
        board.getPlayArea(playerNumber).addCardToArea(card);
        removeCardFromHand(card);
    }

    public void removeCardFromBoardPlayArea(CharacterCard card) {
        if (board.getPlayArea(playerNumber).getCardsInArea().contains(card)) {
            board.getPlayArea(playerNumber).removeCardFromArea(card);
        }
    }

    public boolean enemyHasCardsThatCanBeAttacked() {
        return !board.getPlayArea(getOppositePlayerNumber()).getCardsInArea().isEmpty();

    }

    public void removeCardFromHand(CharacterCard card) {
        hand.removeCardFromArea(card);
    }


    public boolean canPlayerAttackEnemyHero() {
        return getCurrentAttacksLeftOnEnemyHero() > 0;
    }

    /**
     * Card will attack the enemy hero if their bounding boxes are overlapping
     *
     * @param card - card that is attacking
     */
    public void attackEnemyHero(CharacterCard card) {
        card.attack(board.getHero(getOppositePlayerNumber()));
        Log.e("hero attacked!", "remaining health " + getHero().getHealth());
        currentAttacksLeftOnEnemyHero--;
    }

    /**
     * If a player has enough mana, this method is called
     *
     * @param manaCost - amount of mana used by playing card/switched to used state
     */
    private void removeManaFromPlayer(int manaCost) {
        int remainingMana = (currentMana - manaCost) > 0 ? (currentMana - manaCost) : 0;
        currentMana = remainingMana;
        for (int i = (getMAX_MANA() - 1); i >= (currentMana); i--) {
            getMana()[i].setManaState(Mana.ManaState.USED);
            getMana()[i].setBitmap(getMana()[i].getManaType()[1]);
        }
    }

    public void attackCard(CharacterCard cardUsedToAttack, CharacterCard cardToAttack) {
        cardUsedToAttack.attack(cardToAttack);
        cardUsedToAttack.setHealth(cardUsedToAttack.getHealth() - cardToAttack.getAttack());
    }

    /**
     * Checks to see if there is a card that can be played from hand
     *
     * @return - true if card's mana cost is less than or equal to player's current mana,
     *          false otherwise
     */
    public boolean isThereACardThatCanBePlacedOnBoard() {
        for (CharacterCard card : hand.getCardsInArea())
            if (card.getMana() <= currentMana)
                return true;
        return false;
    }


    /**
     *  Gets opposing player's player number
     *
     * @return - opposing player's number
     */
    public int getOppositePlayerNumber() {
        return ((getPlayerNumber() + 1) % 2);
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
