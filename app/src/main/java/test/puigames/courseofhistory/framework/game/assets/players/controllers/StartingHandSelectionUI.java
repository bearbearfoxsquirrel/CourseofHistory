package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.engine.ui.UIElement;
import test.puigames.courseofhistory.framework.game.assets.StartingHandSelector;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;

/**
 * Created by Michael on 11/04/2017.
 */

public class StartingHandSelectionUI extends UIElement {
    private static final float STARTING_HAND_SELECTOR_WIDTH = 400.f;
    private static final float STARTING_HAND_SELECTOR_HEIGHT = 200.f;

    //Created for the human controller
    private final float ADJUSTED_CARD_WIDTH = 72.f;
    private final float ADJUSTED_CARD_HEIGHT = 99.6f;
    private final float CARD_SELECTED_OVERLAY_OFFSET_X = 20;
    private final float CARD_SELECTED_OVERLAY_OFFSET_Y = 20;

    private final float CONFIRMATION_BUTTON_OFFSET_X = 50;
    private final float CONFIRMATION_BUTTON_OFFSET_Y = 30;

    private final float CONFIRMATION_BUTTON_WIDTH = 75;
    private final float CONFIRMATION_BUTTON_HEIGHT = 50;

    public MenuButton confirmationButton;
    private Bitmap confirmationButtonBitmap;

    public Player player;
    //TODO add highlight bitmap

    public StartingHandSelectionUI(Screen screen, final Player player, Bitmap uIBackground, Bitmap confirmationButtonBitmap) {
        super(screen, uIBackground, STARTING_HAND_SELECTOR_WIDTH, STARTING_HAND_SELECTOR_HEIGHT);
        this.player = player;
        this.confirmationButtonBitmap = confirmationButtonBitmap;

        this.confirmationButton = new MenuButton(this.currentScreen, confirmationButtonBitmap, CONFIRMATION_BUTTON_WIDTH, CONFIRMATION_BUTTON_HEIGHT) {
            @Override
            public void applyAction() {
                player.playerCurrentState = Player.PlayerState.FINISHED_CREATING_START_HAND;
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
    public void place(Screen screen, float placementX, float placementY, float rotation) {
        super.place(screen, placementX, placementY, rotation);
        confirmationButton.place(screen, findXPositionInRelationToContainer(CONFIRMATION_BUTTON_OFFSET_X), findYPositionInRelationToContainer(CONFIRMATION_BUTTON_OFFSET_Y), rotation);

        for (CharacterCard card : player.startingHandSelector.cardsToKeep)
            card.place(screen, 20, 40, rotation);

        resizeAllCardsInSelector(player.startingHandSelector);

        screen.drawables.addAll(player.startingHandSelector.cardsToKeep);
        screen.drawables.addAll(player.startingHandSelector.cardsToToss);
    }


    private float findXPositionInRelationToContainer(float offsetX) {
        return getPosX() + offsetX;
    }

    private float findYPositionInRelationToContainer(float offsetY) {
        return getPosY() + offsetY;
    }
}
