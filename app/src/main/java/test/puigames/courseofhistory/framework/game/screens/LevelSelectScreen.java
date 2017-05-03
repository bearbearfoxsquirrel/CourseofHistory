package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.ImageUIElement;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.game.levels.TestLevel;

/**
 * Created by Christopher on 28/04/2017.
 */

public class LevelSelectScreen extends Menu {

    private ImageUIElement backgroundLevelSelect, levelOne, levelOneText;
    private MenuButton back;
    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;
    private float bgCentreX = 240.0f, bgCentreY = 160.0f;
    private float L1ImageWidth = 133.33f, L1ImageHeight = 200.0f;
    private float L1ImageCentreX = 140.0f, L1ImageCentreY = 136.67f;
    private float L1TextWidth = 75.0f, L1TextHeight = 35.0f;
    private float L1TextCentreX = 160.0f, L1TextCentreY = 265.0f;
    private float backWidth = 75.0f, backHeight = 35.0f;
    private float backCentreX = 240.0f, backCentreY = 300.0f;

    public LevelSelectScreen(final GameProperties gameProperties){
        super(gameProperties);

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

        if (gameProperties.getInput().getTouchEvents().size() > 0)
            if(levelOne.getBoundingBox().isTouchOn(gameProperties.getInput().getTouchEvents().get(0)))
                gameProperties.setScreen(new TestLevel(this.gameProperties));

        if(back.checkForInput(inputBuddy)) {
            back.applyAction();
        }
    }

    @Override
    public void load() {

        backgroundLevelSelect = null;
        levelOne = null;

        try{
            backgroundLevelSelect = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/level_select_background.png"),
                bgImageWidth, bgImageHeight);
            levelOne = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/Level/level_one_image.png"), L1ImageWidth, L1ImageHeight);
        }
        catch(NullPointerException e){
            Log.d("Error", "UI Element loading has failed");
        }

        backgroundLevelSelect.place(this, bgCentreX, bgCentreY);
        levelOne.place(this, L1ImageCentreX, L1ImageCentreY);
        back.place(this, backCentreX, backCentreY);

        uiElements.add(backgroundLevelSelect);
        uiElements.add(levelOne);
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
}
