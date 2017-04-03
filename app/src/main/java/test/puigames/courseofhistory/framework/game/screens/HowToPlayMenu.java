package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;

/**
 * Created by Christopher on 02/03/2017.
 */

public class HowToPlayMenu extends Menu {

    Bitmap background;
    Bitmap scaledBackground;

    public HowToPlayMenu(GameProperties gameProperties){
        super(gameProperties);
        load();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public void load(){
        background = null;
        try{
            background = gameProperties.getResourceFetcher().getBitmapFromFile("images/backgrounds/background.png");
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
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
