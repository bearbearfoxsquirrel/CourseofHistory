package test.puigames.courseofhistory.framework.game.assets.boards;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;

/**
 * Created by 40123577 on 14/11/2016.
 */


public class Board extends Sprite implements Drawable {

    private int width;
    private int height;


    public Board(Bitmap bitmap){
        super(bitmap, 1920/2, 1080/2, 1920, 1080);
    }


    @Override
    public void draw(Canvas canvas, float lastFrameTime) {
        super.draw(canvas, lastFrameTime);
    }

    @Override
    public void update(InputBuddy inputBuddy, float lastFrameTime) {

    }
}
