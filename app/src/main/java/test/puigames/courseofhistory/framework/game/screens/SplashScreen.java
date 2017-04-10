package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameobjects.imageUIElement;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.game.levels.TestLevel;

/**
 * Created by Christopher on 24/11/2016.
 */

public class SplashScreen extends Menu
{
    imageUIElement logo;
    float duration = 0.0f;
    float splashscreenDelay = 3.0f;

    public SplashScreen(GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    public void load() {
        //load image
        try {
            logo = new imageUIElement(resourceFetcher.getBitmapFromFile("images/splashscreen/splash.png"),
                    480.0f, 320.0f);
        }
        catch(NullPointerException e) {
            Log.d("Error", "Can't load UI elements");
            gameProperties.setScreen(new SplashScreen(this.gameProperties));
        }
        logo.placeUIElement(240f, 160.0f);
        uiElements.add(logo);
    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);

        if(duration > splashscreenDelay)
                gameProperties.setScreen(new MainMenu(gameProperties));

        duration += deltaTime;
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
