package test.puigames.courseofhistory.framework.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.Game;
import test.puigames.courseofhistory.framework.engine.Level;
import test.puigames.courseofhistory.framework.engine.Screen;
import test.puigames.courseofhistory.framework.input.AndroidInput;

/**
 * Created by Christopher on 24/11/2016.
 */

public class SplashScreen extends Level
{

    Bitmap logo;
    Bitmap scaledLogo;
    int height;
    int width;
    float duration = 0;
    Screen testLevel;

    public SplashScreen(Game game) {
        super(game);
        game.getResourceFetcher().getBitmapFromFile("splash.png");
        logo = null;
        try{
            logo = game.getResourceFetcher().getBitmapFromFile("splash.png");// graphicsIO.loadBitmap("blank-card.png", Bitmap.Config.ARGB_4444);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        game.calculateScreenSize();
        height = game.getScreenHeight();
        width = game.getScreenWidth();
        scaledLogo = Bitmap.createScaledBitmap(logo, width, height, true);
    }

    public void load() {

    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);
        duration += deltaTime;
        if(duration > 5)
            game.setScreen(new TestLevel(game));

    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(scaledLogo, 0.f, 0.f, null);
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
