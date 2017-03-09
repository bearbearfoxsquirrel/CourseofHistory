package test.puigames.courseofhistory.framework.game.assets.boards;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.game.PlayArea;
import test.puigames.courseofhistory.framework.game.assets.Deck;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by 40123577 on 14/11/2016.
 */


public class Board extends Sprite implements Drawable {

    //public PlayArea[] playAreas;
    public PlayArea playArea;
    private float areaPaddingX = 10.0f;
    private float areaPaddingY = 6.0f;

    public Board(Bitmap bitmap){
        super(bitmap, 480/2.f, 320/2.f, 480, 320);

        playArea = new PlayArea(boundingBox.left + areaPaddingX, halfWidth + areaPaddingY,
                460, 140);
    }

    @Override
    public void draw(Canvas canvas, float lastFrameTime) {
        super.draw(canvas, lastFrameTime);
    }

    @Override
    public void update(InputBuddy inputBuddy, float deltaTime) {
        super.update(inputBuddy, deltaTime);
        playArea.update(inputBuddy, deltaTime);
    }


    /**
     * Only needed to be called if the bounding box you are testing against
     * this one is not encapsulated
     * @see test.puigames.courseofhistory.framework.engine.gameobjects
     * .properties.BoundingBox.isEncapsulated();
     *
     * @this - the game object's bounding box we are testing against sprite
     *          we are containing the sprite within @this' bounding box
     * @param sprite - sprite you want to keep in bounds
     */
    private void keepInsideBoundingBox(Sprite sprite)
    {
        if(sprite.boundingBox.left < this.boundingBox.left)
            sprite.origin.x = (this.boundingBox.left + sprite.halfWidth);
        else if(sprite.boundingBox.right > this.boundingBox.right)
            sprite.origin.x = (this.boundingBox.right - sprite.halfWidth);
        else if(sprite.boundingBox.top < this.boundingBox.top)
            sprite.origin.y = (this.boundingBox.top + sprite.halfHeight);
        else if(sprite.boundingBox.bottom > this.boundingBox.bottom)
            sprite.origin.y = (this.boundingBox.bottom - sprite.halfHeight);

//        if(this.boundingBox.left < boundingBox.left)
//            this.origin.x = (boundingBox.left + width/2);
//        else if(this.boundingBox.right > boundingBox.right)
//            this.origin.x = (boundingBox.right - width/2);
//        else if(this.boundingBox.top < boundingBox.top)
//            this.origin.y = (boundingBox.top + height/2);
//        else if(this.boundingBox.bottom > boundingBox.bottom)
//            this.origin.y = (boundingBox.bottom - height/2);
    }
}
