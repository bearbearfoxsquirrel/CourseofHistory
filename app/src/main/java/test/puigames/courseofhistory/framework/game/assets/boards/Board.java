package test.puigames.courseofhistory.framework.game.assets.boards;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.Hero;
import test.puigames.courseofhistory.framework.game.assets.PlayArea;

/**
 * Created by 40123577 on 14/11/2016.
 */


public class Board extends Sprite implements Drawable {
    public static final int MAX_PLAYERS = 2;
    private PlayArea[] playAreas = new PlayArea[MAX_PLAYERS];
    private Hero[] heroes = new Hero[MAX_PLAYERS];

    private int playAreaWidth = 460;
    private int playAreaHeight = 90;

    public Board(Screen screen, Bitmap bitmap, Bitmap playAreaBitmap, Hero[] heroes) {
        super(screen, bitmap, 480, 320);
        overlapAllowance = 1.f;
        this.heroes = heroes;

        for (int i = 0; i < playAreas.length; i++) {
            playAreas[i] = new PlayArea(this.currentScreen, playAreaBitmap, playAreaWidth, playAreaHeight);
        }
    }
/*

    public void update(float deltaTime) {
        super.update(deltaTime);
        for (PlayArea playArea : playAreas)
            playArea.update(deltaTime); //update play areas
    }
*/

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

    public int getPlayAreaWidth() {
        return playAreaWidth;
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

