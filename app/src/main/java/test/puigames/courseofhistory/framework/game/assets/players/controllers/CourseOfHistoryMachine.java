package test.puigames.courseofhistory.framework.game.assets.players.controllers;

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
    private static float TURN_TIME = 20.f;
    //initial turn
    private static final float COIN_TOSS_DELAY = 3.f;
    private static int PLAYER_COUNT = 2;
    private float startDelayTimeRemaining;
    private float turnTimeRemaining;
    private Player[] players;
    private int[] manaCount;
    private GameState currentGameState;
    private int turnIndex;
    private Coin coin;
    private Board board;


    public enum GameState {
        CREATED, COIN_TOSS, CREATE_STARTING_HAND, GAME_ACTIVE, GAME_PAUSED, GG
    }

    public CourseOfHistoryMachine(Player[] players, Coin coin, Board board) {
        this.players = players;
        this.coin = coin;
        this.currentGameState = GameState.CREATED;
        this.startDelayTimeRemaining = COIN_TOSS_DELAY;
        this.turnTimeRemaining = TURN_TIME;
        this.board = board;
        this.manaCount = new int[players.length];
        this.manaCount[0] = 0;  this.manaCount[1] = 0;
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
                currentGameState = GameState.CREATE_STARTING_HAND;//To transition FSM to the game being active so turns are now being made

                for (Player player : players)
                    player.setPlayerCurrentState(Player.PlayerState.WAITING_TO_BEGIN_CREATING_HAND);
                break;

            case CREATE_STARTING_HAND:
                /*
                transitionPlayerStatesFromCreatingHandToTurnStates();
                currentGameState = GameState.GAME_ACTIVE;
                //TO SKIP THIS STAGE OF THE GAME UNCOMMENT THIS BLOCK
                */

                if (isBothPlayersFinishedCreatingStartHand()) {
                    nextPlayersTurn(); //get back to original players turn
                    //If both players are finished taking their turn it selects the player that won the coin toss and lets them go first
                    transitionPlayerStatesFromCreatingHandToTurnStates();
                    currentGameState = GameState.GAME_ACTIVE;
                }

                //Handles each player creating their hand and checking if they are finished making their hand
                switch (players[turnIndex].getPlayerCurrentState()) {
                    case WAITING_FOR_TURN:
                        players[turnIndex].setPlayerCurrentState(Player.PlayerState.BEGIN_CREATING_STARTING_HAND);
                    case WAITING_TO_BEGIN_CREATING_HAND:
                        players[turnIndex].createNewStartingHand();
                        break;

                    case FINISHED_CREATING_START_HAND:
                        nextPlayersTurn();
                        players[turnIndex].setPlayerCurrentState(Player.PlayerState.BEGIN_CREATING_STARTING_HAND);
                        break;
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

    private void checkWinStatus(){
        for (int i = 0; i < players.length; i++) {
            if(players[i].getPlayerDeck().size() == 0 && players[i].getPlayerCurrentState() == Player.PlayerState.TURN_STARTED) { //TODO add if hero health is <= 0
                players[i].setPlayerCurrentState(Player.PlayerState.LOSE);
                players[findNextPlayer(i)].setPlayerCurrentState(Player.PlayerState.WIN);
                currentGameState = GameState.GG;
            }
        }
    }

    private void updateCardsInPlay() {
        for (Player player: players)
            for (CharacterCard card : player.getBoard().getPlayAreas()[player.getPlayerNumber()].getCardsInArea())
                if (card.isDeaders())
                    player.getBoard().getPlayAreas()[player.getPlayerNumber()].removeCardFromArea(card);
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
                nextPlayersTurn(); //Changes turn index to the next player
                break;
        }
    }

    private void startTurn() {
        turnTimeRemaining = TURN_TIME;
        if (players[turnIndex].getPlayerDeck().size() != 0) {
            players[turnIndex].startTurn();
            players[turnIndex].getBoard().getCardHands()[turnIndex].addToHand(players[turnIndex].drawCardFromDeck());
        }

        for (CharacterCard card : players[turnIndex].getBoard().getPlayAreas()[turnIndex].getCardsInArea()) {
            card.setCurrentAttackEnergy(card.getMaxAttackEnergy());

            if (manaCount[turnIndex] < players[turnIndex].getMAX_MANA()) //don't want it going over 10 - max
            {
                manaCount[turnIndex]++;
                giveManaToPlayer();
            }
        }
    }

    /**
     * At the start of every turn, give player +1 available mana than last turn
     * Change mana bitmaps to reflect that
     */
    private void giveManaToPlayer()
    {
        players[turnIndex].setCurrentMana(manaCount[turnIndex]);
        for(int i = 0; i < players[turnIndex].getMAX_MANA(); i++)
        {
            players[turnIndex].getMana()[i].setManaState(Mana.ManaState.available);
            players[turnIndex].getMana()[i].setBitmap(players[turnIndex].getMana()[i].getManaType()[0]);
        }
    }

    private void updateAndCheckTurnTimeRemaining(float deltaTime) {
        turnTimeRemaining -= deltaTime; //decrements their turn time by the delta time
        if (isTurnTimeLeft())
            nextPlayersTurn(); //Checks if the player's turn is over
    }

    private boolean isTurnTimeLeft(){
        return turnTimeRemaining <= 0.f;
    }

    private void nextPlayersTurn() {
        players[turnIndex].setPlayerCurrentState(Player.PlayerState.WAITING_FOR_TURN);
        incrementTurnIndex();
        coin.setBitmap(coin.getCoinSides()[turnIndex]); //Just to test turns are working :)
        players[turnIndex].setPlayerCurrentState(Player.PlayerState.TURN_STARTED);
    }

    private void incrementTurnIndex() {
        this.turnIndex++;
        this.turnIndex %= 2;
    }

    public int findNextPlayer(int playerIndex) {
        return (playerIndex + 1) & 2;
    }

    public Player
    getPlayerWithCurrentTurn() {
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
