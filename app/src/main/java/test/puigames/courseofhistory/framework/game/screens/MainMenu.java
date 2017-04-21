package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;
import android.text.method.Touch;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.engine.ui.imageUIElement;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.game.levels.TestLevel;

/**
 * Created by Christopher on 08/02/2017.
 */

public class MainMenu extends Menu {

    imageUIElement backgroundMainMenu, title;
    MenuButton playGame, settings, howToPlay;
    float buttonWidth = 75.f;
    float buttonHeight = 50.f;

    public MainMenu(GameProperties gameProperties){
        super(gameProperties);
        load();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public void load(){
        backgroundMainMenu = null;
        title = null;
        playGame = null;
        settings = null;
        howToPlay = null;

        try{
            backgroundMainMenu = new imageUIElement(resourceFetcher.getBitmapFromFile("images/backgrounds/main_menu_background.png"),
                    480.0f, 320.0f);
            title = new imageUIElement(resourceFetcher.getBitmapFromFile("images/title/coh_title.png"),
                    340.0f, 100.0f);
            playGame = new MenuButton(gameProperties.getResourceFetcher().getBitmapFromFile("images/buttons/button_play.png"),
                    buttonWidth, buttonHeight);
            howToPlay = new MenuButton(gameProperties.getResourceFetcher().getBitmapFromFile("images/buttons/button_how-to-play.png"),
                    buttonWidth, buttonHeight);
            settings = new MenuButton(gameProperties.getResourceFetcher().getBitmapFromFile("images/buttons/button_settings.png"),
                    buttonWidth, buttonHeight);
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }

        uiElements.add(backgroundMainMenu);
        uiElements.add(title);
        uiElements.add(playGame);
        uiElements.add(howToPlay);
        uiElements.add(settings);

        backgroundMainMenu.placeUIElement(240.f, 160.f);
        title.placeUIElement(240.0f, 30.0f);
        playGame.placeUIElement((240.f), (100.f));
        howToPlay.placeUIElement((240.f), (175.f));
        settings.placeUIElement((240.f), (250.f));
    }

    public void switchScreen(Screen screen){
        if(playGame.boundingBox.isTouchOn(gameProperties.getInput().getTouchEvents().get(0))){
            gameProperties.setScreen(new TestLevel(this.gameProperties));
        }
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
