package test.puigames.courseofhistory.framework.engine.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;

/**
 * Created by Michael on 21/11/2016.
 */

public abstract class Sprite extends GameObject implements Drawable {
    public Bitmap image;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected float velocity;
    protected float acceleration;

    public Sprite(Bitmap bitmap, float spawnX, float spawnY, int width, int height) {
        super(spawnX, spawnY,  width,  height);
        this.image = bitmap;
        this.velocity = 0;
        this.acceleration = 0;
    }

    @Override
    public void update(InputBuddy inputBuddy, float deltaTime) {
        super.update(inputBuddy, deltaTime);
    }

    @Override
    //drawing to the canvas for all sprites
    public void draw(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(image, matrix, paint);
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