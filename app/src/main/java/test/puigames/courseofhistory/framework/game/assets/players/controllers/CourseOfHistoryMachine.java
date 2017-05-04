package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Updateable;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.CardHand;
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
    public Player[] players;
    public int[] manaCount;
    public GameState currentGameState;
    int turnIndex;
    private Coin coin;
    public Board board;

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
        this.manaCount[0] = 0;  this.manaCount[1] = 0;
    }

    private void startGame() {
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
                    player.playerCurrentState = Player.PlayerState.WAITING_TO_BEGIN_CREATING_HAND;
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
                    switch (players[turnIndex].playerCurrentState) {
                        case BEGIN_CREATING_STARTING_HAND:
                            players[turnIndex].createNewStartingHand();
                            players[turnIndex].playerCurrentState = Player.PlayerState.STARTING_HAND_CHOOSING_CARDS_TO_TOSS;
                            break;

                        case WAITING_TO_BEGIN_CREATING_HAND:
                            players[turnIndex].playerCurrentState = Player.PlayerState.BEGIN_CREATING_STARTING_HAND;
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
                //TODO check who wins
                break;
        }
    }

    private void transitionPlayerStatesFromCreatingHandToTurnStates() {
        for (int i = 0; i < players.length; i++) {
            if (i == turnIndex)
                players[i].playerCurrentState = Player.PlayerState.TURN_STARTED;
            else
                players[i].playerCurrentState = Player.PlayerState.WAITING_FOR_TURN;
        }
    }

    private boolean isBothPlayersFinishedCreatingStartHand() {
        for (int i = 0; i < players.length; i++)
            if (!isPlayerFinishedCreatingStartingHand(i))
                return false;
        return true;
    }

    private boolean isPlayerFinishedCreatingStartingHand(int playerIndex) {
        return (players[playerIndex].playerCurrentState == Player.PlayerState.FINISHED_CREATING_START_HAND);
    }

    private void checkWinStatus(){
        for (int i = 0; i < players.length; i++) {
            if(players[i].playerDeck.size() == 0 && players[i].playerCurrentState == Player.PlayerState.TURN_STARTED) { //TODO add if hero health is <= 0
                players[i].playerCurrentState = Player.PlayerState.LOSE;
                players[findNextPlayer(i)].playerCurrentState = Player.PlayerState.WIN;
                currentGameState = GameState.GG;
            }
        }
    }

    private void updateCardsInPlay() {
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

    private void takeTurn(float deltaTime) {
        //Turn being made
        switch (players[turnIndex].playerCurrentState) {
            case CREATED:
                players[turnIndex].playerCurrentState = Player.PlayerState.TURN_STARTED;
                break;

            case TURN_STARTED:
                startTurn();
                players[turnIndex].playerCurrentState = Player.PlayerState.TURN_ACTIVE;
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
            players[turnIndex].board.cardHands[turnIndex].addCardToArea(players[turnIndex].drawCardFromDeck());
        }

        for (CharacterCard card : players[turnIndex].board.playAreas[turnIndex].cardsInArea) {
            card.currentAttackEnergy = card.maxAttackEnergy;

            if (manaCount[turnIndex] < players[turnIndex].MAX_MANA) //don't want it going over 10 - max
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
        players[turnIndex].currentMana = manaCount[turnIndex];
        for(int i = 0; i < players[turnIndex].MAX_MANA; i++)
        {
            players[turnIndex].mana[i].manaState = Mana.ManaState.available;
            players[turnIndex].mana[i].setBitmap(players[turnIndex].mana[i].manaType[0]);
        }
    }

    private void updateAndCheckTurnTimeRemaining(float deltaTime) {
        turnTimeRemaining -= deltaTime; //decrements their turn time by the delta time
        if (isOutOfTurnTime())
            players[turnIndex].playerCurrentState = Player.PlayerState.TURN_ENDED; //Checks if the player's turn is over
    }

    private boolean isOutOfTurnTime(){
        return turnTimeRemaining <= 0.f;
    }

    private void nextPlayersTurn() {
        players[turnIndex].playerCurrentState = Player.PlayerState.WAITING_FOR_TURN;
        incrementTurnIndex();
        coin.setBitmap(coin.coinSides[turnIndex]); //Just to test turns are working :)
        players[turnIndex].playerCurrentState = Player.PlayerState.TURN_STARTED;
    }

    private void incrementTurnIndex() {
        this.turnIndex++;
        this.turnIndex %= 2;
    }

    public int findNextPlayer(int playerIndex) {
        return (playerIndex + 1) & 2;
    }

    public Player getPlayerWithCurrentTurn() {
        return players[turnIndex];
    }

    public GameState getCurrentGameState(){
        return  this.currentGameState;
    }
}
