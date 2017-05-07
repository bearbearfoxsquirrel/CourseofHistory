package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.ImageUIElement;

/**
 * Created by Christopher on 24/11/2016.
 */

public class SplashScreen extends Menu {

    private ImageUIElement logo;
    private float logoImageWidth = 480.0f, logoImageHeight = 320.0f;
    private float logoImageCentreX = 240.0f, logoImageCentreY = 160.0f;

    private float screenDuration = 0.0f, splashScreenDelay = 3.0f;

    public SplashScreen(final GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    @Override
    public void load() {
        try {
            logo = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/splashscreen/splash.png"),
                    logoImageWidth, logoImageHeight);
        }
        catch(NullPointerException e) {
            Log.d("Error", "Can't load UI elements");
            this.gameProperties.setScreen(new SplashScreen(this.gameProperties));
        }

        logo.place(this, logoImageCentreX, logoImageCentreY);

        uiElements.add(logo);
    }

    //Allows for the splash image to display and then swaps screen to main menu after a set time
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(screenDuration > splashScreenDelay)
                this.gameProperties.setScreen(new StartScreen(this.gameProperties));

        screenDuration += deltaTime;
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

    //Getters and Setters
    public ImageUIElement getLogo() {
        return logo;
    }

    public void setLogo(ImageUIElement logo) {
        this.logo = logo;
    }

    public float getScreenDuration() {
        return screenDuration;
    }

    public void setScreenDuration(float screenDuration) {
        this.screenDuration = screenDuration;
    }

    public float getSplashScreenDelay() {
        return splashScreenDelay;
    }

    public void setSplashScreenDelay(float splashScreenDelay) {
        this.splashScreenDelay = splashScreenDelay;
    }

    public float getLogoImageWidth() {
        return logoImageWidth;
    }

    public void setLogoImageWidth(float logoImageWidth) {
        this.logoImageWidth = logoImageWidth;
    }

    public float getLogoImageHeight() {
        return logoImageHeight;
    }

    public void setLogoImageHeight(float logoImageHeight) {
        this.logoImageHeight = logoImageHeight;
    }

    public float getLogoImageCentreX() {
        return logoImageCentreX;
    }

    public void setLogoImageCentreX(float logoImageCentreX) {
        this.logoImageCentreX = logoImageCentreX;
    }

    public float getLogoImageCentreY() {
        return logoImageCentreY;
    }

    public void setLogoImageCentreY(float logoImageCentreY) {
        this.logoImageCentreY = logoImageCentreY;
    }

}
