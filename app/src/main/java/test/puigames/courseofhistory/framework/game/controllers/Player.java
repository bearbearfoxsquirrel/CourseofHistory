package test.puigames.courseofhistory.framework.game.controllers;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameController;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.gameobjects.Pawn;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 20/02/2017.
 */


//Player is
public class Player extends Pawn {
    //TODO: Add deck, testCards, hero, and board area
    public ArrayList<Card> playerGraveyard;
    public CharacterCard[] testCards;
    public PawnState playerCurrentState;
    public PawnAction currentAction;

    public enum PawnState {
        TURN_STARTED, TURN_ACTIVE, CREATED, WAITING_FOR_TURN, TURN_ENDED, WIN, LOSE;
        //PLAY_ACTIVE refers to when the player is allowed to take active decision in their turn
    }

    public enum PawnAction {
        ATTACK, PLACE_CARD_ON_BOARD, END_TURN, NONE;
    }


    public Player(CharacterCard[] playerCards) {
        this.playerCurrentState = PawnState.TURN_STARTED; //TODO set to created, set to taking turn for testing only!
        this.currentAction = PawnAction.NONE; //Initialised to none as a pawn is doing nothing when the game starts
        this.playerGraveyard = new ArrayList<>();
        this.testCards = playerCards;
    }

    public void drawCardFromDeck() {
        //TODO animate
        //TODO hand.addToHand(deck.pop());
        playerCurrentState = PawnState.TURN_ACTIVE;
    }

    public void moveCard(Card card, float posX, float posY)
    {
        card.translateCard(posX, posY);
    }

    public void update(float deltaTime) {

       // controller.update(this, deltaTime);
    }

    public PawnAction getCurrentAction() {
        return currentAction;
    }

    public void attack(CharacterCard theAttacker, GameObject recipientOfMyFatalBlow) throws GameController.ControllerException {
        //Takes in an object that the pawn wishes to attack and tries to attack the given object
        //Or else throws a controller exception
        if (recipientOfMyFatalBlow instanceof CharacterCard) {
            theAttacker.attackCard((CharacterCard) recipientOfMyFatalBlow);
            //else if (recipientOfMyFatalBlow instanceof Hero) {
            // attackHero();
        } else {
            throw new GameController.ControllerException("Cannot attack this object!");
        }
    }

    private void attackCard(CharacterCard theAttacker, CharacterCard theRecpient) {
        theAttacker.attackCard(theRecpient);
        theRecpient.attackCard(theAttacker);

        //TODO check cards' health and see if they are deaded then take appropriate action
        //TODO find nice way to create attacks as an EVENT
        //TODO GAME SHOULD INITIATE CHECKING CARDS ARE DEADED AND SEND THEM TO THEIR GRAVES IF NECESSARY
    }

    public void sendCardToTheNeverZone(Card card) {

    }

    private void attackHero() {

    }

    public void endTurn() {
        playerCurrentState = PawnState.TURN_ENDED;
    }



    public void placeCardOnBoard(CharacterCard card) {

    }

    public void endPlayerTurn() {

    }
}
