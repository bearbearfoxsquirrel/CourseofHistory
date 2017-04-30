package test.puigames.courseofhistory.framework.engine.inputfriends;

import java.util.ArrayList;
import java.util.List;

import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;

/**
 * Created by Michael on 24/11/2016.
 */

public class InputBuddy {
    private AndroidInput androidInput;
    //Our little buddy for giving us the nice input that he got off his other input friends
    //He holds this while we have to use it
    //And afterwards goes and talk to his friends again to bring us more input
    private List<Input.TouchEvent> touchEvents;
    private List<Input.KeyEvent> keyEvents;
    private float accelerationX;
    private float accelerationY;
    private float accelerationZ;

    //Creates storage for the events of input until the next time the update loop for the screen is made
    public InputBuddy(AndroidInput androidInput) {
        this.androidInput = androidInput;
        //null check for switching screens
        update();
    }

    public void update() {
        if(androidInput == null)  {
            touchEvents = new ArrayList<>(0);
            keyEvents = new ArrayList<>(0);
            accelerationX = 0.f;
            accelerationY = 0.f;
            accelerationZ = 0.f;
        } else {
            touchEvents = androidInput.getTouchEvents();
            keyEvents = androidInput.getKeyEvents();
            accelerationX = androidInput.getAccelX();
            accelerationY = androidInput.getAccelY();
            accelerationZ = androidInput.getAccelZ();
        }
    }

    public List<Input.TouchEvent> getTouchEvents() {
        return touchEvents;
    }

    public void setTouchEvents(List<Input.TouchEvent> touchEvents) {
        this.touchEvents = touchEvents;
    }

    public List<Input.KeyEvent> getKeyEvents() {
        return keyEvents;
    }

    public void setKeyEvents(List<Input.KeyEvent> keyEvents) {
        this.keyEvents = keyEvents;
    }

    public float getAccelerationX() {
        return accelerationX;
    }

    public void setAccelerationX(float accelerationX) {
        this.accelerationX = accelerationX;
    }

    public float getAccelerationY() {
        return accelerationY;
    }

    public void setAccelerationY(float accelerationY) {
        this.accelerationY = accelerationY;
    }

    public float getAccelerationZ() {
        return accelerationZ;
    }

    public void setAccelerationZ(float accelerationZ) {
        this.accelerationZ = accelerationZ;
    }

    public AndroidInput getAndroidInput() {
        return androidInput;
    }

    public void setAndroidInput(AndroidInput androidInput) {
        this.androidInput = androidInput;
    }

}
