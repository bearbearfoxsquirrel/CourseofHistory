package test.puigames.courseofhistory.framework.engine.gameobjects.properties;

import test.puigames.courseofhistory.framework.engine.gameobjects.collision.CollisionDetector;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;

/**
 * Created by Michael on 21/11/2016.
 */

public class BoundingBox {
    //Set up so top left of screen is 0,0;
    //Arbitrary values
    public float left;
    public float right;
    public float top;
    public float bottom;
    private float width, halfWidth;
    private float height, halfHeight;
    private CollisionDetector collisionDetector;

    public enum bound {
        LEFT, RIGHT, TOP, BOTTOM, NONE;
    }

    //sets up the bounding box based upon the width, height and the origin location of the bounding box
    public BoundingBox(float width, float height, Origin origin) {
        this.width = width;     this.halfWidth = (width/2);
        this.height = height;   this.halfHeight = (height/2);
        this.left = origin.x - this.halfWidth ;
        this.right = origin.x + this.halfWidth;
        this.top = origin.y - this.halfHeight;
        this.bottom = origin.y + this.halfHeight;
        collisionDetector = new CollisionDetector();
    }

    //checks if this bounding box if overlapping with another bounding box (on one side or more)
    public boolean isOverlapping(BoundingBox boundingBox) {
        return !isOutside(boundingBox);
    }

    //checks if a touch event is within the bounding box
    public boolean isTouchOn(Input.TouchEvent touchEvent) {
        return (touchEvent.x >= left && touchEvent.x <= right &&
                touchEvent.y >= top && touchEvent.y <= bottom);
    }

    //checks if this bounding box is fully encapsulated by another bounding box
    public boolean isEncapsulated(BoundingBox boundingBox) {
        return (this.left > boundingBox.left && this.right < boundingBox.right &&
                this.top > boundingBox.top && this.bottom < boundingBox.bottom);
    }

    //checks if two bounding boxes are not colliding with each other
    public boolean isOutside(BoundingBox boundingBox) {
        return (this.left > boundingBox.left || this.right < boundingBox.right
                && this.top > boundingBox.bottom || this.bottom > boundingBox.top);
    }

    //updates the bounding box for sprites not taking user input
    public void setBoundingBox(float width, float height, Origin origin) {
        this.left = origin.x - (width/2) ;
        this.right = origin.x + (width/2);
        this.top = origin.y - (height/2);
        this.bottom = origin.y + (height/2);
    }

    //udates where the bounding box refers to on screen
    public void setBoundingBox(Origin origin) {
        this.left = origin.x - this.halfWidth ;
        this.right = origin.x + this.halfWidth;
        this.top = origin.y - this.halfHeight;
        this.bottom = origin.y + this.halfHeight;
    }

    //returns the bound that has been broken,
    // should only be called if it has been verified that two
    // bounding boxes or either overlapping
    // or if one bounding box if outside of another
    //Otherwise will return NONE!!!!
    public bound getBoundBroken(BoundingBox boundingBox) {
        if (this.bottom < boundingBox.bottom)
            return bound.BOTTOM;
        else if (this.top > boundingBox.top)
            return  bound.TOP;
        else if (this.left > boundingBox.left)
            return bound.LEFT;
        else if (this.right <  boundingBox.right)
            return bound.RIGHT;
        else
            return bound.NONE;
    }

    @Override
    public String toString() {
        return "BoundingBox{" +
                "left=" + left +
                ", right=" + right +
                ", top=" + top +
                ", bottom=" + bottom +
                '}';
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public CollisionDetector getCollisionDetector()
    {
        return collisionDetector;
    }
}
