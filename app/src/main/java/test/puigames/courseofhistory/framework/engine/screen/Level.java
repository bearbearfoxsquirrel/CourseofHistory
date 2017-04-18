package test.puigames.courseofhistory.framework.engine.screen;

import android.graphics.Canvas;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.resourceloading.Fetcher;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;
import test.puigames.courseofhistory.framework.engine.ui.imageUIElement;

/**
 * Created by Michael on 24/11/2016.
 */

public abstract class Level extends Screen {
    protected ArrayList<Sprite> sprites;
    protected ArrayList<UIElement> uiElements;
    protected InputBuddy inputBuddy;
    protected Fetcher resourceFetcher;

    public Level(GameProperties gameProperties) {
        super(gameProperties);
        this.sprites = new ArrayList<>();
        this.uiElements = new ArrayList<>();
        this.resourceFetcher = gameProperties.getResourceFetcher();
    }

    public void spawnSprite(Sprite sprite, float spawnX, float spawnY) {
        sprite.spawnObject(spawnX, spawnY);
        sprites.add(sprite);
    }

    public void placeUI(imageUIElement ui, float posX, float posY)
    {
        ui.placeUIElement(posX, posY);
        uiElements.add(ui);
    }

    public abstract void load();


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
       // scaler.scaleTouchInput(inputBuddy);

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
