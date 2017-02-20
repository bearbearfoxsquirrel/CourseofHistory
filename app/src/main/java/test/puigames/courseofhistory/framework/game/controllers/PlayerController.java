package test.puigames.courseofhistory.framework.game.controllers;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameController;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 20/02/2017.
 */

public class PlayerController extends GameController implements PlayerInteraction{
    //TODO: Add deck, hand, hero, and board area
    public Card playersSuperDuperCard;
    public ArrayList<Card> playerGraveyard;

    public PlayerController(Card playersGivenCard) {
        this.playersSuperDuperCard = playersGivenCard;
        this.playerGraveyard = new ArrayList<>();
    }

    @Override
    public void attack(CharacterCard theAttacker, GameObject recipientOfMyFatalBlow) throws ControllerException{
        //Takes in an object that the player wishes to attack and tries to attack the given object
        //Or else throws a controller exception
        if (recipientOfMyFatalBlow instanceof CharacterCard) {
            theAttacker.attackCard((CharacterCard) recipientOfMyFatalBlow);
            attackCard(theAttacker, (CharacterCard) recipientOfMyFatalBlow);
            //if()

        } //else if (recipientOfMyFatalBlow instanceof Hero) {
           // attackHero();
        throw new ControllerException("Cannot attack this object!");
    }

    private void attackCard(CharacterCard theAttacker, CharacterCard theRecpient) {

    }

    private void attackHero() {

    }

    @Override
    public Card drawCardFromDeck() {
        return null;
    }

    @Override
    public void placeCardOnBoard() {

    }

    @Override
    public void endPlayerTurn() {

    }
}
