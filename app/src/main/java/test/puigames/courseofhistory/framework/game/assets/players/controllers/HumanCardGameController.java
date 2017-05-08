package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import android.graphics.Bitmap;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.controlling.Inputable;
import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.game.assets.Hero;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.ui.HumanCardGameUIContainer;


//This class is for allowing the user to interact with a pawn pawn
public class HumanCardGameController extends CardGameController implements Inputable {
    private InputBuddy inputBuddy;
    private HumanCardGameUIContainer controllerUI;

    private final static float TIME_TO_CONFIRM_USER_TOUCH_SELECTION = 0.19f;

    private float uiPlacementX;
    private float uiPlacementY;
    private float timeLeftToConfirmInput = 0;

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
        for (MenuButton menuButton : controllerUI.getShownMenuButtonsAsArray())
            if (menuButton.checkForInput(inputBuddy))
                menuButton.applyAction();

        switch (player.getPlayerCurrentState()) {
            case TURN_ACTIVE:
                updateCardsInHand(deltaTime);
                updateCardsOnBoardPlayArea(deltaTime);
                updateHero(deltaTime);
                break;

            case STARTING_HAND_CHOOSING_CARDS_TO_TOSS:
                handleInputOnStartingHandSelector();
                break;
        }
        //Inter card collision checking and handling
        cardCollisionCheckAndResolve(getPlayersCardsInPlayArea());
        cardCollisionCheckAndResolve(getPlayersCardsInCardHand());
        //Keep cards on board: don't check hand, causes issues
        keepCardsInBoardBounds(getPlayersCardsInPlayArea());
    }

    /**
     *  Checks for input on the starting hand selector UI components
     */
    private void handleInputOnStartingHandSelector() {
        if (controllerUI.getStartingHandSelectorUI().confirmationButton.checkForInput(inputBuddy))
            controllerUI.getStartingHandSelectorUI().confirmationButton.applyAction();

        ArrayList<CharacterCard> cardsToBeSelectedForTossing = new ArrayList<>();
        ArrayList<CharacterCard> cardsToBeDeselectedForTossing = new ArrayList<>();
        //Array lists set up outside and cleared every iteration of touch events to avoid needlessly redeclaring the arraylists
        for (Input.TouchEvent touchEvent : inputBuddy.getTouchEvents()) {
            //check cards to toss selection
            if(touchEvent.type == Input.TouchEvent.TOUCH_UP) {
                updateCardsBeingSelectedInStartingHandSelectorUI(touchEvent, cardsToBeSelectedForTossing, cardsToBeDeselectedForTossing);
                cardsToBeSelectedForTossing.clear();
                cardsToBeDeselectedForTossing.clear();
            }
        }
    }

    /**
     *  Checks cards that are selected for tossing or keeping by the user and sorts them into the appropriate list
     * @param touchEvent
     * @param cardsToBeSelectedForTossing Queue of cards to be selected for tossing from cardsToKeep list
     * @param cardsToBeDeselectedForTossing Queue of cards to be selected for tossing from cardsToToss list
     */
    private void updateCardsBeingSelectedInStartingHandSelectorUI(Input.TouchEvent touchEvent, ArrayList<CharacterCard> cardsToBeSelectedForTossing, ArrayList<CharacterCard> cardsToBeDeselectedForTossing) {
        //Check cards to keep selection
        for (CharacterCard card : player.getStartingHandSelector().getCardsToKeep())
            if (card.getBoundingBox().isTouchOn(touchEvent))
                cardsToBeSelectedForTossing.add(card);

        for (CharacterCard card : cardsToBeSelectedForTossing)
            player.getStartingHandSelector().selectCardToToss(card);

        //Check cards to toss selection
        for (CharacterCard card : player.getStartingHandSelector().getCardsToToss())
            if (card.getBoundingBox().isTouchOn(touchEvent) && !cardsToBeSelectedForTossing.contains(card))
                cardsToBeDeselectedForTossing.add(card);

        for (CharacterCard card : cardsToBeDeselectedForTossing)
            player.getStartingHandSelector().deselectCardToToss(card);
    }

    /**
     * Handles updating cards on the board: allows cards to attack if player indicates so/dragging cards around the board.
     * @param deltaTime - would be USED for animations, should we have those
    */
    private void updateCardsOnBoardPlayArea(float deltaTime) {
        if (inputBuddy.getTouchEvents().size() > 0) {
            Input.TouchEvent touchEvent = inputBuddy.getTouchEvents().get(0);
            timeLeftToConfirmInput = TIME_TO_CONFIRM_USER_TOUCH_SELECTION;
            for (CharacterCard card : getPlayersCardsInPlayArea()) {
                if (checkIfCardCanUnleashHell(touchEvent, card)) {
                    player.moveCard(card, touchEvent.x, touchEvent.y);
                }
                preventGoddamnCardOriginOverlappingHopefullySuperAwesomeFunMethod(getPlayersCardsInPlayArea());
            }
        } else {
            timeLeftToConfirmInput -= deltaTime;
            if (timeLeftToConfirmInput <= 0) {
                for (CharacterCard card : getPlayersCardsInPlayArea()) {
                    for (CharacterCard opponentCard : player.getBoard().getPlayArea(player.getOppositePlayerNumber()).getCardsInArea()) {
                        if (card.getBoundingBox().getCollisionDetector().checkForCollision(card.getBoundingBox(), opponentCard.getBoundingBox()) && card.hasEnergyToAttack()) {
                            card.attack(opponentCard);
                        }
                    }
                    if (card.getBoundingBox().getCollisionDetector().checkForCollision(player.getBoard().getHero(player.getOppositePlayerNumber()).getBoundingBox(), card.getBoundingBox()) && card.hasEnergyToAttack() && player.canPlayerAttackEnemyHero())
                        player.attackEnemyHero(card);
                }
                player.getBoard().getPlayArea(player.getPlayerNumber()).positionCardsInArea();
            }
        }
    }

    /**
     * Handles updating cards in the hand: allows players to play cards if they have the mana to do so
     * @param deltaTime - would be USED for animations, should we have those
     */
    private void updateCardsInHand(float deltaTime) {
        if(inputBuddy.getTouchEvents().size() > 0) {
            Input.TouchEvent touchEvent = inputBuddy.getTouchEvents().get(0);
            // TODO ADD QUEUE OF CARDS TO ADD TO BOARD BREAKS AVOID THIS FOR NOW
            for(CharacterCard card : getPlayersCardsInCardHand()) {
                if (checkIsTouched(touchEvent, card)) {
                    player.moveCard(card, touchEvent.x, touchEvent.y);

                    if (card.getBoundingBox().isEncapsulated(player.getBoard().getPlayArea(player.getPlayerNumber()).getBoundingBox()) && player.canCardBePlacedOnPlayArea(card) ) {
                        player.playCard(card);
                    }
                    preventGoddamnCardOriginOverlappingHopefullySuperAwesomeFunMethod(getPlayersCardsInCardHand());
                    break;
                } else if(isCardReleased(touchEvent, card))
                    player.getHand().positionCardsInArea();
            }
        } else
            player.getHand().positionCardsInArea();
    }

