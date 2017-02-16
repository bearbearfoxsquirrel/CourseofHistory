package test.puigames.courseofhistory.framework.engine.screen.scaling;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;

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



    public void handleOutOfBounds(Sprite sprite) {
        if (sprite.boundingBox.isOutside(this.boundingBox) || sprite.boundingBox.isOverlapping(this.boundingBox)) {
        }
    }
}
