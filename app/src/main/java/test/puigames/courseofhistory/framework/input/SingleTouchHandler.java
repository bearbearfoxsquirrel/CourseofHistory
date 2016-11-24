package test.puigames.courseofhistory.framework.input;

import android.view.MotionEvent;
import android.view.View;


import test.puigames.courseofhistory.framework.input.Input.TouchEvent;
import test.puigames.courseofhistory.framework.Pool;
import test.puigames.courseofhistory.framework.Pool.PoolObjectFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 06/11/2016.
 */

public class SingleTouchHandler implements TouchHandler
{
    boolean isTouched;
    float touchX;
    float touchY;
    Pool<TouchEvent> touchEventPool;
    List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
    List<TouchEvent> touchEventBuffer = new ArrayList<TouchEvent>();
    float scaleX;
    float scaleY;

    public SingleTouchHandler(View view, float scaleX, float scaleY)
    {
        PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>()
        {
            @Override
            public TouchEvent createObject()
            {
                return new TouchEvent();
            }
        };
        touchEventPool = new Pool<TouchEvent>(factory, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public boolean onTouch(View v, MotionEvent event)
    {
        synchronized (this)
        {
            TouchEvent touchEvent = touchEventPool.newObject();
            switch (event.getAction())
            {
            case MotionEvent.ACTION_DOWN:
                touchEvent.type = TouchEvent.TOUCH_DOWN;
                isTouched = true;
                break;
            case MotionEvent.ACTION_MOVE:
                touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                isTouched = true;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                touchEvent.type = TouchEvent.TOUCH_UP;
                isTouched = false;
                break;
            }

            touchEvent.x = touchX = (event.getX() * scaleX);
            touchEvent.y = touchY = (event.getY() * scaleY);
            touchEventBuffer.add(touchEvent);

            return true;
        }
    }

    public boolean isTouchDown(int pointer)
    {
        synchronized (this)
        {
            if(pointer == 0)
                return isTouched;
            else
                return false;
        }
    }

    public float getTouchX(int pointer)
    {
        synchronized (this)
        {
            return touchX;
        }
    }

    public float getTouchY(int pointer)
    {
        synchronized (this)
        {
            return touchY;
        }
    }

    public List<TouchEvent> getTouchEvents()
    {
        synchronized (this)
        {
            int len = touchEvents.size();
            for(int i = 0; i < len; i++)
                touchEventPool.free(touchEvents.get(i));
            touchEvents.clear();
            touchEvents.addAll(touchEventBuffer);
            touchEventBuffer.clear();
            return touchEvents;
        }
    }
}
