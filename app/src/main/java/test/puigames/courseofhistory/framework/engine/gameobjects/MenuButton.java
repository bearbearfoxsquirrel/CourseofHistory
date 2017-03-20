package test.puigames.courseofhistory.framework.engine.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;

/**
* Created by Christopher on 09/03/2017.
*/

public class MenuButton extends uiElement implements Drawable{

    protected Bitmap buttonImage;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MenuButton(Bitmap buttonImage, Paint paint){
        super(0, 0);
        this.buttonImage = buttonImage;
        this.boundingBox = new BoundingBox(width, height, origin);
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(buttonImage, matrix, paint);
    }

    public void update(InputBuddy inputBuddy, float deltaTime){
        super.update(inputBuddy, deltaTime);

//        for(Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
////            if(this.boundingBox.isTouchOn(touchEvent)) {
//////                changeScreen(screenName);
////            }
//        }
    }

    public Bitmap getButtonImage(){
        return buttonImage;
    }

    public void setButtonImage(Bitmap buttonImage) {
        this.buttonImage = buttonImage;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
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

}
