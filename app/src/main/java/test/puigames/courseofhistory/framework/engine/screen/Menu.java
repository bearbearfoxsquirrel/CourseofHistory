package test.puigames.courseofhistory.framework.engine.screen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameloop.MainGame;
import test.puigames.courseofhistory.framework.engine.gameobjects.UIElement;
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
    protected ArrayList<UIElement> uiElements;

    public Menu(GameProperties gameProperties){
        super(gameProperties);

        this.resourceFetcher = gameProperties.getResourceFetcher();
        this.uiElements = new ArrayList<>();
    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        inputBuddy = new InputBuddy(input);
        scaler.scaleTouchInput(inputBuddy);

        for(UIElement uiElement : uiElements)
        {
            uiElement.update(deltaTime);
            scaler.scaleToScreen(uiElement);
        }
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        for(UIElement uiElement : uiElements)
            uiElement.draw(canvas, deltaTime);
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
