package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameobjects.imageUIElement;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.screen.Menu;

/**
 * Created by Christopher on 02/03/2017.
 */

public class HowToPlayMenu extends Menu {

    imageUIElement backgroundHowToPlay;

    public HowToPlayMenu(GameProperties gameProperties){
        super(gameProperties);
        load();
    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);
    }

    public void load(){
        backgroundHowToPlay = null;
        try{
            backgroundHowToPlay = new imageUIElement(resourceFetcher.getBitmapFromFile("images/backgrounds/how_to_play_background.png"),
                    480.0f, 320.0f);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        uiElements.add(backgroundHowToPlay);
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
