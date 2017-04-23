package test.puigames.courseofhistory.framework.game.assets;

/**
 * Created by Jordan on 02/03/2017.
 */

import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Play area a player can use to play their cards in (their area)
 * Can have a max of 5 cards on board at once
 */
public class PlayArea extends CardArea
{
    public PlayArea(Screen screen, int width, int height, int spawnX, int spawnY)
    {
        super(screen, width, height);
        maxCardsInArea = 5; //number of cards you can have in play area
        initPlacement(spawnX, spawnY);
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
    }

    @Override
    public void initPlacement(float spawnX, float spawnY){
        super.initPlacement(spawnX, spawnY);
        setUpPositions();
    }
}
