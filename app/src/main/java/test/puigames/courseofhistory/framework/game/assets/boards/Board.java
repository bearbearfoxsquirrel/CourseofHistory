package test.puigames.courseofhistory.framework.game.assets.boards;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.CardHand;
import test.puigames.courseofhistory.framework.game.assets.PlayArea;

/**
 * Created by 40123577 on 14/11/2016.
 */


public class Board extends Sprite implements Drawable {
    private PlayArea[] playAreas = new PlayArea[2];
    private CardHand[] cardHands = new CardHand[2];
    private int playAreaWidth = 460;
    private int playAreaHeight = 90;

    //spawnX = 480/2, spawnY = 320/2
    public Board(Screen screen, Bitmap bitmap){
        super(screen, bitmap, 480, 320);
        overlapAllowance = 1.0f;
    }

    @Override
    public void draw(Canvas canvas, float lastFrameTime) {
        super.draw(canvas, lastFrameTime);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        for(PlayArea playArea : playAreas)
            playArea.update(deltaTime); //update play areas
    }

    @Override
    public void place(Screen screen, float placementX, float placementY) {
        super.place(screen, placementX, placementY);
        //update hands
    //    for (int i = 0; i < playAreas.length; i++) {
        //    playAreas[i].place(screen, );
      //  }
    }

    @Override
    public void initPlacement(float spawnX, float spawnY){
        super.initPlacement(spawnX, spawnY);
        //player 1 - human: player one - bottom half of screen
        //spawnX = boundingBox.left + areaPaddingX
        //spawnY = halfWidth + areaPaddingY
        //width = 460, height = 140
        for (int i = 0; i < cardHands.length; i++) {
            if(i == 0) {
                cardHands[i] = new CardHand(this.currentScrren, 240f, 288f);
                playAreas[i] = new PlayArea(this.currentScrren, playAreaWidth, playAreaHeight, (int)halfWidth, 114);
            }else{
                cardHands[i] = new CardHand(this.currentScrren, 240f, 65f);
                playAreas[i] = new PlayArea(this.currentScrren, playAreaWidth, playAreaHeight, (int)halfWidth, 208);
            }

            Log.d("cardhand " + i, "" + cardHands[i].boundingBox.toString());
            Log.d("playArea " + i, "" + playAreas[i].boundingBox.toString());
        }

        //player 2 - opponent: AI/other player - top half of screen
        //spawnX = boundingBox.left + areaPaddingX
        //spawnY = boundingBox.top
        //width = 460, height = 140
        //playAreas[1] = new PlayArea(460, 140);


//
//        playAreas[1] = new PlayArea(playAreaWidth, playAreaHeight, (int)halfWidth, (int)
//                (halfHeight * 0.75));
    }

    public PlayArea[] getPlayAreas() {
        return playAreas;
    }

    public void setPlayAreas(PlayArea[] playAreas) {
        this.playAreas = playAreas;
    }

    public CardHand[] getCardHands() {
        return cardHands;
    }

    public void setCardHands(CardHand[] cardHands) {
        this.cardHands = cardHands;
    }

    public int getPlayAreaWidth() {
        return playAreaWidth;
    }

    public void setPlayAreaWidth(int playAreaWidth) {
        this.playAreaWidth = playAreaWidth;
    }

    public int getPlayAreaHeight() {
        return playAreaHeight;
    }

    public void setPlayAreaHeight(int playAreaHeight) {
        this.playAreaHeight = playAreaHeight;
    }
}

