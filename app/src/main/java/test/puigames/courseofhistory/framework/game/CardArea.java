package test.puigames.courseofhistory.framework.game;

import android.util.Log;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;

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

    ArrayList<Card> cardsInArea;

    public CardArea(float spawnX, float spawnY, int width, int height)
    {
        super(spawnX, spawnY, width, height);

        this.cardPadding = 2.0f;
        this.cardsInArea = new ArrayList<>();
    }

    //width: 480 - 20 = 460 units (10 units on each side)
    //height: (320 / 2) - 10 = 150 units (5 units above/below)


    public void update(InputBuddy inputBuddy, float deltaTime, Card[] cards)
    {
        super.update(inputBuddy, deltaTime);

        for(Card card : cards)
            manageCardArea(card);
    }

    /**
     * Try to position card in area
     * @param card - what we (hope) to move to position
     */
    protected void manageCardArea(Card card)
    {
        String logMessage = "nope :^)";
        if(this.boundingBox.isOverlapping(card.boundingBox))
        {
            cardsInArea.add(card);
            logMessage = "card added";
        }


        Log.d("cardArea", "" + logMessage);
        Log.d("cardArea size", "" + cardsInArea.size());
    }
}
