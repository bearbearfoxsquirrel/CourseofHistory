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
    private float width;
    private float height;

    public Deck(Screen screen, Bitmap bitmap) {
        this.currentScreen = screen;
        this.bitmap = bitmap;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        this.height = CharacterCard.getCardHeight();
        this.width = CharacterCard.getCardWidth();
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
    public synchronized Object pop() {
        return super.pop();
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    @Override
    public void place(Screen screen, float placementX, float placementY) {
        this.origin = new Origin(placementX, placementY);
        this.matrix = new Matrix();
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
        this.getMatrix().postScale((this.getWidth() / this.getBitmap().getWidth()) * scaleFactorX,
                (this.getHeight() / this.getBitmap().getHeight()) * scaleFactorY);
        this.getMatrix().postRotate(0, scaleFactorX * this.getBitmap().getWidth()/ 2.0f,
                scaleFactorY * this.getBitmap().getHeight() / 2.0f);
        this.getMatrix().postTranslate((this.getOrigin().getOriginX() - this.getWidth() / 2) * scaleFactorX,
                (this.getOrigin().getOriginY() - this.getHeight() / 2) * scaleFactorY);
    }

    @Override
    public Matrix getMatrix() {
        return this.matrix;
    }

    @Override
    public Origin getOrigin() {
        return this.origin;
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
}



