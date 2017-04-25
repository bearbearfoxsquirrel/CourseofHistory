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
    public Matrix matrix;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public UIElement(Screen screen, Bitmap bitmap, float width, float height){
        this.currentScreen = screen;
        this.image = bitmap;
        this.width = width;
        this.halfWidth = (width / 2);
        this.height = height;
        this.halfHeight = (height / 2);
    }


    public void initPlacement(float spawnX, float spawnY) {
        this.origin = new Origin(spawnX, spawnY);
        this.boundingBox = new BoundingBox(width, height, origin);
        this.matrix = new Matrix();
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(image, matrix, paint);
    }


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

    @Override
    public void place(Screen screen, float placementX, float placementY) {
        initPlacement(placementX, placementY);
        screen.scalables.add(this);
        screen.drawables.add(this);
    }

    @Override
    public void remove(Screen screen) {
        //Checks if the object exists in the arrays and then removes them
        if (screen.drawables.contains(this))
            screen.drawables.remove(this);
    }
}
