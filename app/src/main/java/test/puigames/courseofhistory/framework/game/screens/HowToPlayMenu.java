package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.ImageUIElement;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;

/**
 * Created by Christopher on 02/03/2017.
 */


public class HowToPlayMenu extends Menu {

    private ImageUIElement backgroundHowToPlay, rulesForward, rulesBackward, howToPlayImage;
    private ArrayList<Bitmap> howToPlayTexts;
    private MenuButton back;
    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;
    private float bgCentreX = 240.0f, bgCentreY = 160.0f;
    private float buttonWidth = 75.0f, buttonHeight = 35.0f;
    private float backCentreX = 40.0f, backCentreY = 300.0f;
    private float rulesForwardCentreX = 450.0f, rulesForwardCentreY = 295.0f;
    private float rulesBackwardCentreX = 390.0f, rulesBackwardCentreY = 295.0f;
    private float rulesWidth = 50.0f, rulesHeight = 50.0f;
    private float howToPlayImageWidth = 440.0f, howToPlayImageHeight = 260.0f;
    private float howToPlayCentreX = 240.0f, howToPlayCentreY = 135.0f;

    public HowToPlayMenu(final GameProperties gameProperties){
        super(gameProperties);

        howToPlayTexts = new ArrayList<>();

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
        if(gameProperties.getInput().getTouchEvents().size() > 0) {
            if(isTouched(inputBuddy.getTouchEvents().get(0), rulesForward))
                swapImageForward();
            if(isTouched(inputBuddy.getTouchEvents().get(0), rulesBackward)) {
                swapImageBackward();
            }
        }
    }

    public void load(){

        backgroundHowToPlay = null;
        rulesForward = null;
        rulesBackward = null;
        howToPlayImage = null;
        try{
            backgroundHowToPlay = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/how_to_play_background.png"),
                    bgImageWidth, bgImageHeight);
            rulesForward = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/buttons/button_forward.png"),
                    rulesWidth, rulesHeight);
            rulesBackward = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/buttons/button_backward.png"),
                    rulesWidth, rulesHeight);
            howToPlayImage = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/how_to_play/Rules1Transparent.png"),
                    howToPlayImageWidth, howToPlayImageHeight);
            howToPlayTexts.add(resourceFetcher.getBitmapFromFile("images/how_to_play/Rules1Transparent.png"));
            howToPlayTexts.add(resourceFetcher.getBitmapFromFile("images/how_to_play/Rules2Transparent.png"));
            howToPlayTexts.add(resourceFetcher.getBitmapFromFile("images/how_to_play/Rules3Transparent.png"));

        }
        catch (NullPointerException e) {
            Log.d("Error", "UI Element loading has failed");
        }

        backgroundHowToPlay.place(this, bgCentreX, bgCentreY);
        back.place(this, backCentreX, backCentreY);
        rulesForward.place(this, rulesForwardCentreX, rulesForwardCentreY);
        rulesBackward.place(this, rulesBackwardCentreX, rulesBackwardCentreY);
        howToPlayImage.place(this, howToPlayCentreX, howToPlayCentreY);

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

    private void swapImageForward() {
        int currentIndex = howToPlayTexts.indexOf(howToPlayImage.getBitmap());
        currentIndex++;
        if(currentIndex < 0)
            currentIndex = howToPlayTexts.size() - 1;
        else if (currentIndex > howToPlayTexts.size() - 1)
            currentIndex = 0;
        howToPlayImage.setImage(howToPlayTexts.get(currentIndex));
    }

    private void swapImageBackward() {
        int currentIndex = howToPlayTexts.indexOf(howToPlayImage.getBitmap());
        currentIndex--;
        if(currentIndex < 0)
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

}
