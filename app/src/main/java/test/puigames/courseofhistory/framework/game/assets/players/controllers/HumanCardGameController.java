package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import android.graphics.Bitmap;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.controlling.Inputable;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.game.assets.CardArea;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;


//This class is for allowing the user to interact with a pawn pawn
public class HumanCardGameController extends CardGameController implements Inputable {
    private InputBuddy inputBuddy;
    private HumanCardGameUIContainer controllerUI;

    private float uiPlacementX;
    private float uiPlacementY;

    public HumanCardGameController(Screen screen, InputBuddy inputBuddy, Player player, Bitmap startingHandSelectorBackgroundBitmap, Bitmap confirmationButtonBitmap, Bitmap endTurnButtonBitmap, Bitmap cardToTossOverlap, float uIPlacementX, float uIPlacementY) {
        super(screen, player);
        this.inputBuddy = inputBuddy;
        this.controllerUI= new HumanCardGameUIContainer(screen, this.player, startingHandSelectorBackgroundBitmap, confirmationButtonBitmap, endTurnButtonBitmap, cardToTossOverlap);
        this.uiPlacementX = uIPlacementX;
        this.uiPlacementY = uIPlacementY;
    }


    public HumanCardGameController(InputBuddy inputBuddy, Player player) {
        this.inputBuddy = inputBuddy;
        this.player = player;
    }

    @Override
    public void update(float deltaTime) {
        for (MenuButton menuButton : controllerUI.getShownMenuButtons())
            if (menuButton.checkForInput(inputBuddy))
                menuButton.applyAction();

        switch (player.getPlayerCurrentState()) {
            case TURN_ACTIVE:
                updateCardsInHand(deltaTime);
                updateCardsOnBoardPlayArea(deltaTime);
                break;

            case TURN_ENDED:

            case BEGIN_CREATING_STARTING_HAND:

                break;
            case STARTING_HAND_CHOOSING_CARDS_TO_TOSS:
                if (controllerUI.startingHandSelectorUI.confirmationButton.checkForInput(inputBuddy))
                    controllerUI.startingHandSelectorUI.confirmationButton.applyAction();

                ArrayList<CharacterCard> cardsToBeSelectedForTossing = new ArrayList<>();
                ArrayList<CharacterCard> cardsToBeDeselectedForTossing = new ArrayList<>();

                for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
                    //check cards to toss selection
                    if(touchEvent.type == Input.TouchEvent.TOUCH_UP) {
                        for (CharacterCard card : player.getStartingHandSelector().getCardsToKeep())
                            if (card.getBoundingBox().isTouchOn(touchEvent))
                                cardsToBeSelectedForTossing.add(card);

                        for (CharacterCard card : cardsToBeSelectedForTossing)
                            player.getStartingHandSelector().selectCardToToss(card);

                        //Check cards to keep selection
                        for (CharacterCard card : player.getStartingHandSelector().getCardsToToss())
                            if (card.getBoundingBox().isTouchOn(touchEvent) && !cardsToBeSelectedForTossing.contains(card))
                                cardsToBeDeselectedForTossing.add(card);

                        for (CharacterCard card : cardsToBeDeselectedForTossing)
                            player.getStartingHandSelector().deselectCardToToss(card);

                        cardsToBeSelectedForTossing.clear();
                        cardsToBeDeselectedForTossing.clear();
                    }
                }
                break;
        }
        collisionCheckAndResolve(player.getBoard().getPlayAreas()[player.getPlayerNumber()]);
        collisionCheckAndResolve(player.getBoard().getCardHands()[player.getPlayerNumber()]);


       /* if (player.playerCurrentState == Player.PlayerState.TURN_ACTIVE) {


            //For actual thing

            if (playerEvents.size() == 0) {
                for (CharacterCard playerCard : player.board.playAreas[player.playerNumber].cardsInArea)
                    for (CharacterCard opponentCard : player.board.playAreas[oppositePlayerNumber].cardsInArea)
                        if (playerCard.boundingBox.isOverlapping(opponentCard.boundingBox))
                            playerEvents.add(player.createAttack(playerCard, opponentCard));
                            playerCard.UpdateCardStats();
                            opponentCard.UpdateCardStats();
            } else {
                for (Eventable event : playerEvents)
                    event.update(deltaTime);

        } else if (player.playerCurrentState == Player.PlayerState.CREATING_START_HAND) {
            updatePlayersStartingHand();
        }*/

