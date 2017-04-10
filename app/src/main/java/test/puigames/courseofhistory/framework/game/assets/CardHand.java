package test.puigames.courseofhistory.framework.game.assets;

import android.util.Log;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Scaler;
import test.puigames.courseofhistory.framework.game.CardArea;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by mjtod on 13/03/2017.
 */

public class CardHand extends CardArea{

    public CardHand(float spawnX, float spawnY){
        super(340, 65);
        spawnObject(spawnX, spawnY);

        maxCardsInArea = 7;
//        for(int i = 1; i <= maxCardsInArea; i++)
//            handPositions[i - 1] = new Origin((width/maxCardsInArea) * i, this.origin.y + this.halfHeight/maxCardsInArea);
        setUpPositions();


    }

    public void adjustCardSize(CharacterCard characterCard){
        characterCard.setHeight(characterCard.height*0.75f);
        characterCard.setWidth(characterCard.width*0.75f);

    }

    public void addToHand(CharacterCard characterCard) {

        if(cardsInArea.size()< maxCardsInArea) {
            adjustCardSize(characterCard);

                characterCard.spawnObject(positions[cardsInArea.size()].x, positions[cardsInArea.size()].y);
                //characterCard.spawnObject(40, 40);
                Log.d("firsthand position", "" +positions[0]);
            super.addCardToArea(characterCard);
            positionCardsInArea();
        }

    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        Log.d("update called", "" + cardsInArea.size());
        positionCardsInArea();
    }

    public void positionCardsInArea()
    {
        for (int i = 0; i < cardsInArea.size(); i++)
        {
//            if(!cardsInArea.get(i).origin.equals(positions[i]))
            cardsInArea.get(i).setOrigin(new Origin(positions[i]));
        }
        //Log.d("Called", "positioned cards");
    }
    public ArrayList<CharacterCard> getCardsInHand(){
        return cardsInArea;
    }

}
