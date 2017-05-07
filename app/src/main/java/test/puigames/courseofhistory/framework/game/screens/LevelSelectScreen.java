package test.puigames.courseofhistory.framework.game.screens;

import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Menu;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;

/**
 * Created by Christopher on 28/04/2017.
 */

public class LevelSelectScreen extends Menu {

    private static final float UI_ROTATION = 0.f;

    private UIElement backgroundLevelSelect, levelGreatMinds, levelGreatMindsText, levelWorldLeaders,
            levelWorldLeadersText;

    private MenuButton back;

    private float bgImageWidth = 480.0f, bgImageHeight = 320.0f;
    private float bgCentreX = 240.0f, bgCentreY = 160.0f;

    private float levelImageWidth = 160.0f, levelImageHeight = 200.0f;
    private float levelTextWidth = 160.0f, levelTextHeight = 75.0f;

    private float levelGreatMindsImageCentreX = 140.0f, levelGreatMindsImageCentreY = 106.67f;
    private float levelGreatMindsTextCentreX = 140.0f, levelGreatMindsTextCentreY = 245.0f;

    private float levelWorldLeadersImageCentreX = 340.0f, levelWorldLeadersImageCentreY = 106.67f;
    private float levelWorldLeadersTextCentreX = 340.0f, levelWorldLeadersTextCentreY = 245.0f;

    private float backWidth = 75.0f, backHeight = 35.0f;
    private float backCentreX = 40.0f, backCentreY = 300.0f;

    private float duration = 0.0f, delay = 0.025f;

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

