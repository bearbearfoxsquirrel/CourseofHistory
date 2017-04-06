package test.puigames.courseofhistory.framework.game.controllers;

import android.util.Log;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameController;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.gameobjects.Pawn;
import test.puigames.courseofhistory.framework.game.assets.PlayArea;
import test.puigames.courseofhistory.framework.game.assets.Deck;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 20/02/2017.
 */

public class Player extends Pawn {
    //TODO: Add deck, testCards, hero, and board area
    public ArrayList<Card> playerGraveyard;
    public CharacterCard[] testCards;
    public PawnState playerCurrentState;
   // public PawnAction currentAction;
    public Deck playerDeck;
    public PlayArea playArea;
    public Board board;

    public enum PawnState {
        TURN_STARTED, TURN_ACTIVE, CREATED, WAITING_FOR_TURN, TURN_ENDED, WIN, LOSE;
        //PLAY_ACTIVE refers to when the player is allowed to take active decision in their turn
    }


      //  this.currentAction = PawnAction.NONE; //Initialised to none as a pawn is doing nothing when the game starts


    public Player(CharacterCard[] playerCards, Board board) {
        this.playerCurrentState = PawnState.CREATED; //TODO set to created, set to taking turn for testing only!
        this.playerGraveyard = new ArrayList<>();
        this.testCards = playerCards;
        this.board = board;
        this.playArea = board.playAreas[0];
        setUpPlayerDeck();


    }

    public void setUpPlayerDeck(){
        this.playerDeck = new Deck();
        playerDeck.setUpDeck(this.testCards);
    }


    public void moveCard(Card card, float posX, float posY)
    {
        card.translateCard(posX, posY);
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

    public CharacterCard drawCardFromDeck() {
        playerCurrentState = PawnState.TURN_ACTIVE;
        return (CharacterCard)playerDeck.pop();
    }



    public void placeCardOnBoard(CharacterCard card) {

    }

    public void endPlayerTurn() {

    }

    public void addCardToArea(CharacterCard card)
    {
            playArea.addCardToArea(card);
    }

    public void removeCardFromArea(CharacterCard card)
    {
            playArea.removeCardFromArea(card);
    }
}
