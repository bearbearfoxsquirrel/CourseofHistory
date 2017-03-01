package test.puigames.courseofhistory.framework.game.controllers;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameController;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 20/02/2017.
 */

public class Competitor implements PlayerInteraction{
    //TODO: Add deck, hand, hero, and board area
    public GameController controller;
    public Card playersSuperDuperCard;
    public ArrayList<Card> playerGraveyard;
   // public Card[] hand;

    public Competitor(Card playersGivenCard, GameController controller) {
        this.controller = controller;
        this.playersSuperDuperCard = playersGivenCard;
        this.playerGraveyard = new ArrayList<>();
        //this.hand = new Card[6];
        //hand[0] = playersGivenCard;
    }




    public void takeTurn(float deltaTime){
        boolean turnActive = true;
        drawCardFromDeck();
       // PlayerAction playerAction = controller.getPlayerAction();

        //TODO add turn limit - just a counter
        while(turnActive) {
            switch (controller.getControllerAction()) {
                case ATTACK:
                    attack(controller.getActiveCard(), controller.getTargetCard());
                    break;

            }
        }
    }

    public void attack(CharacterCard theAttacker, GameObject recipientOfMyFatalBlow) throws ControllerException {
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
