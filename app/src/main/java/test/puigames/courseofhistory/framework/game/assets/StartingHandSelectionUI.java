package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 11/04/2017.
 */

public class StartingHandSelectionUI extends UIElement {
    //Created for the human controller
    private final float ADJUSTED_CARD_WIDTH = 72.f;
    private final float ADJUSTED_CARD_HEIGHT = 99.6f;
    private final float CARD_SELECTED_OVERLAY_OFFSET_X = 20;
    private final float CARD_SELECTED_OVERLAY_OFFSET_Y = 20;

    private final float CONFIRMATION_BUTTON_POS_X = 300;
    private final float CONFIRMATION_BUTTON_POS_Y = 200;

    private final float CONFIRMATION_BUTTON_WIDTH = 10;
    private final float CONFIRMATION_BUTTON_HEIGHT = 20;

    MenuButton confirmationButton;

    StartingHandSelector startingHandSelector;
    //TODO add highlight bitmap



    public StartingHandSelectionUI(StartingHandSelector startingHandSelector, Bitmap uIBackground, Bitmap confirmationButtonBitmap) {
        super(uIBackground, 300, 100);

        this.startingHandSelector = startingHandSelector;

        this.confirmationButton = new MenuButton(confirmationButtonBitmap, CONFIRMATION_BUTTON_WIDTH, CONFIRMATION_BUTTON_HEIGHT) {
            @Override
            public void placeUIElement(float spawnX, float spawnY) {
                super.placeUIElement(spawnX, spawnY);
            }
        };

    //    placeUIElement(spawnX, spawnY);
      //  confirmationButton.placeUIElement(CONFIRMATION_BUTTON_POS_X, CONFIRMATION_BUTTON_POS_Y);
    }

    public void adjustCardSize(CharacterCard card){
        card.setWidth(ADJUSTED_CARD_WIDTH);
        card.setHeight(ADJUSTED_CARD_HEIGHT);
    }

    public void selectCardToToss(int cardToBeRemoved) {

    }

    public void deselectCardToToss(int cardToBeKept) {

    }

}
