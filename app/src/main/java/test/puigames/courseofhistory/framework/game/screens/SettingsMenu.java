package test.puigames.courseofhistory.framework.game.screens;


import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;


/**
* Created by Christopher on 02/03/2017.
*/


public class SettingsMenu extends Menu {

    private static final float UI_ROTATION = 0.f;

    private UIElement backgroundSettings;

    private MenuButton back, volumeOn, volumeOff;
    private static final float VOLUME_STEP = 0.2f;
    private static final float MAX_VOLUME = 1.f;
    private static final float MIN_VOLUME = 0.f;
    private float currentVolume;

    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;
    private float bgImageX = 240.0f, bgImageY = 160.0f;

    private float buttonWidth = 75.0f, buttonHeight = 35.0f;
    private float backCentreX = 40.0f, backCentreY = 300.0f;

    private float volumeWidth = 100.0f, volumeHeight= 75.0f;

    private float volumeOnCentreX = 160.0f, volumeOnCentreY = 120.0f;

    private float volumeOffCentreX = 320.0f, volumeOffCentreY = 120.0f;



    public SettingsMenu(final GameProperties gameProperties) {
        super(gameProperties);
        currentVolume = 1.f;

        //Creation of the menu buttons, which can either go back to the main menu, or raise or lower the
        //volume respectively.
        this.back = new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_back.png"),
                buttonWidth, buttonHeight) {
            @Override
            public void applyAction() {
                gameProperties.setScreen(new MainMenu(gameProperties));
            }
        };

        this.volumeOn = new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_volume_increase.png"),
                volumeWidth, volumeHeight){
            @Override
            public void applyAction(){
                if(currentVolume < MAX_VOLUME) {
                    currentVolume += VOLUME_STEP;
                    gameProperties.getMediaPlayer().setVolume(currentVolume, currentVolume);
                }
            }
        };

        this.volumeOff= new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_volume_decrease.png"),
                volumeWidth, volumeHeight) {
            @Override
            public void applyAction() {
                if(currentVolume > MIN_VOLUME) {
                    currentVolume -= VOLUME_STEP;
                    gameProperties.getMediaPlayer().setVolume(currentVolume, currentVolume);
                }
            }
        };

        load();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(back.checkForInput(inputBuddy))
            back.applyAction();
        if(volumeOn.checkForInput(inputBuddy))
            volumeOn.applyAction();
        if(volumeOff.checkForInput(inputBuddy))
            volumeOff.applyAction();
    }

    //Method to load all of the assets used and then place, scale, and draw them.
    public void load(){
        //Settings up uiElements.
        try {
            backgroundSettings = new UIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/settings_background.png"),
                    bgImageWidth, bgImageHeight);
        }
        catch(NullPointerException e){
            Log.d("Error", "UI Element loading has failed");
        }

        //uiElements are given a location to be placed on the screen based on the centre of their image.
        backgroundSettings.place(this, bgImageX, bgImageY, UI_ROTATION);
        back.place(this, backCentreX, backCentreY, UI_ROTATION);
        volumeOn.place(this, volumeOnCentreX, volumeOnCentreY, UI_ROTATION);
        volumeOff.place(this, volumeOffCentreX, volumeOffCentreY, UI_ROTATION);

        //Added to the uiElements ArrayList to be scaled and drawn to the screen.
        uiElements.add(backgroundSettings);
        uiElements.add(back);
        uiElements.add(volumeOn);
        uiElements.add(volumeOff);
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

    //Getters and setters.
    public UIElement getBackgroundSettings() {
        return backgroundSettings;
    }

    public void setBackgroundSettings(UIElement backgroundSettings) { this.backgroundSettings = backgroundSettings; }

    public MenuButton getBack() {
        return back;
    }

    public void setBack(MenuButton back) {
        this.back = back;
    }

    public float getBgImageWidth() {
        return bgImageWidth;
    }

    public void setBgImageWidth(float bgImageWidth) {
        this.bgImageWidth = bgImageWidth;
    }

    public float getBgImageHeight() {
        return bgImageHeight;
    }

    public void setBgImageHeight(float bgImageHeight) {
        this.bgImageHeight = bgImageHeight;
    }

    public float getBgImageX() {
        return bgImageX;
    }

    public void setBgImageX(float bgImageX) {
        this.bgImageX = bgImageX;
    }

    public float getBgImageY() {
        return bgImageY;
    }

    public void setBgImageY(float bgImageY) {
        this.bgImageY = bgImageY;
    }

    public float getButtonWidth() {
        return buttonWidth;
    }

    public void setButtonWidth(float buttonWidth) {
        this.buttonWidth = buttonWidth;
    }

    public float getButtonHeight() {
        return buttonHeight;
    }

    public void setButtonHeight(float buttonHeight) {
        this.buttonHeight = buttonHeight;
    }

    public float getBackCentreX() {
        return backCentreX;
    }

    public void setBackCentreX(float backCentreX) {
        this.backCentreX = backCentreX;
    }

    public float getBackCentreY() {
        return backCentreY;
    }

    public void setBackCentreY(float backCentreY) {
        this.backCentreY = backCentreY;
    }

}
