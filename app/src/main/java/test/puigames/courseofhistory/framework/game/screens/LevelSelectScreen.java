package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;

/**
 * Created by Christopher on 28/04/2017.
 */

public class LevelSelectScreen extends Menu {

    private static final float UI_ROTATION = 0.f;

    private UIElement backgroundLevelSelect, PvP, PvPText, PvCPU,
            PvCPUText, CPUvCPU, CPUvCPUText;

    private MenuButton back;

    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;
    private float bgCentreX = 240.0f, bgCentreY = 160.0f;

    private float levelImageWidth = 100.0f, levelImageHeight = 180.0f;
    private float levelTextWidth = 100.0f, levelTextHeight = 75.0f;

    private float PvPImageCentreX = 100.0f, PvPImageCentreY = 106.67f;
    private float PvPTextCentreX = 100.0f, PvPTextCentreY = 245.0f;

    private float PvCPUImageCentreX = 230.0f, PvCPUImageCentreY = 106.67f;
    private float PvCPUTextCentreX = 230.0f, PvCPUTextCentreY = 245.0f;

    private float CPUvCPUImageCentreX = 360.0f, CPUvCPUImageCentreY = 106.67f;
    private float CPUvCPUTextCentreX = 360, CPUvCPUTextCentreY = 245.0f;

    private float backWidth = 75.0f, backHeight = 35.0f;
    private float backCentreX = 40.0f, backCentreY = 300.0f;

    private float duration = 0.0f, delay = 0.025f;

    public LevelSelectScreen(final GameProperties gameProperties){
        super(gameProperties);

        //Creation of the back button to return to the main menu.
        this.back = new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_back.png"),
                backWidth, backHeight) {
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

        //Checks for input and prevents null pointer errors, delay to prevent multiple input
        //occurrences.
        if(duration > delay) {
            if (gameProperties.getInput().getTouchEvents().size() > 0) {
                if (isTouched(inputBuddy.getTouchEvents().get(0), PvP))
                    gameProperties.setScreen(new LoadingScreenPVP(this.gameProperties));
                if (isTouched(inputBuddy.getTouchEvents().get(0), PvCPU))
                    gameProperties.setScreen(new LoadingScreenPvCPU(this.gameProperties));
                if (isTouched(inputBuddy.getTouchEvents().get(0), CPUvCPU))
                    gameProperties.setScreen(new LoadingScreenCPUvCPU(this.gameProperties));
            }


            if (back.checkForInput(inputBuddy))
                back.applyAction();
        }
        duration += deltaTime;
    }

    //Method to load all of the assets used and then place, scale, and draw them.
    @Override
    public void load() {
        //Setting up uiElements.
        backgroundLevelSelect = null;
        PvP = null;
        PvPText = null;
        PvCPU = null;
        PvCPUText = null;
        CPUvCPU = null;
        CPUvCPUText = null;

        try{
            backgroundLevelSelect = new UIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/level_select_background.png"),
                    bgImageWidth, bgImageHeight);
            PvP = new UIElement(this, resourceFetcher.getBitmapFromFile("images/level_images/PvP_framed.png"),
                    levelImageWidth, levelImageHeight);
            PvPText = new UIElement(this, resourceFetcher.getBitmapFromFile("images/level_text/PvP.png"),
                    levelTextWidth, levelTextHeight);
            PvCPU = new UIElement(this, resourceFetcher.getBitmapFromFile("images/level_images/PvCPU_framed.png"),
                    levelImageWidth, levelImageHeight);
            PvCPUText = new UIElement(this, resourceFetcher.getBitmapFromFile("images/level_text/PvCPU.png"),
                    levelTextWidth, levelTextHeight);
            CPUvCPU = new UIElement(this, resourceFetcher.getBitmapFromFile("images/level_images/CPUvCPU_framed.png"),
                    levelImageWidth, levelImageHeight);
            CPUvCPUText = new UIElement(this, resourceFetcher.getBitmapFromFile("images/level_text/CPUvCPU.png"),
                    levelTextWidth, levelTextHeight);
        }
        catch(NullPointerException e){
            Log.d("Error", "UI Element loading has failed");
        }

        //uiElements are given a location to be placed on the screen based on the centre of their image.
        backgroundLevelSelect.place(this, bgCentreX, bgCentreY, UI_ROTATION);
        PvP.place(this, PvPImageCentreX, PvPImageCentreY, UI_ROTATION);
        PvPText.place(this, PvPTextCentreX, PvPTextCentreY, UI_ROTATION);
        PvCPU.place(this, PvCPUImageCentreX, PvCPUImageCentreY, UI_ROTATION);
        PvCPUText.place(this, PvCPUTextCentreX, PvCPUTextCentreY, UI_ROTATION);
        CPUvCPU.place(this, CPUvCPUImageCentreX, CPUvCPUImageCentreY, UI_ROTATION);
        CPUvCPUText.place(this, CPUvCPUTextCentreX, CPUvCPUTextCentreY, UI_ROTATION);
        back.place(this, backCentreX, backCentreY, UI_ROTATION);

        //Added to the uiElements ArrayList to be scaled and drawn to the screen.
        uiElements.add(backgroundLevelSelect);
        uiElements.add(PvP);
        uiElements.add(PvPText);
        uiElements.add(PvCPU);
        uiElements.add(PvCPUText);
        uiElements.add(CPUvCPU);
        uiElements.add(CPUvCPUText);
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

