package test.puigames.courseofhistory.framework.game;

import test.puigames.courseofhistory.framework.game.assets.cards.Card;

/**
 * Created by Jordan on 02/03/2017.
 */

public class PlayArea extends CardArea
{

    public PlayArea(float spawnX, float spawnY, int width, int height)
    {
        super(spawnX, spawnY, width, height);
    }

    @Override
    public void manageCardArea(Card card)
    {
        super.manageCardArea(card);

//        if(cardsInArea.contains(card))
//            return;

//        if(!card.isOnTouch)
//        {
//            if(cardsInArea.size() <= 0)
//            {
//                card.origin.x = this.origin.x;
//                card.origin.y = this.origin.y;
//            }
//            else
//            {
//                card.origin.x = (cardsInArea.get(cardsInArea.size() - 1).origin.x + cardPadding);
//                card.origin.y = (cardsInArea.get(cardsInArea.size() - 1).origin.x + cardPadding);
//            }
//        }
    }
}
