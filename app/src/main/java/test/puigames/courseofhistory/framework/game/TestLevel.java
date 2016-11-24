package test.puigames.courseofhistory.framework.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.io.IOException;

import test.puigames.courseofhistory.framework.engine.Game;
import test.puigames.courseofhistory.framework.engine.Level;
import test.puigames.courseofhistory.framework.game.Board.Board;
import test.puigames.courseofhistory.framework.game.cards.Card;
import test.puigames.courseofhistory.framework.implementation.GraphicsIO;
import test.puigames.courseofhistory.framework.input.AndroidInput;

/**
 * Created by Jordan on 10/11/2016.
 */

public class TestLevel extends Level
{
   // AndroidInput input;
//    AssetManager assets;
   private GraphicsIO graphicsIO;

    //Card card1;
    //Card card2;
    Card[] cards = new Card[2];
    Board board;
    Bitmap boardImage;


    public TestLevel(Game game) {
        super(game);
        this.graphicsIO = game.getGraphicsIO();
        game.calculateScreenSize();
        board = new Board(graphicsIO, boardImage, game);
        Bitmap cardImage = null;
        try {
            cardImage = graphicsIO.loadBitmap("blank-card.png", Bitmap.Config.ARGB_4444);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //cards[0] = new Card(cardImage, 400, 300);
        cards[0] = new Card(cardImage, 400, 300);
        cards[1] = new Card(cardImage, 1000, 300);



    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);
        cards[0].update(inputBuddy, deltaTime);
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
        cards[0].draw(canvas, deltaTime);
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
