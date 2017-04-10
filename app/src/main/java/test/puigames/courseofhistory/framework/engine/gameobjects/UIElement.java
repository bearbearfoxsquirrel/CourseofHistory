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
 * Created by Christopher on 13/03/2017.
 */

public abstract class UIElement implements Drawable
{
    public Bitmap image;
    public float width, halfWidth;
    public float height, halfHeight;
    public BoundingBox boundingBox;
    public Origin origin;
    public Matrix matrix;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public UIElement(Bitmap bitmap, float width, float height){
        this.image = bitmap;
        this.width = width;
        this.halfWidth = (width / 2);
        this.height = height;
        this.halfHeight = (height / 2);
    }


    public void placeUIElement(float spawnX, float spawnY)
    {
        this.origin = new Origin(spawnX, spawnY);
        this.boundingBox = new BoundingBox(width, height, origin);
        this.matrix = new Matrix();
    }


    public void update(InputBuddy inputBuddy, float deltaTime){
        this.boundingBox.setBoundingBox(this.origin);
    }


    public void update(float deltaTime)
    {
        this.boundingBox.setBoundingBox(this.origin);
    }


    @Override
    public void draw(Canvas canvas, float deltaTime)
    {
        canvas.drawBitmap(image, matrix, paint);
    }


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
