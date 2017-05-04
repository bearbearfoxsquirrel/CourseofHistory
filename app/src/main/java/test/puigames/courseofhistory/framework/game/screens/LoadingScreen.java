package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.ImageUIElement;
import test.puigames.courseofhistory.framework.game.levels.TestLevel;

/**
 * Created by Christopher on 04/05/2017.
 */

public class LoadingScreen extends Menu {

    private ImageUIElement bgImage, logo;

    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;
    private float bgImageCentreX = 240.0f, bgImageCentreY = 160.0f;

    private float logoWidth = 100.0f, logoHeight = 100.0f;
    private float logoCentreX = 44.0f, logoCentreY = 100;

    private float duration = 0.0f, delay = 5.0f;

    public LoadingScreen(final GameProperties gameProperties){
        super(gameProperties);
        load();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(duration > delay)
            gameProperties.setScreen(new TestLevel(this.gameProperties));

        duration += deltaTime;
    }

    public void load(){
        bgImage = null;
        try{
            bgImage = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/loading_screen_background.png"),
                    bgImageWidth, bgImageHeight);
            logo = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/title/logo_base.png"),
                    logoWidth, logoHeight);
        }
        catch(NullPointerException e){
            Log.d("Error", "UI Element loading has failed");
        }

        bgImage.place(this, bgImageCentreX, bgImageCentreY);
        logo.place(this, logoCentreX, logoCentreY);

        uiElements.add(bgImage);
        uiElements.add(logo);

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

    public ImageUIElement getBgImage() {
        return bgImage;
    }

    public void setBgImage(ImageUIElement bgImage) {
        this.bgImage = bgImage;
    }

    public ImageUIElement getLogo() {
        return logo;
    }

    public void setLogo(ImageUIElement logo) {
        this.logo = logo;
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

    public float getBgImageCentreX() {
        return bgImageCentreX;
    }

    public void setBgImageCentreX(float bgImageCentreX) {
        this.bgImageCentreX = bgImageCentreX;
    }

    public float getBgImageCentreY() {
        return bgImageCentreY;
    }

    public void setBgImageCentreY(float bgImageCentreY) {
        this.bgImageCentreY = bgImageCentreY;
    }

    public float getLogoWidth() {
        return logoWidth;
    }

    public void setLogoWidth(float logoWidth) {
        this.logoWidth = logoWidth;
    }

    public float getLogoHeight() {
        return logoHeight;
    }

    public void setLogoHeight(float logoHeight) {
        this.logoHeight = logoHeight;
    }

    public float getLogoCentreX() {
        return logoCentreX;
    }

    public void setLogoCentreX(float logoCentreX) {
        this.logoCentreX = logoCentreX;
    }

    public float getLogoCentreY() {
        return logoCentreY;
    }

    public void setLogoCentreY(float logoCentreY) {
        this.logoCentreY = logoCentreY;
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
