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

    private float buttonWidth = 75.0f, buttonHeight = 35.0f;
    private float backCentreX = 40.0f, backCentreY = 300.0f;

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

        if(back.checkForInput(inputBuddy))
            back.applyAction();

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

    public ImageUIElement getBackgroundSettings() {
        return backgroundSettings;
    }

    public void setBackgroundSettings(ImageUIElement backgroundSettings) { this.backgroundSettings = backgroundSettings; }

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
