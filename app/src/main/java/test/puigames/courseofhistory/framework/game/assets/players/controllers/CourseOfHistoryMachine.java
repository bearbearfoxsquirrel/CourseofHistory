package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import android.util.Log;

import java.util.Iterator;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Updateable;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;

/**
 * Created by Michael on 06/03/2017.
 */

public class CourseOfHistoryMachine implements Updateable {

    private Screen drawScreen;

    private static float TURN_TIME = 20.f;
    private static final float COIN_TOSS_DELAY = 3.f;
    public static int PLAYER_COUNT = 2;

    private float startDelayTimeRemaining;
    private float turnTimeRemaining;
    private Player[] players;
    private int[] manaCount;
    private GameState currentGameState;
    private int turnIndex;
    private Coin coin;
    private Board board;

    public enum GameState {
        CREATED, COIN_TOSS, CREATING_STARTING_HANDS, GAME_ACTIVE, GAME_PAUSED, GG
    }

    public CourseOfHistoryMachine(Player[] players, Coin coin, Board board) {
        this.players = players;
        this.coin = coin;
        this.currentGameState = GameState.CREATED;
        this.startDelayTimeRemaining = COIN_TOSS_DELAY;
        this.turnTimeRemaining = TURN_TIME;
        this.board = board;
        this.manaCount = new int[players.length];
        for (int i = 0; i < manaCount.length; i++)
            manaCount[i] = 0;
    }

    private void startGame() {
        currentGameState = GameState.COIN_TOSS;
        //Tells the game it needs to make a coin toss
    }

    private void tossCoin() {
        coin.flipCoin();

        if(coin.getFaceUp() == Coin.Result.HEADS)
            turnIndex = 0;
        else
            turnIndex = 1;

        coin.setBitmap(coin.getCoinSides()[turnIndex]);
    }

    @Override
    public void startTicking(Screen screen) {
        if (!screen.isInUpdateables(this))
            screen.addToUpdateables(this);
    }

    @Override
    public void stopTicking(Screen screen) {
        if (screen.isInUpdateables(this))
            screen.removeFromUpdateables(this);
    }

    @Override
    public void update(float deltaTime) {
        switch (currentGameState) {
            case CREATED:
                startDelayTimeRemaining -= deltaTime;
                if (startDelayTimeRemaining <= 0.f)
                    startGame();
                break;

            case COIN_TOSS:
                tossCoin();
                currentGameState = GameState.CREATING_STARTING_HANDS;//To transition FSM to the game being active so turns are now being made

                for (Player player : players)
                    player.setPlayerCurrentState(Player.PlayerState.WAITING_TO_BEGIN_CREATING_HAND);
                break;

            case CREATING_STARTING_HANDS:
               /* transitionPlayerStatesFromCreatingHandToTurnStates();
                currentGameState = GameState.GAME_ACTIVE;*/
                //TO SKIP THIS STAGE OF THE GAME UNCOMMENT THIS BLOCK AND COMMENT REST OF THIS SWITCH CASE

                if (isBothPlayersFinishedCreatingStartHand()) {
                    nextPlayersTurn(); //get back to original players turn
                    //If both players are finished taking their turn it selects the player that won the coin toss and lets them go first
                    transitionPlayerStatesFromCreatingHandToTurnStates();
                    currentGameState = GameState.GAME_ACTIVE;

                } else {
                    //Handles each player creating their hand and checking if they are finished making their hand
                    switch (players[turnIndex].getPlayerCurrentState()) {
                        case BEGIN_CREATING_STARTING_HAND:
                            players[turnIndex].createNewStartingHand();
                            players[turnIndex].setPlayerCurrentState(Player.PlayerState.STARTING_HAND_CHOOSING_CARDS_TO_TOSS);
                            break;

                        case WAITING_TO_BEGIN_CREATING_HAND:
                            players[turnIndex].setPlayerCurrentState(Player.PlayerState.BEGIN_CREATING_STARTING_HAND);
                            break;

                        case FINISHED_CREATING_START_HAND:
                            players[turnIndex].confirmSelectedCardsFromStartingHandSelector();
                            incrementTurnIndex();
                            break;
                    }
                }
                break;

            case GAME_ACTIVE:
                takeTurn(deltaTime);
                updateCardsInPlay();
                checkWinStatus();
                break;

            case GAME_PAUSED:
                //TODO handle when the game is paused
                break; //What to do when the player has set the game to be paused

            case GG:
                //Handle endgame
                Log.v("VICTORY!", "player " + (turnIndex + 1) + " wins!! :D :D :D :D");
                //TODO check who wins
                break;
        }
    }

