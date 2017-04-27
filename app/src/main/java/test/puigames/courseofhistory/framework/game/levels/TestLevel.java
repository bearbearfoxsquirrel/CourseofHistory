package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Bitmap;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.controlling.Controlling;
import test.puigames.courseofhistory.framework.engine.screen.Level;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.Deck;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.CourseOfHistoryMachine;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.HumanCardGameController;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.StartingHandSelectionUI;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class TestLevel extends Level {
    private final String[] DECK_NAMES = {"greatMindsCards", "evilLeaderCards"};
    private final String[] TEST_CARD_NAMES = {"cards1", "cards2"};
    StartingHandSelectionUI startingHandSelectionUI;

    CourseOfHistoryMachine gameMachine;

    public TestLevel(GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    @Override
    public void load() {
       try {
           //Setting up the board and spawning it
           Board board = resourceFetcher.loadBoard(this, "testBoard");
           board.place(this, 480/2, 320/2);

           //Setting up the coin and spawning it
           Bitmap coinSides[] = {resourceFetcher.getBitmapFromFile("images/coins/coin-heads.png"), resourceFetcher.getBitmapFromFile("images/coins/coin-tails.png")};
           Coin coin = new Coin(this, coinSides, 80, 80);
           coin.place(this, 300, 200);

           Player[] players = new Player[CourseOfHistoryMachine.PLAYER_COUNT];

           //Load mana images
           Bitmap manaTypes[] = {resourceFetcher.getBitmapFromFile("images/mana/mana.png"),
                   resourceFetcher.getBitmapFromFile("images/mana/mana-used.png")};

           //Creates a controller and a player for each participant
           for(int i = 0; i < players.length; i++) {
               players[i] = new Player(resourceFetcher.loadCharacterCards(this, DECK_NAMES[i]), board, new Deck(this, resourceFetcher.getBitmapFromFile("images/splashscreen/splash.png")), i); //Creating a new player pawn for each controller
               players[i].playerDeck.place(this, 300, 50);
               //TODO give proper deck image!!!

               for (int j = 0; j < players[i].MAX_MANA; j++)
                   players[i].mana[j] = new Mana(this, manaTypes);
           }

           //creating the game machine for processing the game
           gameMachine = new CourseOfHistoryMachine(players, coin, board);
           gameMachine.startTicking(this);

           Controlling[] controllers = new HumanCardGameController[CourseOfHistoryMachine.PLAYER_COUNT];
           //Giving the controllers possession of the corresponding player in the game machine
           for (int i = 0; i < controllers.length; i++) {
               controllers[i] = new HumanCardGameController(this, inputBuddy, gameMachine.players[i], resourceFetcher.getBitmapFromFile("images/backgrounds/starting_hand_selection_ui_background.png"), resourceFetcher.getBitmapFromFile("images/buttons/confirmation_button.png"), resourceFetcher.getBitmapFromFile("images/buttons/end_turn_button.png"));
               controllers[i].startTicking(this);
           }

           //Create the starting hand selection UI for each human player
        //   startingHandSelectionUIs = new StartingHandSelectionUI[gameMachine.players.length];
          // for (int i = 0; i < players.length; i++)
           //    startingHandSelectionUIs[i] = new StartingHandSelectionUI(this, gameMachine.players[i], resourceFetcher.getBitmapFromFile("images/backgrounds/starting_hand_selection_ui_background.png"), resourceFetcher.getBitmapFromFile("images/buttons/confirmation_button.png"));

       } catch(NullPointerException e) {
//           Log.d("Loading Error:", "Error fetching resources, returning to menu");
           Log.e("ERROR", e.getMessage() + "\n" + e.getCause());
           //TODO do properly
           //Failed loading the gameProperties - won't cause crash if resources set up wrong!
           gameProperties.setScreen(new SplashScreen(this.gameProperties));
       }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
/*
        if (gameMachine.getCurrentGameState() == CourseOfHistoryMachine.GameState.CREATE_STARTING_HAND)
            switch (gameMachine.getPlayerWithCurrentTurn().playerCurrentState) {
                case BEGIN_CREATING_STARTING_HAND:
                    startingHandSelectionUI = new StartingHandSelectionUI(this, gameMachine.getPlayerWithCurrentTurn(),
                            resourceFetcher.getBitmapFromFile("images/backgrounds/starting_hand_selection_ui_background.png"),
                            resourceFetcher.getBitmapFromFile("images/buttons/confirmation_button.png"));
                    startingHandSelectionUI.place(this, viewport.centerX, viewport.centerY);
            }*/
    }

  /*  public void update(float deltaTime) {
        super.update(deltaTime);
      //  updateControllers(deltaTime); //Should be called before the game machine is updated
        //gameMachine.update(deltaTime);

       for(int i = 0; i < gameMachine.players.length; i++){
            gameMachine.players[i].board.cardHands[i].update(deltaTime);
            for(int j = 0; j < gameMachine.players[i].board.cardHands[i].cardsInArea.size(); j++){
                gameMachine.players[i].board.cardHands[i].cardsInArea.get(j).update(deltaTime);
               // if (
               // gameMachine.players[i].playerCurrentState == Player.PlayerState.CREATING_START_HAND)
                 //   drawables.add()
            }



            for(int j = 0; j < gameMachine.players[i].cardHand.cardsInArea.size(); j++){
                gameMachine.players[i].cardHand.cardsInArea.get(j).update(deltaTime);
            }
        }

    }/*

    private void collisionCheckAndResolve() {
        for(CharacterCard card : controllers[gameMachine.turnIndex].getPlayer().board.playAreas[controllers[gameMachine.turnIndex].getPlayer().playerNumber].cardsInArea)
        {
            for(CharacterCard card2 : controllers[gameMachine.turnIndex].getPlayer().board.playAreas[controllers[gameMachine.findNextPlayer(gameMachine.turnIndex)].getPlayer().playerNumber].cardsInArea)
            {
                if(card.boundingBox.getCollisionDetector().checkForCollision(card, card2))
                    card.boundingBox.getCollisionDetector().resolveCollision(card, card2, card.overlapAllowance);
            }
        }
    }

  /*  @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
        /*for(int i = 0; i < gameMachine.players.length; i++) {

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
        // FIXME: 21/04/2017 pls make me draw things better
        for(int i = 0; i < gameMachine.players.length; i++) {
            for (int j = 0; j < gameMachine.players[i].board.cardHands[i].cardsInArea.size(); j++) {
                scaler.scaleToScreen(gameMachine.players[i].board.cardHands[i].cardsInArea.get(j));
                gameMachine.players[i].board.cardHands[i].cardsInArea.get(j).draw(canvas, deltaTime);
            }
//            for(int j = 0; j < gameMachine.players[i].board.playAreas[i].cardsInArea.size(); j++) {
//                scaler.scaleToScreen(gameMachine.players[i].board.playAreas[i].cardsInArea.get(j));
//                gameMachine.players[i].board.cardHands[i].cardsInArea.get(j).draw(canvas, deltaTime);
//            }
        }
    }*/

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
