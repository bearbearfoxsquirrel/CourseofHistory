package test.puigames.courseofhistory.framework.engine;

import android.graphics.Bitmap;

/**
 * Created by Michael on 24/11/2016.
 */

public class Viewport {
    float width;
    float height;
    BoundingBox boundingBox;
    Origin origin;

    public Viewport(float width, float height) {
        this.width = width;
        this.height = height;
        this.origin = new Origin(width/2, height/2);
        this.boundingBox = new BoundingBox(width, height, origin);
    }

    public float getScaleFactor(Bitmap bitmap) {
        float screenAspect = width / height;
        float bitmapAspect =  bitmap.getWidth()/ bitmap.getWidth();

        float scaleFactor;
        if (screenAspect > bitmapAspect)
            scaleFactor = width / bitmap.getHeight();
        else
            scaleFactor = width / bitmap.getWidth();
        return scaleFactor;
    }

    public void handleOutOfBounds(Sprite sprite) {
        if (sprite.boundingBox.isOutside(this.boundingBox) || sprite.boundingBox.isOverlapping(this.boundingBox)) {
        }
    }
}
