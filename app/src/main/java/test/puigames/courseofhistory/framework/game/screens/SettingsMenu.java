package test.puigames.courseofhistory.framework.game.screens;


import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.ui.ImageUIElement;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;


/**
* Created by Christopher on 02/03/2017.
*/


public class SettingsMenu extends Menu {

    private ImageUIElement backgroundSettings;
    private MenuButton back;
    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;
    private float bgImageX = 240.0f, bgImageY = 160.0f;
    private float buttonWidth = 75.0f, buttonHeight = 45.0f;
    private float backCentreX = 40.0f, backCentreY = 280.0f;

    public SettingsMenu(final GameProperties gameProperties) {
        super(gameProperties);

        this.back = new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_back.png"),
                buttonWidth, buttonHeight) {
            @Override
            public void applyAction() {
                gameProperties.setScreen(new MainMenu(gameProperties));
            }
        };

        load();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(back.checkForInput(inputBuddy)){
            back.applyAction();
        }
    }

    public void load(){
        try {
            backgroundSettings = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/settings_background.png"),
                    bgImageWidth, bgImageHeight);
        }
        catch(NullPointerException e){
            Log.d("Error", "UI Element loading has failed");
        }

        backgroundSettings.place(this, bgImageX, bgImageY);
        back.place(this, backCentreX, backCentreY);

        uiElements.add(backgroundSettings);
        uiElements.add(back);
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
    }

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
