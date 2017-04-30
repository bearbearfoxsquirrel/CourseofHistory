package test.puigames.courseofhistory.framework.engine.screen.scaling;

import android.graphics.Rect;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;

/**
 * Created by Michael on 24/11/2016.
 */

public class Viewport {
    private float width;
    private float height;
    private float centerX;
    private float centerY;
    private BoundingBox boundingBox;
    private Origin origin;
    private Rect rect = new Rect();

    public Viewport(float width, float height) {
        this.height = height;
        this.width = width;
        this.origin = new Origin(width/2, height/2);
        this.boundingBox = new BoundingBox(width, height, origin);
        this.centerX = origin.getOriginX();
        this.centerY = origin.getOriginY();
    }

    public Rect createViewportRect(float width, float height){
        rect.left = (int)(width-width);
        rect.right = (int)width;
        rect.bottom = (int)(height-height);
        rect.top = (int)(height);

        return rect;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }


}
