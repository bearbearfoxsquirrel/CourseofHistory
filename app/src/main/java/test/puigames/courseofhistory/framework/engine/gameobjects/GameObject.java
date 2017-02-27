package test.puigames.courseofhistory.framework.engine.gameobjects;

import android.graphics.Matrix;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;

/**
 * Created by Michael on 13/02/2017.
 */

public abstract class GameObject {
    public float width;
    public float height;
    public BoundingBox boundingBox;
    public Origin origin;
    public Matrix matrix;



    public GameObject(float spawnX, float spawnY, int width, int height) {
        this.width = width;
        this.height = height;
        this.origin = new Origin(spawnX, spawnY);
        this.boundingBox = new BoundingBox(width, height, origin);
        this.matrix = new Matrix();
        matrix.postTranslate(origin.x - width/2, origin.y - width/2);
    }

    //updating objects that take user input
    public void update(InputBuddy inputBuddy, float deltaTime) {
        matrix.reset();
        //This updates the card to draw from where the origin is
        //Matrix translates bitmaps etc. to where the origin has moved to
        //matrix.postScale(width, height);
        matrix.postTranslate(origin.x - width/2, origin.y - width/2);
        this.boundingBox.setBoundingBox(this.origin);
    }

    //Updating objects that don't take user input
    public void update(float lastFrameTime) {
        matrix.setTranslate(origin.x, origin.y);
        boundingBox.setBoundingBox(this.origin);
    }




}
