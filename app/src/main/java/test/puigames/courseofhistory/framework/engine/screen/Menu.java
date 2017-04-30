package test.puigames.courseofhistory.framework.engine.screen;

import android.graphics.Canvas;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;

/**
 * Created by Christopher on 20/02/2017.
 */

public abstract class Menu extends Screen {
    protected ArrayList<UIElement> uiElements;

    //Constructor - assigns resource fetcher as one in game properties, and sets up the
    //uiElements array list
    public Menu(GameProperties gameProperties) {
        super(gameProperties);
        this.uiElements = new ArrayList<>();
    }

    //Allows scaling of the objects in uiElements to the screen
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (UIElement uiElement : uiElements)
            scaler.scaleToScreen(uiElement);
    }

    //Enhanced for loop that moves through the array and draw each to the screen
    @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
        for (UIElement uiElement : uiElements)
            uiElement.draw(canvas, deltaTime);
    }

    public ArrayList<UIElement> getUiElements() {
        return uiElements;
    }

    public void setUiElements(ArrayList<UIElement> uiElements) {
        this.uiElements = uiElements;
    }
}
