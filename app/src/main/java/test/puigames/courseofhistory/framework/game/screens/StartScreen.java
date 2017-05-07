package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.ImageUIElement;

/**
 * Created by Christopher on 07/05/2017.
 */

public class StartScreen extends Menu {

    private ImageUIElement backgroundStartScreen, title, pressScreen;

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


        if(duration > delay) {
            if(gameProperties.getInput().getTouchEvents().size() > 0)
                if(isTouched(inputBuddy.getTouchEvents().get(0), backgroundStartScreen))
                    gameProperties.setScreen(new MainMenu(gameProperties));
        }

        duration += deltaTime;
    }

    @Override
    public void load(){
        backgroundStartScreen = null;
        title = null;
        pressScreen = null;

        try{
            backgroundStartScreen = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/start_screen_background.png"),
                    bgImageWidth, bgImageHeight);
            title = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/title/coh_title.png"),
                    titleImageWidth, titleImageHeight);
            pressScreen = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/title/start_screen_instruction.png"),
                    pressScreenWidth, pressScreenHeight);
        }
        catch(NullPointerException e){
            Log.d("Error", "UI Element loading has failed");
        }

        backgroundStartScreen.place(this, centreX, bgCentreY);
        title.place(this, centreX, titleCentreY);
        pressScreen.place(this, centreX, pressScreenCentreY);
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

    public ImageUIElement getBackgroundStartScreen() {
        return backgroundStartScreen;
    }

    public void setBackgroundStartScreen(ImageUIElement backgroundStartScreen) {
        this.backgroundStartScreen = backgroundStartScreen;
    }

    public ImageUIElement getTitle() {
        return title;
    }

    public void setTitle(ImageUIElement title) {
        this.title = title;
    }

    public ImageUIElement getPressScreen() {
        return pressScreen;
    }

    public void setPressScreen(ImageUIElement pressScreen) {
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
