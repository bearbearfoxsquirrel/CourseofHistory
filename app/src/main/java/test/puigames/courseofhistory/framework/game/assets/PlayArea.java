package test.puigames.courseofhistory.framework.game.assets;

/**
 * Created by Jordan on 02/03/2017.
 */

import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Play area a player can use to play their cards in (their area)
 * Can have a max of 5 cards on board at once
 */
public class PlayArea extends CardArea
{
    private static final float CARD_SCALE_FACTOR = 1.11f;

    public PlayArea(Screen screen, int width, int height, float spawnX, float spawnY)
    {
        super(screen, width, height);
        maxCardsInArea = 5; //number of cards you can have in play area
        initPlacement(spawnX, spawnY);
    }

    @Override
    public void initPlacement(float spawnX, float spawnY){
        super.initPlacement(spawnX, spawnY);
        setUpPositions();
    }

    @Override
    public void addCardToArea(CharacterCard card)
    {
        if (!cardsInArea.contains(card))
            card.adjustCardSize(CARD_SCALE_FACTOR);
        super.addCardToArea(card);
    }
}
