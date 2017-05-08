package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;

/**
 * Created by Christopher on 02/03/2017.
 */


public class HowToPlayMenu extends Menu {

    private UIElement backgroundHowToPlay, howToPlayImage;

    private ArrayList<Bitmap> howToPlayTexts;

    private MenuButton back, rulesForward, rulesBackward;

    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;
    private float bgCentreX = 240.0f, bgCentreY = 160.0f;

    private float buttonWidth = 75.0f, buttonHeight = 35.0f;
    private float backCentreX = 40.0f, backCentreY = 300.0f;

    private float rulesForwardCentreX = 450.0f, rulesForwardCentreY = 293.0f;
    private float rulesBackwardCentreX = 390.0f;

    private float rulesBackwardCentreY = 293.0f;
    private final static float UI_ROTATION = 0;

    private float rulesWidth = 50.0f, rulesHeight = 50.0f;
    private float howToPlayImageWidth = 440.0f, howToPlayImageHeight = 260.0f;

    private float howToPlayCentreX = 240.0f, howToPlayCentreY = 135.0f;

    int currentIndex = 0;


    public HowToPlayMenu(final GameProperties gameProperties) {
        super(gameProperties);

        howToPlayTexts = new ArrayList<>();

        //Creation of the menu buttons, each with a specific function based on what they are required
        //to do.
        this.back = new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_back.png"),
                buttonWidth, buttonHeight) {
            @Override
            public void applyAction() {
                gameProperties.setScreen(new MainMenu(gameProperties));
            }
        };

