package test.puigames.courseofhistory.framework.engine.gameobjects;

import android.graphics.Matrix;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;

/**
 * Created by Michael on 13/02/2017.
 */

public abstract class GameObject {
    public float width, halfWidth;
    public float height, halfHeight;
    public BoundingBox boundingBox;
    public Origin origin;
    public Matrix matrix;
    public float overlapAllowance; //default overlap allowance
    public final float MAX_OVERLAP_ALLOWANCE = 1.0f;
    public final float MIN_OVERLAP_ALLOWANCE = 0.0f;

    public GameObject(int width, int height) {
        this.width = width;
        this.halfWidth = (width / 2);
        this.height = height;
        this.halfHeight = (height / 2);
        this.overlapAllowance = MIN_OVERLAP_ALLOWANCE;
    }

    public void spawnObject(float spawnX, float spawnY) {
        this.origin = new Origin(spawnX, spawnY);
        this.boundingBox = new BoundingBox(width, height, origin);
        this.matrix = new Matrix();
    }


    //updating objects that take user input
    public void update(float deltaTime) {
        //This updates the card to draw from where the origin is
        this.boundingBox.setBoundingBox(this.origin);
    }

    public boolean checkForCollision(GameObject object)
    {
       if(this.equals(object))
           return false;
        else
            if(this.boundingBox.isOverlapping(object.boundingBox))
                return true;
        return false;
    }

    public void resolveCollision(GameObject object, float overlapModifier)
    {
        if(overlapModifier == MIN_OVERLAP_ALLOWANCE)
            return;
        else
            this.boundingBox.getCollisionDetector()
                    .determineAndResolveCollision(this, object, overlapModifier);
    }
}
