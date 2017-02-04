package test.puigames.courseofhistory.framework.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.io.IOException;

import test.puigames.courseofhistory.framework.engine.Game;
import test.puigames.courseofhistory.framework.engine.Level;
import test.puigames.courseofhistory.framework.game.Board.Board;
import test.puigames.courseofhistory.framework.game.cards.Card;
import test.puigames.courseofhistory.framework.game.cards.CharacterCard;
import test.puigames.courseofhistory.framework.input.AndroidInput;

/**
 * Created by Jordan on 10/11/2016.
 */

public class TestLevel extends Level
{
    Card[] cards = new Card[2];
    Board board;
    CharacterCard cards1;

    public TestLevel(Game game) {
        super(game);
        //this.graphicsIO = game.getGraphicsIO();
        game.calculateScreenSize();
        Bitmap cardImage = null;
        Bitmap boardImage = null;
        try {
            cardImage = game.getResourceFetcher().getBitmapFromFile("blank-card.png");// graphicsIO.loadBitmap("blank-card.png", Bitmap.Config.ARGB_4444);
            boardImage = game.getResourceFetcher().getBitmapFromFile("board.png");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        board = new Board(boardImage, game);
        try {
            cards1 = resourceFetcher.loadCharacterCard(0, game.getResourceFetcher().getJSONString("cardtests.json"));
        } catch (IOException e){
            Log.d("Loading asset: ", "Failed to load assets");
        }
        cards[1] = new Card(cardImage, 1000, 300);



    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);
//        cards[0].update(inputBuddy, deltaTime);
        cards1.update(inputBuddy, deltaTime);
        cards[1].update(inputBuddy, deltaTime);

    }
    float lastFrame;
    float thisFrame;
    float deltaMS;

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        //super.draw(canvas, deltaTime);
        lastFrame = thisFrame;
//        canvas.drawColor(Color.WHITE);
//        card1.draw(canvas, deltaTime, game);
        board.draw(canvas, deltaTime);
        cards1.draw(canvas, deltaTime);
      //  cards[0].draw(canvas, deltaTime);
        cards[1].draw(canvas, deltaTime);

        //card2.draw(canvas, deltaTime);
//        card2.draw(canvas, deltaTime);

//        deltaMS = thisFrame - lastFrame;
//        Log.d("FPS", "" +  1/deltaMS+ "");


    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {

    }
}
