package test.puigames.courseofhistory.framework.game.cards;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.Drawable;
import test.puigames.courseofhistory.framework.engine.InputBuddy;
import test.puigames.courseofhistory.framework.engine.Sprite;
import test.puigames.courseofhistory.framework.engine.resourceloading.GraphicsIO;
import test.puigames.courseofhistory.framework.input.Input.TouchEvent;

/**
 * Created by Jordan on 10/11/2016.
 */

public class Card extends Sprite implements Drawable {
    //private variables
    GraphicsIO graphicsIO;

    //constructor
    public Card(Bitmap cardImage, float spawnX, float spawnY) {
        super(cardImage, (spawnX), (spawnY), 225, 341);
        matrix.setTranslate(origin.x - width/2, origin.y - width/2);
    }

    //possibly the legit touch input update one
    public void update(InputBuddy inputBuddy, float lastFrameTime) {
        if(inputBuddy.getTouchEvents() != null) //null check for switching screens
        {
            for (TouchEvent touchEvent : inputBuddy.getTouchEvents())
            {
                if (this.boundingBox.isTouchOn(touchEvent))
                {
                    switch (touchEvent.type)
                    {
                        case TouchEvent.TOUCH_DRAGGED:
                            this.origin.x -= ((origin.x - touchEvent.x) / 2);
                            this.origin.y -= ((origin.y - touchEvent.y) / 2);
                            break;
                    }
                }
                super.update(inputBuddy, lastFrameTime);
            }
        }
    }

    //draw
    @Override
    public void draw(Canvas canvas, float lastFrameTime) {
        super.draw(canvas, lastFrameTime);
    }

}
