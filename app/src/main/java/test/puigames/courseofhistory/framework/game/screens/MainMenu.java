package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.game.levels.TestLevel;

/**
 * Created by Christopher on 08/02/2017.
 */

public class MainMenu extends Menu {

    Bitmap background;
    Bitmap scaledBackground;

    public MainMenu(GameProperties gameProperties){
        super(gameProperties);
        load();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(inputBuddy.getTouchEvents() != null) {
            for(Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
                switch (touchEvent.type) {
                    case Input.TouchEvent.TOUCH_DOWN:
                        gameProperties.setScreen(new TestLevel(gameProperties));
                        break;
                }
            }
        }

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
