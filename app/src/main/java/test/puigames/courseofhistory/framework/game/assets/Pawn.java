package test.puigames.courseofhistory.framework.game.assets;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.game.controllers.GameController;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.controllers.PlayerInteraction;

/**
 * Created by Michael on 20/02/2017.
 */

public class Pawn implements PlayerInteraction {
    //TODO: Add deck, testCards, hero, and board area
    public ArrayList<Card> playerGraveyard;
    public Card[] testCards;
    public PawnState playerCurrentState;
    public PawnAction currentAction;

    public enum PawnState {
        CREATED, TAKING_TURN, WAITING_FOR_TURN, WIN, LOSE;
    }

    public enum PawnAction {
        ATTACK, PLACE_CARD_ON_BOARD, END_TURN, NONE;
    }


    public Pawn(Card[] playerCards) {
        this.playerCurrentState = PawnState.TAKING_TURN; //TODO set to created, set to taking turn for testing only!
        currentAction = PawnAction.NONE; //Initialised to none as a player is doing nothing when the game starts
        this.playerGraveyard = new ArrayList<>();
        this.testCards = playerCards;
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
        //Takes in an object that the player wishes to attack and tries to attack the given object
        //Or else throws a controller exception
        if (recipientOfMyFatalBlow instanceof CharacterCard) {
            theAttacker.attackCard((CharacterCard) recipientOfMyFatalBlow);
            attackCard(theAttacker, (CharacterCard) recipientOfMyFatalBlow);
            //if()

        } //else if (recipientOfMyFatalBlow instanceof Hero) {
        // attackHero();
        throw new GameController.ControllerException("Cannot attack this object!");
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
