package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;

/**
 * Created by Christopher on 08/02/2017.
 */



public class MainMenu extends Menu {

    private static final float UI_ROTATION = 0.f;
    private UIElement backgroundMainMenu, title;

    private MenuButton playGame, settings, howToPlay, exitGame;

    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;

    private float titleImageWidth = 360.0f, titleImageHeight = 100.0f;

    private float centreX = 240.0f;

    private float bgCentreY = 160.0f, titleCentreY = 30.0f, playGameCentreY = 100.0f,
    howToPlayCentreY = 160.0f, settingsCentreY = 220.0f, exitGameY = 280.0f;

    private float buttonWidth = 75.0f, buttonHeight = 40.0f;

    private float duration = 0.0f, delay = 0.0025f;


    public MainMenu(final GameProperties gameProperties){
        super(gameProperties);

        //Creation of the menu buttons, each with a specific function based on what they are required
        //to do.
        this.playGame = new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_play.png"),
                buttonWidth, buttonHeight) {
            @Override
            public void applyAction() {
                gameProperties.setScreen(new LevelSelectScreen(gameProperties));
            }
        };

        this.howToPlay = new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_how-to-play.png"),
                buttonWidth, buttonHeight){
            @Override
            public void applyAction(){
                gameProperties.setScreen(new HowToPlayMenu(gameProperties));
            }
        };

       this.settings = new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_settings.png"),
                buttonWidth, buttonHeight){
            @Override
            public void applyAction(){
                gameProperties.setScreen(new SettingsMenu(gameProperties));
            }
        };

        this.exitGame = new MenuButton(this, resourceFetcher.getBitmapFromFile("images/buttons/button_exit.png"),
                buttonWidth, buttonHeight) {
            @Override
            public void applyAction() {
                System.exit(0);
            }
        };

        load();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //Checks for input then performs button specific function, delay to prevent multiple
        //input occurrences.
        if(duration > delay) {
            if (playGame.checkForInput(inputBuddy))
                playGame.applyAction();
            if (howToPlay.checkForInput(inputBuddy))
                howToPlay.applyAction();
            if (settings.checkForInput(inputBuddy))
                settings.applyAction();
            if (exitGame.checkForInput(inputBuddy))
                exitGame.applyAction();
        }

        duration += deltaTime;
    }

    //Method to load all of the assets used and then place, scale, and draw them.
    public void load(){

        //Settings up uiElements.
        backgroundMainMenu = null;
        title = null;
        try{
           backgroundMainMenu = new UIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/main_menu_background.png"),
                  bgImageWidth, bgImageHeight);
            title = new UIElement(this, resourceFetcher.getBitmapFromFile("images/title/coh_title.png"),
                    titleImageWidth, titleImageHeight);
        }
        catch(NullPointerException e){
            Log.d("Error", "UI Element loading has failed");
        }

        //uiElements are given a location to be placed on the screen based on the centre of their image.
        backgroundMainMenu.place(this, centreX, bgCentreY, UI_ROTATION);
        title.place(this, centreX, titleCentreY, UI_ROTATION);
        playGame.place(this, centreX, playGameCentreY, UI_ROTATION);
        howToPlay.place(this, centreX, howToPlayCentreY, UI_ROTATION);
        settings.place(this, centreX, settingsCentreY, UI_ROTATION);
        exitGame.place(this, centreX, exitGameY, UI_ROTATION);

        //Added to the uiElements ArrayList to be scaled and drawn to the screen.
        uiElements.add(backgroundMainMenu);
        uiElements.add(title);
        uiElements.add(playGame);
        uiElements.add(howToPlay);
        uiElements.add(settings);
        uiElements.add(exitGame);
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
    public UIElement getBackgroundMainMenu() {
        return backgroundMainMenu;
    }

    public void setBackgroundMainMenu(UIElement backgroundMainMenu) {
        this.backgroundMainMenu = backgroundMainMenu;
    }

    public UIElement getTitle() {
        return title;
    }

    public void setTitle(UIElement title) {
        this.title = title;
    }

    public MenuButton getPlayGame() {
        return playGame;
    }

    public void setPlayGame(MenuButton playGame) {
        this.playGame = playGame;
    }

    public MenuButton getSettings() {
        return settings;
    }

    public void setSettings(MenuButton settings) {
        this.settings = settings;
    }

    public MenuButton getHowToPlay() {
        return howToPlay;
    }

    public void setHowToPlay(MenuButton howToPlay) {
        this.howToPlay = howToPlay;
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

    public float getPlayGameCentreY() {
        return playGameCentreY;
    }

    public void setPlayGameCentreY(float playGameCentreY) {
        this.playGameCentreY = playGameCentreY;
    }

    public float getHowToPlayCentreY() {
        return howToPlayCentreY;
    }

    public void setHowToPlayCentreY(float howToPlayCentreY) {
        this.howToPlayCentreY = howToPlayCentreY;
    }

    public float getSettingsCentreY() {
        return settingsCentreY;
    }

    public void setSettingsCentreY(float settingsCentreY) {
        this.settingsCentreY = settingsCentreY;
    }
}
