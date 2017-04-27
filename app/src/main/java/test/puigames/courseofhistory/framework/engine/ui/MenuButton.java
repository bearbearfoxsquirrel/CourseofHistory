package test.puigames.courseofhistory.framework.engine.ui;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Created by Christopher on 09/03/2017.
 */
public abstract class MenuButton extends UIElement implements Drawable, UIActionable {
    public MenuButton(Screen screen, Bitmap buttonImage, float width, float height) {
        super(screen, buttonImage, width, height);
    }

    @Override
    public boolean checkForInput(InputBuddy inputBuddy) {
        for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents())
            if (this.boundingBox.isTouchOn(touchEvent))
                return true;
        return false;
    }
}
