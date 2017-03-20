package test.puigames.courseofhistory.framework.engine.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;

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