    //Getters and setters.
    public UIElement getBackgroundLevelSelect() {
        return backgroundLevelSelect;
    }

    public void setBackgroundLevelSelect(UIElement backgroundLevelSelect) {
        this.backgroundLevelSelect = backgroundLevelSelect;
    }

    public UIElement getPvP() {
        return PvP;
    }

    public void setPvP(UIElement pvP) {
        this.PvP = pvP;
    }

    public UIElement getPvPText() {
        return PvPText;
    }

    public void setPvPText(UIElement pvPText) {
        this.PvPText = pvPText;
    }

    public UIElement getPvCPU() {
        return PvCPU;
    }

    public void setPvCPU(UIElement pvCPU) {
        this.PvCPU = pvCPU;
    }

    public UIElement getPvCPUText() {
        return PvCPUText;
    }

    public void setPvCPUText(UIElement pvCPUText) {
        this.PvCPUText = pvCPUText;
    }

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

    public float getLevelImageWidth() {
        return levelImageWidth;
    }

    public void setLevelImageWidth(float levelImageWidth) {
        this.levelImageWidth = levelImageWidth;
    }

    public float getLevelImageHeight() {
        return levelImageHeight;
    }

    public void setLevelImageHeight(float levelImageHeight) {
        this.levelImageHeight = levelImageHeight;
    }

    public float getLevelTextWidth() {
        return levelTextWidth;
    }

    public void setLevelTextWidth(float levelTextWidth) {
        this.levelTextWidth = levelTextWidth;
    }

    public float getLevelTextHeight() {
        return levelTextHeight;
    }

    public void setLevelTextHeight(float levelTextHeight) {
        this.levelTextHeight = levelTextHeight;
    }

    public float getPvPImageCentreX() {
        return PvPImageCentreX;
    }

    public void setPvPImageCentreX(float pvPImageCentreX) {
        this.PvPImageCentreX = pvPImageCentreX;
    }

    public float getPvPImageCentreY() {
        return PvPImageCentreY;
    }

    public void setPvPImageCentreY(float pvPImageCentreY) {
        this.PvPImageCentreY = pvPImageCentreY;
    }

    public float getPvPTextCentreX() {
        return PvPTextCentreX;
    }

    public void setPvPTextCentreX(float pvPTextCentreX) {
        this.PvPTextCentreX = pvPTextCentreX;
    }

    public float getPvPTextCentreY() {
        return PvPTextCentreY;
    }

    public void setPvPTextCentreY(float pvPTextCentreY) {
        this.PvPTextCentreY = pvPTextCentreY;
    }

    public float getPvCPUImageCentreX() {
        return PvCPUImageCentreX;
    }

    public void setPvCPUImageCentreX(float pvCPUImageCentreX) {
        this.PvCPUImageCentreX = pvCPUImageCentreX;
    }

    public float getPvCPUImageCentreY() {
        return PvCPUImageCentreY;
    }

    public void setPvCPUImageCentreY(float pvCPUImageCentreY) {
        this.PvCPUImageCentreY = pvCPUImageCentreY;
    }

    public float getPvCPUTextCentreX() {
        return PvCPUTextCentreX;
    }

    public void setPvCPUTextCentreX(float pvCPUTextCentreX) {
        this.PvCPUTextCentreX = pvCPUTextCentreX;
    }

    public float getPvCPUTextCentreY() {
        return PvCPUTextCentreY;
    }

    public void setPvCPUTextCentreY(float pvCPUTextCentreY) {
        this.PvCPUTextCentreY = pvCPUTextCentreY;
    }

    public static float getUiRotation() {
        return UI_ROTATION;
    }

    public UIElement getCPUvCPU() {
        return CPUvCPU;
    }

    public void setCPUvCPU(UIElement CPUvCPU) {
        this.CPUvCPU = CPUvCPU;
    }

    public UIElement getCPUvCPUText() {
        return CPUvCPUText;
    }

    public void setCPUvCPUText(UIElement CPUvCPUText) {
        this.CPUvCPUText = CPUvCPUText;
    }

    public float getCPUvCPUImageCentreX() {
        return CPUvCPUImageCentreX;
    }

    public void setCPUvCPUImageCentreX(float CPUvCPUImageCentreX) {
        this.CPUvCPUImageCentreX = CPUvCPUImageCentreX;
    }

    public float getCPUvCPUImageCentreY() {
        return CPUvCPUImageCentreY;
    }

    public void setCPUvCPUImageCentreY(float CPUvCPUImageCentreY) {
        this.CPUvCPUImageCentreY = CPUvCPUImageCentreY;
    }

    public float getCPUvCPUTextCentreX() {
        return CPUvCPUTextCentreX;
    }

    public void setCPUvCPUTextCentreX(float CPUvCPUTextCentreX) {
        this.CPUvCPUTextCentreX = CPUvCPUTextCentreX;
    }

    public float getCPUvCPUTextCentreY() {
        return CPUvCPUTextCentreY;
    }

    public void setCPUvCPUTextCentreY(float CPUvCPUTextCentreY) {
        this.CPUvCPUTextCentreY = CPUvCPUTextCentreY;
    }

    public float getBackWidth() {
        return backWidth;
    }

    public void setBackWidth(float backWidth) {
        this.backWidth = backWidth;
    }

    public float getBackHeight() {
        return backHeight;
    }

    public void setBackHeight(float backHeight) {
        this.backHeight = backHeight;
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

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }
}
