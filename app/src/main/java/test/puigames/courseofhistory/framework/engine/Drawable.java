package test.puigames.courseofhistory.framework.engine;

import android.graphics.Canvas;

/**
 * Created by Michael on 08/11/2016.
 */

public interface Drawable {
    public void draw(Canvas canvas, float lastFrameTime);

    public void update(InputBuddy inputBuddy, float lastFrameTime);

}
