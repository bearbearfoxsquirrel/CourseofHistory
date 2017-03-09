package test.puigames.courseofhistory.framework.engine.screen;

import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.resourceloading.Fetcher;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Viewport;

/**
 * Created by Christopher on 20/02/2017.
 */

public abstract class Menu extends Screen{

    protected InputBuddy inputBuddy;
    protected Fetcher resourceFetcher;
    Viewport viewport;
    //Scaler scaler;  // TODO
    int width = 640;
    int height = 400;

    public Menu(GameProperties gameProperties){
        super(gameProperties);
        viewport = new Viewport(width, height);
//        scaler = new Scaler(gameProperties, viewport);
//        scaler.scaleViewport(viewport);
        this.resourceFetcher = gameProperties.getResourceFetcher();
    }

    @Override
    public void update(float deltaTime, AndroidInput input) {

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
