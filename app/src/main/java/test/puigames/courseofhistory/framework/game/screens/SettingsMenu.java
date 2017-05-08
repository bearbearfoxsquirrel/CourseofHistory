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

    private UIElement backgroundSettings, volumeDownImage, volumeUpImage;

    private MenuButton back, volumeDown, volumeUp;
    private static final float VOLUME_STEP = 0.2f;
    private static final float MAX_VOLUME = 1.f;
    private static final float MIN_VOLUME = 0.f;
    private float currentVolume;

    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;
    private float bgImageX = 240.0f, bgImageY = 160.0f;

    private float buttonWidth = 75.0f, buttonHeight = 35.0f;
    private float backCentreX = 40.0f, backCentreY = 300.0f;

    private float volumeWidth = 100.0f, volumeHeight= 75.0f;

    private float volumeDownImageCentreX = 160.0f, volumeDownImageCentreY = 140.0f;
    private float volumeUpImageCentreX = 320.0f, volumeUpImageCentreY = 140.0f;

    private float volumeDownCentreX = 160.0f, volumeDownCentreY = 200.0f;
    private float volumeUpCentreX = 320.0f, volumeUpCentreY = 200.0f;



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

        this.volumeDown = new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_lower-volume.png"),
                buttonWidth, buttonHeight) {
            @Override
            public void applyAction() {
                if (currentVolume > MIN_VOLUME) {
                    currentVolume -= VOLUME_STEP;
                    gameProperties.getMediaPlayer().setVolume(currentVolume, currentVolume);
                }
            }
        };

        this.volumeUp = new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_raise-volume.png"),
                buttonWidth, buttonHeight) {
            @Override
            public void applyAction() {

                if (currentVolume < MAX_VOLUME) {
                    currentVolume += VOLUME_STEP;
                    gameProperties.getMediaPlayer().setVolume(currentVolume, currentVolume);
                }
            }
        };

        load();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //Checks for input and performs button specific action.
        if(back.checkForInput(inputBuddy))
            back.applyAction();
        if(volumeDown.checkForInput(inputBuddy))
            volumeDown.applyAction();
        if(volumeUp.checkForInput(inputBuddy))
            volumeUp.applyAction();
    }

    //Method to load all of the assets used and then place, scale, and draw them.
    public void load(){
        //Settings up uiElements.
        backgroundSettings = null;
        try {
            backgroundSettings = new UIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/settings_background.png"),
                    bgImageWidth, bgImageHeight);
            volumeDownImage = new UIElement(this, resourceFetcher.getBitmapFromFile("images/buttons/button_volume_decrease.png"),
                    volumeWidth, volumeHeight);
            volumeUpImage = new UIElement(this, resourceFetcher.getBitmapFromFile("images/buttons/button_volume_increase.png"),
                    volumeWidth, volumeHeight);
        }
        catch(NullPointerException e){
            Log.d("Error", "UI Element loading has failed");
        }

        //uiElements are given a location to be placed on the screen based on the centre of their image.
        backgroundSettings.place(this, bgImageX, bgImageY, UI_ROTATION);
        back.place(this, backCentreX, backCentreY, UI_ROTATION);
        volumeDown.place(this, volumeDownCentreX, volumeDownCentreY, UI_ROTATION);
        volumeUp.place(this, volumeUpCentreX, volumeUpCentreY, UI_ROTATION);
        volumeDownImage.place(this, volumeDownImageCentreX, volumeDownImageCentreY, UI_ROTATION);
        volumeUpImage.place(this, volumeUpImageCentreX, volumeUpImageCentreY, UI_ROTATION);

        //Added to the uiElements ArrayList to be scaled and drawn to the screen.
        uiElements.add(backgroundSettings);
        uiElements.add(back);
        uiElements.add(volumeDown);
        uiElements.add(volumeUp);
        uiElements.add(volumeDownImage);
        uiElements.add(volumeUpImage);
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

    public static float getUiRotation() {
        return UI_ROTATION;
    }

    public UIElement getVolumeDownImage() {
        return volumeDownImage;
    }

    public void setVolumeDownImage(UIElement volumeDownImage) {
        this.volumeDownImage = volumeDownImage;
    }

    public UIElement getVolumeUpImage() {
        return volumeUpImage;
    }

    public void setVolumeUpImage(UIElement volumeUpImage) {
        this.volumeUpImage = volumeUpImage;
    }

    public MenuButton getVolumeDown() {
        return volumeDown;
    }

    public void setVolumeDown(MenuButton volumeDown) {
        this.volumeDown = volumeDown;
    }

    public MenuButton getVolumeUp() {
        return volumeUp;
    }

    public void setVolumeUp(MenuButton volumeUp) {
        this.volumeUp = volumeUp;
    }

    public static float getVolumeStep() {
        return VOLUME_STEP;
    }

    public static float getMaxVolume() {
        return MAX_VOLUME;
    }

    public static float getMinVolume() {
        return MIN_VOLUME;
    }

    public float getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(float currentVolume) {
        this.currentVolume = currentVolume;
    }

    public float getVolumeWidth() {
        return volumeWidth;
    }

    public void setVolumeWidth(float volumeWidth) {
        this.volumeWidth = volumeWidth;
    }

    public float getVolumeHeight() {
        return volumeHeight;
    }

    public void setVolumeHeight(float volumeHeight) {
        this.volumeHeight = volumeHeight;
    }

    public float getVolumeUpImageCentreX() {
        return volumeUpImageCentreX;
    }

    public void setVolumeUpImageCentreX(float volumeUpImageCentreX) {
        this.volumeUpImageCentreX = volumeUpImageCentreX;
    }

    public float getVolumeUpImageCentreY() {
        return volumeUpImageCentreY;
    }

    public void setVolumeUpImageCentreY(float volumeUpImageCentreY) {
        this.volumeUpImageCentreY = volumeUpImageCentreY;
    }

    public float getVolumeDownImageCentreX() {
        return volumeDownImageCentreX;
    }

    public void setVolumeDownImageCentreX(float volumeDownImageCentreX) {
        this.volumeDownImageCentreX = volumeDownImageCentreX;
    }

    public float getVolumeDownImageCentreY() {
        return volumeDownImageCentreY;
    }

    public void setVolumeDownImageCentreY(float volumeDownImageCentreY) {
        this.volumeDownImageCentreY = volumeDownImageCentreY;
    }

    public float getVolumeUpCentreX() {
        return volumeUpCentreX;
    }

    public void setVolumeUpCentreX(float volumeUpCentreX) {
        this.volumeUpCentreX = volumeUpCentreX;
    }

    public float getVolumeUpCentreY() {
        return volumeUpCentreY;
    }

    public void setVolumeUpCentreY(float volumeUpCentreY) {
        this.volumeUpCentreY = volumeUpCentreY;
    }

    public float getVolumeDownCentreX() {
        return volumeDownCentreX;
    }

    public void setVolumeDownCentreX(float volumeDownCentreX) {
        this.volumeDownCentreX = volumeDownCentreX;
    }

    public float getVolumeDownCentreY() {
        return volumeDownCentreY;
    }

    public void setVolumeDownCentreY(float volumeDownCentreY) {
        this.volumeDownCentreY = volumeDownCentreY;
    }
}
