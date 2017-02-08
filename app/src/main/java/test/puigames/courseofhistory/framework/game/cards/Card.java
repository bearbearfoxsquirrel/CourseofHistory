package test.puigames.courseofhistory.framework.game.cards;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.Drawable;
import test.puigames.courseofhistory.framework.engine.InputBuddy;
import test.puigames.courseofhistory.framework.engine.Sprite;
import test.puigames.courseofhistory.framework.engine.resourceloading.GraphicsIO;
import test.puigames.courseofhistory.framework.game.Board.Board;
import test.puigames.courseofhistory.framework.input.Input.TouchEvent;

/**
 * Created by Jordan on 10/11/2016.
 */

public class Card extends Sprite implements Drawable {
    //private variables

    //constructor
    public Card(Bitmap cardImage, float spawnX, float spawnY) {
        super(cardImage, (spawnX), (spawnY), 225, 341);
        //moves card to position on board initially
        matrix.setTranslate(origin.x - width/2, origin.y - width/2);
    }

    //TODO: make card collision checks on not dragged
    //FIXME: smooth out bounds resolution to be less jaggy
    //TODO:: investigate time and frame timing
    public void update(InputBuddy inputBuddy, float deltaTime, Card[] cards, Board board) {
        if(!this.boundingBox.isEncapsulated(board.boundingBox))
            resolveOffBoard(board);
        checkForAndResolveCollision(cards);
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
                super.update(inputBuddy, deltaTime);
            }
        }
    }

    //draw
    @Override
    public void draw(Canvas canvas, float lastFrameTime) {
        super.draw(canvas, lastFrameTime);
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
     * Only called if detected card is not encapsulated by board
     * @param board - game board of level
     *              bounds are x: 0 -> screen width
     *                         y: 0 -> screen height
     */
    private void resolveOffBoard(Board board)
    {
        if(this.boundingBox.getLeft() < board.boundingBox.getLeft())
            this.origin.x = (board.boundingBox.getLeft() + width/2);
        else if(this.boundingBox.getRight() > board.boundingBox.getRight())
            this.origin.x = (board.boundingBox.getRight() - width/2);
        else if(this.boundingBox.getTop() < board.boundingBox.getTop())
            this.origin.y = (board.boundingBox.getTop() + height/2);
        else if(this.boundingBox.getBottom() > board.boundingBox.getBottom())
            this.origin.y = (board.boundingBox.getBottom() - height/2);
    }
}
