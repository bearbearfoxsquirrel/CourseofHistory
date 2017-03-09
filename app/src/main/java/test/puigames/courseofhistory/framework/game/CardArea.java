package test.puigames.courseofhistory.framework.game;

import android.util.Log;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;
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

    protected int maxCardsInArea = 0; //default value - overwrite in hand

    public CardArea(float spawnX, float spawnY, int width, int height)
    {
        super(spawnX, spawnY, width, height);

        this.cardPadding = 2.0f;
        this.cardsInArea = new ArrayList<>();
    }

    /**
     * Try to position card in area
     * @param card - what we (hope) to move to position
     */
    public void addCardToArea(CharacterCard card)
    {
        if(!cardsInArea.contains(card) && cardsInArea.size() < maxCardsInArea)
            if (card.boundingBox.isOverlapping(this.boundingBox))
                cardsInArea.add(card);
        else
            return;
    }

    public void removeCardFromArea(CharacterCard card)
    {
        if(cardsInArea.contains(card) && cardsInArea.size() > 0)
                cardsInArea.remove(card);
        else
            return;
    }
}
