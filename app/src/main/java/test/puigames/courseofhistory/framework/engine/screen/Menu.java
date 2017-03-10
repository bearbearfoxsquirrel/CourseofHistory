package test.puigames.courseofhistory.framework.engine.screen;

import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameloop.MainGame;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.resourceloading.Fetcher;
import test.puigames.courseofhistory.framework.engine.resourceloading.ResourceFetcher;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Viewport;

/**
 * Created by Christopher on 20/02/2017.
 */

public abstract class Menu extends Screen{

    protected InputBuddy inputBuddy;
    protected Fetcher resourceFetcher;

    public Menu(GameProperties gameProperties){
        super(gameProperties);
        resourceFetcher = gameProperties.getResourceFetcher();
    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        inputBuddy = new InputBuddy(input);
        scaler.scaleTouchInput(inputBuddy);
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {

    }

    public abstract void load();

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
