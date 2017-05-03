package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Bitmap;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.controlling.Controlling;
import test.puigames.courseofhistory.framework.engine.screen.Level;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.Deck;
import test.puigames.courseofhistory.framework.game.assets.Hero;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.StartingHandSelectionUI;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.CourseOfHistoryMachine;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.HumanCardGameController;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class TestLevel extends Level {
    private final String[] DECK_NAMES = {"greatMindsCards", "evilLeaderCards"};
    private final String[] TEST_CARD_NAMES = {"cards1", "cards2"}; //TODO: remove this maybe perhaps possibly
    StartingHandSelectionUI startingHandSelectionUI;
    private final int COIN_SIZE = 80;
    private final float COIN_POS_X = 300.f;
    private final float COIN_POS_Y = 200.0f;
    private final int HERO_PORTRAIT_SIZE = 55;
    private final float HERO_PLAYER_1_POS_X = 20.f;
    private final float HERO_PLAYER_1_POS_Y = 300.f;
    private final float HERO_PLAYER_2_POS_X = 460.f;
    private final float HERO_PLAYER_2_POS_Y = 20.f;
    private final int TOP_PLAYER_ROTATION = 180;
    private final float PLAYER_DECK_POS_X = 300;
    private final float PLAYER_DECK_POS_Y = 50;

    CourseOfHistoryMachine gameMachine;

    public TestLevel(GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    @Override
    public void load() {
       try {

           Bitmap[] heroBitmaps = {
                   resourceFetcher.getBitmapFromFile("images/heroes/great-minds-hero.png"),
                   resourceFetcher.getBitmapFromFile("images/heroes/evil-leaders-hero.png")};

//           //Setting up the board and spawning it
//           Board board = resourceFetcher.loadBoard(this, "testBoard");
//           board.place(this, viewport.getCenterX(), viewport.getCenterY());

           //Setting up the coin and spawning it
           Bitmap[] coinSides = {
                   resourceFetcher.getBitmapFromFile("images/coins/coin-heads.png"),
                   resourceFetcher.getBitmapFromFile("images/coins/coin-tails.png")};
           Coin coin = new Coin(this, coinSides, COIN_SIZE, COIN_SIZE);
           coin.place(this, COIN_POS_X, COIN_POS_Y);

           Player[] players = new Player[CourseOfHistoryMachine.getPlayerCount()];
           Hero[] heroes = new Hero[CourseOfHistoryMachine.getPlayerCount()];
           for(int i = 0; i < players.length; i++)
               heroes[i] = new Hero(this, heroBitmaps[i], HERO_PORTRAIT_SIZE, HERO_PORTRAIT_SIZE); //create heroes

           //Setting up the board and spawning it
           Board board = resourceFetcher.loadBoard(this, "testBoard", heroes);
           board.place(this, viewport.getCenterX(), viewport.getCenterY());

           //Load mana images
           Bitmap[] manaTypes = {
                   resourceFetcher.getBitmapFromFile("images/mana/mana.png"),
                   resourceFetcher.getBitmapFromFile("images/mana/mana-used.png")};

//           //Load hero images
//           Bitmap[] heroBitmaps = {
//                   resourceFetcher.getBitmapFromFile("images/heroes/great-minds-hero.png"),
//                   resourceFetcher.getBitmapFromFile("images/heroes/evil-leaders-hero.png")};

           //Creates a controller and a player for each participant
           for(int i = 0; i < players.length; i++) {
//               heroes[i] = new Hero(this, heroBitmaps[i], HERO_PORTRAIT_SIZE, HERO_PORTRAIT_SIZE);
//               players[i] = new Player(resourceFetcher.loadCharacterCards(this, DECK_NAMES[i]), board, new Deck(this, resourceFetcher.getBitmapFromFile("images/splashscreen/splash.png")), heroes[i], i); //Creating a new player pawn for each controller
               players[i] = new Player(resourceFetcher.loadCharacterCards(this, DECK_NAMES[i]), board, new Deck(this, resourceFetcher.getBitmapFromFile("images/splashscreen/splash.png")), i);
               players[i].getPlayerDeck().place(this, PLAYER_DECK_POS_X, PLAYER_DECK_POS_Y);
               //TODO give proper deck image!!!

               for (int j = 0; j < players[i].getMAX_MANA(); j++)
                   players[i].getMana()[j] = new Mana(this, manaTypes);
           }
           heroes[0].place(this, HERO_PLAYER_1_POS_X, HERO_PLAYER_1_POS_Y); //player 1 - bottom left
           heroes[1].setRotation(TOP_PLAYER_ROTATION); //set rotation for opposite side of screen
           heroes[1].place(this, HERO_PLAYER_2_POS_X, HERO_PLAYER_2_POS_Y); //player 2 - top right

           //creating the game machine for processing the game
           gameMachine = new CourseOfHistoryMachine(players, coin, board);
           gameMachine.startTicking(this);

           Controlling[] controllers = new HumanCardGameController[CourseOfHistoryMachine.getPlayerCount()];
           //Giving the controllers possession of the corresponding player in the game machine
           for (int i = 0; i < controllers.length; i++) {
               controllers[i] = new HumanCardGameController(this, inputBuddy, gameMachine.getPlayers()[i]);
               controllers[i].startTicking(this);
           }

           //Create the starting hand selection UI for each human player
        //   startingHandSelectionUIs = new StartingHandSelectionUI[gameMachine.players.length];
          // for (int i = 0; i < players.length; i++)
           //    startingHandSelectionUIs[i] = new StartingHandSelectionUI(this, gameMachine.players[i], resourceFetcher.getBitmapFromFile("images/backgrounds/starting_hand_selection_ui_background.png"), resourceFetcher.getBitmapFromFile("images/buttons/confirmation_button.png"));

       } catch(NullPointerException e) {
           Log.e("ERROR", e.getMessage() + "\n" + e.getCause());
           //TODO do properly
           //Failed loading the gameProperties - won't cause crash if resources set up wrong!
           gameProperties.setScreen(new SplashScreen(this.gameProperties));
       }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (gameMachine.getCurrentGameState() == CourseOfHistoryMachine.GameState.CREATE_STARTING_HAND)
            switch (gameMachine.getPlayerWithCurrentTurn().getPlayerCurrentState()) {
                case BEGIN_CREATING_STARTING_HAND:
                    startingHandSelectionUI = new StartingHandSelectionUI(this, gameMachine.getPlayerWithCurrentTurn(),
                            resourceFetcher.getBitmapFromFile("images/backgrounds/starting_hand_selection_ui_background.png"),
                            resourceFetcher.getBitmapFromFile("images/buttons/confirmation_button.png"));
                    startingHandSelectionUI.place(this, viewport.getCenterX(), viewport.getCenterY());
            }
    }


   /* public void updateControllers(float deltaTime) {
        for (Possessor controller: controllers)
            controller.update(deltaTime);
    }*/

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

  /*  @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
        /*for(int i = 0; i < gameMachine.players.length; i++) {

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
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
