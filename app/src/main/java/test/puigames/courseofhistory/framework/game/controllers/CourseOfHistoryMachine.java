package test.puigames.courseofhistory.framework.game.controllers;

import android.util.Log;

import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;

/**
 * Created by Michael on 06/03/2017.
 */

public class CourseOfHistoryMachine {
    private static float TURN_TIME = 6.f;
    private static float COIN_TOSS_DELAY = 5.f;

    private float startDelayTimeRemaining;
    private float turnTimeRemaining;
    public Player[] players;
    GameState currentGameState;
    int turnIndex;
    Coin coin;
    Board board;


    public enum GameState {
        CREATED, COIN_TOSS, GAME_ACTIVE, GAME_PAUSED, GG
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

        coin.setImage(coin.coinSides[turnIndex]);
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

            case GAME_ACTIVE:
                takeTurn(deltaTime);
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

    public void takeTurn(float deltaTime) {
        //Turn being made
        switch (players[turnIndex].playerCurrentState) {
            case CREATED:
                players[turnIndex].playerCurrentState = Player.PawnState.TURN_STARTED;
            case TURN_STARTED:
                startTurn();
                players[turnIndex].playerCurrentState = Player.PawnState.TURN_ACTIVE;

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
//                Log.d("startturn", "called");
//                board.cardHands[i].addToHand(players[i].drawCardFromDeck());
                players[turnIndex].board.cardHands[turnIndex].addToHand(players[turnIndex].drawCardFromDeck());
            }

    }

    private void updateAndCheckTurnTimeRemaining(float deltaTime) {
        turnTimeRemaining -= deltaTime; //decrements their turn time by the delta time
        checkTurnTimeRemaining(); //Checks if the player's turn is over
    }

    private void checkTurnTimeRemaining(){
        if (turnTimeRemaining <= 0.f) {
            nextPlayersTurn();
        }
    }

    private void nextPlayersTurn() {
        players[turnIndex].playerCurrentState = Player.PawnState.WAITING_FOR_TURN;
        incrementTurnIndex();
        coin.setImage(coin.coinSides[turnIndex]); //Just to test turns are working :)
        players[turnIndex].playerCurrentState = Player.PawnState.TURN_STARTED;
    }

    private void incrementTurnIndex() {
        this.turnIndex++;
        this.turnIndex %= 2;
    }
}
