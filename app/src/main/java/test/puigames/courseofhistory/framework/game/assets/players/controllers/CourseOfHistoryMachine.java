package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Updateable;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.StartingHandSelector;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;

/**
 * Created by Michael on 06/03/2017.
 */

public class CourseOfHistoryMachine implements Updateable {
    private static float TURN_TIME = 20.f;
    private static float COIN_TOSS_DELAY = 3.f;
    private static int STARTING_HAND_SIZE = 3;

    private float startDelayTimeRemaining;
    private float turnTimeRemaining;
    public Player[] players;
    public GameState currentGameState;
    public int turnIndex;
    public Coin coin;
    public Board board;

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
    }

    public void startGame() {
        currentGameState = GameState.COIN_TOSS;
        //Tells the game it needs to make a coin toss
    }

    private void tossCoin() {
        coin.flipCoin();

        if(coin.faceUp == Coin.Result.HEADS)
            turnIndex = 0;
        else
            turnIndex = 1;

        coin.setBitmap(coin.coinSides[turnIndex]);
        currentGameState = GameState.GAME_ACTIVE;//To transition FSM to the game being active so turns are now being made
    }

    public void update(float deltaTime) {
        switch (currentGameState) {
            case CREATED:
                startDelayTimeRemaining -= deltaTime;
                if (startDelayTimeRemaining <= 0.f)
                    startGame();
                break;

            case COIN_TOSS:
                tossCoin();
                break;

            case CREATE_STARTING_HAND:
                if (players[turnIndex].playerCurrentState != Player.PawnState.CREATING_START_HAND)
                    createStartingHand(turnIndex);
                else
                    updateStartingHandCreation(turnIndex);
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

    private void updateStartingHandCreation(int turnIndex) {

    }

    public void createStartingHand(int playerIndex) {
        players[playerIndex].playerCurrentState = Player.PawnState.BEGIN_CREATING_STARTING_HAND;

        CharacterCard[] startingHand = new CharacterCard[STARTING_HAND_SIZE];
        for (int handIndex = 0; handIndex < STARTING_HAND_SIZE; handIndex++)
            startingHand[playerIndex] = players[playerIndex].drawCardFromDeck(); //Draws the amount of starting cards from the player's deck for each player

        players[playerIndex].startingHandSelector = new StartingHandSelector(startingHand); //Creates a new instance of starting hand selector for the player to use
    }

    private boolean isPlayerFinishedCreatingStartingHand(int playerIndex) {
        return (players[playerIndex].playerCurrentState == Player.PawnState.FINISHED_CREATING_START_HAND);
    }

    public void checkWinStatus(){
        for (int i = 0; i < players.length; i++) {
            if(players[i].playerDeck.size() == 0 && players[i].playerCurrentState == Player.PawnState.TURN_STARTED) { //TODO add if hero health is <= 0
                players[i].playerCurrentState = Player.PawnState.LOSE;
                players[findNextPlayer(i)].playerCurrentState = Player.PawnState.WIN;
                currentGameState = GameState.GG;
            }
        }
    }

    public void updateCardsInPlay() {
        for (Player player: players)
            for (CharacterCard card : player.board.playAreas[player.playerNumber].cardsInArea)
                if (card.isDeaders())
                    player.board.playAreas[player.playerNumber].removeCardFromArea(card);
        //Testing purposes only!
         /*  for (Player player: players)
            for (CharacterCard card : player.testCards)
                if (card.isDeaders())
                    card.height = 0;*/
    }

    public void takeTurn(float deltaTime) {
        //Turn being made
        switch (players[turnIndex].playerCurrentState) {
            case CREATED:
                players[turnIndex].playerCurrentState = Player.PawnState.TURN_STARTED;
                break;

            case TURN_STARTED:
                startTurn();
                players[turnIndex].playerCurrentState = Player.PawnState.TURN_ACTIVE;
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
        if (players[turnIndex].playerDeck.size() != 0) {
            players[turnIndex].startTurn();
            players[turnIndex].board.cardHands[turnIndex].addToHand(players[turnIndex].drawCardFromDeck());
        }

        for (CharacterCard card : players[turnIndex].board.playAreas[turnIndex].cardsInArea) {
            card.currentAttackEnergy = card.maxAttackEnergy;
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
        players[turnIndex].playerCurrentState = Player.PawnState.WAITING_FOR_TURN;
        incrementTurnIndex();
        coin.setBitmap(coin.coinSides[turnIndex]); //Just to test turns are working :)
        players[turnIndex].playerCurrentState = Player.PawnState.TURN_STARTED;
    }

    private void incrementTurnIndex() {
        this.turnIndex++;
        this.turnIndex %= 2;
    }

    public int findNextPlayer(int playerIndex) {
        return (playerIndex + 1) & 2;
    }
}
