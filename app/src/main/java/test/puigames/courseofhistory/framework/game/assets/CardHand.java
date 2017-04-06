package test.puigames.courseofhistory.framework.game.assets;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by mjtod on 13/03/2017.
 */

public class CardHand extends CardArea{
    private ArrayList<CharacterCard> cardHand = new ArrayList<CharacterCard>();
    private Origin[] handPositions;


    public CardHand(){
        super(160, 40);


        maxCardsInArea = 8;
        handPositions = new Origin[maxCardsInArea];
        for(int i = 1; i <= maxCardsInArea; i++)
            handPositions[i - 1] = new Origin((width/maxCardsInArea) * i, this.origin.y + this.halfHeight/maxCardsInArea);
    }

    public void halfCardSize(CharacterCard characterCard){
        characterCard.setHeight(characterCard.height*0.75f);
        characterCard.setWidth(characterCard.width*0.75f);

    }

    public void addToHand(CharacterCard characterCard, ArrayList<Sprite> sprites) {
        halfCardSize(characterCard);
        super.addCardToArea(characterCard);
        positionCardsInArea();
        sprites.add(characterCard);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        positionCardsInArea();
    }

    public void positionCardsInArea()
    {
        for (int i = 0; i < cardsInArea.size(); i++)
            cardsInArea.get(i).setOrigin(new Origin(handPositions[i]));
    }

}
