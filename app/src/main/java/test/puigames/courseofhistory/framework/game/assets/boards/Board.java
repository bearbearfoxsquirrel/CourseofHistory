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
    public Board(Bitmap bitmap){
        super(bitmap, 480/2, 320/2, bitmap.getWidth(), bitmap.getHeight());
        origin.x = 480/2;
        origin.y = 320/2;
    }

    @Override
    public void draw(Canvas canvas, float lastFrameTime) {
        super.draw(canvas, lastFrameTime);
    }

    @Override
    public void update(InputBuddy inputBuddy, float lastFrameTime) {

    }
}
