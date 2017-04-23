package test.puigames.courseofhistory.framework.engine.screen.scaling;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;

/**
 * Created by Michael on 21/04/2017.
 */

public interface Scalable {
    Matrix getMatrix();

    Origin getOrigin();

    float getWidth();

    float getHeight();

    interface ImageScalable extends Scalable {
        Bitmap getBitmap();
    }
}
