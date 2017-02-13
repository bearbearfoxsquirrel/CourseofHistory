package test.puigames.courseofhistory.framework.engine.inputfriends.subfriends;

import android.view.View.OnTouchListener;

import java.util.List;

import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input.TouchEvent;

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