//    /**
//     * Handles updating cards in the hand: allows players to play cards if they have the mana to do so
//     * @param deltaTime - would be USED for animations, should we have those
//     */
//    private void updateCardsInHand(float deltaTime) {
//        if(inputBuddy.getTouchEvents().size() > 0) {
//            Input.TouchEvent touchEvent = inputBuddy.getTouchEvents().get(0);
//            for(CharacterCard card : getPlayersCardsInCardHand()) {
//                if(checkIfCardCanBePlayedNow(touchEvent, card)) {
//                    card.setOrigin(touchEvent.x, touchEvent.y);
//                    player.moveCard(card, card.getOrigin().getOriginX(), card.getOrigin().getOriginY());
//                    if(card.getBoundingBox().isEncapsulated(player.getBoard().getPlayArea(player.getPlayerNumber()).getBoundingBox())) {
//                        playCard(card);
//                    }
//                    preventGoddamnCardOriginOverlappingHopefullySuperAwesomeFunMethod(getPlayersCardsInCardHand());
//                    break;
//                } else if (releasedCard(touchEvent, card))
//                    player.getHand().positionCardsInArea();
//            }
//        }
//    }

    /**
     * Press the hero, the end turn button will appear
     * SPOILER: the button doesn't appear
     */
    private void updateHero(float deltaTime) {
        if (inputBuddy.getTouchEvents().size() > 0) {
            Input.TouchEvent touchEvent = inputBuddy.getTouchEvents().get(0);
            if (checkIsTouched(touchEvent, player.getHero()) && touchEvent.type == Input.TouchEvent.TOUCH_UP) {
                controllerUI.showMenuButton(controllerUI.getEndTurnButton(), controllerUI.END_TURN_BUTTON_OFFSET_X, controllerUI.END_TURN_BUTTON_OFFSET_Y, controllerUI.getAbsoluteRotationOfUIElement(controllerUI.getRotation()));
                controllerUI.setEndTurnButtonCoolDown(controllerUI.END_TURN_BUTTON_COOLDOWN_TIME);
            }
        }
    }

    /** Card passed in will attack if there is an opposing card's bounding box overlapping with it's bounding box
     *
     * Both cards will take damage
     * @param card - card whose bounding box we are checking for overlaps with opponent's cards
     */
    public void attackIfThereIsACardHere(CharacterCard card) {
        for(CharacterCard opponentCard : player.getBoard().getPlayArea(player.getOppositePlayerNumber()).getCardsInArea())
            if(card.getBoundingBox().isOverlapping(opponentCard.getBoundingBox())) {
                player.attackCard(card, opponentCard);
                //TODO: add update for stats here man bruh dudeseph
            }
    }

    /**
     * checks if there is an attack
     * @param playerCard - attacking card
     * @param opponentCard - receiving card
     * @return true if playerCard's bounding box is overlapping opponentCard's bounding box
     */
    private boolean checkIfThereIsAnAttackOnOpponentCard(CharacterCard playerCard, CharacterCard opponentCard) {
        return playerCard.getBoundingBox().isOverlapping(opponentCard.getBoundingBox());
    }

    /**
     * Checks that card was released from touch. Returns true if released.
     * @param touchEvent - touch event being checked
     * @param card - card being checked for touch
     * @return - true if card was is touched and touch event is touch_up, false otherwise
     */
    private boolean isCardReleased(Input.TouchEvent touchEvent, CharacterCard card) {
        return (checkIsTouched(touchEvent,  card) && touchEvent.type == Input.TouchEvent.TOUCH_UP);
    }

    /**
     * collision between cards - only if there are no touch events
     * @param cardList - players cards we are checking for collisions in
     */
    private void cardCollisionCheckAndResolve(ArrayList<CharacterCard> cardList) {
        if(inputBuddy.getTouchEvents().isEmpty()) {
            for (CharacterCard card : cardList)
                for (CharacterCard card2 : cardList)
                    if (card.getBoundingBox().getCollisionDetector().checkForCollision(card.getBoundingBox(), card2.getBoundingBox()))
                        card.getBoundingBox().getCollisionDetector().resolveCollision(card, card2, card.getOverlapAllowance());
        }
    }

