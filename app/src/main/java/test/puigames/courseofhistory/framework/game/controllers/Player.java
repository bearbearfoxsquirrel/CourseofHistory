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
        TURN_STARTED, CREATED, TAKING_TURN, WAITING_FOR_TURN, WIN, LOSE;
    }

    public enum PawnAction {
        ATTACK, PLACE_CARD_ON_BOARD, END_TURN, NONE;
    }


    public Player(CharacterCard[] playerCards, CardGameController controller) {
        super(controller);
        this.playerCurrentState = PawnState.TAKING_TURN; //TODO set to created, set to taking turn for testing only!
        this.currentAction = PawnAction.NONE; //Initialised to none as a pawn is doing nothing when the game starts
        this.playerGraveyard = new ArrayList<>();
        this.testCards = playerCards;
    }

    public void update(float deltaTime) {

       // controller.update(this, deltaTime);
    }

    /*public void takeTurn(float deltaTime){
       playerCurrentState = PawnState.TAKING_TURN;
        boolean turnActive = true;
        drawCardFromDeck();

        while(turnActive) {
            currentAction = getCurrentAction();
            switch (currentAction) {
                case ATTACK:
                    attack();
                    break;
                case PLACE_CARD_ON_BOARD:
                    placeCardOnBoard();
                    break;

            }
        }
        playerCurrentState = PawnState.WAITING_FOR_TURN;
    }*/

    public PawnAction getCurrentAction() {
        return currentAction;
    }

    public void attack(CharacterCard theAttacker, GameObject recipientOfMyFatalBlow) throws GameController.ControllerException {
        //Takes in an object that the pawn wishes to attack and tries to attack the given object
        //Or else throws a controller exception
        if (recipientOfMyFatalBlow instanceof CharacterCard) {
            theAttacker.attackCard((CharacterCard) recipientOfMyFatalBlow);
        } //else if (recipientOfMyFatalBlow instanceof Hero) {
        // attackHero();
        throw new GameController.ControllerException("Cannot attack this object!");
    }

    private void attackCard(CharacterCard theAttacker, CharacterCard theRecpient) {

    }

    private void attackHero() {

    }


    public Card drawCardFromDeck() {
        return null;
    }

    public void placeCardOnBoard(CharacterCard card) {

    }

    public void endPlayerTurn() {

    }
}
