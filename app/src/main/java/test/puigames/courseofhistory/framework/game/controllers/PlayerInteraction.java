package test.puigames.courseofhistory.framework.game.controllers;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 20/02/2017.
 */

public interface PlayerInteraction {
    public void attack(CharacterCard theAttacker, GameObject recipientOfMyFatalBlow) throws ControllerException;

    public Card drawCardFromDeck();

    public void placeCardOnBoard();

    public void endPlayerTurn();
}
