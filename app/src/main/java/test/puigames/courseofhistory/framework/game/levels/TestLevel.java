package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.screen.Level;
import test.puigames.courseofhistory.framework.game.assets.Animation;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.CardGameController;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.CourseOfHistoryMachine;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.HumanCardGameController;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class TestLevel extends Level
{
    public static int MAX_PLAYERS = 2;
    private String[] deckNames = {"greatMindsCards", "evilLeaderCards"};
    private Animation animation;
    private Bitmap explodeAnimation;
    private Board board;

    HumanCardGameController controllers[];
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

           //Setting up the players and controllers
           controllers = new HumanCardGameController[MAX_PLAYERS];
           Player[] players = new Player[MAX_PLAYERS];
           for(int i = 0; i < controllers.length; i++) {
               controllers[i] = new HumanCardGameController();
               players[i] = new Player(resourceFetcher.loadCharacterCards(deckNames[i]), board, i); //Creating a new player pawn for each controller
               controllers[i].possessPlayer(players[i]); //Giving the player controller a pawn to manipulate for the game

               for(int playersDeckCardIndex = 0; playersDeckCardIndex < players[i].playerDeck.size(); playersDeckCardIndex++)
                   //spawning the cards in from each players deck
                   spawnSprite((CharacterCard)players[i].playerDeck.get(playersDeckCardIndex),(float) Math.random() * 1000, (float) Math.random() * 1000);
           }

           //creating the game machine for turns :)
           gameMachine = new CourseOfHistoryMachine(players, coin);
       } catch(NullPointerException e) {
           Log.d("Loading Error:", "Error fetching resources, returning to menu");
           //TODO do properly
           //Failed loading the gameProperties - won't cause crash if resources set up wrong!
           gameProperties.setScreen(new SplashScreen(this.gameProperties));
       }
    }

    public void updateControllers(float deltaTime) {
        for (CardGameController controller: controllers)
            try {
                if (controller instanceof HumanCardGameController)
                    ((HumanCardGameController)controller).update(inputBuddy, deltaTime);
               // else if (controller instanceof AICardGameController)
                    //((AICardGameController)controller).update(deltaTime);
            } catch (Exception e) {
                Log.d("Updating Controller: ", "Error processing controller " + controller);
            }
    }


    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);
        updateControllers(deltaTime); //Should be called before the game machine is updated
        gameMachine.update(deltaTime);
    }

    private void collisionCheckAndResolve(int turnIndex)
    {
        for(CharacterCard card : controllers[turnIndex].player.testCards)
        {
            for(CharacterCard card2 : controllers[turnIndex].player.testCards)
            {
                if(card.checkForCollision(card2))
                    card.resolveCollision(card2, card.overlapAllowance);
            }
        }
    }



    @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void spawn(Sprite sprite) {

    }

    @Override
    public void dispose() {

    }
}
