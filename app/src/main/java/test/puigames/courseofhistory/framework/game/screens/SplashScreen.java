package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.ImageUIElement;
import test.puigames.courseofhistory.framework.game.levels.TestLevel;

/**
 * Created by Christopher on 24/11/2016.
 */

public class SplashScreen extends Menu {

    private ImageUIElement logo;
    private float duration = 0.0f, splashscreenDelay = 3.0f;
    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;
    private float bgImageX = 240.0f, bgImageY = 160.0f;

    public SplashScreen(GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    @Override
    public void load() {
        //Try to load the bitmap
        try {
            logo = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/splashscreen/splash.png"),
                    bgImageWidth, bgImageHeight);
        }
        catch(NullPointerException e) {
            Log.d("Error", "Can't load UI elements");
            this.gameProperties.setScreen(new SplashScreen(this.gameProperties));
        }

        logo.place(this, bgImageX, bgImageY);

        uiElements.add(logo);
    }

    //Allows for the splash image to display and then swaps screen to main menu after a set time
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(duration > splashscreenDelay)
                this.gameProperties.setScreen(new TestLevel(this.gameProperties)); //TODO: change back to MainMenu

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
