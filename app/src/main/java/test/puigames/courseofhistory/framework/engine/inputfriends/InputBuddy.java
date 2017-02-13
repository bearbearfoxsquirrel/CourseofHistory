package test.puigames.courseofhistory.framework.engine.inputfriends;

import java.util.List;

import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;

/**
 * Created by Michael on 24/11/2016.
 */

public class InputBuddy {
    //Our little buddy for giving us the nice input that he got off his other input friends
    //He holds this while we have to use it
    //And afterwards goes and talk to his friends again to bring us more input
    public List<Input.TouchEvent> touchEvents;
    public List<Input.KeyEvent> keyEvents;
    public float accelerationX;
    public float accelerationY;
    public float accelerationZ;

    //Creates storage for the events of input until the next time the update loop for the screen is made
    public InputBuddy(AndroidInput androidInput) {
      if(androidInput != null)  //null check for switching screens
      {
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
    //  public  boolean isTouchDown;
}
