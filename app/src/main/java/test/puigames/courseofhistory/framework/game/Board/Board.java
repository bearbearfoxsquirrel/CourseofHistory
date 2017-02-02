package test.puigames.courseofhistory.framework.game.Board;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.io.IOException;

import test.puigames.courseofhistory.framework.engine.Drawable;
import test.puigames.courseofhistory.framework.engine.Game;
import test.puigames.courseofhistory.framework.engine.InputBuddy;
import test.puigames.courseofhistory.framework.engine.resourceloading.GraphicsIO;

/**
 * Created by 40123577 on 14/11/2016.
 */


public class Board implements Drawable{

    //private variables
    GraphicsIO graphicsIO;
    private Bitmap boardImage;
    private int width;
    private int height;

    Bitmap scaledBoard;

    public Board(GraphicsIO graphicsIO, Bitmap boardImage, Game game){


        this.graphicsIO = graphicsIO;

        try
        {
            //Uses graphics IO class to load a bitmap
            //Passes in the card file name + format
            boardImage = graphicsIO.loadBitmap("board.png", Bitmap.Config.ARGB_4444);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        //set board image and size
        this.boardImage = boardImage;
        /*width = 600;
        height = 1000;*/



        height = game.getScreenHeight();
        width = game.getScreenWidth();

        this.scaledBoard = Bitmap.createScaledBitmap(boardImage, width, height, true);
    }


    @Override
    public void draw(Canvas canvas, float lastFrameTime) {

        canvas.drawBitmap(scaledBoard, 0.f, 0.f, null);

    }

    @Override
    public void update(InputBuddy inputBuddy, float lastFrameTime) {

    }
}
