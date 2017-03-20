package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Random;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;

/**
 * Created by Jordan on 01/03/2017.
 */

public class Coin extends Sprite
{

    private Random rand; //for generating random number for flipping
    public Result faceUp = null;

    public enum Result
    {
        HEADS, TAILS
    }

    public Coin(Bitmap bitmap, int width, int height)
    {
        super(bitmap, width, height);
        rand = new Random();
    }

    public void flipCoin()
    {
        int randomNumber = rand.nextInt(2); //random number between 0 and 1

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
    public void update(float deltaTime)
    {
        super.update(deltaTime);

//        if(inputBuddy.getTouchEvents() != null)
//        {
//            for (Input.TouchEvent touchEvent: inputBuddy.getTouchEvents())
//            {
//                if(this.boundingBox.isTouchOn(touchEvent))
//                {
//                    switch (touchEvent.type)
//                    {
//                        case Input.TouchEvent.TOUCH_DOWN:
//                            flipCoin();
//                            Log.d("FLIPPED", "" + faceUp);
//                            break;
//                    }
//                }
//            }
//        }
    }
}
