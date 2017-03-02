package test.puigames.courseofhistory.framework.game.assets.cards;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input.TouchEvent;

/**
 * Created by Jordan on 10/11/2016.
 */

public class Card extends Sprite {
    //private variables

    //REMOVE THIS AT SOME POINT BECAUSE INPUT AND STUFF AHHH
    public boolean isOnTouch;

    //constructor
    public Card(Bitmap cardImage, float spawnX, float spawnY) {
        super(cardImage, (spawnX), (spawnY), 60, 83);
        //moves card to position on board initially
    }

    //TODO: make card collision checks on not dragged
    //FIXME: smooth out bounds resolution to be less jaggy
    //TODO:: investigate time and frame timing
    public void update(InputBuddy inputBuddy, float deltaTime, Card[] cards, Board board) {
        if(!this.boundingBox.isEncapsulated(board.boundingBox))
            keepInsideBoundingBox(board.boundingBox);
        checkForAndResolveCollision(cards);
        onTouch(inputBuddy);
        super.update(inputBuddy, deltaTime);
    }

    //draw
    @Override
    public void draw(Canvas canvas, float lastFrameTime) {
        super.draw(canvas, lastFrameTime);
    }

    /**
     * Used to handle (hopefully) any sort of touch events that a card might need to handle
     * - currently only does DRAGGED type events.
     *
     * @TOUCH_DRAGGED - sets card origin(x, y) to where the touch event occurred, plus some
     *                  smoothing to avoid it jumping to your finger, as much.
     * @param inputBuddy - our little friend, passed down from level. Used to get touch events
     */
    private void onTouch(InputBuddy inputBuddy)
    {
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
                            isOnTouch = true;
                            break;
                        //REMOVE THIS AT SOME POINT BECAUSE INPUT AND STUFF
                        case TouchEvent.TOUCH_UP:
                            isOnTouch = false;
                            break;
                    }
                }
            }
        }
    }

    private void checkForAndResolveCollision(Card[] cards)
    {
        for(Card card : cards)
        {
            if(this == card)
                continue;
            else
                boundingBox.getCollisionDetector().determineAndResolveCollision(this, card);
        }
    }

    /**
     * Only needed to be called if the bounding box you are testing against
     * this one is not encapsulated
     * @see test.puigames.courseofhistory.framework.engine.gameobjects
     * .properties.BoundingBox.isEncapsulated();
     *
     * @this - the game object's bounding box we are testing against boundingBox
     *         its origin (x, y) will be altered accordingly to keep it within the
     *         bounding box of the passed variable.
     * @param boundingBox - bounding box you want to be the outer bounds
     */
    private void keepInsideBoundingBox(BoundingBox boundingBox)
    {
        if(this.boundingBox.left < boundingBox.left)
            this.origin.x = (boundingBox.left + width/2);
        else if(this.boundingBox.right > boundingBox.right)
            this.origin.x = (boundingBox.right - width/2);
        else if(this.boundingBox.top < boundingBox.top)
            this.origin.y = (boundingBox.top + height/2);
        else if(this.boundingBox.bottom > boundingBox.bottom)
            this.origin.y = (boundingBox.bottom - height/2);
    }
}
