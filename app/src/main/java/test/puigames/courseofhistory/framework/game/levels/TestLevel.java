package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Arrays;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.game.controllers.GameController;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.screen.Level;
import test.puigames.courseofhistory.framework.game.CardArea;
import test.puigames.courseofhistory.framework.game.PlayArea;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.Pawn;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.controllers.HumanController;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class TestLevel extends Level
{
    private Board board;
    private CharacterCard[] testCards;

    GameController contestants[];

    private Coin coin;
    private PlayArea playArea;
    private float areaPaddingX = 10.0f;
    private float areaPaddingY = 6.0f;

    public TestLevel(GameProperties gameProperties) {
        super(gameProperties);
        contestants = new GameController[1];
        load();
        contestants[0] = new HumanController(new Pawn(testCards));
    }

    public void load() {
       try {
           this.board = resourceFetcher.loadBoard("testBoard");
           this.testCards = resourceFetcher.loadCharacterCards();
           coin = new Coin(resourceFetcher.getBitmapFromFile("images/coins/coin-heads.png"),
                   240, 160, 80, 80);
           sprites.add(board);
           sprites.addAll(Arrays.asList(testCards));
//           sprites.add(coin);

           //play area
           playArea = new PlayArea(board.boundingBox.left, board.boundingBox.top, 480, 320);
       } catch(NullPointerException e) {
           Log.d("Loading Error:", "Error fetching resources, returning to menu");
           //Failed loading the gameProperties - won't cause crash if resources set up wrong!
           gameProperties.setScreen(new SplashScreen(this.gameProperties));
       }
    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);
        //while game not won
        for(GameController contestent  : contestants) {
            if(contestent.pawn.playerCurrentState.equals(Pawn.PawnState.TAKING_TURN)) {
                contestent.update(inputBuddy, deltaTime);
            }
            //contestent.update(inputBuddy, deltaTime);
            //player.takeTurn(deltaTime);

        }

       // board.update(inputBuddy, deltaTime);

        scaler.scaleToScreen(board);
        for (Card card : testCards) {
            card.update(inputBuddy, deltaTime, testCards, board);
            scaler.scaleToScreen(card);
        }

        scaler.scaleToScreen(coin);
        coin.update(inputBuddy, deltaTime);

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
