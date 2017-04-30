package test.puigames.courseofhistory.framework.engine.inputfriends.subfriends;

import android.annotation.TargetApi;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import test.puigames.courseofhistory.framework.engine.Pool;
import test.puigames.courseofhistory.framework.engine.Pool.PoolObjectFactory;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input.TouchEvent;

/**
 * Created by Jordan on 06/11/2016.
 */

@TargetApi(5)
public class MultiTouchHandler implements TouchHandler
{
    private static final int MAX_TOUCHPOINTS = 10;
    private boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
    private float[] touchX = new float[MAX_TOUCHPOINTS];
    private float[] touchY = new float[MAX_TOUCHPOINTS];
    private int[] id = new int[MAX_TOUCHPOINTS];
    private Pool<TouchEvent> touchEventPool;
    private List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
    private List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
    private float scaleX;
    private float scaleY;

    public MultiTouchHandler(View view, float scaleX, float scaleY)
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

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public boolean onTouch(View v, MotionEvent event)
    {
        synchronized (this)
        {
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK)
                    >> MotionEvent.ACTION_POINTER_ID_SHIFT;
            int pointerCount = event.getPointerCount();
            TouchEvent touchEvent;
            for(int i = 0; i < MAX_TOUCHPOINTS; i++)
            {
                if(i >= pointerCount)
                {
                    isTouched[i] = false;
                    id[i] = -1;
                    continue;
                }
                int pointerId = event.getPointerId(i);
                if(event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex)
                {
                    //if its an up/down/cancel/out event, mask the id to see if we should
                    //process for this touch
                    continue;
                }
                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_DOWN;
                        touchEvent.pointer = pointerId;
                        touchEvent.x = touchX[i] = (event.getX(i) / scaleX);
                        touchEvent.y = touchY[i] = (event.getY(i) / scaleY);
                        isTouched[i] = true;
                        id[i] = pointerId;
                        touchEventsBuffer.add(touchEvent);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_CANCEL:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_UP;
                        touchEvent.pointer = pointerId;
                        touchEvent.x = touchX[i] = (event.getX(i) / scaleX);
                        touchEvent.y = touchY[i] = (event.getY(i) / scaleY);
                        isTouched[i] = false;
                        id[i] = -1;
                        touchEventsBuffer.add(touchEvent);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                        touchEvent.pointer = pointerId;
                        touchEvent.x = touchX[i] = (event.getX(i) / scaleX);
                        touchEvent.y = touchY[i] = (event.getY(i) / scaleY);
                        isTouched[i] = true;
                        id[i] = pointerId;
                        touchEventsBuffer.add(touchEvent);
                        break;
                }
            }
            return true;
        }
    }

    public boolean isTouchDown(int pointer)
    {
        synchronized (this)
        {
            int index = getIndex(pointer);
            if(index < 0 || index >= MAX_TOUCHPOINTS)
                return false;
            else
                return isTouched[index];
        }
    }

    public float getTouchX(int pointer)
    {
        synchronized (this)
        {
            int index = getIndex(pointer);
            if(index < 0 || index >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchX[index];
        }
    }

    public float getTouchY(int pointer)
    {
        synchronized (this)
        {
            int index = getIndex(pointer);
            if(index < 0 || index >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchY[index];
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
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }

    //returns index for a given pointerId or -1 if no index
    private int getIndex(int pointerId)
    {
        for(int i = 0; i < MAX_TOUCHPOINTS; i++)
            if(id[i] == pointerId)
                return i;
        return -1;
    }

    public static int getMaxTouchpoints() {
        return MAX_TOUCHPOINTS;
    }

    public boolean[] getIsTouched() {
        return isTouched;
    }

    public void setIsTouched(boolean[] isTouched) {
        this.isTouched = isTouched;
    }

    public float[] getTouchX() {
        return touchX;
    }

    public void setTouchX(float[] touchX) {
        this.touchX = touchX;
    }

    public float[] getTouchY() {
        return touchY;
    }

    public void setTouchY(float[] touchY) {
        this.touchY = touchY;
    }

    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
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

    public List<TouchEvent> getTouchEventsBuffer() {
        return touchEventsBuffer;
    }

    public void setTouchEventsBuffer(List<TouchEvent> touchEventsBuffer) {
        this.touchEventsBuffer = touchEventsBuffer;
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }


}
