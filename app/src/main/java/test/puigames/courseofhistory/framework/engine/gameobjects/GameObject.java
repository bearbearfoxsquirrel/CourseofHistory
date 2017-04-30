package test.puigames.courseofhistory.framework.engine.gameobjects;

import android.graphics.Matrix;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Placeable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Updateable;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Scalable;

/**
 * Created by Michael on 13/02/2017.
 */

public abstract class GameObject implements Updateable, Placeable, Scalable {
    protected Screen currentScrren;
    public float width, halfWidth;
    public float height, halfHeight;
    public BoundingBox boundingBox;
    public Origin origin;
    public Matrix matrix;
    public float overlapAllowance; //default overlap allowance
    public final float MAX_OVERLAP_ALLOWANCE = 1.0f;
    public final float MIN_OVERLAP_ALLOWANCE = 0.0f;
    public float rotation = 0;


    public GameObject(Screen screen, int width, int height) {
        this.width = width;
        this.halfWidth = (width / 2);
        this.height = height;
        this.halfHeight = (height / 2);
        this.overlapAllowance = MIN_OVERLAP_ALLOWANCE;
        this.currentScrren = screen;
    }

    protected void initPlacement(float spawnX, float spawnY, float rotation) {
        this.origin = new Origin(spawnX, spawnY);
        this.boundingBox = new BoundingBox(width, height, origin);
        this.matrix = new Matrix();
        this.rotation = rotation;
    }

    @Override
    //updating objects that take user input
    public void update(float deltaTime) {
        //This updates the card to draw from where the origin is
        this.boundingBox.setBoundingBox(this.origin);
    }


    @Override
    public void scale(float scaleFactorX, float scaleFactorY) {
        getMatrix().reset(); // Resets the matrix for
        getMatrix().postScale(getWidth() * scaleFactorX, getHeight() * scaleFactorY); //Scales the object size by the scale factor
        getMatrix().postRotate(getRotation(), getHalfWidth() * scaleFactorX, getHalfHeight() * scaleFactorY); //Rotates from the middle of the object on the screen
        getMatrix().postTranslate((getPosX() - getWidth() / 2.f) * scaleFactorX, (getPosY() - getHeight() / 2.f) * scaleFactorY); //Translates the object by the scale factor
    }

    @Override
    public Matrix getMatrix() {
        return this.matrix;
    }

    public Origin getOrigin() {
        return this.origin;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public void startTicking(Screen screen) {
        if (!screen.isInUpdateables(this))
            screen.addToUpdateables(this);
    }

    @Override
    public void place(Screen screen, float placementX, float placementY, float rotation) {
        this.initPlacement(placementX, placementY, rotation);
        //Checks if the object does not already exist on the screen and then places them if it is not
        startTicking(screen);

        if (!screen.scalables.contains(this))
            screen.scalables.add(this);
    }

    @Override
    public void stopTicking(Screen screen){
        if (screen.isInUpdateables(this))
            screen.removeFromUpdateables(this);
    }

    @Override
    public void remove(Screen screen) {
        //Checks if the object exists in the arrays and then removes them
        if (screen.scalables.contains(this))
            screen.scalables.remove(this);
        stopTicking(screen);
    }

    @Override
    public float getPosX() {
        return origin.x;
    }

    @Override
    public float getPosY() {
        return origin.y;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    public float getHalfHeight() {return halfHeight;}

    public float getHalfWidth() {return halfWidth;}
}
