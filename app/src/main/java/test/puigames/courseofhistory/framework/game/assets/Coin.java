package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;

import java.util.Random;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Created by Jordan on 01/03/2017.
 */

public class Coin extends Sprite {

    private Random rand; //for generating random number for flipping
    public Result faceUp = null;
    public Bitmap coinSides[]; //Note 0 is heads and 1 is tails

    public enum Result
    {
        HEADS, TAILS
    }

    public Coin(Screen screen, Bitmap[] coinSides, int width, int height) {
        super(screen, coinSides[0], width, height);
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
}
