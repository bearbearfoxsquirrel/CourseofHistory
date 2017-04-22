package test.puigames.courseofhistory.framework.engine.ui;

import android.graphics.Bitmap;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;

/**
* Created by Christopher on 09/03/2017.
*/

public class MenuButton extends UIElement implements Drawable {

    public MenuButton(Bitmap buttonImage, float width, float height){
        super(buttonImage, width, height);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

}
