package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import android.util.Log;

import test.puigames.courseofhistory.framework.engine.Controlling.Inputable;
import test.puigames.courseofhistory.framework.engine.Controlling.Possessor;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.game.assets.players.events.Eventable;

//This class is for allowing the user to interact with a pawn pawn
public class HumanCardGameController extends CardGameController implements Inputable, Possessor{
    public InputBuddy inputBuddy;

    public HumanCardGameController(InputBuddy inputBuddy) {
        this.inputBuddy = inputBuddy;
    }

    public void update(float deltaTime) {
        if (player.playerCurrentState == Player.PawnState.TURN_ACTIVE) {
            //   updateCardsInHand(deltaTime);
            updateCardsOnBoardPlayArea(deltaTime);

            //For test cards
//            if (playerEvents.size() == 0) {
//                for (CharacterCard playerCard : player.board.playAreas[player.playerNumber].cardsInArea)
//                    for (CharacterCard opponentCard : player.board.playAreas[player.playerNumber].cardsInArea)
//                        if (playerCard.boundingBox.isOverlapping(opponentCard.boundingBox)) {
//                            playerEvents.add(player.createAttack(playerCard, opponentCard));
//                        }
//            } else {
//                for (Eventable event : playerEvents)
//                    event.update(deltaTime);
//            }

            //For actual thing

//            if (playerEvents.size() == 0) {
//                for (CharacterCard playerCard : player.board.playAreas[player.playerNumber].cardsInArea)
//                    for (CharacterCard opponentCard : player.board.playAreas[oppositePlayerNumber].cardsInArea)
//                        if (playerCard.boundingBox.isOverlapping(opponentCard.boundingBox))
//                            playerEvents.add(player.createAttack(playerCard, opponentCard));
//            } else {
//                for (Eventable event : playerEvents)
//                    event.update(deltaTime);
//            }
        }
    }

    public void updateCardsOnBoardPlayArea(float deltaTime) {
        for(CharacterCard card : player.board.playAreas[player.playerNumber].cardsInArea) {
            for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
                if (checkIsTouched(touchEvent, card)) {
                    player.moveCard(card, touchEvent.x, touchEvent.y);
                    if(player.board.playAreas[player.playerNumber].cardsInArea.contains(card)) {
                        card.setOrigin(new Origin(touchEvent.x, touchEvent.y));
                        player.removeCardFromArea(card);
                    }
                } else if(card.boundingBox.isOverlapping(player.board.playAreas[player.playerNumber]
                        .boundingBox))
                    player.addCardToArea(card);
            }
            if(card.boundingBox.isOverlapping(player.board.playAreas[player.playerNumber].boundingBox)
                    && inputBuddy.touchEvents.isEmpty())
                player.board.playAreas[player.playerNumber].addCardToArea(card);
        }
        collisionCheckAndResolve();
    }

    private void collisionCheckAndResolve()
    {
        for(CharacterCard card : player.board.playAreas[player.playerNumber].cardsInArea)
        {
            for(CharacterCard card2 : player.board.playAreas[player.playerNumber].cardsInArea)
            {
                if(card.origin.equals(card2.origin))
                    card.boundingBox.getCollisionDetector().resolveCollision(card, card2, card.overlapAllowance);

                if(card.boundingBox.getCollisionDetector().checkForCollision(card, card2))
                    //collision with cards
                    card.boundingBox.getCollisionDetector().resolveCollision(card, card2, card.overlapAllowance);
                if(!card2.boundingBox.isEncapsulated(player.board.boundingBox)) //collision with board
                    player.board.boundingBox.getCollisionDetector().keepInsideBoundingBox(player.board, card2);
            }
        }
    }

    /**
     * TODO: inform user of not having enough mana for action??
     * When a player moves a card from their hand to board, this is called
     * Checks if player has enough mana for the action,
     *      if they do, card is played
     *          and the corresponding mana cost of card is removed from player's
     *          currentMana
     * @param card - card that is being played
     */
    public void addCardToBoardPlayArea(CharacterCard card) {
        //player.currentAction = Player.PawnAction.PLACE_CARD_ON_BOARD;
        if(player.currentMana >= card.mana)
        {
            player.placeCardOnBoard(card);
            removeManaFromPlayer(card.mana);
        }
        else
        {
            //should probably tell the user that they can't do that
        }
    }

    /**
     * If a player has enough mana, this method is called
     * @param manaCost - amount of mana used by playing card/switched to used state
     */
    private void removeManaFromPlayer(int manaCost)
    {
        player.currentMana -= manaCost;
        for(int i = (player.MAX_MANA - 1); i >= (player.currentMana - 1); i++)
        {
            player.mana[i].manaState = Mana.ManaState.used;
            player.mana[i].setImage(player.mana[i].manaType[1]);
        }
    }

    private boolean checkIsTouched(Input.TouchEvent touchEvent, GameObject object) {
        return (object.boundingBox.isTouchOn(touchEvent));
    }

    @Override
    public InputBuddy getInput() {
        return inputBuddy;
    }

    @Override
    public void possessPlayer(Player player) {
        super.possessPlayer(player);
    }

    @Override
    public Player getPlayer() {
        return super.getPlayer();
    }
}
