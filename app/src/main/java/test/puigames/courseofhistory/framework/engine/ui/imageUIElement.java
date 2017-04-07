package test.puigames.courseofhistory.framework.engine.ui;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;

/**
 * Created by Jordan on 20/03/2017.
 */

public class imageUIElement extends UIElement implements Drawable
{
    public Bitmap image;

    public imageUIElement(Bitmap bitmap, float width, float height)
    {
        super(bitmap, width, height);
        this.image = bitmap;
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);
    }
}
