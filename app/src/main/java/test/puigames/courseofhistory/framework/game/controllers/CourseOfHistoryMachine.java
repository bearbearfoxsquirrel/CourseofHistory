package test.puigames.courseofhistory.framework.game.controllers;

import test.puigames.courseofhistory.framework.game.assets.Coin;
/**
 * Created by Michael on 06/03/2017.
 */

public class CourseOfHistoryMachine {
    public static int TURN_TIME = 1000000;

    private int turnTimeRemaining;
    Player[] players;
    GameState currentGameState;
    int turnIndex;
    Coin coin;


    public enum GameState {
        CREATED, COIN_TOSS, GAME_ACTIVE, GAME_PAUSED, GG
    }

    public CourseOfHistoryMachine(Player[] players){
        this.players = players;
        currentGameState = GameState.CREATED;
    }

    public void startGame() {
        //TODO implement
        turnIndex = 1;
        currentGameState = GameState.COIN_TOSS;
        //Coin toss and decide which player should go first
    }

    public void takeTurns() {
        //Turns being made
        switch (players[turnIndex].playerCurrentState) {
            case TURN_STARTED:
                players[turnIndex].drawCardFromDeck();
                break;
            case TURN_ENDED:
                updateTurnIndex();
                break;
        }
    }



    public void update(float deltaTime) {
        //TODO update turnTimeRemaining
       // checkCurrentGameState();
        switch (currentGameState) {
            case CREATED:
                startGame();
                break;
            case COIN_TOSS:
                //tossCoin();
            case GAME_ACTIVE:
                takeTurns();
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

    public void updateTurnIndex() {
        this.turnIndex++;
        this.turnIndex %= 2;
    }

    public void makePlayerAction() {

    }


}
