package test.puigames.courseofhistory.framework.engine.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Vector;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Scalable;

/**
 * Created by Michael on 21/11/2016.
 */

public abstract class Sprite extends GameObject implements Drawable, Scalable.ImageScalable {
    public Bitmap bitmap;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //set reference default max acceleration adn velocity
    protected final float DEFAULT_MAX_ACCELERATION = Float.MAX_VALUE;
    protected final float DEFAULT_MAX_VELOCITY = Float.MAX_VALUE;

    //acceleration and velocity of sprite, with max values that can be set
    public Vector velocity = new Vector();
    public Vector acceleration = new Vector();

    public float maxAcceleration = DEFAULT_MAX_ACCELERATION;
    public float maxVelocity = DEFAULT_MAX_VELOCITY;


    public Sprite(Screen screen, Bitmap bitmap, int width, int height) {
        super(screen, width,  height);
        this.bitmap = bitmap;
    }

    //deals with acceleration and velocity of sprite
    private void handleMovement(float deltaTime)
    {
        //ensure max acceleration isn't exceeded
        if(acceleration.lengthSquared() > maxAcceleration * maxAcceleration)
        {
            acceleration.normalise();
            acceleration.multiply(maxAcceleration);
        }

        //update velocity using acceleration
        //ensure max velocity isn't exceeded
        velocity.add(acceleration.x * deltaTime, acceleration.y * deltaTime);

        if(velocity.lengthSquared() > maxVelocity * maxVelocity)
        {
            velocity.normalise();
            velocity.multiply(maxVelocity);
        }

        //update position using velocity
        origin.x += (velocity.x * deltaTime);
        origin.y += (velocity.y * deltaTime);
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        handleMovement(deltaTime);
    }

    @Override
    //drawing to the canvas for all sprites
    public void draw(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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
        this.width = width; this.halfWidth = (this.width/2);
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height; this.halfHeight = (this.height/2);
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
    public void scale(float scaleFactorX, float scaleFactorY) {
        this.getMatrix().postScale((this.getWidth() / this.getBitmap().getWidth()) * scaleFactorX,
                (this.getHeight() / this.getBitmap().getHeight()) * scaleFactorY);
        this.getMatrix().postRotate(0, scaleFactorX * this.getBitmap().getWidth()/ 2.0f,
                scaleFactorY * this.getBitmap().getHeight() / 2.0f);
        this.getMatrix().postTranslate((this.getOrigin().x - this.getWidth() / 2) * scaleFactorX,
                (this.getOrigin().y - this.getHeight() / 2) * scaleFactorY);
    }

    @Override
    public void place(Screen screen, float placementX, float placementY){
        super.place(screen, placementX, placementY);
        if (!screen.drawables.contains(this))
            screen.drawables.add(this);
    }

    @Override
    public void remove(Screen screen) {
        super.remove(screen);
        if (screen.drawables.contains(this))
            screen.drawables.remove(this);
    }
}