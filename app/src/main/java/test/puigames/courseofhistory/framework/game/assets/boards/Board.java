package test.puigames.courseofhistory.framework.game.assets.boards;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.CardHand;
import test.puigames.courseofhistory.framework.game.assets.Hero;
import test.puigames.courseofhistory.framework.game.assets.PlayArea;

/**
 * Created by 40123577 on 14/11/2016.
 */


public class Board extends Sprite implements Drawable {
    private PlayArea[] playAreas = new PlayArea[2];
    private Hero[] heroes = new Hero[MAX_PLAYERS_ON_BOARD];

    private int playAreaWidth = 460;
    private int playAreaHeight = 90;

    public Board(Screen screen, Bitmap bitmap, Hero[] heroes) {
        super(screen, bitmap, 480, 320);
        overlapAllowance = 1.f;
        this.heroes = heroes;
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        for (PlayArea playArea : playAreas)
            playArea.update(deltaTime); //update play areas
    }

    @Override
    public void initPlacement(float spawnX, float spawnY, float rotation) {
        super.initPlacement(spawnX, spawnY, rotation);
        for (int i = 0; i < playAreas.length; i++) {
            playAreas[i] = new PlayArea(this.currentScreen, playAreaWidth, playAreaHeight);
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
    public int getPlayAreaWidth() {
        return playAreaWidth;
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

    public int getMAX_PLAYERS_ON_BOARD() {
        return MAX_PLAYERS_ON_BOARD;
    }

    public Hero[] getHeroes() {
        return heroes;
    }

    public Hero getHero(int i) {
        return heroes[i];
    }

    public void setHeroes(Hero[] heroes) {
        this.heroes = heroes;
    }

    public void setHero(Hero hero, int i) {
        this.heroes[i] = hero;
    }
}

