package test.puigames.courseofhistory.framework.game.controllers;

/**
 * Created by Michael on 20/02/2017.
 */

public interface PlayerInteraction {
  // public void attack(CharacterCard theAttacker, GameObject recipientOfMyFatalBlow) throws GameController.ControllerException;

  //  public Card drawCardFromDeck();

  //  public void placeCardOnBoard();

  //  public void endPlayerTurn();
    public void updateCardsInHand(float deltaTime);

    public enum ControllerState {
        CREATED, MOVING_CARD_IN_HAND, ATTACKING;
    }


}
