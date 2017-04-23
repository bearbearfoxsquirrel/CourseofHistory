package test.puigames.courseofhistory.framework.engine.screen.scaling;

import android.graphics.Rect;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;

/**
 * Created by Michael on 24/11/2016.
 */

public class Viewport {
    public float width;
    public float height;
    public float centerX;
    public float centerY;

    BoundingBox boundingBox;
    Origin origin;
    private Rect rect = new Rect();

    public Viewport(float width, float height) {
        this.height = height;
        this.width = width;
        this.origin = new Origin(width/2, height/2);
        this.boundingBox = new BoundingBox(width, height, origin);
        this.centerX = origin.x;
        this.centerY = origin.y;
    }

    public Rect createViewportRect(float width, float height){
        rect.left = (int)(width-width);
        rect.right = (int)width;
        rect.bottom = (int)(height-height);
        rect.top = (int)(height);

        return rect;
    }


}
