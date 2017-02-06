package test.puigames.courseofhistory.framework.game.Board;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.Drawable;
import test.puigames.courseofhistory.framework.engine.Game;
import test.puigames.courseofhistory.framework.engine.InputBuddy;
import test.puigames.courseofhistory.framework.engine.Sprite;
import test.puigames.courseofhistory.framework.engine.resourceloading.GraphicsIO;

/**
 * Created by 40123577 on 14/11/2016.
 */


public class Board extends Sprite implements Drawable {

    //private variables
    GraphicsIO graphicsIO;
    private Bitmap boardImage;
    private int width;
    private int height;

    Bitmap scaledBoard;

    public Board(Bitmap bitmap, Game game){
        super(bitmap, 0, 0, game.getScreenHeight(), game.getScreenWidth());
        //set board image and size
        //this.boardImage = bitmap;
        /*width = 600;
        height = 1000;*/
       // this.scaledBoard = Bitmap.createScaledBitmap(boardImage, width, height, true);
    }


    @Override
    public void draw(Canvas canvas, float lastFrameTime) {
        super.draw(canvas, lastFrameTime);
       // canvas.drawBitmap(scaledBoard, 0.f, 0.f, null);

    }

    @Override
    public void update(InputBuddy inputBuddy, float lastFrameTime) {

    }
}
