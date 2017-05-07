package test.puigames.courseofhistory.framework.engine.screen.scaling;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Michael on 21/04/2017.
 */

public interface Scalable {
    void scale(float scaleFactorX, float scaleFactorY);

    //Matrix is USED for objects scaling
    Matrix getMatrix();

    //Generic to allow for any way of getting dimensions
    float getWidth();

    float getHeight();

    //Generic to allow for any way of getting position
    float getPosX();

    float getPosY();

    //Generic to allow for any way of getting rotation
    float getRotation();

    interface ImageScalable extends Scalable {
        //Saleable objects must have a bitmap
        Bitmap getBitmap();
    }
}
