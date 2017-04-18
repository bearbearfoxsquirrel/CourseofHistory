package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.ui.imageUIElement;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.game.levels.TestLevel;

/**
 * Created by Christopher on 02/03/2017.
 */

public class SettingsMenu extends Menu {

    imageUIElement backgroundSettings;

    public SettingsMenu(GameProperties gameProperties){
        super(gameProperties);
        load();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public void load(){
        try {
            backgroundSettings = new imageUIElement(resourceFetcher.getBitmapFromFile("images/backgrounds/settings_background.png"),
                    480.0f, 320.0f);
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }

        backgroundSettings.placeUIElement(0.f, 0.f);

        uiElements.add(backgroundSettings);
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
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
