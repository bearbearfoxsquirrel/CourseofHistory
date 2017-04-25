package test.puigames.courseofhistory.framework.engine.screen;

import android.graphics.Canvas;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.engine.resourceloading.Fetcher;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;

/**
 * Created by Christopher on 20/02/2017.
 */

public abstract class Menu extends Screen{

    protected Fetcher resourceFetcher;
    protected ArrayList<UIElement> uiElements;

    //Constructor - assigns resource fetcher as one in game properties, and sets up the
    //uiElements array list
    public Menu(GameProperties gameProperties){
        super(gameProperties);

        this.resourceFetcher = gameProperties.getResourceFetcher();
        this.uiElements = new ArrayList<>();
    }

    //Allows scaling of the objects in uiElements to the screen
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for(UIElement uiElement : uiElements)
        {
            uiElement.update(deltaTime);
            scaler.scaleToScreen(uiElement);
        }
    }

    //Enhanced for loop that moves through the array and draw each to the screen
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