        if(duration > delay) {
            if (gameProperties.getInput().getTouchEvents().size() > 0)
                if (isTouched(inputBuddy.getTouchEvents().get(0), levelGreatMinds))
                    gameProperties.setScreen(new LoadingScreen(this.gameProperties));
                else if(isTouched(inputBuddy.getTouchEvents().get(0), levelWorldLeaders))
                    gameProperties.setScreen(new LoadingScreen(this.gameProperties));


            if (back.checkForInput(inputBuddy)) {
                back.applyAction();
            }
        }
        duration += deltaTime;
    }

    @Override
    public void load() {
        backgroundLevelSelect = null;
        levelGreatMinds = null;
        levelGreatMindsText = null;
        levelWorldLeaders = null;
        levelWorldLeadersText = null;

        try{
            backgroundLevelSelect = new UIElement(this, resourceFetcher.getBitmapFromFile("images/backgrounds/level_select_background.png"),
                    bgImageWidth, bgImageHeight);
            levelGreatMinds = new UIElement(this, resourceFetcher.getBitmapFromFile("images/level_images/level_great_minds_framed.png"),
                    levelImageWidth, levelImageHeight);
            levelGreatMindsText = new UIElement(this, resourceFetcher.getBitmapFromFile("images/level_text/level_text_great_minds.png"),
                    levelTextWidth, levelTextHeight);
            levelWorldLeaders= new UIElement(this, resourceFetcher.getBitmapFromFile("images/level_images/level_world_leaders_framed.png"),
                    levelImageWidth, levelImageHeight);
            levelWorldLeadersText = new UIElement(this, resourceFetcher.getBitmapFromFile("images/level_text/level_text_world_leaders.png"),
                    levelTextWidth, levelTextHeight);
        }
        catch(NullPointerException e){
            Log.d("Error", "UI Element loading has failed");
        }

        backgroundLevelSelect.place(this, bgCentreX, bgCentreY, UI_ROTATION);
        levelGreatMinds.place(this, levelGreatMindsImageCentreX, levelGreatMindsImageCentreY, UI_ROTATION);
        levelGreatMindsText.place(this, levelGreatMindsTextCentreX, levelGreatMindsTextCentreY, UI_ROTATION);
        levelWorldLeaders.place(this, levelWorldLeadersImageCentreX, levelWorldLeadersImageCentreY, UI_ROTATION);
        levelWorldLeadersText.place(this,levelWorldLeadersTextCentreX, levelWorldLeadersTextCentreY, UI_ROTATION);
        back.place(this, backCentreX, backCentreY, UI_ROTATION);

        uiElements.add(backgroundLevelSelect);
        uiElements.add(levelGreatMinds);
        uiElements.add(levelGreatMindsText);
        uiElements.add(levelWorldLeaders);
        uiElements.add(levelWorldLeadersText);
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

    public UIElement getBackgroundLevelSelect() {
        return backgroundLevelSelect;
    }

    public void setBackgroundLevelSelect(UIElement backgroundLevelSelect) {
        this.backgroundLevelSelect = backgroundLevelSelect;
    }

    public UIElement getLevelGreatMinds() {
        return levelGreatMinds;
    }

    public void setLevelGreatMinds(UIElement levelGreatMinds) {
        this.levelGreatMinds = levelGreatMinds;
    }

    public UIElement getLevelGreatMindsText() {
        return levelGreatMindsText;
    }

    public void setLevelGreatMindsText(UIElement levelGreatMindsText) {
        this.levelGreatMindsText = levelGreatMindsText;
    }

    public UIElement getLevelWorldLeaders() {
        return levelWorldLeaders;
    }

    public void setLevelWorldLeaders(UIElement levelWorldLeaders) {
        this.levelWorldLeaders = levelWorldLeaders;
    }

    public UIElement getLevelWorldLeadersText() {
        return levelWorldLeadersText;
    }

    public void setLevelWorldLeadersText(UIElement levelWorldLeadersText) {
        this.levelWorldLeadersText = levelWorldLeadersText;
    }

    public MenuButton getBack() {
        return back;
    }

    public void setBack(MenuButton back) {
        this.back = back;
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

    public float getBgCentreX() {
        return bgCentreX;
    }

    public void setBgCentreX(float bgCentreX) {
        this.bgCentreX = bgCentreX;
    }

    public float getBgCentreY() {
        return bgCentreY;
    }

    public void setBgCentreY(float bgCentreY) {
        this.bgCentreY = bgCentreY;
    }

    public float getLevelImageWidth() {
        return levelImageWidth;
    }

    public void setLevelImageWidth(float levelImageWidth) {
        this.levelImageWidth = levelImageWidth;
    }

    public float getLevelImageHeight() {
        return levelImageHeight;
    }

    public void setLevelImageHeight(float levelImageHeight) {
        this.levelImageHeight = levelImageHeight;
    }

    public float getLevelTextWidth() {
        return levelTextWidth;
    }

    public void setLevelTextWidth(float levelTextWidth) {
        this.levelTextWidth = levelTextWidth;
    }

    public float getLevelTextHeight() {
        return levelTextHeight;
    }

    public void setLevelTextHeight(float levelTextHeight) {
        this.levelTextHeight = levelTextHeight;
    }

    public float getLevelGreatMindsImageCentreX() {
        return levelGreatMindsImageCentreX;
    }

    public void setLevelGreatMindsImageCentreX(float levelGreatMindsImageCentreX) {
        this.levelGreatMindsImageCentreX = levelGreatMindsImageCentreX;
    }

    public float getLevelGreatMindsImageCentreY() {
        return levelGreatMindsImageCentreY;
    }

    public void setLevelGreatMindsImageCentreY(float levelGreatMindsImageCentreY) {
        this.levelGreatMindsImageCentreY = levelGreatMindsImageCentreY;
    }

    public float getLevelGreatMindsTextCentreX() {
        return levelGreatMindsTextCentreX;
    }

    public void setLevelGreatMindsTextCentreX(float levelGreatMindsTextCentreX) {
        this.levelGreatMindsTextCentreX = levelGreatMindsTextCentreX;
    }

    public float getLevelGreatMindsTextCentreY() {
        return levelGreatMindsTextCentreY;
    }

    public void setLevelGreatMindsTextCentreY(float levelGreatMindsTextCentreY) {
        this.levelGreatMindsTextCentreY = levelGreatMindsTextCentreY;
    }

    public float getLevelWorldLeadersImageCentreX() {
        return levelWorldLeadersImageCentreX;
    }

    public void setLevelWorldLeadersImageCentreX(float levelWorldLeadersImageCentreX) {
        this.levelWorldLeadersImageCentreX = levelWorldLeadersImageCentreX;
    }

    public float getLevelWorldLeadersImageCentreY() {
        return levelWorldLeadersImageCentreY;
    }

    public void setLevelWorldLeadersImageCentreY(float levelWorldLeadersImageCentreY) {
        this.levelWorldLeadersImageCentreY = levelWorldLeadersImageCentreY;
    }

    public float getLevelWorldLeadersTextCentreX() {
        return levelWorldLeadersTextCentreX;
    }

    public void setLevelWorldLeadersTextCentreX(float levelWorldLeadersTextCentreX) {
        this.levelWorldLeadersTextCentreX = levelWorldLeadersTextCentreX;
    }

    public float getLevelWorldLeadersTextCentreY() {
        return levelWorldLeadersTextCentreY;
    }

    public void setLevelWorldLeadersTextCentreY(float levelWorldLeadersTextCentreY) {
        this.levelWorldLeadersTextCentreY = levelWorldLeadersTextCentreY;
    }

    public float getBackWidth() {
        return backWidth;
    }

    public void setBackWidth(float backWidth) {
        this.backWidth = backWidth;
    }

    public float getBackHeight() {
        return backHeight;
    }

    public void setBackHeight(float backHeight) {
        this.backHeight = backHeight;
    }

    public float getBackCentreX() {
        return backCentreX;
    }

    public void setBackCentreX(float backCentreX) {
        this.backCentreX = backCentreX;
    }

    public float getBackCentreY() {
        return backCentreY;
    }

    public void setBackCentreY(float backCentreY) {
        this.backCentreY = backCentreY;
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