//    /**
//     * collision between cards - only if there are no touch events
//     * @param cardList - players cards we are checking for collisions in
//     */
//    private void cardCollisionCheckAndResolve(ArrayList<CharacterCard> cardList) {
//        for (CharacterCard card : cardList) {
//            for (CharacterCard card2 : cardList) {
//                if (inputBuddy.getTouchEvents().size() <= 0) {
//                    if (card.getBoundingBox().getCollisionDetector().checkForCollision(card.getBoundingBox(), card2.getBoundingBox()))
//                        card.getBoundingBox().getCollisionDetector().resolveCollision(card, card2, card.getOverlapAllowance());
//                }
//            }
//        }
//    }

    /**
     * Returns true if card is touched and touch event is not a drag
     *
     * @param touchEvent - touch event we are checking against card & type of touch event
     * @param card - card we are checking if its touched
     * @return - true if card is touched and touch event type is touch_up or touch_down, false otherwise
     */
    private boolean youAreTryingToAttackAnotherCard(Input.TouchEvent touchEvent, CharacterCard card) {
        return card.getBoundingBox().isEncapsulated(card.getBoundingBox());
    }


    /**
     * Checks that no cards in the parameter card list have the taunt ability
     *
     * @param characterCards - cards we are checking
     * @return - true if no cards have taunt, false otherwise
     */
    private boolean noCardsInListHaveTaunt(ArrayList<CharacterCard> characterCards) {
        for(CharacterCard card : characterCards)
            if(hasTaunt(card))
                return false;
        return true;
    }

    /**
     * Returns true if card has taunt ability
     *
     * @param card - card tested to see if it has taunt
     * @return - true if card has taunt, false otherwise
     */
    private boolean hasTaunt(CharacterCard card) {
        return card.getAbilityDescription().equalsIgnoreCase("taunt");
    }

    /**
     * Returns true if card's bounding box is overlapping the enemy hero's bounding box
     *
     * @param touchEvent
     * @param hero - hero we are checking if it's
     * @return - true if card is touched and touch event type is touch_up or touch_down, false otherwise
     */
    private boolean youAreTryingToAttackTheEnemyHero(Input.TouchEvent touchEvent, CharacterCard card, Hero hero) {
        return card.getBoundingBox().isOverlapping(hero.getBoundingBox());
    }


    /**
     * **tries** to stop two cards from snapping to one point of touch
     *
     * @param cardList - list of cards we are checking for origin overlapping
     */
    private void preventGoddamnCardOriginOverlappingHopefullySuperAwesomeFunMethod(ArrayList<CharacterCard> cardList) {
        int superFunHappyGreatMultiplier = 2;
        for(CharacterCard card : cardList) {
            for (CharacterCard otherCard : cardList) {
                if(card.getOrigin().equals(otherCard.getOrigin()))
                        card.getBoundingBox().getCollisionDetector().resolveCollision(card, otherCard, card.getOverlapAllowance() * superFunHappyGreatMultiplier);
            }
        }
    }

    /**
     * @return this player's cards in their hand
     */
    private ArrayList<CharacterCard> getPlayersCardsInCardHand() {
        return player.getHand().getCardsInArea();
    }

    /**
     * @return this player's cards in their play area
     */
    private ArrayList<CharacterCard> getPlayersCardsInPlayArea() {
        return player.getBoard().getPlayArea(player.getPlayerNumber()).getCardsInArea();
    }

    private void keepCardsInBoardBounds(ArrayList<CharacterCard> cardList) {
        for(CharacterCard card : cardList)
            if(!card.getBoundingBox().isEncapsulated(player.getBoard().getBoundingBox()))
                card.getBoundingBox().getCollisionDetector().keepInsideBoundingBox(player.getBoard(), card);
    }

    /**
     * Checks if card is touched, and if the card has energy to attack
     *
     * @param touchEvent - checking this event against card
     * @param card - card we are checking
     * @return - true if card is touched, the touch event type is dragged, and card has attack energy, false otherwise
     */
    private boolean checkIfCardCanUnleashHell(Input.TouchEvent touchEvent, CharacterCard card) {
        return (checkIsTouched(touchEvent, card) && card.hasEnergyToAttack());
    }

    /**
     * Checks if card has mana to be played now nad is touched
     *
     * @param touchEvent - touch event on card
     * @param card - card being touched and card's mana that is being checked
     * @return - true if card is touched and player's mana is >= card's mana
     */
    public boolean checkIfCardCanBePlayedNow(Input.TouchEvent touchEvent, CharacterCard card) {
        return (checkIsTouched(touchEvent, card) && player.getCurrentMana() >= card.getMana());
    }

    /**
     * Checks if gameObject is touched
     *
     * @param touchEvent - touch event object we are checking pos(x, y) of
     * @param object - object we are checking
     * @return - true if touch event(x, y) falls within object's bounding box, false otherwise
     */
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
