package test.puigames.courseofhistory.framework.game.assets.boards;

import android.graphics.Bitmap;

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

    public Board(Screen screen, Bitmap bitmap) {
        super(screen, bitmap, 480, 320);
        overlapAllowance = 1.0f;
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        for (PlayArea playArea : playAreas)
            playArea.update(deltaTime); //update play areas
    }

    @Override
    public void initPlacement(float spawnX, float spawnY, float rotation) {
        super.initPlacement(spawnX, spawnY, rotation);
        for (int i = 0; i < cardHands.length; i++) {
            cardHands[i] = new CardHand(this.currentScreen);
            playAreas[i] = new PlayArea(this.currentScreen, playAreaWidth, playAreaHeight);
        }
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

