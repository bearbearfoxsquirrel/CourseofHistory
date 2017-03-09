package test.puigames.courseofhistory.framework.engine.screen;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Objects;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Scaler;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Viewport;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.resourceloading.Fetcher;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;

/**
 * Created by Michael on 24/11/2016.
 */

public abstract class Level extends Screen {
    protected ArrayList<Sprite> sprites;
    protected InputBuddy inputBuddy;
    protected Fetcher resourceFetcher;
    protected Viewport viewport;
    protected Scaler scaler;// TODO
    final static float LEVEL_HEIGHT = 320.f;
    final static float LEVEL_WIDTH = 480.f;




    public Level(GameProperties gameProperties) {
        super(gameProperties);
        viewport = new Viewport(LEVEL_WIDTH, LEVEL_HEIGHT);
        scaler = new Scaler(gameProperties, viewport);
     //   inputBuddy = new InputBuddy();

        this.sprites = new ArrayList<>();
        this.resourceFetcher = gameProperties.getResourceFetcher();
    }

    public abstract void load();



    @Override
    public void update(float deltaTime, AndroidInput input) {
        inputBuddy = new InputBuddy(input);
        scaler.scaleTouchInput(inputBuddy);

        for (Sprite sprite : sprites) {
            sprite.update(inputBuddy, deltaTime);
            scaler.scaleToScreen(sprite);
        }
    }


    @Override
    public void draw(Canvas canvas, float deltaTime) {
        for (Sprite sprite : sprites) {
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
