package test.puigames.courseofhistory.framework.engine.inputfriends.unusedfriends;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import test.puigames.courseofhistory.framework.engine.Pool;
import test.puigames.courseofhistory.framework.engine.Pool.PoolObjectFactory;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input.TouchEvent;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.TouchHandler;

/**
 * Created by Jordan on 06/11/2016.
 */

public class SingleTouchHandler implements TouchHandler
{
    private boolean isTouched;
    private float touchX;
    private float touchY;
    private Pool<TouchEvent> touchEventPool;
    private List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
    private List<TouchEvent> touchEventBuffer = new ArrayList<TouchEvent>();
    private float scaleX;
    private float scaleY;

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

    public boolean isTouched() {
        return isTouched;
    }

    public void setTouched(boolean touched) {
        isTouched = touched;
    }

    public float getTouchX() {
        return touchX;
    }

    public void setTouchX(float touchX) {
        this.touchX = touchX;
    }

    public float getTouchY() {
        return touchY;
    }

    public void setTouchY(float touchY) {
        this.touchY = touchY;
    }

    public Pool<TouchEvent> getTouchEventPool() {
        return touchEventPool;
    }

    public void setTouchEventPool(Pool<TouchEvent> touchEventPool) {
        this.touchEventPool = touchEventPool;
    }

    public void setTouchEvents(List<TouchEvent> touchEvents) {
        this.touchEvents = touchEvents;
    }

    public List<TouchEvent> getTouchEventBuffer() {
        return touchEventBuffer;
    }

    public void setTouchEventBuffer(List<TouchEvent> touchEventBuffer) {
        this.touchEventBuffer = touchEventBuffer;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }
}
