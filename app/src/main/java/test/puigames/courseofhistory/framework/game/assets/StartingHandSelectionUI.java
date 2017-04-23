package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 11/04/2017.
 */

public class StartingHandSelectionUI extends UIElement {
    //Created for the human controller
    public final float UI_POS_X = 240;
    public final float UI_POS_Y = 160;

    private final float ADJUSTED_CARD_WIDTH = 72.f;
    private final float ADJUSTED_CARD_HEIGHT = 99.6f;
    private final float CARD_SELECTED_OVERLAY_OFFSET_X = 20;
    private final float CARD_SELECTED_OVERLAY_OFFSET_Y = 20;

    private final float CONFIRMATION_BUTTON_POS_X = 300;
    private final float CONFIRMATION_BUTTON_POS_Y = 200;

    private final float CONFIRMATION_BUTTON_WIDTH = 10;
    private final float CONFIRMATION_BUTTON_HEIGHT = 20;

    public MenuButton confirmationButton;

    public StartingHandSelector startingHandSelector;
    //TODO add highlight bitmap



    public StartingHandSelectionUI(Screen screen, StartingHandSelector startingHandSelector, Bitmap uIBackground, Bitmap confirmationButtonBitmap) {
        super(screen, uIBackground, 300, 100);
        this.startingHandSelector = startingHandSelector;

        this.confirmationButton = new MenuButton(this.currentScreen, confirmationButtonBitmap, CONFIRMATION_BUTTON_WIDTH, CONFIRMATION_BUTTON_HEIGHT);
    }

    public StartingHandSelectionUI(Screen screen, Bitmap uIBackground, Bitmap confirmationButtonBitmap) {
        super(screen, uIBackground, 300, 100);

        this.confirmationButton = new MenuButton(this.currentScreen, confirmationButtonBitmap, CONFIRMATION_BUTTON_WIDTH, CONFIRMATION_BUTTON_HEIGHT);
    }

    public void adjustCardSize(CharacterCard card){
        card.setWidth(ADJUSTED_CARD_WIDTH);
        card.setHeight(ADJUSTED_CARD_HEIGHT);
    }

    public void selectCardToToss(int cardToBeRemoved) {

    }

    public void deselectCardToToss(int cardToBeKept) {

    }

    @Override
    public void initPlacement(float spawnX, float spawnY) {
        super.initPlacement(spawnX, spawnY);
        this.confirmationButton.initPlacement(CONFIRMATION_BUTTON_POS_X, CONFIRMATION_BUTTON_POS_Y);
    }

}
