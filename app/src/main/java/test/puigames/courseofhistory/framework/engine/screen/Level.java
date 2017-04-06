package test.puigames.courseofhistory.framework.engine.screen;

import android.graphics.Canvas;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.resourceloading.Fetcher;

/**
 * Created by Michael on 24/11/2016.
 */

public abstract class Level extends Screen {
    protected ArrayList<Sprite> sprites;
    protected InputBuddy inputBuddy;
    protected Fetcher resourceFetcher;

    public Level(GameProperties gameProperties) {
        super(gameProperties);
        this.sprites = new ArrayList<>();
        this.resourceFetcher = gameProperties.getResourceFetcher();
    }

    public void spawnSprite(Sprite sprite, float spawnX, float spawnY) {
        sprite.spawnObject(spawnX, spawnY);
        sprites.add(sprite);
    }


    public abstract void load();


    @Override
    public void update(float deltaTime, AndroidInput input) {
        inputBuddy = new InputBuddy(input);
        scaler.scaleTouchInput(inputBuddy);

        for (Sprite sprite : sprites)
            sprite.update(deltaTime);
    }


    @Override
    public void draw(Canvas canvas, float deltaTime) {
        for (Sprite sprite : sprites) {
            sprite.draw(canvas, deltaTime);
            scaler.scaleToScreen(sprite);
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
