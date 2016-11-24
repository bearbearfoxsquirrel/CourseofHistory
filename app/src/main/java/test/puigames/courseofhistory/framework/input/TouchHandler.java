package test.puigames.courseofhistory.framework.input;

import android.view.View.OnTouchListener;

import test.puigames.courseofhistory.framework.input.Input.TouchEvent;

import java.util.List;

/**
 * Created by Jordan on 06/11/2016.
 */

public interface TouchHandler extends OnTouchListener
{
    public boolean isTouchDown(int pointer);

    public float getTouchX(int pointer);

    public float getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}
