package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Arrays;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.screen.Level;
import test.puigames.courseofhistory.framework.game.PlayArea;
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
    private Board board;
    private CharacterCard[] testCards;

    HumanCardGameController controllers[];

    private Coin coin;
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
           this.board = resourceFetcher.loadBoard("testBoard");
           sprites.add(board);

           this.testCards = resourceFetcher.loadCharacterCards();
           sprites.addAll(Arrays.asList(testCards));

           coin = new Coin(resourceFetcher.getBitmapFromFile("images/coins/coin-heads.png"),
                   240, 160, 80, 80);
           sprites.add(coin);

           //for (CardGameController contestant : cardGameControllers)
           controllers = new HumanCardGameController[MAX_PLAYERS];
           Player[] players = new Player[MAX_PLAYERS];

           for(int i = 0; i < controllers.length; i++) {
               players[i] = new Player(testCards);
               controllers[i] = new HumanCardGameController();
               controllers[i].possessPlayer(players[i]);
           }


           gameMachine = new CourseOfHistoryMachine(players);


           //play area
           playArea = new PlayArea(board.boundingBox.left + areaPaddingX, board.halfWidth + areaPaddingY,
                   190, 140);

           gameMachine.startGame();
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
                Log.d("Updating Controller: ", "Error processing controller " + controller.toString());
            }
    }


    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);

        updateControllers(deltaTime); //Should be called before the game machine is updated
        gameMachine.update(deltaTime);

        decideTurn();
        scaler.scaleToScreen(playArea);
        playArea.update(inputBuddy, deltaTime, testCards);
    }


    private void decideTurn()
    {
        if(coin.faceUp == Coin.Result.HEADS)
            coin.setImage(resourceFetcher.getBitmapFromFile("images/coins/coin-heads.png"));
        else
            coin.setImage((resourceFetcher.getBitmapFromFile("images/coins/coin-tails.png")));
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    public void spawn(Sprite sprite) {

    }

    @Override
    public void dispose()
    {

    }
}
