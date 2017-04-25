package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.Controlling.Possessor;
import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.screen.Level;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;
import test.puigames.courseofhistory.framework.engine.ui.imageUIElement;
import test.puigames.courseofhistory.framework.game.assets.Animation;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.CourseOfHistoryMachine;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.HumanCardGameController;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class TestLevel extends Level
{
    public static int MAX_PLAYERS = 2;
    private final String[] DECK_NAMES = {"greatMindsCards", "evilLeaderCards"};
    private final String[] TEST_CARD_NAMES = {"cards1", "cards2"};
    private Animation animation;
    private Bitmap explodeAnimation;
    private Board board;

    Possessor controllers[];
    CourseOfHistoryMachine gameMachine;

    public TestLevel(GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    public void load() {
       try {
           //Setting up the board and spawning it
           board = resourceFetcher.loadBoard("testBoard");
           spawnSprite(board, 480/2, 320/2);

           //Setting up the coin and spawning it
           Bitmap coinSides[] = {resourceFetcher.getBitmapFromFile("images/coins/coin-heads.png"), resourceFetcher.getBitmapFromFile("images/coins/coin-tails.png")};
           Coin coin = new Coin(coinSides, 80, 80);
           spawnSprite(coin, 300, 200);

           //Load mana images
           Bitmap manaTypes[] = {resourceFetcher.getBitmapFromFile("images/mana/mana.png"),
                   resourceFetcher.getBitmapFromFile("images/mana/mana-used.png")};

           //Setting up the players and controllers
           controllers = new HumanCardGameController[MAX_PLAYERS];
           Player[] players = new Player[MAX_PLAYERS];
           for(int i = 0; i < controllers.length; i++) {
               controllers[i] = new HumanCardGameController(gameProperties.getInput());
               players[i] = new Player(resourceFetcher.loadCharacterCards(DECK_NAMES[i]), board, i); //Creating a new player pawn for each controller
               controllers[i].possessPlayer(players[i]); //Giving the player controller a pawn to manipulate for the game

               /*for(int playersDeckCardIndex = 0; playersDeckCardIndex < players[i].playerDeck.size(); playersDeckCardIndex++)
                   //spawning the cards in from each players deck
                   spawnSprite((CharacterCard)players[i].playerDeck.get(playersDeckCardIndex),(float) Math.random() * 1000, (float) Math.random() * 1000);*/


               for(int j = 0; j < players[i].MAX_MANA; j++)
                   players[i].mana[j] = new Mana(manaTypes);
           }

           //creating the game machine for turns :)
           gameMachine = new CourseOfHistoryMachine(players, coin, board);
       } catch(NullPointerException e) {
//           Log.d("Loading Error:", "Error fetching resources, returning to menu");
           Log.e("ERROR", e.getMessage() + "\n" + e.getCause());
           //TODO do properly
           //Failed loading the gameProperties - won't cause crash if resources set up wrong!
           gameProperties.setScreen(new SplashScreen(this.gameProperties));
       }
    }

    public void updateControllers(float deltaTime) {
        for (Possessor controller: controllers)
            controller.update(deltaTime);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        updateControllers(deltaTime); //Should be called before the game machine is updated
        gameMachine.update(deltaTime);
        for(int i = 0; i < gameMachine.players.length; i++){
            gameMachine.players[i].board.cardHands[i].update(deltaTime);
            for(int j = 0; j < gameMachine.players[i].board.cardHands[i].cardsInArea.size(); j++){
                gameMachine.players[i].board.cardHands[i].cardsInArea.get(j).update(deltaTime);
            }
            /*
            for(int j = 0; j < gameMachine.players[i].cardHand.cardsInArea.size(); j++){
                gameMachine.players[i].cardHand.cardsInArea.get(j).update(deltaTime);
            }*/
        }
    }


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
    }

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
