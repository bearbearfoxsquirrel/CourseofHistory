package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

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
    float duration = 0;
    Matrix matrix;


    public SplashScreen(GameProperties gameProperties) {
        super(gameProperties);
        matrix = new Matrix();
        load();
    }

    public void load() {
        logo = resourceFetcher.getBitmapFromFile("images/splashscreen/splash.png");
        scaler.scaleToScreen(logo, matrix);
    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);
        duration += deltaTime;
        if(duration > 5)
            try{
                gameProperties.setScreen(new MainMenu(gameProperties));
            }
            catch(NullPointerException e){
                e.printStackTrace();
            }
        else{
            gameProperties.setScreen(new TestLevel(gameProperties));
        }
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(logo, 0.f, 0.f, null);
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
