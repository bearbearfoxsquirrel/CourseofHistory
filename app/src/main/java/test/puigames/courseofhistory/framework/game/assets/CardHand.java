package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;

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

    public CardHand(Screen screen, Bitmap bitmap, CharacterCard[] startCards){
        super(screen, bitmap, 340, 65);

       cardsInArea.addAll(Arrays.asList(startCards));
        maxCardsInArea = 7;
        setUpPositions();
    }

    public CardHand(Screen screen, Bitmap bitmap, int width, int height){
        super(screen, bitmap, width, height);
        maxCardsInArea = 7;
    }

    @Override
    public void addCardToArea(CharacterCard characterCard) {
        if(cardsInArea.size() < maxCardsInArea) {
            characterCard.adjustCardSize(CARD_SCALE_FACTOR);
            super.addCardToArea(characterCard);
            characterCard.place(this.currentScreen, positions[cardsInArea.size() - 1].getOriginX(), positions[cardsInArea.size() - 1].getOriginY(), this.rotation);
            positionCardsInArea();
        }

    }

    @Override
    public void removeCardFromArea(CharacterCard characterCard) {
        super.removeCardFromArea(characterCard);
        positionCardsInArea();
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
}
