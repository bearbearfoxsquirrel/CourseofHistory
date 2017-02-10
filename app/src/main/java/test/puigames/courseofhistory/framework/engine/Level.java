package test.puigames.courseofhistory.framework.engine;

import android.graphics.Canvas;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.resourceloading.FetchingIO;
import test.puigames.courseofhistory.framework.input.AndroidInput;

/**
 * Created by Michael on 24/11/2016.
 */

public abstract class Level extends Screen{
    protected ArrayList<Sprite> sprites;
    protected InputBuddy inputBuddy;
    protected FetchingIO resourceFetcher;
    Viewport viewport;
    //Sclaer scaler; TODO
    final static float levelHeight = 480.f;
    final static float levelWidth = 320.f;




    public Level(Game game) {
        super(game);
        viewport = new Viewport(levelHeight, levelWidth);
        //scaler= new Scaler(game, viewport); TODO
        this.sprites = new ArrayList<Sprite>();
        this.resourceFetcher = game.getResourceFetcher();
    }

    public abstract void load();



    @Override
    public void update(float deltaTime, AndroidInput input) {
        inputBuddy = new InputBuddy(input);
        for (Sprite sprite: sprites) {
            sprite.update(inputBuddy, deltaTime);
            //scaler.scaleToScreen(sprite.matrix); TODO
        }
    }


    @Override
    public void draw(Canvas canvas, float deltaTime) {
        for (Sprite sprite: sprites) {
            sprite.draw(canvas, deltaTime);
        }
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {

    }
}
