package test.puigames.courseofhistory.framework.engine.inputfriends.subfriends;

import android.view.View.OnTouchListener;

import java.util.List;

import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input.TouchEvent;

/**
 * Created by Jordan on 06/11/2016.
 */

public interface TouchHandler extends OnTouchListener
{
    boolean isTouchDown(int pointer);

    float getTouchX(int pointer);

    float getTouchY(int pointer);

    List<TouchEvent> getTouchEvents();
}
