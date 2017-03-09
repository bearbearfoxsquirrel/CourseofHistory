package test.puigames.courseofhistory.framework.game;

import android.util.Log;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Jordan on 02/03/2017.
 */

public class PlayArea extends CardArea
{
    private Origin[] positions;

    public PlayArea(float spawnX, float spawnY, int width, int height)
    {
        super(spawnX, spawnY, width, height);

        maxCardsInArea = 5; //number of cards you can have in this play area
        positions = new Origin[maxCardsInArea];

        for(int i = 1; i <= maxCardsInArea; i++)
            positions[i - 1] = new Origin((width/6) * i, this.origin.y + this.halfHeight/6);
    }


    @Override
    public void addCardToArea(CharacterCard card)
    {
        super.addCardToArea(card);
        positionCardsInArea();
    }

    @Override
    public void removeCardFromArea(CharacterCard card)
    {
        super.removeCardFromArea(card);
        positionCardsInArea();
    }

    @Override
    public void update(InputBuddy inputBuddy, float deltaTime)
    {
        super.update(inputBuddy, deltaTime);
        positionCardsInArea();
    }

    public void positionCardsInArea()
    {
        for (int i = 0; i < cardsInArea.size(); i++)
        {
//            if(!cardsInArea.get(i).origin.equals(positions[i]))
                cardsInArea.get(i).setOrigin(new Origin(positions[i]));
        }
        Log.d("Called", "positioned cards");
    }
}