        collisionCheckAndResolve(player.getBoard().getPlayAreas()[player.getPlayerNumber()]);
        collisionCheckAndResolve(player.getBoard().getCardHands()[player.getPlayerNumber()]);
    }



    private void updateCardsOnBoardPlayArea(float deltaTime){
        for(int i = 0; i < player.getBoard().getPlayAreas()[player.getPlayerNumber()].getCardsInArea().size(); i++) {
            CharacterCard card = player.getBoard().getPlayAreas()[player.getPlayerNumber()].getCardsInArea().get(i);
            if(inputBuddy.getTouchEvents().size() > 0) {
                Input.TouchEvent touchEvent = inputBuddy.getTouchEvents().get(0);
                if(checkIsTouched(touchEvent, card)) {
                    card.getOrigin().setOrigin(touchEvent.x, touchEvent.y);
                    player.moveCard(card, card.getOrigin().getOriginX(), card.getOrigin().getOriginY());
                }
            } else if(inputBuddy.getTouchEvents().isEmpty()) {
                player.getBoard().getPlayAreas()[player.getPlayerNumber()].positionCardsInArea();
            }
            if(card.isDeaders()) {
                player.getBoard().getPlayAreas()[player.getPlayerNumber()].getCardsInArea().remove(card);
                //TODO: its dead so should be removed from the universe
            }
        }
    }

    private void updateCardsInHand(float deltaTime) {
        for(int i = 0; i < player.getBoard().getCardHands()[player.getPlayerNumber()].getCardsInArea().size(); i++) {
            CharacterCard card = player.getBoard().getCardHands()[player.getPlayerNumber()].getCardsInArea().get(i);
            if(inputBuddy.getTouchEvents().size() > 0) {
                Input.TouchEvent touchEvent = inputBuddy.getTouchEvents().get(0);
                if(checkIsTouched(touchEvent, card)) {
                    card.getOrigin().setOrigin(touchEvent.x, touchEvent.y);
                    player.moveCard(card, card.getOrigin().getOriginX(), card.getOrigin().getOriginY());
                }
            } else if(card.getBoundingBox().isOverlapping(player.getBoard().getPlayAreas()[player.getPlayerNumber()].getBoundingBox()) && inputBuddy.getTouchEvents().isEmpty()) {
                playCard(card);
            }
        }
    }

    private boolean checkIfThereIsAnAttackOnOpponentCard(CharacterCard playerCard, CharacterCard opponentCard) {
        return playerCard.getBoundingBox().isOverlapping(opponentCard.getBoundingBox());
    }

    private void collisionCheckAndResolve(CardArea cardArea)
    {
        for(CharacterCard card : cardArea.getCardsInArea())
        {
            for(CharacterCard card2 : cardArea.getCardsInArea())
            {
                if(card.getOrigin().equals(card2.getOrigin()))
                    card.getBoundingBox().getCollisionDetector().resolveCollision(card, card2, card.getOverlapAllowance());

                if(card.getBoundingBox().getCollisionDetector().checkForCollision(card.getBoundingBox(), card2.getBoundingBox()))
                    card.getBoundingBox().getCollisionDetector().resolveCollision(card, card2, card.getOverlapAllowance());
                if(!card2.getBoundingBox().isEncapsulated(player.getBoard().getBoundingBox())) //collision with board
                    player.getBoard().getBoundingBox().getCollisionDetector().keepInsideBoundingBox(player.getBoard(), card2);
            }
            if(!card.getBoundingBox().isEncapsulated(player.getBoard().getBoundingBox()))
                card.getBoundingBox().getCollisionDetector().keepInsideBoundingBox(player.getBoard(), card);
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
    public void playCard(CharacterCard card)
    {
        player.placeCardOnBoard(card);
    }

    /**
     * If a player has enough mana, this method is called
     * @param manaCost - amount of mana used by playing card/switched to used state
     */
    private void removeManaFromPlayer(int manaCost)
    {
        player.setCurrentMana(player.getCurrentMana()- manaCost);
        for(int i = (player.getMAX_MANA() - 1); i >= (player.getCurrentMana() - 1); i++)
        {
            player.getMana()[i].setManaState(Mana.ManaState.used);
            player.getMana()[i].setBitmap(player.getMana()[i].getManaType()[1]);
        }
    }

    private boolean checkIsTouched(Input.TouchEvent touchEvent, GameObject object) {
        return (object.getBoundingBox().isTouchOn(touchEvent));
    }

    @Override
    public InputBuddy getInput() {
        return inputBuddy;
    }

    public HumanCardGameUIContainer getControllerUI() {
        return this.controllerUI;
    }

    public void placeControllerUI(Screen screen, float placementX, float placementY) {
        controllerUI.place(screen, placementX, placementY, player.getRotation());
        //UI is bound to the rotation of the player
    }

    @Override
    public void possessPlayer(Player player) {
        super.possessPlayer(player);
    }

    @Override
    public Player getPlayer() {
        return super.getPlayer();
    }

    public InputBuddy getInputBuddy() {
        return inputBuddy;
    }

    public void setInputBuddy(InputBuddy inputBuddy) {
        this.inputBuddy = inputBuddy;
    }

    @Override
    public void startTicking(Screen screen) {
        super.startTicking(screen);
        placeControllerUI(screen, uiPlacementX, uiPlacementY);
        // controllerUI.setUpGamePiecePositions(screen, 480/2, 320/2 );
    }

    @Override
    public void stopTicking(Screen screen) {
       super.stopTicking(screen);
        controllerUI.stopTicking(screen);
    }
}
