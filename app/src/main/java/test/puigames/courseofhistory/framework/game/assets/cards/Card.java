package test.puigames.courseofhistory.framework.game.assets.cards;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;

/**
 * Created by Jordan on 10/11/2016.
 */

public class Card extends Sprite {
    //private variables

    //constructor
    public Card(Bitmap cardImage, float spawnX, float spawnY) {
        super(cardImage, (spawnX), (spawnY), 60, 83);
        //moves card to position on board initially
    }

    public void translateCard(float updatedX, float updatedY) {
        this.origin.x -= ((origin.x - updatedX) / 2);
        this.origin.y -= ((origin.y - updatedY) / 2);
    }

    //TODO: make card collision checks on not dragged
    //FIXME: smooth out bounds resolution to be less jaggy
    //TODO:: investigate time and frame timing
    public void update(InputBuddy inputBuddy, float deltaTime, Card[] cards, Board board) {
        if(!this.boundingBox.isEncapsulated(board.boundingBox))
            keepInsideBoundingBox(board.boundingBox);
        checkForAndResolveCollision(cards);
        super.update(inputBuddy, deltaTime);
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
