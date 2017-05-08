package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;

/**
 * Created by Christopher on 07/05/2017.
 */

public class StartScreen extends Menu {

    private static final float UI_ROTATION = 0.f;

    private UIElement backgroundStartScreen, title, pressScreen;

    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;

    private float titleImageWidth = 380.0f, titleImageHeight = 150.0f;

    private float pressScreenWidth = 400.0f, pressScreenHeight = 100.0f;

    private float centreX = 240.0f;

    private float bgCentreY = 160.0f, titleCentreY = 45.0f, pressScreenCentreY = 290.0f;

    private float duration = 0.0f, delay = 0.0025f;

    public StartScreen(final GameProperties gameProperties){
        super(gameProperties);
        load();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //Checks for input and prevents null pointer errors
        if(duration > delay) {
            if(gameProperties.getInput().getTouchEvents().size() > 0)
                if(isTouched(inputBuddy.getTouchEvents().get(0), backgroundStartScreen))
                    gameProperties.setScreen(new MainMenu(gameProperties));
        }

        duration += deltaTime;
    }

    //Method to load all of the assets used and then place, scale, and draw them.
    @Override
    public void load(){
        backgroundStartScreen = null;
        title = null;
        pressScreen = null;

        //Settings up uiElements.
        try{
            backgroundStartScreen = new UIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/start_screen_background.png"),
                    bgImageWidth, bgImageHeight);
            title = new UIElement(this, resourceFetcher.getBitmapFromFile("images/title/coh_title.png"),
                    titleImageWidth, titleImageHeight);
            pressScreen = new UIElement(this, resourceFetcher.getBitmapFromFile("images/title/start_screen_instruction.png"),
                    pressScreenWidth, pressScreenHeight);
        }
        catch(NullPointerException e){
            Log.d("Error", "UI Element loading has failed");
        }

        //uiElements are given a location to be placed on the screen based on the centre of their image.
        backgroundStartScreen.place(this, centreX, bgCentreY, UI_ROTATION);
        title.place(this, centreX, titleCentreY, UI_ROTATION);
        pressScreen.place(this, centreX, pressScreenCentreY, UI_ROTATION);

        //Added to the uiElements ArrayList to be scaled and drawn to the screen.
        uiElements.add(backgroundStartScreen);
        uiElements.add(title);
        uiElements.add(pressScreen);
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
    public UIElement getBackgroundStartScreen() {
        return backgroundStartScreen;
    }

    public void setBackgroundStartScreen(UIElement backgroundStartScreen) {
        this.backgroundStartScreen = backgroundStartScreen;
    }

    public UIElement getTitle() {
        return title;
    }

    public void setTitle(UIElement title) {
        this.title = title;
    }

    public UIElement getPressScreen() {
        return pressScreen;
    }

    public void setPressScreen(UIElement pressScreen) {
        this.pressScreen = pressScreen;
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

    public float getTitleImageWidth() {
        return titleImageWidth;
    }

    public void setTitleImageWidth(float titleImageWidth) {
        this.titleImageWidth = titleImageWidth;
    }

    public float getTitleImageHeight() {
        return titleImageHeight;
    }

    public void setTitleImageHeight(float titleImageHeight) {
        this.titleImageHeight = titleImageHeight;
    }

    public float getPressScreenWidth() {
        return pressScreenWidth;
    }

    public void setPressScreenWidth(float pressScreenWidth) {
        this.pressScreenWidth = pressScreenWidth;
    }

    public float getPressScreenHeight() {
        return pressScreenHeight;
    }

    public void setPressScreenHeight(float pressScreenHeight) {
        this.pressScreenHeight = pressScreenHeight;
    }

    public float getCentreX() {
        return centreX;
    }

    public void setCentreX(float centreX) {
        this.centreX = centreX;
    }

    public float getBgCentreY() {
        return bgCentreY;
    }

    public void setBgCentreY(float bgCentreY) {
        this.bgCentreY = bgCentreY;
    }

    public float getTitleCentreY() {
        return titleCentreY;
    }

    public void setTitleCentreY(float titleCentreY) {
        this.titleCentreY = titleCentreY;
    }

    public float getPressScreenCentreY() {
        return pressScreenCentreY;
    }

    public void setPressScreenCentreY(float pressScreenCentreY) {
        this.pressScreenCentreY = pressScreenCentreY;
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