    private void transitionPlayerStatesFromCreatingHandToTurnStates() {
        for (int i = 0; i < players.length; i++) {
            if (i == turnIndex)
                players[i].setPlayerCurrentState(Player.PlayerState.TURN_STARTED);
            else
                players[i].setPlayerCurrentState(Player.PlayerState.WAITING_FOR_TURN);
        }
    }

    private boolean isBothPlayersFinishedCreatingStartHand() {
        for (int i = 0; i < players.length; i++)
            if (!isPlayerFinishedCreatingStartingHand(i))
                return false;
        return true;
    }

    private boolean isPlayerFinishedCreatingStartingHand(int playerIndex) {
        return (players[playerIndex].getPlayerCurrentState() == Player.PlayerState.FINISHED_CREATING_START_HAND);
    }

    private void checkWinStatus() {
        for (int i = 0; i < players.length; i++) {
            if(players[i].getPlayerDeck().size() == 0 && players[i].getPlayerCurrentState() == Player.PlayerState.TURN_STARTED) { //TODO add if hero health is <= 0
               if(players[i].getHero().getHealth() < players[findNextPlayer(i)].getHero().getHealth()) {
                   resolveGame(i);
               } else if(players[i].getHero().getHealth() == players[findNextPlayer(i)].getHero().getHealth()) {
                   //tie??
                   resolveGame(findNextPlayer(i)); //other player wins by default xD xD xD xD :3c
               } else {
                   resolveGame(findNextPlayer(i));
               }
            }
            if(players[i].getHero().getHealth() <= 0) {
                resolveGame(i);
            }
        }
    }

    private void resolveGame(int loser) {
        players[loser].setPlayerCurrentState(Player.PlayerState.LOSE);
        players[findNextPlayer(loser)].setPlayerCurrentState(Player.PlayerState.WIN);
        currentGameState = GameState.GG;
    }

    private void updateCardsInPlay() {
        for (Player player: players)
            for (Iterator<CharacterCard> iterator = players[turnIndex].getBoard().getPlayArea(players[turnIndex].getPlayerNumber()).getCardsInArea().iterator(); iterator.hasNext();) {
                CharacterCard card = iterator.next();
                if (card.isDeaders()) {
                    player.getBoard().getPlayArea(player.getPlayerNumber()).removeCardFromArea(card);
                    card.remove(player.getBoard().getCurrentScreen());
                    Log.wtf("EVENT", "A card on p" + (player.getPlayerNumber() + 1) + "'s side has died" + "\n" + card.toString());
                    //TODO: possibly need to set all things to null to "delete" it so it doesn't interact with objects in the game
                }
            }
        //Testing purposes only!
         /*  for (Player player: players)
            for (CharacterCard card : player.testCards)
                if (card.isDeaders())
                    card.height = 0;*/
    }

    private void takeTurn(float deltaTime) {
        //Turn being made
        switch (players[turnIndex].getPlayerCurrentState()) {
            case CREATED:
                players[turnIndex].setPlayerCurrentState(Player.PlayerState.TURN_STARTED);
                break;

            case TURN_STARTED:
                startTurn();
                players[turnIndex].setPlayerCurrentState(Player.PlayerState.TURN_ACTIVE);
                break;

            case TURN_ACTIVE:
                updateAndCheckTurnTimeRemaining(deltaTime);
                break;

            case TURN_ENDED:
                repositionCards();
                nextPlayersTurn(); //Changes turn index to the next player
                break;
        }
    }

