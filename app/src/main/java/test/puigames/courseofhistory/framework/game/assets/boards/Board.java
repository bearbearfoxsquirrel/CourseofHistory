package test.puigames.courseofhistory.framework.game.assets.boards;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.game.CardArea;
import test.puigames.courseofhistory.framework.game.PlayArea;
import test.puigames.courseofhistory.framework.game.assets.CardHand;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by 40123577 on 14/11/2016.
 */


public class Board extends Sprite implements Drawable {

    //public PlayArea[] playAreas;
    public PlayArea[] playAreas = new PlayArea[2];
    public CardHand[] cardHands = new CardHand[2];
    private float areaPaddingX = 10.0f;
    private float areaPaddingY = 6.0f;

    //spawnX = 480/2, spawnY = 320/2
    public Board(Bitmap bitmap){
        super(bitmap, 480, 320);
    }

    @Override
    public void draw(Canvas canvas, float lastFrameTime) {
        super.draw(canvas, lastFrameTime);

    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        Log.d("board update called", "");

        //update hands


        //keep cards inside board bounds

    }

    @Override
    public void spawnObject(float spawnX, float spawnY){
        super.spawnObject(spawnX, spawnY);
        //player 1 - human: player one - bottom half of screen
        //spawnX = boundingBox.left + areaPaddingX
        //spawnY = halfWidth + areaPaddingY
        //width = 460, height = 140
        for (int i = 0; i < cardHands.length; i++) {
            playAreas[i] = new PlayArea(460, 140, 480 / 2, 320 / 4);
            if(i == 0) {
                cardHands[i] = new CardHand(240f, 288f);
            }else{
                cardHands[i] = new CardHand(240f, 65f);
            }
        }

        //player 2 - opponent: AI/other player - top half of screen
        //spawnX = boundingBox.left + areaPaddingX
        //spawnY = boundingBox.top
        //width = 460, height = 140
        //playAreas[1] = new PlayArea(460, 140);
    }
}

