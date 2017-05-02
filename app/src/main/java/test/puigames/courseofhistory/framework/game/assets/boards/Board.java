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
    private final int PLAY_AREA_WIDTH = 460;
    private final int PLAY_AREA_HEIGHT = 85;
    private final float PLAY_AREA_POS_Y_PLAYER1 = 208.f;
    private final float PLAY_AREA_POS_Y_PLAYER2 = 88.f;
    private final float CARD_HAND_POS_Y_PLAYER_1 = 288.f;
    private final float CARD_HAND_POS_Y_PLAYER_2 = 10.f;

    public Board(Screen screen, Bitmap bitmap){
        super(screen, bitmap, 480, 320);
        overlapAllowance = 1.f;
    }

    @Override
    public void draw(Canvas canvas, float lastFrameTime) {
        super.draw(canvas, lastFrameTime);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void place(Screen screen, float placementX, float placementY) {
        super.place(screen, placementX, placementY);
    }

    @Override
    public void initPlacement(float spawnX, float spawnY){
        super.initPlacement(spawnX, spawnY);
        for (int i = 0; i < cardHands.length; i++) {
            if(i == 0) {               //PLAYER 1 - bottom half of screen
                cardHands[i] = new CardHand(this.currentScreen, halfWidth, CARD_HAND_POS_Y_PLAYER_1);
                playAreas[i] = new PlayArea(this.currentScreen, PLAY_AREA_WIDTH, PLAY_AREA_HEIGHT, halfWidth, PLAY_AREA_POS_Y_PLAYER1);
            } else {                  //PLAYER 2 - top half of screen
                cardHands[i] = new CardHand(this.currentScreen, halfWidth, CARD_HAND_POS_Y_PLAYER_2);
                playAreas[i] = new PlayArea(this.currentScreen, PLAY_AREA_WIDTH, PLAY_AREA_HEIGHT, halfWidth, PLAY_AREA_POS_Y_PLAYER2);
            }
        }
    }

    public PlayArea[] getPlayAreas() {
        return playAreas;
    }

    public void setPlayAreas(PlayArea[] playAreas) {
        this.playAreas = playAreas;
    }

    public PlayArea getPlayArea(int i) {
        return playAreas[i];
    }

    public void setPlayArea(PlayArea playArea, int i) {
        this.playAreas[i] = playArea;
    }

    public CardHand[] getCardHands() {
        return cardHands;
    }

    public void setCardHands(CardHand[] cardHands) {
        this.cardHands = cardHands;
    }

    public CardHand getCardHand(int i) {
        return cardHands[i];
    }

    public void setCardHand(CardHand cardHand, int i) {
        this.cardHands[i] = cardHand;
    }

    public int getPLAY_AREA_WIDTH() {
        return PLAY_AREA_WIDTH;
    }

    public int getPLAY_AREA_HEIGHT() {
        return PLAY_AREA_HEIGHT;
    }

    public float getPLAY_AREA_POS_Y_PLAYER1() {
        return PLAY_AREA_POS_Y_PLAYER1;
    }

    public float getPLAY_AREA_POS_Y_PLAYER2() {
        return PLAY_AREA_POS_Y_PLAYER2;
    }

    public float getCARD_HAND_POS_Y_PLAYER_1() {
        return CARD_HAND_POS_Y_PLAYER_1;
    }

    public float getCARD_HAND_POS_Y_PLAYER_2() {
        return CARD_HAND_POS_Y_PLAYER_2;
    }
}

