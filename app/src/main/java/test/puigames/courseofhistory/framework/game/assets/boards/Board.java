package test.puigames.courseofhistory.framework.game.assets.boards;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.CardHand;
import test.puigames.courseofhistory.framework.game.assets.PlayArea;

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
    public Board(Screen screen, Bitmap bitmap) {
        super(screen, bitmap, 480, 320);
        overlapAllowance = 1.0f;
    }

    @Override
    public void draw(Canvas canvas, float lastFrameTime) {
        super.draw(canvas, lastFrameTime);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        for (PlayArea playArea : playAreas)
            playArea.update(deltaTime); //update play areas
    }

    @Override
    public void initPlacement(float spawnX, float spawnY, float rotation) {
        super.initPlacement(spawnX, spawnY, rotation);
        //player 1 - human: player one - bottom half of screen
        //spawnX = boundingBox.left + areaPaddingX
        //spawnY = halfWidth + areaPaddingY
        //width = 460, height = 140
        for (int i = 0; i < cardHands.length; i++) {
            cardHands[i] = new CardHand(this.currentScrren);
            playAreas[i] = new PlayArea(this.currentScrren, playAreaWidth, playAreaHeight);
        }
    }
}

