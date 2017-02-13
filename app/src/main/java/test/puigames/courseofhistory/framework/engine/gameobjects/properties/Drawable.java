package test.puigames.courseofhistory.framework.engine.gameobjects.properties;

import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;

/**
 * Created by Michael on 08/11/2016.
 */

public interface Drawable {
    public void draw(Canvas canvas, float lastFrameTime);

    public void update(InputBuddy inputBuddy, float lastFrameTime);

}
