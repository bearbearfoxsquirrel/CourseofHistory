package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.game.levels.TestLevel;

/**
 * Created by Christopher on 24/11/2016.
 */

public class SplashScreen extends Menu
{
    Bitmap logo;
    float duration = 0.0f;

    public SplashScreen(GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    public void load() {
        logo = resourceFetcher.getBitmapFromFile("images/splashscreen/splash.png");
        bitmaps.add(logo);
    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);

        if(duration > 5.0f)
            try{
                gameProperties.setScreen(new TestLevel(gameProperties));
            }
            catch(NullPointerException e){
                gameProperties.setScreen(new SplashScreen(gameProperties));
            }

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
