package test.puigames.courseofhistory.framework.engine.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;

/**
 * Created by Michael on 21/11/2016.
 */

public abstract class Sprite implements Drawable {
    public float width;
    protected float height;
    public BoundingBox boundingBox;
    public Origin origin;
    protected Bitmap image;
    protected Matrix matrix;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Sprite(Bitmap bitmap, float spawnX, float spawnY, int width, int height) {
        this.width = width;
        this.height = height;
        this.origin = new Origin(spawnX, spawnY);
        this.boundingBox = new BoundingBox(width, height, origin);
        this.image = bitmap;
        this.matrix = new Matrix();
    }

    @Override
    //drawing to the canvas for all sprites
    public void draw(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(image, matrix, paint);
    }

    @Override
    //updating sprites that take user input
    public void update(InputBuddy inputBuddy, float deltaTime) {
        //This updates the card to draw from where the origin is
        //Matrix translates bitmaps etc. to where the origin has moved to
        matrix.setTranslate(origin.x - width/2, origin.y - width/2);
        this.boundingBox.setBoundingBox(this.origin);
    }

    //Updating sprites that don't take user input
    public void update(float lastFrameTime) {
        matrix.setTranslate(origin.x, origin.y);
        boundingBox.setBoundingBox(this.origin);
    }

    public Bitmap getImage() {
        return image;
    }

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