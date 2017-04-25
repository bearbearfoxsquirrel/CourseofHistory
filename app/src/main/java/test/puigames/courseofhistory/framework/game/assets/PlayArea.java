package test.puigames.courseofhistory.framework.game.assets;

/**
 * Created by Jordan on 02/03/2017.
 */

import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Play area a player can use to play their cards in (their area)
 * Can have a max of 5 cards on board at once
 */
public class PlayArea extends CardArea
{
    public PlayArea(int width, int height, int spawnX, int spawnY)
    {
        super(width, height);
        maxCardsInArea = 5; //number of cards you can have in play area
        spawnObject(spawnX, spawnY);
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
    }

    @Override
    public void spawnObject(float spawnX, float spawnY){
        super.spawnObject(spawnX, spawnY);
        setUpPositions();
    }

    @Override
    public void addCardToArea(CharacterCard card)
    {
        if (!cardsInArea.contains(card))
            card.adjustCardSize(1.25f);
        super.addCardToArea(card);
    }
}
