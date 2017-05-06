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
    protected Screen currentScreen;
    protected float width, halfWidth;
    protected float height, halfHeight;
    private BoundingBox boundingBox;
    protected Origin origin;
    protected Matrix matrix;
    protected float overlapAllowance; //default overlap allowance
    protected final float MAX_OVERLAP_ALLOWANCE = 1.0f;
    protected final float MIN_OVERLAP_ALLOWANCE = 0.0f;
    protected float rotation = 0;


    public GameObject(Screen screen, int width, int height) {
        this.width = width;
        this.halfWidth = (width / 2);
        this.height = height;
        this.halfHeight = (height / 2);
        this.overlapAllowance = MIN_OVERLAP_ALLOWANCE;
        this.currentScreen = screen;
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
        getMatrix().postScale(width * scaleFactorX, height * scaleFactorY); //Scales the object size by the scale factor
        getMatrix().postRotate(rotation, getHalfWidth() * scaleFactorX, getHalfHeight() * scaleFactorY); //Rotates from the middle of the object on the screen
        getMatrix().postTranslate((getPosX() - halfWidth) * scaleFactorX, (getPosY() - halfHeight) * scaleFactorY); //Translates the object by the scale factor
    }

    @Override
    public Matrix getMatrix() {
        return this.matrix;
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

        if (!screen.getScalables().contains(this))
            screen.getScalables().add(this);
    }

    @Override
    public void stopTicking(Screen screen){
        if (screen.isInUpdateables(this))
            screen.removeFromUpdateables(this);
    }

    @Override
    public void remove(Screen screen) {
        //Checks if the object exists in the arrays and then removes them
        if (screen.getScalables().contains(this))
            screen.getScalables().remove(this);
        stopTicking(screen);
    }

    @Override
    public float getPosX() {
        return origin.getOriginX();
    }

    @Override
    public float getPosY() {return origin.getOriginY();}

    @Override
    public float getRotation() {
        return rotation;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public void setHalfWidth(float halfWidth) {
        this.halfWidth = halfWidth;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public void setHalfHeight(float halfHeight) {
        this.halfHeight = halfHeight;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public void setOrigin(float x, float y) {
        this.origin.setOriginX(x);
        this.origin.setOriginY(y);
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public float getOverlapAllowance() {
        return overlapAllowance;
    }

    public void setOverlapAllowance(float overlapAllowance) {
        this.overlapAllowance = overlapAllowance;
    }

    public float getMAX_OVERLAP_ALLOWANCE() {
        return MAX_OVERLAP_ALLOWANCE;
    }

    public float getMIN_OVERLAP_ALLOWANCE() {
        return MIN_OVERLAP_ALLOWANCE;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Origin getOrigin() {
        return origin;
    }
}
