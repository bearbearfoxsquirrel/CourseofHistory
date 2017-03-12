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

    public void initialiseGame() {
        //TODO implement
    }

    public void update() {
        //TODO update turnTimeRemaining
       // checkCurrentGameState();
        switch (currentGameState) {
            case CREATED:
                turnIndex = 1;
                currentGameState = GameState.COIN_TOSS;
                //Coin toss and decide which player should go first
                break;
            case COIN_TOSS:
                //tossCoin();
            case GAME_ACTIVE:
                //Turns being made
                switch (players[turnIndex].getCurrentAction()) {
                    case PLACE_CARD_ON_BOARD:
                        players[turnIndex].placeCardOnBoard();
                    case END_TURN:
                        updateTurnIndex();
                        break;
                }
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
