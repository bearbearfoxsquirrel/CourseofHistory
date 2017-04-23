package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.ImageUIElement;

/**
 * Created by Christopher on 02/03/2017.
 */

/*
public class HowToPlayMenu extends Menu {

    ImageUIElement backgroundHowToPlay;

    public HowToPlayMenu(GameProperties gameProperties){
        super(gameProperties);
        load();
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public void load(){
        backgroundHowToPlay = null;
        try{
            backgroundHowToPlay = new ImageUIElement(resourceFetcher.getBitmapFromFile("images/backgrounds/how_to_play_background.png"),
                    480.0f, 320.0f);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        backgroundHowToPlay.initPlacement(240.f, 160.f);

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
*/