package test.puigames.courseofhistory.framework.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.Game;
import test.puigames.courseofhistory.framework.engine.Level;
import test.puigames.courseofhistory.framework.input.AndroidInput;
import test.puigames.courseofhistory.framework.input.Input;

/**
 * Created by Christopher on 08/02/2017.
 */

public class MainMenu extends Level {

    Bitmap background;
    Bitmap scaledBackground;
    int height;
    int width;

    public MainMenu(Game game){
        super(game);
        game.getResourceFetcher().getBitmapFromFile("background.png");
        background = null;
        try{
            background = game.getResourceFetcher().getBitmapFromFile("background.png");
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        game.calculateScreenSize();
        height = game.getScreenHeight();
        width = game.getScreenWidth();
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
                        game.setScreen(new TestLevel(game));
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
