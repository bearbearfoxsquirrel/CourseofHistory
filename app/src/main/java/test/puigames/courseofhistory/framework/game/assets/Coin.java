package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;

import java.util.Random;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Created by Jordan on 01/03/2017.
 */

public class Coin extends Sprite
{
    public static final int COIN_SIZE = 50;
    public static final float COIN_FLIP_TIME = 4.f;

    private Random rand; //for generating random number for flipping
    private Result faceUp = null;
    private Bitmap coinSides[]; //Note 0 is heads and 1 is tails

    public enum Result
    {
        HEADS, TAILS
    }

    public Coin(Screen screen, Bitmap[] coinSides, int width, int height)
    {
        super(screen, coinSides[0], width, height);
        this.coinSides = coinSides;
        this.rand = new Random();
    }

    public Coin(Screen screen, Bitmap[] coinSides)
    {
        super(screen, coinSides[0], COIN_SIZE, COIN_SIZE);
        this.coinSides = coinSides;
        this.rand = new Random();
    }


    public void flipCoin()
    {
        int randomNumber = rand.nextInt(2); //rando number between 0 and 1
        switch(randomNumber)
        {
            case 0:
                faceUp = Result.HEADS;
                break;
            case 1:
                faceUp = Result.TAILS;
                break;
            default:
                faceUp = Result.HEADS; //if for some reason the randomNumber is null?
                break;
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public Result getFaceUp() {
        return faceUp;
    }

    public void setFaceUp(Result faceUp) {
        this.faceUp = faceUp;
    }

    public Bitmap[] getCoinSides() {
        return coinSides;
    }

    public void setCoinSides(Bitmap[] coinSides) {
        this.coinSides = coinSides;
    }
}
