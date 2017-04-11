package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;

/**
 * Created by Michael on 06/03/2017.
 */

public class CourseOfHistoryMachine {
    private static float TURN_TIME = 6.f;
    private static float COIN_TOSS_DELAY = 5.f;

    private float startDelayTimeRemaining;
    private float turnTimeRemaining;
    public Player[] players;
    public int manaCount;
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
        this.manaCount = -1; //init to -1 because of += 2 each turn start
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
                break;

            case GAME_ACTIVE:
                takeTurn(deltaTime);
                updateCardsInPlay();
                checkPlayersStatus();
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


    public void checkPlayersStatus(){
        for (Player player: players) {
            if(player.playerDeck.size() == 0 && player.playerCurrentState == Player.PawnState.TURN_STARTED) { //TODO add if hero health is <= 0
                player.playerCurrentState = Player.PawnState.LOSE;
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
        if (players[turnIndex].playerDeck.size() != 0)
            players[turnIndex].board.cardHands[turnIndex].addToHand(players[turnIndex].drawCardFromDeck());
        manaCount += 2; //its decremented at the end of a turn, += 2 at the start of turn
        if(manaCount < players[turnIndex].MAX_MANA) //don't want it going over 10 - max
            giveManaToPlayer();
    }

    /**
     * At the start of every turn, the
     */
    private void giveManaToPlayer()
    {
        players[turnIndex].currentMana = manaCount;
        for(int i = 0; i < players[turnIndex].MAX_MANA; i++)
            players[turnIndex].mana[i].manaState = Mana.ManaState.available;
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
        manaCount--;
        coin.setImage(coin.coinSides[turnIndex]); //Just to test turns are working :)
        players[turnIndex].playerCurrentState = Player.PawnState.TURN_STARTED;
    }

    private void incrementTurnIndex() {
        this.turnIndex++;
        this.turnIndex %= 2;
    }

    private int findNextPlayer() {
        return (turnIndex + 1) & 2;
    }
}
