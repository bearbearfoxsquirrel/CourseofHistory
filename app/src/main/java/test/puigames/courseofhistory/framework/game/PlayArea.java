package test.puigames.courseofhistory.framework.game;

import android.util.Log;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Jordan on 02/03/2017.
 */

/**
 * Play area a player can use to play their cards in (their area)
 * Can have a max of 5 cards on board at once
 */
public class PlayArea extends CardArea
{
    public PlayArea(int width, int height)
    {
        super(width, height);
        maxCardsInArea = 5; //number of cards you can have in play area

        setUpPositions();
    }
}
