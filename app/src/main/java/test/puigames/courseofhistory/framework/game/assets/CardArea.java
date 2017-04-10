package test.puigames.courseofhistory.framework.game.assets;

import android.util.Log;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Jordan on 02/03/2017.
 */

/**
 * Basic card-area class
 * Designed to try and keep cards in a certain area with pseudo-decided positions
 */
public abstract class CardArea extends GameObject
{
    public float cardPadding;
    public ArrayList<CharacterCard> cardsInArea;
    public Origin[] positions;
    protected int maxCardsInArea;

    public CardArea(int width, int height)
    {
        super(width, height);

        this.cardPadding = 2.0f;
        this.cardsInArea = new ArrayList<>();

        maxCardsInArea = 0; //default value - overwrite when inheriting
    }

    /**
     * Try to add card to Area
     * @param card - what we (hope) to add to Area
     */
    public void addCardToArea(CharacterCard card)
    {
        if(!cardsInArea.contains(card) && cardsInArea.size() < maxCardsInArea)
            if (card.boundingBox.isOverlapping(this.boundingBox))
                cardsInArea.add(card);
        positionCardsInArea();
    }

    /**
     * Try to remove card from Area
     * @param card - what we (hope) to remove from Area
     */
    public void removeCardFromArea(CharacterCard card)
    {
        if(cardsInArea.contains(card) && cardsInArea.size() > 0)
            cardsInArea.remove(card);
        positionCardsInArea();
    }

    /**
     * Try to position all the cards in the area based on
     * the positions Origin array
     */
    public void positionCardsInArea()
    {
        for (int i = 0; i < cardsInArea.size(); i++)
            if(!cardsInArea.get(i).origin.equals(positions[i]))
                cardsInArea.get(i).setOrigin(new Origin(positions[i]));
    }

    /**
     * Only call in stuff that extends from CardArea
     *
     * Initialises the positions array
     * Must be called in classes higher than CardArea!
     */
    public void setUpPositions()
    {
        positions = new Origin[maxCardsInArea];
        for(int i = 1; i <= maxCardsInArea; i++)
            positions[i - 1] = new Origin(cardPadding + ((width / maxCardsInArea) * i) / 1.2f,
                    this.origin.y + (cardPadding * 2));
    }
}