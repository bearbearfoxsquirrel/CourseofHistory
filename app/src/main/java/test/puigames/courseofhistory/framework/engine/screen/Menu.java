package test.puigames.courseofhistory.framework.engine.screen;

import android.graphics.Canvas;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;

/**
 * Created by Christopher on 20/02/2017.
 */

public abstract class Menu extends Screen{
    protected ArrayList<UIElement> uiElements;
    
    public Menu(GameProperties gameProperties){
        super(gameProperties);
        this.uiElements = new ArrayList<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for(UIElement uiElement : uiElements) {
            scaler.scaleToScreen(uiElement);
        }
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
        for (UIElement uiElement : uiElements)
            uiElement.draw(canvas, deltaTime);
    }
}
