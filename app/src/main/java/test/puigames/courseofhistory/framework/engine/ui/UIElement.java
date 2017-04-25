package test.puigames.courseofhistory.framework.engine.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;

/**
 * Created by Christopher on 13/03/2017.
 */

public abstract class UIElement implements Drawable {

    public Bitmap image;
    public float width, halfWidth;
    public float height, halfHeight;
    public BoundingBox boundingBox;
    public Origin origin;
    public Matrix matrix;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


    //Constructor - takes in a bitmap image, along with a width and height
    public UIElement(Bitmap bitmap, float width, float height){
        this.image = bitmap;
        this.width = width;
        this.halfWidth = (width / 2);
        this.height = height;
        this.halfHeight = (height / 2);
    }


    //Method to place the UI Element on screen based on the middle of the object
    //takes in a position and calculated with the origin
    public void placeUIElement(float spawnX, float spawnY) {
        this.origin = new Origin(spawnX, spawnY);
        this.boundingBox = new BoundingBox(width, height, origin);
        this.matrix = new Matrix();
    }


    //Sets the bounding box around the origin
    public void update(float deltaTime) {
        this.boundingBox.setBoundingBox(this.origin);
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

}
