package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.LinkedList;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Placeable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Updateable;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Scalable;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;
import test.puigames.courseofhistory.framework.game.assets.players.Player;

/**
 * Created by Michael on 27/04/2017.
 */

public class HumanCardGameUIContainer implements Updateable, Placeable, Drawable, Scalable {
    public final static float PAUSE_BUTTON_OFFSET_X = 100.f;
    public final static float PAUSE_BUTTON_OFFSET_Y = 100.f;
    public final static float PAUSE_BUTTON_WIDTH = 50.f;
    public final static float PAUSE_BUTTON_HEIGHT = 20.f;

    public final static float END_TURN_BUTTON_OFFSET_X = 100.f;
    public final static float END_TURN_BUTTON_OFFSET_Y = 100.f;

    public final static float END_TURN_BUTTON_WIDTH = 50.f;
    public final static float END_TURN_BUTTON_HEIGHT = 20.f;

    public final static float STARTING_HAND_SELECTOR_OFFSET_X = 0.f;
    public final static float STARTING_HAND_SELECTOR_OFFSET_Y = 0.f;

    Screen currentScreen;
    Matrix matrix;
    Origin origin;
    float rotation;
    float width;
    float height;
    private LinkedList<MenuButton> menuButtonsShown;
    private LinkedList<UIElement> uiElementsShown;
    private Player player;

    StartingHandSelectionUI startingHandSelectorUI;
    MenuButton pauseButton;
    MenuButton endTurnButton;

    //Will have a pause button
    //Starting hand selector ui
    //End turn button


    public HumanCardGameUIContainer(final Screen currentScreen, final Player player, Bitmap startingHandSelectorBackgroundBitmap, Bitmap confirmationButtonBitmap, Bitmap endTurnButtonBitmap) {
        this.currentScreen = currentScreen;
        this.player = player;

        this.menuButtonsShown = new LinkedList<>();
        this.uiElementsShown = new LinkedList<>();

        this.matrix = new Matrix();

        //ADD STARTING HAND SELECTOR UI
        startingHandSelectorUI = new StartingHandSelectionUI(currentScreen, player, startingHandSelectorBackgroundBitmap, confirmationButtonBitmap);

        //Add PAUSE GAME BUTTON
        this.pauseButton = new MenuButton(currentScreen, confirmationButtonBitmap, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT) {
            @Override
            public void applyAction() {
                currentScreen.pause();
                //TODO INSTEAD PAUSE GAMEMACHINE AND SHOW A PAUSE MENU
                //TODO PAUSE MENU WILL BE ADDED TO THIS UI CONTAINER
            }
        };

        //ADD END TURN BUTTON
        this.endTurnButton = new MenuButton(currentScreen, endTurnButtonBitmap, END_TURN_BUTTON_WIDTH, END_TURN_BUTTON_HEIGHT) {
            @Override
            public void applyAction() {
                player.playerCurrentState = Player.PlayerState.TURN_ENDED;
            }
        };
    }

    private void initPlacement(float spawnX, float spawnY, float rotation) {
        this.origin = new Origin(spawnX, spawnY);
        this.matrix = new Matrix();
        this.rotation = rotation;
    }

    @Override
    public void place(Screen screen, float placementX, float placementY, float rotation) {
        initPlacement(placementX, placementY, rotation);
        startTicking(screen);
        if (!screen.scalables.contains(this))
            screen.scalables.add(this);
    }

    @Override
    public void remove(Screen screen) {
        if (screen.scalables.contains(this))
            screen.scalables.remove(this);

        for (UIElement uiElement : uiElementsShown)
            uiElement.remove(screen);
    }

    @Override
    public Matrix getMatrix() {
        return this.matrix;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public float getPosX() { return this.origin.x; }

    @Override
    public float getPosY() { return this.origin.y; }

    @Override
    public float getRotation() { return this.rotation; }

    private void showUIElement(UIElement uiElement, float positionX, float positionY, float rotation) {
        uiElement.place(currentScreen, positionX, positionY, rotation);
        uiElementsShown.add(uiElement);
    }

    private void showMenuButton(MenuButton menuButton, float positionX, float positionY, float rotation) {
        showUIElement(menuButton, positionX, positionY, rotation);
        menuButtonsShown.add(menuButton);
    }

    private void hideMenuButton(MenuButton menuButton) {
        menuButtonsShown.remove(menuButton);
        hideUIElement(menuButton);
    }

    private void hideUIElement(UIElement uiElement) {
        uiElementsShown.remove(uiElement);
    }

    @Override
    public void startTicking(Screen screen) {
        if (!screen.isInUpdateables(this))
            screen.addToUpdateables(this);
    }

    @Override
    public void stopTicking(Screen screen) {
        if (screen.isInUpdateables(this))
            screen.removeFromUpdateables(this);
    }

    @Override
    public void update(float deltaTime) {
        switch (this.player.playerCurrentState) {
            case TURN_ACTIVE:
               showMenuButton(endTurnButton,
                       findXPositionInRelationToContainer(END_TURN_BUTTON_OFFSET_X),
                       findYPositionInRelationToContainer(END_TURN_BUTTON_OFFSET_Y),
                       rotation);
                break;

            case TURN_ENDED:
                hideMenuButton(endTurnButton);
                break;


            case BEGIN_CREATING_STARTING_HAND:
                showUIElement(startingHandSelectorUI,
                        findXPositionInRelationToContainer(STARTING_HAND_SELECTOR_OFFSET_X),
                        findYPositionInRelationToContainer(STARTING_HAND_SELECTOR_OFFSET_Y),
                        rotation);
                break;

            case FINISHED_CREATING_START_HAND:
                hideUIElement(startingHandSelectorUI);
                break;
        }

    }

    private float findXPositionInRelationToContainer(float offsetX) {
        return getPosX() + offsetX;
    }

    private float findYPositionInRelationToContainer(float offsetY) {
        return getPosY() + offsetY;
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        for (UIElement uiElement : uiElementsShown)
            uiElement.draw(canvas, deltaTime);
    }

    @Override
    public void scale(float scaleFactorX, float scaleFactorY) {
        this.getMatrix().reset();

        this.getMatrix().postScale((this.getWidth() / this.getWidth()) * scaleFactorX,
                (this.getHeight() / this.getHeight()) * scaleFactorY);
        this.getMatrix().postRotate(this.getRotation(), scaleFactorX * this.getWidth()/ 2.0f,
                scaleFactorY * this.getHeight() / 2.0f);
        this.getMatrix().postTranslate((this.getPosX() - this.getWidth() / 2) * scaleFactorX,
                (this.getPosY() - this.getHeight() / 2) * scaleFactorY);

        for (UIElement uiElement : uiElementsShown)
            uiElement.scale(scaleFactorX, scaleFactorY);
    }

    public UIElement[] getShownUIElements() {
        return uiElementsShown.toArray(new UIElement[uiElementsShown.size()]);
    }

    public MenuButton[] getShownMenuButtons() {
        return menuButtonsShown.toArray(new MenuButton[menuButtonsShown.size()]);
    }
}
