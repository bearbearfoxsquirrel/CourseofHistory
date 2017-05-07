package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Jordan on 02/03/2017.
 */

/**
 * Basic card-area class
 * Keeps cards in a certain area with pseudo-decided positions
 */
public abstract class CardArea extends Sprite
{
    protected float cardPadding;
    protected ArrayList<CharacterCard> cardsInArea;
    protected Origin[] positions;
    protected int maxCardsInArea;

    public CardArea(Screen screen, Bitmap bitmap, int width, int height)
    {
        super(screen, bitmap, width, height);

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
            cardsInArea.add(card);
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
        if(cardsInArea.size() > 0 ) {
            for (int i = cardsInArea.size() - 1; i >= 0; i--)
                for (int j = 0; j < cardsInArea.size(); j++)
                    if (!cardsInArea.get(i).getOrigin().equals(positions[j]) && !cardsInArea.get(i).getOrigin().equals(positions[i]))
                        cardsInArea.get(i).getOrigin().setOrigin(new Origin(positions[i]));
        }
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
            positions[i - 1] = new Origin(cardPadding + ((width / maxCardsInArea) * i) / 1.2f, this.origin.getOriginY() + (cardPadding * 2));
    }

    public float getCardPadding() {
        return cardPadding;
    }

    public void setCardPadding(float cardPadding) {
        this.cardPadding = cardPadding;
    }

    public ArrayList<CharacterCard> getCardsInArea() {
        return cardsInArea;
    }

    public void setCardsInArea(ArrayList<CharacterCard> cardsInArea) {
        this.cardsInArea = cardsInArea;
    }

    public Origin[] getPositions() {
        return positions;
    }

    public void setPositions(Origin[] positions) {
        this.positions = positions;
    }

    public int getMaxCardsInArea() {
        return maxCardsInArea;
    }

    public void setMaxCardsInArea(int maxCardsInArea) {
        this.maxCardsInArea = maxCardsInArea;
    }
}