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
    private MenuButton back, next, previous;
    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;
    private float bgCentreX = 240.0f, bgCentreY = 160.0f;
    private float buttonWidth = 75.0f, buttonHeight = 35.0f;
    private float backCentreX = 40.0f, backCentreY = 300.0f;

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

/*
        if(forwardRules.)){
            //on input
            swapImage(1);
        }
        else if(backward.checkForInput(inputBuddy)){
            howToPlay.applyAction(-1);
        }*/
    }

    public void load(){

        backgroundHowToPlay = null;
        rulesForward = null;
        try{rulesBackward = null;
            backgroundHowToPlay = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/how_to_play_background.png"),
                    bgImageWidth, bgImageHeight);
            /*rulesForward = new ImageUIElement(this, resourceFetcher.getBitmapFromFile(""),
                    buttonWidth, buttonHeight);
            rulesBackward = new ImageUIElement(this, resourceFetcher.getBitmapFromFile(""),
                    buttonWidth, buttonHeight);*/
            howToPlayTexts.add(resourceFetcher.getBitmapFromFile(""));
            howToPlayTexts.add(resourceFetcher.getBitmapFromFile(""));
            //add those
        }
        catch (NullPointerException e) {
            Log.d("Error", "UI Element loading has failed");
        }

        backgroundHowToPlay.place(this, bgCentreX, bgCentreY);
        back.place(this, backCentreX, backCentreY);

        uiElements.add(backgroundHowToPlay);
        uiElements.add(back);

    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
    }

    /*private void swapImage(int index) {
        int currentIndex = howToPlayTexts.indexOf(howToPlayImage.getBitmap());
        currentIndex += index;
        if(currentIndex < 0)
            currentIndex = howToPlayTexts.size() - 1;
        currentIndex %= howToPlayTexts.size();
        howToPlayImage.setImage(howToPlayTexts.get(currentIndex));
    }*/

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
