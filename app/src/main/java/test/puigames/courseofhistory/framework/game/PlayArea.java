package test.puigames.courseofhistory.framework.game;

/**
 * Created by Jordan on 02/03/2017.
 */

/**
 * Play area a player can use to play their cards in (their area)
 * Can have a max of 5 cards on board at once
 */
public class PlayArea extends CardArea
{
    public PlayArea(int width, int height, int spawnX, int spawnY) {
        super(width, height);
        maxCardsInArea = 5; //number of cards you can have in play area
        spawnObject(spawnX, spawnY);
    }

    @Override
    public void spawnObject(float spawnX, float spawnY){
        super.spawnObject(spawnX, spawnY);
        setUpPositions();
    }
}
