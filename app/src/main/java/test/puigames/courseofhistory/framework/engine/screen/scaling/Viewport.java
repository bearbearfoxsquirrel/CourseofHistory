package test.puigames.courseofhistory.framework.engine.screen.scaling;

import android.graphics.Rect;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;

/**
 * Created by Michael on 24/11/2016.
 */

public class Viewport {
    private float initialWidth;
    private float initialHeight;
    private float scaledWidth;
    private float scaledHeight;
    BoundingBox boundingBox;
    Origin origin;
    private Rect rect = new Rect();

    public Viewport(float width, float height, Scaler scaler) {
        this.initialHeight = height;
        this.initialWidth = width;
       scaler.setScaleFactor(width, height);
       // scaler.scaleViewport(width, height);
        this.scaledWidth = width;
        this.scaledHeight = height;

        this.origin = new Origin(width/2, height/2);
        this.boundingBox = new BoundingBox(width, height, origin);

    }

    public Rect createViewportRect(float width, float height){
        rect.left = (int)(width-width);
        rect.right = (int)width;
        rect.bottom = (int)(height-height);
        rect.top = (int)(height);

        return rect;
    }


}
