package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Created by mjtod on 02/05/2017.
 */


public class StatImage extends Sprite {
    private Number state = Number.UNASSIGNED;
    private Bitmap[] numberImages = new Bitmap[10];

    public StatImage(Screen screen, Bitmap[] numImages, int width, int height){
        super(screen, numImages[0], width, height);

        for(int i= 0; i < numImages.length; i++){
            numberImages[i] = numImages[i];
        }
        UpdateStats();
    }

    @Override
    public void setRotation(float rotation) {
        super.setRotation(rotation);
    }

    public enum Number
    {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, UNASSIGNED
    }

    public void UpdateStats(){
        switch(state)
        {
            case ZERO:
                bitmap = numberImages[0];
                break;
            case ONE:
                bitmap = numberImages[1];
                break;
            case TWO:
                bitmap = numberImages[2];
                break;
            case THREE:
                bitmap = numberImages[3];
                break;
            case FOUR:
                bitmap = numberImages[4];
                break;
            case FIVE:
                bitmap = numberImages[5];
                break;
            case SIX:
                bitmap = numberImages[6];
                break;
            case SEVEN:
                bitmap = numberImages[7];
                break;
            case EIGHT:
                bitmap = numberImages[8];
                break;
            case NINE:
                bitmap = numberImages[9];
                break;
            default:
                bitmap = numberImages[0];
                break;

        }
    }



    public Number getState() {
        return state;
    }

    public void setState(Number state) {
        this.state = state;
    }

    public Bitmap[] getNumberImages() {
        return numberImages;
    }

    public void setNumberImages(Bitmap[] numberImages) {
        this.numberImages = numberImages;
    }

    @Override
    public void draw(Canvas canvas, float deltatime){
        super.draw(canvas, deltatime);
    }
}
