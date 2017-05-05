package test.puigames.courseofhistory.framework.game.assets;

import java.util.ArrayList;
import java.util.Arrays;

import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by mjtod on 13/03/2017.
 */


public class CardHand extends CardArea {
    private static final float CARD_SCALE_FACTOR = 0.75f;
    float positionPadding = 76.09525f;

    public CardHand(Screen screen, CharacterCard[] startCards){
        super(screen, 340, 65);

       cardsInArea.addAll(Arrays.asList(startCards));
        maxCardsInArea = 7;
    }

    public CardHand(Screen screen){
        super(screen, 340, 65);
        maxCardsInArea = 7;
    }

    @Override
    public void addCardToArea(CharacterCard characterCard) {
        if(cardsInArea.size() < maxCardsInArea) {
            characterCard.adjustCardSize(CARD_SCALE_FACTOR);
            super.addCardToArea(characterCard);
            characterCard.place(this.currentScrren, positions[cardsInArea.size() - 1].x, positions[cardsInArea.size() - 1].y, this.rotation);
            positionCardsInArea();
        }

    }

    @Override
    public void setUpPositions() {
        super.setUpPositions();
        for(int i = 0; i < positions.length; i++) {
            positions[i].setOriginX(positions[i].getOriginX() + positionPadding);
        }
    }

    public ArrayList<CharacterCard> getCardsInHand(){
        return cardsInArea;
    }

    @Override
    public void place(Screen screen, float placementX, float placementY, float rotation) {
        super.place(screen, placementX, placementY, rotation);
        setUpPositions();
    }

    public static float getCardScaleFactor() {
        return CARD_SCALE_FACTOR;
    }

    public float getPositionPadding() {
        return positionPadding;
    }

    public void setPositionPadding(float positionPadding) {
        this.positionPadding = positionPadding;
    }

    public boolean isAi() {
        return isAi;
    }

    public void setAi(boolean ai) {
        isAi = ai;
    }
}
