package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.game.levels.TestLevel;

/**
 * Created by Christopher on 02/03/2017.
 */

public class SettingsMenu extends Menu {

    Bitmap background;
    Bitmap scaledBackground;

    public SettingsMenu(GameProperties gameProperties){
        super(gameProperties);
        load();
    }


    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);

        gameProperties.setScreen(new TestLevel(gameProperties))
    }

    public void load(){
        background = resourceFetcher.getBitmapFromFile("background.png");
        scaledBackground = Bitmap.createScaledBitmap(background, width, height, true);
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(scaledBackground, 0.f, 0.f, null);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
