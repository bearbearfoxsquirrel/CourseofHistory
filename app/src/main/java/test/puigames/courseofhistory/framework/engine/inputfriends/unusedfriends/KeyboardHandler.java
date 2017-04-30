package test.puigames.courseofhistory.framework.engine.inputfriends.unusedfriends;

import android.view.View;
import android.view.View.OnKeyListener;

import java.util.ArrayList;
import java.util.List;

import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input.KeyEvent;
import test.puigames.courseofhistory.framework.engine.Pool;
import test.puigames.courseofhistory.framework.engine.Pool.PoolObjectFactory;

/**
 * Created by Jordan on 06/11/2016.
 */

public class KeyboardHandler implements OnKeyListener
{
    private boolean[] pressedKeys = new boolean[128];
    private Pool<KeyEvent> keyEventPool;
    private List<KeyEvent> keyEventsBuffer = new ArrayList<KeyEvent>();
    private List<KeyEvent> keyEvents = new ArrayList<KeyEvent>();

    public KeyboardHandler(View view)
    {
        PoolObjectFactory<KeyEvent> factory = new PoolObjectFactory<KeyEvent>()
        {
            @Override
            public KeyEvent createObject()
            {
                return new KeyEvent();
            }
        };
        keyEventPool = new Pool<KeyEvent>(factory, 100);
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    public boolean onKey(View v, int keyCode, android.view.KeyEvent event)
    {
        if(event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE)
            return false;

        synchronized (this)
        {
            KeyEvent keyEvent = keyEventPool.newObject();
            keyEvent.keyCode = keyCode;
            keyEvent.keyChar = (char) event.getUnicodeChar();
            if(event.getAction() == android.view.KeyEvent.ACTION_DOWN)
            {
                keyEvent.type = KeyEvent.KEY_DOWN;
                if(keyCode > 0 && keyCode < 127)
                    pressedKeys[keyCode] = true;
            }
            if(event.getAction() == android.view.KeyEvent.ACTION_UP)
            {
                keyEvent.type = KeyEvent.KEY_UP;
                if(keyCode > 0 && keyCode < 127)
                    pressedKeys[keyCode] = false;
            }
            keyEventsBuffer.add(keyEvent);
        }
        return false;
    }

    /*
        -When working with primitive types, there is no need
            to synchronise events.
        -This is due to the way the JVM does its shit (or something)
     */
    public boolean isKeyPressed(int keyCode)
    {
        if(keyCode < 0 || keyCode > 127)
            return false;
        return pressedKeys[keyCode];
    }

    /*
        -This is synchronised because this method will be in
            a different block
     */
    public List<KeyEvent> getKeyEvents()    //this is called a lot or keyEvents fills up
    {                                       //and no objects are returned to pool
        synchronized (this)
        {
            int len = keyEvents.size();
            for(int i = 0; i < len; i++)
                keyEventPool.free(keyEvents.get(i));    //add events to pool
            keyEvents.clear();
            keyEvents.addAll(keyEventsBuffer);
            keyEventsBuffer.clear();
            return keyEvents;
        }
    }

    public boolean[] getPressedKeys() {
        return pressedKeys;
    }

    public void setPressedKeys(boolean[] pressedKeys) {
        this.pressedKeys = pressedKeys;
    }

    public Pool<KeyEvent> getKeyEventPool() {
        return keyEventPool;
    }

    public void setKeyEventPool(Pool<KeyEvent> keyEventPool) {
        this.keyEventPool = keyEventPool;
    }

    public List<KeyEvent> getKeyEventsBuffer() {
        return keyEventsBuffer;
    }

    public void setKeyEventsBuffer(List<KeyEvent> keyEventsBuffer) {
        this.keyEventsBuffer = keyEventsBuffer;
    }

    public void setKeyEvents(List<KeyEvent> keyEvents) {
        this.keyEvents = keyEvents;
    }
}
