package test.puigames.courseofhistory.framework.game.assets.boards;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.game.assets.PlayArea;
import test.puigames.courseofhistory.framework.game.assets.CardArea;
import test.puigames.courseofhistory.framework.game.assets.CardHand;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by 40123577 on 14/11/2016.
 */


public class Board extends Sprite implements Drawable {

    //public PlayArea[] playAreas;
    public PlayArea[] playAreas = new PlayArea[2];
    public CardHand[] cardHands = new CardHand[2];
    private int playAreaWidth = 460;
    private int playAreaHeight = 90;

    //spawnX = 480/2, spawnY = 320/2
    public Board(Bitmap bitmap){
        super(bitmap, 480, 320);
        overlapAllowance = 1.0f;
    }

    @Override
    public void draw(Canvas canvas, float lastFrameTime) {
        super.draw(canvas, lastFrameTime);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
//        Log.d("board update called", "");
//        Log.d("board update", "called");
        for(PlayArea playArea : playAreas)
            playArea.update(deltaTime); //update play areas

        //update hands
        for(CardHand cardhand : cardHands)
            cardhand.update(deltaTime); //update play areas

    }

    @Override
    public void spawnObject(float spawnX, float spawnY){
        super.spawnObject(spawnX, spawnY);
        //player 1 - human: player one - bottom half of screen
        //spawnX = boundingBox.left + areaPaddingX
        //spawnY = halfWidth + areaPaddingY
        //width = 460, height = 140
        for (int i = 0; i < cardHands.length; i++) {
            if(i == 0) {
                cardHands[i] = new CardHand(240.f, 32.25f);
                playAreas[0] = new PlayArea(playAreaWidth, playAreaHeight, (int)halfWidth, (int)(114));
            }else{
                cardHands[i] = new CardHand(240.f, 287.75f);
                playAreas[1] = new PlayArea(playAreaWidth, playAreaHeight, (int)halfWidth, (int)(208));
            }
            Log.d("cardhand " + i, "" + cardHands[i].boundingBox.toString());
            Log.d("playArea " + i, "" + playAreas[i].boundingBox.toString());
        }

        //player 2 - opponent: AI/other player - top half of screen
        //spawnX = boundingBox.left + areaPaddingX
        //spawnY = boundingBox.top
        //width = 460, height = 140
        //playAreas[1] = new PlayArea(460, 140);

//        playAreas[0] = new PlayArea(playAreaWidth, playAreaHeight, (int)halfWidth, (int)
//                (halfHeight * 0.50));
//
//        playAreas[1] = new PlayArea(playAreaWidth, playAreaHeight, (int)halfWidth, (int)
//                (halfHeight * 0.75));
    }
}

