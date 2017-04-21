package test.puigames.courseofhistory.framework.game.assets;

import android.util.Log;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by mjtod on 13/03/2017.
 */


public class CardHand extends CardArea{
    boolean isAi = false;
    float positionPadding = 76.09525f;
    public CardHand(float spawnX, float spawnY){
        super(340, 65);
        maxCardsInArea = 7;
        spawnObject(spawnX, spawnY);
        if(spawnY < 160){
            isAi = true;
        }




//        for(int i = 1; i <= maxCardsInArea; i++)
//            handPositions[i - 1] = new Origin((width/maxCardsInArea) * i, this.origin.y + this.halfHeight/maxCardsInArea);
        setUpPositions();
        for(int i = 0; i < positions.length; i++) {
            positions[i].x = positions[i].x + positionPadding;
        }


    }

    public void addToHand(CharacterCard characterCard) {

        if(cardsInArea.size()< maxCardsInArea) {
            characterCard.adjustCardSize(0.75f);


            characterCard.spawnObject(positions[cardsInArea.size()].x, positions[cardsInArea.size()].y);
            if(isAi) {
                characterCard.rotateCard(180);
            }

//            Log.d("Positions", ""  +cardsInArea.size() +positions[cardsInArea.size()].toString() +isAi);


            super.addCardToArea(characterCard);
            positionCardsInArea();

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

}
