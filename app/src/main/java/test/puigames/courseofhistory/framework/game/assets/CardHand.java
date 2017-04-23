package test.puigames.courseofhistory.framework.game.assets;

import android.util.Log;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by mjtod on 13/03/2017.
 */

public class CardHand extends CardArea {
    public CardHand(Screen screen, float spawnX, float spawnY, CharacterCard[] startCards){
        super(screen, 340, 65);
        initPlacement(spawnX, spawnY);

        for(CharacterCard card : startCards)
            //Adds cards given at start to the player's hand
            cardsInArea.add(card);

        maxCardsInArea = 7;
        setUpPositions();
    }

    public CardHand(Screen screen, float spawnX, float spawnY){
        super(screen, 340, 65);
        initPlacement(spawnX, spawnY);
        maxCardsInArea = 7;
        setUpPositions();
    }

    public void adjustCardSize(CharacterCard characterCard){
        characterCard.setHeight(characterCard.height*0.75f);
        characterCard.setWidth(characterCard.width*0.75f);

    }

    public void addToHand(CharacterCard characterCard) {

        if(cardsInArea.size()< maxCardsInArea) {
            adjustCardSize(characterCard);
                characterCard.place(this.currentScrren, positions[cardsInArea.size()].x, positions[cardsInArea.size()].y);
                //characterCard.initPlacement(positions[cardsInArea.size()].x, positions[cardsInArea.size()].y);
                //characterCard.initPlacement(40, 40);
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