    private void startTurn() {
        turnTimeRemaining = TURN_TIME;
        if (players[turnIndex].getPlayerDeck().size() != 0) {
            players[turnIndex].startTurn();
            players[turnIndex].getBoard().getCardHand(turnIndex).addCardToArea(players[turnIndex].drawCardFromDeck());
        }

        if (manaCount[turnIndex] < players[turnIndex].getMAX_MANA()) { //don't want it going over 10 - max
            manaCount[turnIndex]++;
            giveManaToPlayer();
        }
        Log.i("Player " + (turnIndex + 1), "" + players[turnIndex].getCurrentMana() + " mana");

        for (CharacterCard card : players[turnIndex].getBoard().getPlayAreas()[turnIndex].getCardsInArea()) {
            card.setCurrentAttackEnergy(card.getMaxAttackEnergy());
        }
    }

    private void repositionCards() {
        players[turnIndex].getBoard().getPlayArea(players[turnIndex].getPlayerNumber()).positionCardsInArea();
        players[turnIndex].getBoard().getCardHand(players[turnIndex].getPlayerNumber()).positionCardsInArea();
    }

    /**
     * At the start of every turn, give player +1 available mana than last turn
     * Change mana bitmaps to reflect that
     */
    private void giveManaToPlayer() {
        players[turnIndex].setCurrentMana(manaCount[turnIndex]);
        for(int i = 0; i < players[turnIndex].getMAX_MANA(); i++) {
            players[turnIndex].getMana()[i].setManaState(Mana.ManaState.available);
            players[turnIndex].getMana()[i].setBitmap(players[turnIndex].getMana()[i].getManaType()[0]);
        }
    }

    private void updateAndCheckTurnTimeRemaining(float deltaTime) {
        turnTimeRemaining -= deltaTime; //decrements their turn time by the delta time
        if (isOutOfTurnTime())
            players[turnIndex].setPlayerCurrentState( Player.PlayerState.TURN_ENDED); //Checks if the player's turn is over
    }

    private boolean isOutOfTurnTime() {
        return turnTimeRemaining <= 0.f;
    }

    private void nextPlayersTurn() {
        repositionCards();
        players[turnIndex].setPlayerCurrentState(Player.PlayerState.WAITING_FOR_TURN);
        incrementTurnIndex();
        coin.setBitmap(coin.getCoinSides()[turnIndex]); //Just to test turns are working :)
        players[turnIndex].setPlayerCurrentState(Player.PlayerState.TURN_STARTED);
    }

    private void incrementTurnIndex() {
        this.turnIndex++;
        this.turnIndex %= PLAYER_COUNT;
    }

    public int findNextPlayer(int playerIndex) {
        return (playerIndex + 1) & PLAYER_COUNT;
    }

    public Player getPlayerWithCurrentTurn() {
        return players[turnIndex];
    }

    public GameState getCurrentGameState(){
        return  this.currentGameState;
    }

    public static float getTurnTime() {
        return TURN_TIME;
    }

    public static void setTurnTime(float turnTime) {
        TURN_TIME = turnTime;
    }

    public static float getCoinTossDelay() {
        return COIN_TOSS_DELAY;
    }

    public static int getPlayerCount() {
        return PLAYER_COUNT;
    }

    public static void setPlayerCount(int playerCount) {
        PLAYER_COUNT = playerCount;
    }

    public float getStartDelayTimeRemaining() {
        return startDelayTimeRemaining;
    }

    public void setStartDelayTimeRemaining(float startDelayTimeRemaining) {
        this.startDelayTimeRemaining = startDelayTimeRemaining;
    }

    public float getTurnTimeRemaining() {
        return turnTimeRemaining;
    }

    public void setTurnTimeRemaining(float turnTimeRemaining) {
        this.turnTimeRemaining = turnTimeRemaining;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Player getPlayer(int i) {
        return players[i];
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public int[] getManaCount() {
        return manaCount;
    }

    public void setManaCount(int[] manaCount) {
        this.manaCount = manaCount;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public int getTurnIndex() {
        return turnIndex;
    }

    public void setTurnIndex(int turnIndex) {
        this.turnIndex = turnIndex;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
