package test.puigames.courseofhistory.framework.game.assets;

import android.util.Log;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by mjtod on 13/03/2017.
 */


public class CardHand extends CardArea {
    private static final float CARD_SCALE_FACTOR = 0.75f;
    private float positionPadding = 76.09525f;
    private boolean isAi = false;

    public CardHand(Screen screen, float spawnX, float spawnY, CharacterCard[] startCards){
        super(screen, 340, 65);
        initPlacement(spawnX, spawnY);

        for(CharacterCard card : startCards)
            //Adds cards given at start to the player's hand
            cardsInArea.add(card);

        if(spawnY < 160){
            isAi = true;
        }

        maxCardsInArea = 7;
        setUpPositions();
    }

    public CardHand(Screen screen, float spawnX, float spawnY){
        super(screen, 340, 65);
        initPlacement(spawnX, spawnY);

        if(spawnY < 160)
            isAi = true;

        maxCardsInArea = 7;
        setUpPositions();
    }

    public void addToHand(CharacterCard characterCard) {
        if(cardsInArea.size()< maxCardsInArea) {
            characterCard.adjustCardSize(CARD_SCALE_FACTOR);
            characterCard.place(this.currentScreen, positions[cardsInArea.size()].getOriginX(), positions[cardsInArea.size()].getOriginY());
            //characterCard.initPlacement(positions[cardsInArea.size()].x, positions[cardsInArea.size()].y);
            //characterCard.initPlacement(40, 40);
//            Log.d("firsthand position", "" + positions[0]);

            characterCard.place(this.currentScreen, positions[cardsInArea.size()].getOriginX(), positions[cardsInArea.size()].getOriginY());
            if(isAi) {
                characterCard.rotateCard(180);
            }

//            Log.d("Positions", ""  +cardsInArea.size() +positions[cardsInArea.size()].toString() +isAi);
            super.addCardToArea(characterCard);
//            positionCardsInArea();
//            Log.d("card", cardsInArea.get(0).toString());
        }

    }

    public void setUpPositions() {
        super.setUpPositions();
        for(int i = 0; i < positions.length; i++) {
            positions[i].setOriginX(positions[i].getOriginX() + positionPadding);
        }
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        //Log.d("update called", "" + cardsInArea.size());
    }

    //    public void positionCardsInArea()
//    {
//        for (int i = 0; i < cardsInArea.size(); i++)
//        {
////            if(!cardsInArea.get(i).origin.equals(positions[i]))
//            cardsInArea.get(i).setOrigin(new Origin(positions[i]));
//        }
//        //Log.d("Called", "positioned cards");
//    }
    public ArrayList<CharacterCard> getCardsInHand(){
        return cardsInArea;
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
