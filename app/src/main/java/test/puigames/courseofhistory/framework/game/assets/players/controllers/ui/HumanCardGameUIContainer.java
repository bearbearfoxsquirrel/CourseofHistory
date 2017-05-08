package test.puigames.courseofhistory.framework.game.assets.players.controllers.ui;

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
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.engine.screen.Placer;

/**
 * Created by Michael on 27/04/2017.
 */

public class HumanCardGameUIContainer implements Updateable, Placeable, Drawable, Scalable {
    public final static float PAUSE_BUTTON_OFFSET_X = 100.f;
    public final static float PAUSE_BUTTON_OFFSET_Y = 100.f;
    public final static float PAUSE_BUTTON_WIDTH = 50.f;
    public final static float PAUSE_BUTTON_HEIGHT = 20.f;

    public final static float END_TURN_BUTTON_OFFSET_X = -200.f;
    public final static float END_TURN_BUTTON_OFFSET_Y = 60.f;

    public final static float END_TURN_BUTTON_WIDTH = 50.f;
    public final static float END_TURN_BUTTON_HEIGHT = 30.f;

    public final static float STARTING_HAND_SELECTOR_OFFSET_X = 0.f;
    public final static float STARTING_HAND_SELECTOR_OFFSET_Y = 0.f;
    public static final int ROTATION = 0;

    public static final float END_TURN_BUTTON_COOLDOWN_TIME = 5.f;

    private Screen currentScreen;
    private Matrix matrix;
    private Origin origin;
    private float rotation;
    private float width;
    private float height;
    private LinkedList<MenuButton> menuButtonsShown;
    private LinkedList<UIElement> uiElementsShown;
    private Player player;
    private Placer patrickPlacer;
    private StartingHandSelectionUI startingHandSelectorUI;

    private MenuButton pauseButton;


    private float endTurnButtonCoolDown = END_TURN_BUTTON_COOLDOWN_TIME;

    private MenuButton endTurnButton;

    //Will have a pause button
    //Starting hand selector ui
    //End turn button


