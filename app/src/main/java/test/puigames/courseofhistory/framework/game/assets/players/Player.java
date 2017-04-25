package test.puigames.courseofhistory.framework.game.assets.players;

import android.util.Log;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.game.assets.CardArea;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.PlayArea;
import test.puigames.courseofhistory.framework.game.assets.PlayArea;
import test.puigames.courseofhistory.framework.game.assets.CardHand;
import test.puigames.courseofhistory.framework.game.assets.Deck;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.Card;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.events.CardAttack;
import test.puigames.courseofhistory.framework.game.assets.players.events.Damageable;

/**
 * Created by Michael on 20/02/2017.
 */

public class Player {
    //TODO: Add deck, testCards, hero, and board area
    public int playerNumber;
    public CharacterCard[] testCards;
    public final int MAX_MANA = 10;
    public Mana[] mana = new Mana[MAX_MANA];
    public int currentMana;
    public PawnState playerCurrentState;
    public Deck playerDeck;
    public Board board;

    public int attackCounter = 1;


    public enum PawnState {
        TURN_STARTED, TURN_ACTIVE, CREATED, WAITING_FOR_TURN, TURN_ENDED, WIN, LOSE;
        //PLAY_ACTIVE refers to when the player is allowed to take active decision in their turn
    }

    public Player(CharacterCard[] playerCards, Board board, int playerNumber) {
        this.playerCurrentState = PawnState.CREATED;
        this.playerNumber = playerNumber;
        this.board = board;
        this.testCards = playerCards;

        currentMana = 0;

        this.board = board;
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

    public CardAttack createAttack(CharacterCard theAttacker, Damageable.Attackable recipientOfMyFatalBlow) {
        //Takes in an object that the pawn wishes to attack and tries to attack the given object
        //Or else throws a controller exception
        //TODO add thing to make sure card can only attack once
        return new CardAttack(theAttacker, recipientOfMyFatalBlow, 5, null);
    }

    private void attackHero() {

    }

    public void endTurn() {
        playerCurrentState = PawnState.TURN_ENDED;
    }

    public CharacterCard drawCardFromDeck() {
        playerCurrentState = PawnState.TURN_ACTIVE;
        ((CharacterCard)playerDeck.peek()).spawnObject(0, 0);
        return (CharacterCard)playerDeck.pop();
    }



    public void placeCardOnBoard(CharacterCard card) {
        board.playAreas[playerNumber].addCardToArea(card);
//        board.cardHands[playerNumber].removeCardFromArea(card);
    }

    public void endPlayerTurn() {

    }

    public void addCardToArea(CharacterCard card)
    {
            board.playAreas[playerNumber].addCardToArea(card);
    }

    public void removeCardFromArea(CharacterCard card)
    {
            board.playAreas[playerNumber].removeCardFromArea(card);
    }
}
