package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.ImageUIElement;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.game.levels.TestLevel;

/**
 * Created by Christopher on 08/02/2017.
 */



public class MainMenu extends Menu {

    private ImageUIElement backgroundMainMenu, title;
    private MenuButton playGame, settings, howToPlay;
    private float buttonWidth = 75.0f, buttonHeight = 45.0f;
    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;
    private float titleImageWidth = 340.0f, titleImageHeight = 100.0f;
    private float centreX = 240.0f;
    private float bgCentreY = 160.0f, titleCentreY = 30.0f, playGameCentreY = 100.0f,
        howToPlayCentreY = 175.0f, settingsCentreY = 250.0f;

    public MainMenu(final GameProperties gameProperties){
        super(gameProperties);

        this.playGame = new MenuButton(this, gameProperties.getResourceFetcher().getBitmapFromFile("images/buttons/button_play.png"),
                buttonWidth, buttonHeight) {
            @Override
            public void applyAction() {
                gameProperties.setScreen(new TestLevel(gameProperties));
            }
        };

        howToPlay = new MenuButton(this, gameProperties.getResourceFetcher().getBitmapFromFile("images/buttons/button_how-to-play.png"),
                buttonWidth, buttonHeight){
            @Override
            public void applyAction(){
                gameProperties.setScreen(new HowToPlayMenu(gameProperties));
            }
        };

        settings = new MenuButton(this, gameProperties.getResourceFetcher().getBitmapFromFile("images/buttons/button_settings.png"),
                buttonWidth, buttonHeight){
            @Override
            public void applyAction(){
                gameProperties.setScreen(new SettingsMenu(gameProperties));
            }
        };

        load();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

     if(playGame.checkForInput(inputBuddy)){
         playGame.applyAction();
     }
     else if(howToPlay.checkForInput(inputBuddy)){
         howToPlay.applyAction();
     }
     else if(settings.checkForInput(inputBuddy)){
            settings.applyAction();
        }
    }

    public void load(){

        backgroundMainMenu = null;
        title = null;
        try{
           backgroundMainMenu = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/main_menu_background.png"),
                  bgImageWidth, bgImageHeight);
            title = new ImageUIElement(this, resourceFetcher.getBitmapFromFile("images/title/coh_title.png"),
                    titleImageWidth, titleImageHeight);
        }
        catch(NullPointerException e){
            Log.d("Error", "UI Element loading has failed");
        }

        backgroundMainMenu.place(this, centreX, bgCentreY);
        title.place(this, centreX, titleCentreY);
        playGame.place(this, centreX, playGameCentreY);
        howToPlay.place(this, centreX, howToPlayCentreY);
        settings.place(this, centreX, settingsCentreY);

        uiElements.add(backgroundMainMenu);
        uiElements.add(title);
        uiElements.add(playGame);
        uiElements.add(howToPlay);
        uiElements.add(settings);
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
