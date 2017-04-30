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

public abstract class UIElement implements Drawable, Scalable.ImageScalable, Placeable {
    protected Screen currentScreen;
    protected Bitmap image;
    protected float width, halfWidth;
    protected float height, halfHeight;
    protected BoundingBox boundingBox;
    protected Origin origin;
    protected Matrix matrix;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //Constructor - takes in a bitmap image, along with a width and height
    public UIElement(Screen screen, Bitmap bitmap, float width, float height){
        this.currentScreen = screen;
        this.image = bitmap;
        this.width = width;
        this.halfWidth = (width / 2);
        this.height = height;
        this.halfHeight = (height / 2);
    }

    //Allows for drawing of the objects to the canvas
    @Override
    public void draw(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(image, matrix, paint);
    }

    //Method to place the UI Element on screen based on the middle of the object
    //takes in a position and calculated with the origin
    public void initPlacement(float spawnX, float spawnY) {
        this.origin = new Origin(spawnX, spawnY);
        this.boundingBox = new BoundingBox(width, height, origin);
        this.matrix = new Matrix();
    }

    //Uses the the initPlacement method for the location of the uiElement and adds it to the
    //scalables and drawables ArrayLists in Screen
    @Override
    public void place(Screen screen, float placementX, float placementY) {
        initPlacement(placementX, placementY);
        screen.scalables.add(this);
        screen.drawables.add(this);
    }

    //Checks if the object exists in the arrays and then removes them
    @Override
    public void remove(Screen screen) {
        if (screen.drawables.contains(this))
            screen.drawables.remove(this);
    }

    @Override
    public void scale(float scaleFactorX, float scaleFactorY) {
        this.getMatrix().postScale((this.getWidth() / this.getBitmap().getWidth()) * scaleFactorX,
                (this.getHeight() / this.getBitmap().getHeight()) * scaleFactorY);
        this.getMatrix().postRotate(0, scaleFactorX * this.getBitmap().getWidth()/ 2.0f,
                scaleFactorY * this.getBitmap().getHeight() / 2.0f);
        this.getMatrix().postTranslate((this.getOrigin().x - this.getWidth() / 2) * scaleFactorX,
                (this.getOrigin().y - this.getHeight() / 2) * scaleFactorY);
    }

    //Getters and Setters
    Bitmap getImage() { return image; }

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

    //Method to place the UI Element on screen based on the middle of the object
    //takes in a position and calculated with the origin
    public void initPlacement(float spawnX, float spawnY) {
        this.origin = new Origin(spawnX, spawnY);
        this.boundingBox = new BoundingBox(width, height, origin);
        this.matrix = new Matrix();
    }


    @Override
    public void place(Screen screen, float placementX, float placementY) {
        initPlacement(placementX, placementY);
        screen.getScalables().add(this);
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
        this.getMatrix().postScale((this.getWidth() / this.getBitmap().getWidth()) * scaleFactorX,
                (this.getHeight() / this.getBitmap().getHeight()) * scaleFactorY);
        this.getMatrix().postRotate(0, scaleFactorX * this.getBitmap().getWidth()/ 2.0f,
                scaleFactorY * this.getBitmap().getHeight() / 2.0f);
        this.getMatrix().postTranslate((this.getOrigin().getOriginX() - this.getWidth() / 2) * scaleFactorX,
                (this.getOrigin().getOriginY() - this.getHeight() / 2) * scaleFactorY);
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

    public void setHalfWidth(float halfWidth) {
        this.halfWidth = halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public void setHalfHeight(float halfHeight) {
        this.halfHeight = halfHeight;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
