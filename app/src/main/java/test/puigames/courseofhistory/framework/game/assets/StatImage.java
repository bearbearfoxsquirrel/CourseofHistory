package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;

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

        for(int i= 0; i < numImages.length; i++)
            numberImages[i] = numImages[i];

        updateStats();
    }

    public enum Number
    {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, UNASSIGNED
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updateStats();
    }

    public void updateStats(){
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

    public Number fromIntToStatState(int number){
        switch(number) {
            case 0:
                return Number.ZERO;
            case 1:
                return Number.ONE;
            case 2:
                return Number.TWO;
            case 3:
                return Number.THREE;
            case 4:
                return Number.FOUR;
            case 5:
                return Number.FIVE;
            case 6:
                return Number.SIX;
            case 7:
                return Number.SEVEN;
            case 8:
                return Number.EIGHT;
            case 9:
                return Number.NINE;
            default:
                return Number.ZERO;
        }
    }
    @Override
    public void setRotation(float rotation) {
        super.setRotation(rotation);
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
}
