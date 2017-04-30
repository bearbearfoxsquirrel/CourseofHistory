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
    public PlayArea(Screen screen, int width, int height) {
        super(screen, width, height);
        maxCardsInArea = 5; //number of cards you can have in play area
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
    }

    @Override
    public void initPlacement(float spawnX, float spawnY, float rotation){
        super.initPlacement(spawnX, spawnY, rotation);
        setUpPositions();
    }


    @Override
    public void addCardToArea(CharacterCard card)
    {
        if (!cardsInArea.contains(card)) {
            card.adjustCardSize(1.25f);
            super.addCardToArea(card);
        }
    }
}
