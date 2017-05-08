package test.puigames.courseofhistory.framework.game.assets.players.controllers.ui;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.screen.Placer;
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
    private static final float STARTING_HAND_SELECTOR_HEIGHT = 270.f;

    //Created for the human controller
    private final float ADJUSTED_CARD_WIDTH = 72.f;
    private final float ADJUSTED_CARD_HEIGHT = 99.6f;
    private final float CARD_ROTATION = 0.f;

    private final float FIRST_CARD_OFFSET_X = -80.f;
    private final float FIRST_CARD_OFFSET_Y = -30.f;
    private final float CARD_PADDING_X = 20;

    public final float CARD_SELECTED_OVERLAY_OFFSET_X = 20;
    public final float CARD_SELECTED_OVERLAY_OFFSET_Y = 20;

    private final float CONFIRMATION_BUTTON_OFFSET_X = 0.f;
    private final float CONFIRMATION_BUTTON_OFFSET_Y = 55.f;
    private final float CONFIRMATION_BUTTON_WIDTH = 50.f;
    private final float CONFIRMATION_BUTTON_HEIGHT = 40.f;
    private static final float CONFIRMATION_BUTTON_ROTATION = 0.f;

  //  private TextUIElement title;
    private static final int TITLE_TEXT_SIZE = 12;

    public MenuButton confirmationButton;
    private Placer placer;

    private UIElement[] cardToTossOverlays;

    public Player player;

    public StartingHandSelectionUI(Screen screen, Player player, Bitmap uIBackground, Bitmap confirmationButtonBitmap,  Bitmap selectedCardToTossOverlay) {
        super(screen, uIBackground, STARTING_HAND_SELECTOR_WIDTH, STARTING_HAND_SELECTOR_HEIGHT);
        this.player = player;

        //this.title = new TextUIElement(screen, "Player " + (player.getPlayerNumber() + 1) + " select your cards to toss", TITLE_TEXT_SIZE);

        //Setting up the array of overlays to be placed
        this.cardToTossOverlays = new UIElement[player.getStartingHandSelector().STARTING_HAND_SIZE];
        for (int i = 0; i < cardToTossOverlays.length; i++)
            cardToTossOverlays[i] = new UIElement(currentScreen, selectedCardToTossOverlay, 30, 30);

        this.confirmationButton = new PlayerButton(this.currentScreen, this.player, confirmationButtonBitmap, CONFIRMATION_BUTTON_WIDTH, CONFIRMATION_BUTTON_HEIGHT) {
            @Override
            public void applyAction() {
                player.finishedCreatingStartHand();
            }
        };
    }

    public StartingHandSelectionUI(Screen screen, Placer placer, Player player, Bitmap uIBackground, Bitmap confirmationButtonBitmap) {
        super(screen, uIBackground, STARTING_HAND_SELECTOR_WIDTH, STARTING_HAND_SELECTOR_HEIGHT);
        this.player = player;
        this.placer = placer;

        this.confirmationButton = new PlayerButton(this.currentScreen, this.player, confirmationButtonBitmap, CONFIRMATION_BUTTON_WIDTH, CONFIRMATION_BUTTON_HEIGHT) {

            @Override
            public void applyAction() {
                player.finishedCreatingStartHand();
            }
        };
    }


    private void resizeAllCardsInSelector(StartingHandSelector startingHandSelector) {
        for(CharacterCard card : startingHandSelector.getCardsToKeep())
            adjustCardSize(card);

        for(CharacterCard card : startingHandSelector.getCardsToToss())
            adjustCardSize(card);
    }

    private void adjustCardSize(CharacterCard card){
        card.setWidth(ADJUSTED_CARD_WIDTH);
        card.setHeight(ADJUSTED_CARD_HEIGHT);

    }

    @Override
    public void remove(Screen screen) {
        super.remove(screen);

        for (CharacterCard card : player.getStartingHandSelector().getCardsToKeep())
            card.remove(screen);

        for (CharacterCard card : player.getStartingHandSelector().getCardsToToss())
            card.remove(screen);

        confirmationButton.remove(screen);
        //title.remove(screen);

        for (UIElement overlay : cardToTossOverlays)
            overlay.remove(screen);
    }

    public float getAbsoluteRotation(float placeableRotation){
        return this.rotation + placeableRotation;
    }

    @Override
    public void place(Screen screen, float placementX, float placementY, float rotation) {
        super.place(screen, placementX, placementY, rotation);
        this.placer = new Placer(screen, placementX, placementY);

        placer.placePlaceableRelativeToAnchorPoint(confirmationButton, CONFIRMATION_BUTTON_OFFSET_X, CONFIRMATION_BUTTON_OFFSET_Y, this.rotation, getAbsoluteRotation(CONFIRMATION_BUTTON_ROTATION));

        float currentCardPaddingX = 0;
        for (CharacterCard card : player.getStartingHandSelector().getCardsToKeep()) {
            placer.placePlaceableRelativeToAnchorPoint(card, FIRST_CARD_OFFSET_X + currentCardPaddingX, FIRST_CARD_OFFSET_Y, this.rotation, getAbsoluteRotation(CARD_ROTATION));
            currentCardPaddingX += card.getWidth() + CARD_PADDING_X;
        }

        resizeAllCardsInSelector(player.getStartingHandSelector());
        //TODO get actual values and rotate properly
    //    placer.placePlaceableRelativeToAnchorPoint(title, -70.f, -20.f, this.rotation, this.rotation);
    }

    private float findXPositionInRelationToContainer(float offsetX) {
        return getPosX() + offsetX;
    }

    private float findYPositionInRelationToContainer(float offsetY) {
        return getPosY() + offsetY;
    }

    public UIElement[] getCardToTossOverlays() {
        return cardToTossOverlays;
    }
}
