package test.puigames.courseofhistory.framework.engine.inputfriends.subfriends;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import java.util.List;

import test.puigames.courseofhistory.framework.engine.inputfriends.unusedfriends.AccelerometerHandler;
import test.puigames.courseofhistory.framework.engine.inputfriends.unusedfriends.KeyboardHandler;
import test.puigames.courseofhistory.framework.engine.inputfriends.unusedfriends.SingleTouchHandler;

/**
 * Created by Jordan on 08/11/2016.
 */

public class AndroidInput implements Input
{
    private AccelerometerHandler accelHandler;
    private KeyboardHandler keyHandler;
    private TouchHandler touchHandler;

    public AndroidInput(Context context, View view, float scaleX,float scaleY)
    {
        accelHandler = new AccelerometerHandler(context);
        keyHandler = new KeyboardHandler(view);
        if(VERSION.SDK_INT< 5)
            touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
        else
            touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
    }

    public boolean isKeyPressed(int keyCode)
    {
        return keyHandler.isKeyPressed(keyCode);
    }

    public boolean isTouchDown(int pointer)
    {
        return touchHandler.isTouchDown(pointer);
    }

    public float getTouchX(int pointer)
    {
        return touchHandler.getTouchX(pointer);
    }

    public float getTouchY(int pointer)
    {
        return touchHandler.getTouchY(pointer);
    }

    public float getAccelX()
    {
        return accelHandler.getAccelX();
    }

    public float getAccelY()
    {
        return accelHandler.getAccelY();
    }

    public float getAccelZ()
    {
        return accelHandler.getAccelZ();
    }

    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }

    public List<KeyEvent> getKeyEvents() {
        return keyHandler.getKeyEvents();
    }

    public AccelerometerHandler getAccelHandler() {
        return accelHandler;
    }

    public void setAccelHandler(AccelerometerHandler accelHandler) {
        this.accelHandler = accelHandler;
    }

    public KeyboardHandler getKeyHandler() {
        return keyHandler;
    }

    public void setKeyHandler(KeyboardHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    public TouchHandler getTouchHandler() {
        return touchHandler;
    }

    public void setTouchHandler(TouchHandler touchHandler) {
        this.touchHandler = touchHandler;
    }
}
