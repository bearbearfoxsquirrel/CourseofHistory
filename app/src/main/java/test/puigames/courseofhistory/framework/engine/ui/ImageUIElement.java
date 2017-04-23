package test.puigames.courseofhistory.framework.engine.ui;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Created by Jordan on 20/03/2017.
 */

public class ImageUIElement extends UIElement implements Drawable {
    public ImageUIElement(Screen screen, Bitmap bitmap, float width, float height) {
        super(screen, bitmap, width, height);
    }
}
