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
    public Bitmap image;
    public float width, halfWidth;
    public float height, halfHeight;
    public BoundingBox boundingBox;
    public Origin origin;
    public float rotation;
    public Matrix matrix;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public UIElement(Screen screen, Bitmap bitmap, float width, float height){
        this.currentScreen = screen;
    //Constructor - takes in a bitmap image, along with a width and height
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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public float getPosX() {
        return this.origin.x;
    }

    @Override
    public float getPosY() {
        return this.origin.y;
    }

    @Override
    public float getRotation() {
        return this.rotation;
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

    //Method to setUpGamePiecePositions the UI Element on screen based on the middle of the object
    //takes in a position and calculated with the origin
    public void initPlacement(float spawnX, float spawnY, float rotation) {
        this.origin = new Origin(spawnX, spawnY);
        this.boundingBox = new BoundingBox(width, height, origin);
        this.matrix = new Matrix();
        this.rotation = rotation;
    }

    @Override
    public void place(Screen screen, float placementX, float placementY, float rotation) {
        initPlacement(placementX, placementY, rotation);
        screen.scalables.add(this);
        screen.drawables.add(this);
    }

    @Override
    public void remove(Screen screen) {
        //Checks if the object exists in the arrays and then removes them
        if (screen.drawables.contains(this))
            screen.drawables.remove(this);
    }

    @Override
    public void scale(float scaleFactorX, float scaleFactorY) {
        this.getMatrix().reset();

        this.getMatrix().postScale((this.getWidth() / this.getBitmap().getWidth()) * scaleFactorX,
                (this.getHeight() / this.getBitmap().getHeight()) * scaleFactorY);
        this.getMatrix().postRotate(getRotation(), getWidth() / 2 * scaleFactorX, getHeight() / 2 * scaleFactorY);
        this.getMatrix().postTranslate((this.getOrigin().x - this.getWidth() / 2) * scaleFactorX,
                (this.getOrigin().y - this.getHeight() / 2) * scaleFactorY);
    }
}
