package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;

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
    private Bitmap confirmationButtonBitmap;

    public Player player;
    //TODO add highlight bitmap

    public StartingHandSelectionUI(Screen screen, final Player player, Bitmap uIBackground, Bitmap confirmationButtonBitmap) {
        super(screen, uIBackground, 300, 100);
        this.player = player;
        this.confirmationButtonBitmap = confirmationButtonBitmap;

        this.confirmationButton = new MenuButton(this.currentScreen, confirmationButtonBitmap, CONFIRMATION_BUTTON_WIDTH, CONFIRMATION_BUTTON_HEIGHT) {
            @Override
            public void applyAction() {
                player.playerCurrentState = Player.PlayerState.BEGIN_CREATING_STARTING_HAND;
            }
        };
    }

    private void resizeAllCardsInSelector(StartingHandSelector startingHandSelector) {
        for(CharacterCard card : startingHandSelector.cardsToKeep)
            adjustCardSize(card);

        for(CharacterCard card : startingHandSelector.cardsToToss)
            adjustCardSize(card);
    }

    private void adjustCardSize(CharacterCard card){
        card.setWidth(ADJUSTED_CARD_WIDTH);
        card.setHeight(ADJUSTED_CARD_HEIGHT);
    }

    public void selectCardToToss(CharacterCard cardToBeRemoved) {
        player.startingHandSelector.cardsToToss.add(cardToBeRemoved);
        player.startingHandSelector.cardsToKeep.remove(cardToBeRemoved);
    }

    public void deselectCardToToss(CharacterCard cardToBeKept) {
        player.startingHandSelector.cardsToKeep.add(cardToBeKept);
        player.startingHandSelector.cardsToToss.remove(cardToBeKept);
    }

    @Override
    public void initPlacement(float spawnX, float spawnY) {
        super.initPlacement(spawnX, spawnY);
        this.confirmationButton.initPlacement(CONFIRMATION_BUTTON_POS_X, CONFIRMATION_BUTTON_POS_Y);
    }

    public void place(Screen screen, float placementX, float placementY) {
        super.place(screen, placementX, placementY);
        confirmationButton.place(screen, placementX, placementY);

        for (CharacterCard card : player.startingHandSelector.cardsToKeep)
            card.place(screen, 20, 40);

        resizeAllCardsInSelector(player.startingHandSelector);

        screen.drawables.addAll(player.startingHandSelector.cardsToKeep);
        screen.drawables.addAll(player.startingHandSelector.cardsToToss);
    }

}
