package test.puigames.courseofhistory.framework.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.io.IOException;

import test.puigames.courseofhistory.framework.engine.Game;
import test.puigames.courseofhistory.framework.engine.Level;
import test.puigames.courseofhistory.framework.engine.resourceloading.GraphicsIO;
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

    public SplashScreen(Game game) {
        super(game);
        this.graphicsIO = game.getGraphicsIO();
        logo = null;
        try{
            logo = graphicsIO.loadBitmap("splash.png", Bitmap.Config.ARGB_4444);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        game.calculateScreenSize();
        height = game.getScreenHeight();
        width = game.getScreenWidth();
        scaledLogo = Bitmap.createScaledBitmap(logo, width, height, true);
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
