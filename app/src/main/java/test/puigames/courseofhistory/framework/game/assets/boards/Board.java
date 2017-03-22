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
    public PlayArea[] playAreas = new PlayArea[2];
    private float areaPaddingX = 10.0f;
    private float areaPaddingY = 6.0f;

    //spawnX = 480/2, spawnY = 320/2
    public Board(Bitmap bitmap){
        super(bitmap, 480, 320);

        //player 1 - human: player one - bottom half of screen
            //spawnX = boundingBox.left + areaPaddingX
            //spawnY = halfWidth + areaPaddingY
            //width = 460, height = 140
        playAreas[0] = new PlayArea(460, 140);

        //player 2 - opponent: AI/other player - top half of screen
            //spawnX = boundingBox.left + areaPaddingX
            //spawnY = boundingBox.top
            //width = 460, height = 140
        playAreas[1] = new PlayArea(460, 140);
    }

    @Override
    public void draw(Canvas canvas, float lastFrameTime) {
        super.draw(canvas, lastFrameTime);
    }

    public void update(float deltaTime, CharacterCard[] characterCards) {
        super.update(deltaTime);

        for(PlayArea playArea : playAreas)
            playArea.update(deltaTime); //update play areas

        //update hands

        //keep cards inside board bounds
        for(CharacterCard characterCard : characterCards)
            if(!characterCard.boundingBox.isEncapsulated(this.boundingBox))
                this.boundingBox.getCollisionDetector().keepInsideBoundingBox(this, characterCard);
    }
}