        this.rulesForward = new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_forward.png"),
                rulesWidth, rulesHeight) {
            @Override
            public void applyAction() {
                swapImageForward();
            }
        };

        this.rulesBackward = new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_backward.png"),
                rulesWidth, rulesHeight) {
            @Override
            public void applyAction() {
                swapImageBackward();
            }
        };

        load();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //Checks for input and prevents null pointer errors.
        if (gameProperties.getInput().getTouchEvents().size() > 0) {
            if (back.checkForInput(inputBuddy))
                back.applyAction();
            if(rulesForward.checkForInput(inputBuddy))
                rulesForward.applyAction();
            if(rulesBackward.checkForInput(inputBuddy))
                rulesBackward.applyAction();
        }

    }

    //Method to load all of the assets used and then place, scale, and draw them.
    public void load() {
        //setting up uiElements
        backgroundHowToPlay = null;
        howToPlayImage = null;
        try {
            backgroundHowToPlay = new UIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/how_to_play_background.png"),
                    bgImageWidth, bgImageHeight);
            howToPlayImage = new UIElement(this, resourceFetcher.getBitmapFromFile("images/how_to_play/Rules1Transparent.png"),
                    howToPlayImageWidth, howToPlayImageHeight);
            howToPlayTexts.add(resourceFetcher.getBitmapFromFile("images/how_to_play/Rules1Transparent.png"));
            howToPlayTexts.add(resourceFetcher.getBitmapFromFile("images/how_to_play/Rules2Transparent.png"));
            howToPlayTexts.add(resourceFetcher.getBitmapFromFile("images/how_to_play/Rules3Transparent.png"));

        } catch (NullPointerException e) {
            Log.d("Error", "UI Element loading has failed");
        }

        //uiElements are given a location to be placed on the screen based on the centre of their image.
        backgroundHowToPlay.place(this, bgCentreX, bgCentreY, UI_ROTATION);
        back.place(this, backCentreX, backCentreY, UI_ROTATION);
        rulesForward.place(this, rulesForwardCentreX, rulesForwardCentreY, UI_ROTATION);
        rulesBackward.place(this, rulesBackwardCentreX, rulesBackwardCentreY, UI_ROTATION);
        howToPlayImage.place(this, howToPlayCentreX, howToPlayCentreY, UI_ROTATION);

        //Added to the uiElements ArrayList to be scaled and drawn to the screen.
        uiElements.add(backgroundHowToPlay);
        uiElements.add(back);
        uiElements.add(rulesForward);
        uiElements.add(rulesBackward);
        uiElements.add(howToPlayImage);
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
    }

    //Method to swap the image being displayed on the screen.
    //The current index is incremented and the how to play image variable is set to be the
    //bitmap at the current index in the Array List containing another part of the guide.
    //The new bitmap is then drawn to the screen in place of the previous.
    private void swapImageForward() {
        currentIndex++;
        if (currentIndex < 0)
            currentIndex = howToPlayTexts.size() - 1;
        else if (currentIndex > howToPlayTexts.size() - 1)
            currentIndex = 0;
        howToPlayImage.setImage(howToPlayTexts.get(currentIndex));
    }

    //Method to swap the image being displayed on the screen.
    //The current index is decremented and the how to play image variable is set to be the
    //bitmap at the current index in the Array List containing another part of the guide.
    //The new bitmap is then drawn to the screen in place of the previous.
    private void swapImageBackward() {
        currentIndex--;
        if (currentIndex < 0)
            currentIndex = howToPlayTexts.size() - 1;
        else if (currentIndex > howToPlayTexts.size() - 1)
            currentIndex = 0;
        howToPlayImage.setImage(howToPlayTexts.get(currentIndex));
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
    public UIElement getBackgroundHowToPlay() { return backgroundHowToPlay; }

    public void setBackgroundHowToPlay(UIElement backgroundHowToPlay) { this.backgroundHowToPlay = backgroundHowToPlay; }

    public UIElement getHowToPlayImage() {
        return howToPlayImage;
    }

    public void setHowToPlayImage(UIElement howToPlayImage) { this.howToPlayImage = howToPlayImage; }

    public ArrayList<Bitmap> getHowToPlayTexts() {
        return howToPlayTexts;
    }

    public void setHowToPlayTexts(ArrayList<Bitmap> howToPlayTexts) { this.howToPlayTexts = howToPlayTexts; }

    public MenuButton getBack() {
        return back;
    }

    public void setBack(MenuButton back) {
        this.back = back;
    }

    public MenuButton getRulesForward() {
        return rulesForward;
    }

    public void setRulesForward(MenuButton rulesForward) {
        this.rulesForward = rulesForward;
    }

    public MenuButton getRulesBackward() {
        return rulesBackward;
    }

    public void setRulesBackward(MenuButton rulesBackward) {
        this.rulesBackward = rulesBackward;
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

    public float getBgCentreX() {
        return bgCentreX;
    }

    public void setBgCentreX(float bgCentreX) {
        this.bgCentreX = bgCentreX;
    }

    public float getBgCentreY() {
        return bgCentreY;
    }

    public void setBgCentreY(float bgCentreY) {
        this.bgCentreY = bgCentreY;
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

    public float getRulesForwardCentreX() {
        return rulesForwardCentreX;
    }

    public void setRulesForwardCentreX(float rulesForwardCentreX) {
        this.rulesForwardCentreX = rulesForwardCentreX;
    }

    public float getRulesForwardCentreY() {
        return rulesForwardCentreY;
    }

    public void setRulesForwardCentreY(float rulesForwardCentreY) {
        this.rulesForwardCentreY = rulesForwardCentreY;
    }

    public float getRulesBackwardCentreX() {
        return rulesBackwardCentreX;
    }

    public void setRulesBackwardCentreX(float rulesBackwardCentreX) {
        this.rulesBackwardCentreX = rulesBackwardCentreX;
    }

    public float getRulesBackwardCentreY() {
        return rulesBackwardCentreY;
    }

    public void setRulesBackwardCentreY(float rulesBackwardCentreY) {
        this.rulesBackwardCentreY = rulesBackwardCentreY;
    }

    public static float getUiRotation() {
        return UI_ROTATION;
    }

    public float getRulesWidth() {
        return rulesWidth;
    }

    public void setRulesWidth(float rulesWidth) {
        this.rulesWidth = rulesWidth;
    }

    public float getRulesHeight() {
        return rulesHeight;
    }

    public void setRulesHeight(float rulesHeight) {
        this.rulesHeight = rulesHeight;
    }

    public float getHowToPlayImageWidth() {
        return howToPlayImageWidth;
    }

    public void setHowToPlayImageWidth(float howToPlayImageWidth) {
        this.howToPlayImageWidth = howToPlayImageWidth;
    }

    public float getHowToPlayImageHeight() {
        return howToPlayImageHeight;
    }

    public void setHowToPlayImageHeight(float howToPlayImageHeight) {
        this.howToPlayImageHeight = howToPlayImageHeight;
    }

    public float getHowToPlayCentreX() {
        return howToPlayCentreX;
    }

    public void setHowToPlayCentreX(float howToPlayCentreX) {
        this.howToPlayCentreX = howToPlayCentreX;
    }

    public float getHowToPlayCentreY() {
        return howToPlayCentreY;
    }

    public void setHowToPlayCentreY(float howToPlayCentreY) {
        this.howToPlayCentreY = howToPlayCentreY;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

}