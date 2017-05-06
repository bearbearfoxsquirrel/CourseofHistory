package test.puigames.courseofhistory.framework.engine.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Vector;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Scalable;

/**
 * Created by Michael on 21/11/2016.
 */

public abstract class Sprite extends GameObject implements Drawable, Scalable.ImageScalable {
    protected Bitmap bitmap;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //set reference default max acceleration adn velocity
    protected final float DEFAULT_MAX_ACCELERATION = Float.MAX_VALUE;
    protected final float DEFAULT_MAX_VELOCITY = Float.MAX_VALUE;

    //acceleration and velocity of sprite, with max values that can be set
    protected Vector velocity = new Vector();
    protected Vector acceleration = new Vector();

    protected float maxAcceleration = DEFAULT_MAX_ACCELERATION;
    protected float maxVelocity = DEFAULT_MAX_VELOCITY;


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
        velocity.add(acceleration.getVectorX() * deltaTime, acceleration.getVectorY() * deltaTime);

        if(velocity.lengthSquared() > maxVelocity * maxVelocity)
        {
            velocity.normalise();
            velocity.multiply(maxVelocity);
        }

        //update position using velocity
        origin.setOriginX(origin.getOriginX() + (velocity.getVectorX() * deltaTime));
        origin.setOriginY(origin.getOriginY() + (velocity.getVectorY() * deltaTime));
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

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    @Override
    public void scale(float scaleFactorX, float scaleFactorY) {
        getMatrix().reset(); // Resets the matrix for
        getMatrix().postScale((width / getBitmap().getWidth()) * scaleFactorX, height / getBitmap().getHeight() * scaleFactorY); //Scales the object/image ratio by the scale factor
        getMatrix().postRotate(rotation, halfWidth * scaleFactorX, halfHeight * scaleFactorY); //Rotates from the middle of the object on the screen
        getMatrix().postTranslate((getPosX() - halfWidth) * scaleFactorX, (getPosY() - halfHeight) * scaleFactorY); //Translates the object by the scale factor
    }

    @Override
    public void place(Screen screen, float placementX, float placementY, float rotation){
        super.place(screen, placementX, placementY, rotation);
        if (!screen.getDrawables().contains(this))
            screen.getDrawables().add(this);
    }

    @Override
    public void remove(Screen screen) {
        super.remove(screen);
        if (screen.getDrawables().contains(this))
            screen.getDrawables().remove(this);
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public float getDEFAULT_MAX_ACCELERATION() {
        return DEFAULT_MAX_ACCELERATION;
    }

    public float getDEFAULT_MAX_VELOCITY() {
        return DEFAULT_MAX_VELOCITY;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }

    public float getMaxAcceleration() {
        return maxAcceleration;
    }

    public void setMaxAcceleration(float maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }
}