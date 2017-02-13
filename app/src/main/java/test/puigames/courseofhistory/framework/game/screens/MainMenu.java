package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Level;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.game.levels.TestLevel;

/**
 * Created by Christopher on 08/02/2017.
 */

public class MainMenu extends Level {

    Bitmap background;
    Bitmap scaledBackground;
    int height;
    int width;

    public MainMenu(GameProperties gameProperties){
        super(gameProperties);
        gameProperties.getResourceFetcher().getBitmapFromFile("background.png");
        background = null;
        try{
            background = gameProperties.getResourceFetcher().getBitmapFromFile("background.png");
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        gameProperties.calculateScreenSize();
        height = gameProperties.getScreenHeight();
        width = gameProperties.getScreenWidth();
        scaledBackground = Bitmap.createScaledBitmap(background, width, height, true);
    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);
        if(inputBuddy.getTouchEvents() != null)
        {
            for(Input.TouchEvent touchEvent : inputBuddy.getTouchEvents())
            {
                switch (touchEvent.type) {
                    case Input.TouchEvent.TOUCH_DOWN:
                        gameProperties.setScreen(new TestLevel(gameProperties));
                        break;
                }
            }
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

    public void load(){

    }
}
