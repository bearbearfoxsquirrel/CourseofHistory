package test.puigames.courseofhistory.framework.game.assets;

/**
 * Created by Jordan on 02/03/2017.
 */

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Play area a player can use to play their cards in (their area)
 * Can have a max of 5 cards on board at once
 */
public class PlayArea extends CardArea
{
    private static final float CARD_SCALE_FACTOR = 0.9f;

    public PlayArea(Screen screen, Bitmap bitmap, int width, int height)
    {
        super(screen, bitmap, width, height);
        maxCardsInArea = 5; //number of cards you can have in play area
    }

    @Override
    public void initPlacement(float spawnX, float spawnY, float rotation)
    {
        super.initPlacement(spawnX, spawnY, rotation);
        setUpPositions();
    }

    @Override
    public void place(Screen screen, float placementX, float placementY, float rotation)
    {
        super.place(screen, placementX, placementY, rotation);
        setUpPositions();
    }

    @Override
    public void addCardToArea(CharacterCard card)
    {
        if (!cardsInArea.contains(card))
            card.adjustCardSize(CARD_SCALE_FACTOR);
        super.addCardToArea(card);
        positionCardsInArea();
    }

    @Override
    public void removeCardFromArea(CharacterCard card)
    {
        super.removeCardFromArea(card);
        positionCardsInArea();
    }
}
