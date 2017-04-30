package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.ImageUIElement;

/**
 * Created by Christopher on 28/04/2017.
 */

public class LevelSelectScreen extends Menu {

    private ImageUIElement backgroundLevelSelect;
    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;
    private float bgCentreX = 240.0f, bgCentreY = 160.0f;

    public LevelSelectScreen(GameProperties gameProperties){
        super(gameProperties);
        load();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void load() {

        backgroundLevelSelect = null;

        try{
            backgroundLevelSelect = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/level_select_background.png"),
                bgImageWidth, bgImageHeight);
        }
        catch(NullPointerException e){
            Log.d("Error", "UI Element loading has failed");
        }

        backgroundLevelSelect.place(this, bgCentreX, bgCentreY);

        uiElements.add(backgroundLevelSelect);

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
