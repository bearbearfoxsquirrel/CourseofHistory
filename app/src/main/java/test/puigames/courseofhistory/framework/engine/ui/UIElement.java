package test.puigames.courseofhistory.framework.engine.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Placeable;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Scalable;

/**
 * Created by Christopher on 13/03/2017.
 */

public class UIElement implements Drawable, Scalable.ImageScalable, Placeable {

    protected Screen currentScreen;
    protected Bitmap image;
    protected float width, halfWidth;
    protected float height, halfHeight;
    protected BoundingBox boundingBox;
    protected Origin origin;
    protected float rotation;
    protected Matrix matrix;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //Constructor - takes in a bitmap image, along with a width and height
    public UIElement(Screen screen, Bitmap bitmap, float width, float height){
        this.currentScreen = screen;
        this.image = bitmap;
        this.width = width;
        this.rotation = 0;
        this.halfWidth = (width / 2);
        this.height = height;
        this.halfHeight = (height / 2);
    }

    //Allows for drawing of the objects to the canvas
    @Override
    public void draw(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(image, matrix, paint);
    }

    //Utilises the initPlacement method to then add the object to the scalables and drawables
    //Array Lists.
    @Override
    public void place(Screen screen, float placementX, float placementY, float rotation) {
        this.currentScreen = screen;
        initPlacement(placementX, placementY, rotation);
        if (!screen.getScalables().contains(this))
             screen.getScalables().add(this);
        if (!screen.getDrawables().contains(this))
            screen.getDrawables().add(this);
    }

    @Override
    public void remove(Screen screen) {
        //Checks if the object exists in the arrays and then removes them
        if (screen.getDrawables().contains(this))
            screen.getDrawables().remove(this);
    }

    @Override
    public void scale(float scaleFactorX, float scaleFactorY) {
        getMatrix().reset(); // Resets the matrix for
        getMatrix().postScale((width / getBitmap().getWidth()) * scaleFactorX, height / getBitmap().getHeight() * scaleFactorY); //Scales the object/image ratio by the scale factor
        getMatrix().postRotate(rotation, getHalfWidth() * scaleFactorX, halfHeight * scaleFactorY); //Rotates from the middle of the object on the screen
        getMatrix().postTranslate((getPosX() - halfWidth) * scaleFactorX, (getPosY() - halfHeight) * scaleFactorY); //Translates the object by the scale factor
    }

    //Method to position uiElement on screen based on the x and y inputted, sets up an origin
    //on the middle of the uiElement, and then sets up a bounding box about it.
    public void initPlacement(float spawnX, float spawnY, float rotation) {
        this.origin = new Origin(spawnX, spawnY);
        this.boundingBox = new BoundingBox(width, height, origin);
        this.matrix = new Matrix();
        this.rotation = rotation;
    }


    //Getters and Setters
    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    @Override
    public Bitmap getBitmap() {
        return image;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public float getPosX() {
        return this.origin.getOriginX();
    }

    @Override
    public float getPosY() {
        return this.origin.getOriginY();
    }

    @Override
    public float getRotation() {
        return this.rotation;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
        this.halfWidth = width / 2;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
        this.halfHeight = height / 2;
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

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public float getHalfHeight() {
        return halfHeight;
    }
}