    public HumanCardGameUIContainer(final Screen currentScreen, final Player player, Bitmap startingHandSelectorBackgroundBitmap, Bitmap confirmationButtonBitmap, Bitmap endTurnButtonBitmap, Bitmap selectedCardToTossOverlay) {
        this.currentScreen = currentScreen;
        this.player = player;
        this.rotation = player.getRotation();

        this.menuButtonsShown = new LinkedList<>();
        this.uiElementsShown = new LinkedList<>();
        this.matrix = new Matrix();
        //ADD STARTING HAND SELECTOR UI
        startingHandSelectorUI = new StartingHandSelectionUI(currentScreen,this.player, startingHandSelectorBackgroundBitmap, confirmationButtonBitmap,selectedCardToTossOverlay);

        //ADD PAUSE GAME BUTTON
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
                player.endTurn();
            }
        };
    }

    private void initPlacement(float spawnX, float spawnY, float rotation) {
        this.origin = new Origin(spawnX, spawnY);
        this.matrix = new Matrix();
        this.rotation = rotation;
        this.patrickPlacer = new Placer(this.currentScreen, getPosX(), getPosY());
    }

    @Override
    public void place(Screen screen, float placementX, float placementY, float rotation) {
        initPlacement(placementX, placementY, rotation);
        startTicking(screen);
        if (!screen.getScalables().contains(this))
            screen.getScalables().add(this);
    }

    @Override
    public void remove(Screen screen) {
        if (screen.getScalables().contains(this))
            screen.getScalables().remove(this);

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
    public float getPosX() { return this.origin.getOriginX(); }

    @Override
    public float getPosY() { return this.origin.getOriginY(); }

    @Override
    public float getRotation() { return this.rotation; }

    public float getAbsoluteRotationOfUIElement(float uiElementRotation) {
        return this.rotation + uiElementRotation;
    }

    public void showUIElement(UIElement uiElement, float offsetFromAnchorX, float offsetFromAnchorY, float uiElementRotation) {
        if (!uiElementsShown.contains(uiElement)) {
            //Places the uielement in the offset from the middle of the screen and places it relative to the playerS
            patrickPlacer.placePlaceableRelativeToAnchorPoint(uiElement,offsetFromAnchorX, offsetFromAnchorY, this.rotation, getAbsoluteRotationOfUIElement(uiElementRotation));
            //uiElement.place(currentScreen, offsetFromAnchorX, offsetFromAnchorY, uiElementRotation);
            uiElementsShown.add(uiElement);
        }
    }

    public void showMenuButton(MenuButton menuButton, float offsetFromAnchorX, float offsetFromAnchorY, float rotation) {
        if (!menuButtonsShown.contains(menuButton)) {
            showUIElement(menuButton, offsetFromAnchorX, offsetFromAnchorY, rotation);
            menuButtonsShown.add(menuButton);
        }
    }

    private void hideMenuButton(MenuButton menuButton) {
        if (menuButtonsShown.contains(menuButton)) {
            menuButtonsShown.remove(menuButton);
            hideUIElement(menuButton);
        }
    }

    private void hideUIElement(UIElement uiElement) {
        if (uiElementsShown.contains(uiElement)) {
            uiElementsShown.remove(uiElement);
            uiElement.remove(currentScreen);
        }
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

    //TO CONTROL WHEN UI ELEMENTS ARE SHOWN USE THIS UPDATE METHOD
    @Override
    public void update(float deltaTime) {
        switch (this.player.getPlayerCurrentState()) {
            case TURN_ACTIVE:
                //Works out how long the end turn button should appear after it is set to active and then hides it again after cooldown runs out
                if (getShownUIElements().contains(getEndTurnButton()))
                    endTurnButtonCoolDown -= deltaTime;
                if (endTurnButtonCoolDown <= 0)
                    hideMenuButton(endTurnButton);
                break;

            case TURN_ENDED:
                hideMenuButton(endTurnButton);
                break;

            case STARTING_HAND_CHOOSING_CARDS_TO_TOSS:
                if (!uiElementsShown.contains(startingHandSelectorUI))
                    showUIElement(startingHandSelectorUI, STARTING_HAND_SELECTOR_OFFSET_X, STARTING_HAND_SELECTOR_OFFSET_Y, ROTATION);

                for (UIElement cardToTossOverlay : this.startingHandSelectorUI.getCardToTossOverlays())
                    cardToTossOverlay.remove(currentScreen);

                int overlayIndex = 0;
                for (CharacterCard card : player.getStartingHandSelector().getCardsToToss()) {
                    startingHandSelectorUI.getCardToTossOverlays()[overlayIndex].place(currentScreen, card.getPosX() + startingHandSelectorUI.CARD_SELECTED_OVERLAY_OFFSET_X, card.getPosY() + startingHandSelectorUI.CARD_SELECTED_OVERLAY_OFFSET_Y, startingHandSelectorUI.getAbsoluteRotation(ROTATION));
                    overlayIndex++;
                }
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
        this.getMatrix().postScale((this.getWidth() / this.getWidth()) * scaleFactorX, (this.getHeight() / this.getHeight()) * scaleFactorY);
        this.getMatrix().postRotate(this.getRotation(), scaleFactorX * this.getWidth()/ 2.0f, scaleFactorY * this.getHeight() / 2.0f);
        this.getMatrix().postTranslate((this.getPosX() - this.getWidth() / 2) * scaleFactorX, (this.getPosY() - this.getHeight() / 2) * scaleFactorY);

        for (UIElement uiElement : uiElementsShown)
            uiElement.scale(scaleFactorX, scaleFactorY);
    }

    public LinkedList<UIElement> getShownUIElements() {
        return uiElementsShown;
    }

    public UIElement[] getShownUIElementsAsArray() {
        return uiElementsShown.toArray(new UIElement[uiElementsShown.size()]);
    }

    public MenuButton[] getShownMenuButtonsAsArray() {
        return menuButtonsShown.toArray(new MenuButton[menuButtonsShown.size()]);
    }

    public StartingHandSelectionUI getStartingHandSelectorUI() {
        return startingHandSelectorUI;
    }

    public MenuButton getEndTurnButton() {
        return endTurnButton;
    }

    public void setEndTurnButtonCoolDown(float endTurnButtonCoolDown) {
        this.endTurnButtonCoolDown = endTurnButtonCoolDown;
    }

    public float getEndTurnButtonCoolDown() {
        return endTurnButtonCoolDown;
    }
}
