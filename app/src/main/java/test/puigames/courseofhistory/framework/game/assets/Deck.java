package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.Random;
import java.util.Stack;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Placeable;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Scalable;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by mjtod on 06/03/2017.
 */

public class Deck extends Stack implements Placeable, Scalable.ImageScalable, Drawable {
    private Bitmap bitmap;
    private Paint paint;
    private Screen currentScreen;
    private Matrix matrix;
    private Origin origin;

    private float rotation;

    private float width, halfWidth;
    private float height, halfHeight;

    public Deck(Screen screen, Bitmap bitmap) {
        this.currentScreen = screen;
        this.bitmap = bitmap;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.rotation = 0;

        this.height = CharacterCard.CARD_HEIGHT; this.halfHeight = height / 2;
        this.width = CharacterCard.CARD_WIDTH; this.halfWidth  = width / 2;
    }

    public void shuffle(CharacterCard[] characterCards) {
        Random random = new Random();
        CharacterCard[] temp = new CharacterCard[characterCards.length];
        for(int i = characterCards.length -1; i >=0; i--){
            int ranNum = random.nextInt(i+1);

            temp[i] = characterCards[i];
            characterCards[i] = characterCards[ranNum];
            characterCards[ranNum] = temp[i];
        }
    }

    public void setUpDeck(CharacterCard[] characterCards){
        shuffle(characterCards);
        for(int i = 0; i < characterCards.length; i++){
            push(characterCards[i]);
        }
    }

    @Override
    //Object pop to popping a character card
    public synchronized CharacterCard pop() {
        return (CharacterCard) super.pop();

    }

    private void initPlacement(float placementX, float placementY, float rotation) {
        this.origin = new Origin(placementX, placementY);
        this.matrix = new Matrix();
        this.rotation = rotation;
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    @Override
    public void place(Screen screen, float placementX, float placementY, float rotation) {
        initPlacement(placementX, placementY, rotation);
        //Checks if the deck is in the list of things to be drawn and scaled
        //And removes them if they are not
        if (!screen.getDrawables().contains(this))
            screen.getDrawables().add(this);

        if (!screen.getScalables().contains(this))
            screen.getScalables().add(this);
    }

    @Override
    public void remove(Screen screen) {
        //Checks if the deck is in the list of things to be drawn and scaled
        //And removes them if they are
        if (screen.getDrawables().contains(this))
            screen.getDrawables().remove(this);

        if (screen.getScalables().contains(this))
            screen.getScalables().remove(this);
    }

    @Override
    public void scale(float scaleFactorX, float scaleFactorY) {
        getMatrix().reset(); // Resets the matrix for
        getMatrix().postScale((getWidth() / getBitmap().getWidth()) * scaleFactorX, getHeight() / getBitmap().getHeight() * scaleFactorY); //Scales the object/image ratio by the scale factor
        getMatrix().postRotate(getRotation(), getHalfWidth() * scaleFactorX, getHalfHeight() * scaleFactorY); //Rotates from the middle of the object on the screen
        getMatrix().postTranslate((getPosX() - getWidth() / 2.f) * scaleFactorX, (getPosY() - getHeight() / 2.f) * scaleFactorY); //Translates the object by the scale factor
    }

    @Override
    public Matrix getMatrix() {
        return this.matrix;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public float getPosX() {
        return origin.getOriginX();
    }

    @Override
    public float getPosY() {
        return origin.getOriginY();
    }

    @Override
    public float getRotation() {
        return this.rotation;
    }

    @Override
    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getHalfWidth() {
        return halfWidth;
    }
}



