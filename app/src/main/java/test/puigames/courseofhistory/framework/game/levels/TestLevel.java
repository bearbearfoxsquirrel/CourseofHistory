package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.screen.Level;
import test.puigames.courseofhistory.framework.game.PlayArea;
import test.puigames.courseofhistory.framework.game.assets.Animation;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.controllers.CardGameController;
import test.puigames.courseofhistory.framework.game.controllers.CourseOfHistoryMachine;
import test.puigames.courseofhistory.framework.game.controllers.HumanCardGameController;
import test.puigames.courseofhistory.framework.game.controllers.Player;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class TestLevel extends Level
{
    public static int MAX_PLAYERS = 2;
    public static int MAX_DECK_SIZE = 36;
   // private Board board;
   // private CharacterCard[] testCards;
    private CharacterCard[] evilLeaderCards = new CharacterCard[36];
    private CharacterCard[] greatMindsCards = new CharacterCard[36];
    private CharacterCard[][] cardsHolder = {evilLeaderCards, greatMindsCards};
    private String[] deckNames = {"greatMindsCards", "evilLeaderCards"};
    private Animation animation;
    private Bitmap explodeAnimation;

    HumanCardGameController controllers[];

    //private Coin coin;
    private PlayArea playArea;
    private float areaPaddingX = 10.0f;
    private float areaPaddingY = 6.0f;
    CourseOfHistoryMachine gameMachine;

    public TestLevel(GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    public void load() {
       try {
           Board board = resourceFetcher.loadBoard("testBoard");
           sprites.add(board);

           Bitmap coinSides[] = {resourceFetcher.getBitmapFromFile("images/coins/coin-heads.png"), resourceFetcher.getBitmapFromFile("images/coins/coin-tails.png")};
           Coin coin = new Coin(coinSides, 80, 80);

           sprites.add(coin);

           controllers = new HumanCardGameController[MAX_PLAYERS];
           Player[] players = new Player[MAX_PLAYERS];

           for(int i = 0; i < controllers.length; i++) {
               switch (i) {
                   case 0:
                       players[i] = new Player(greatMindsCards, board);
                       break;
                   case 1:
                       players[i] = new Player(evilLeaderCards, board);

               }
               controllers[i] = new HumanCardGameController();
               controllers[i].possessPlayer(players[i]);
           }


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
