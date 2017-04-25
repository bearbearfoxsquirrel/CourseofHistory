package test.puigames.courseofhistory.framework.engine.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Vector;

/**
 * Created by Michael on 21/11/2016.
 */

public abstract class Sprite extends GameObject implements Drawable {
    public Bitmap image;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //set reference default max acceleration adn velocity
    protected final float DEFAULT_MAX_ACCELERATION = Float.MAX_VALUE;
    protected final float DEFAULT_MAX_VELOCITY = Float.MAX_VALUE;

    //acceleration and velocity of sprite, with max values that can be set
    public Vector velocity = new Vector();
    public Vector acceleration = new Vector();

    public float maxAcceleration = DEFAULT_MAX_ACCELERATION;
    public float maxVelocity = DEFAULT_MAX_VELOCITY;


    public Sprite(Bitmap bitmap, int width, int height) {
        super(width,  height);
        this.image = bitmap;

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



    public void update(float deltaTime) {
        super.update(deltaTime);
        handleMovement(deltaTime);
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