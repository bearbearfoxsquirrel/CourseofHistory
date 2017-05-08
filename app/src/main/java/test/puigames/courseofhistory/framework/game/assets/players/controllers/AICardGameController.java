package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.assets.players.Player;

/**
 * Created by Michael on 07/05/2017.
 */

public class AICardGameController extends CardGameController {
    private final float TIME_BETWEEN_EACH_DECISION = 1.f;

    private float currentTimeBetweenDecision;
    boolean noMoreMovesCanBeMade;

    public AICardGameController(Screen screen, Player player) {
        super(screen, player);
        currentTimeBetweenDecision = TIME_BETWEEN_EACH_DECISION;
    }

    /*public enum PlayerState {
        CREATED, TURN_STARTED, WAITING_FOR_FIRST_TURN, TURN_ACTIVE, WAITING_FOR_TURN, TURN_ENDED, WIN, LOSE,
        WAITING_TO_BEGIN_CREATING_HAND, BEGIN_CREATING_STARTING_HAND, STARTING_HAND_CHOOSING_CARDS_TO_TOSS, FINISHED_CREATING_START_HAND
        //PLAY_ACTIVE refers to when the player is allowed to take active decision in their turn
    }*/

    @Override
    public void update(float deltaTime) {
        currentTimeBetweenDecision -= deltaTime;
        if (currentTimeBetweenDecision <= 0) {
            switch (player.getPlayerCurrentState()) {
                case STARTING_HAND_CHOOSING_CARDS_TO_TOSS:
                    handleCreatingStartingHand();
                    break;
                case TURN_ACTIVE:
                    updateMakingTurn();
                    break;
            }
            currentTimeBetweenDecision = TIME_BETWEEN_EACH_DECISION;
        }
    }

    /**
     *  Finds out how good the card is by returning its rating
     *  (only used for AI, to give some meaning to cards it chooses)
     *  WIP algorithm for selecting cards
     * @param mana
     * @param attack
     * @param health
     * @return a factor of how good the card is
     */
    private float getCardRating(int mana, int attack, int health) {
        return (attack + health) / mana;
    }

    private void handleCreatingStartingHand() {
        final int MAX_MANA_ALLOWED = 3;
        final float MIN_RATING_IF_CARD_IS_OVER_MAX_MANA = 0.5f;
        // Choose cards with low mana, so they can be played soon
        // Especially cards with one mana!
        // Apart from that have a preference for cards with a high attack or high health
        ArrayList<CharacterCard> cardsToTossAfterProcessing = new ArrayList<>();
        for (CharacterCard card : player.getStartingHandSelector().getCardsToKeep())
            if (card.getMana() > MAX_MANA_ALLOWED || getCardRating(card.getMana(), card.getAttack(), card.getHealth()) < MIN_RATING_IF_CARD_IS_OVER_MAX_MANA)
                 cardsToTossAfterProcessing.add(card);

        for (CharacterCard card : cardsToTossAfterProcessing)
            player.getStartingHandSelector().selectCardToToss(card);

        player.confirmSelectedCardsFromStartingHandSelector();
    }

    //In future should use player events so there can be a queue of events for the ai to over a period of time rather than all at once
    private void updateMakingTurn() {
        noMoreMovesCanBeMade = true;
        // First check for threats to self, so see if the enemy player can kill your hero with cards they have
        // If they can then see if you have enough to kill the threat
        // if cards in play area can get rid of enemy threat then just use those cards
        // or else check if the player has any cards in their hand that could help
        // if they do then place that card and then recheck if you can destroy they threat

        if (isEnemyAThreat())
            handleEnemyThreat();
        else
            haveARelaxingTurn();

        if (noMoreMovesCanBeMade)
            player.endTurn();


        // Check cards in your hand, find the best cards to draw that you are able to and then draw them
        // If there no good cards to draw then just move on to cards in play

        // For cards in play, calculate max damage that could be done
        // If it is enough to kill the hero then use cards to kill the hero
        // If not, then calculate what are the most amount of cards that could be killed
        //
    }

    private void handleEnemyThreat() {
        if (player.hasCardThatCanBeDrawn()) {
            ArrayList<CharacterCard> cardsToBeDrawn = getCardsThatCanBePlayedFromHand();
            for (CharacterCard card : cardsToBeDrawn)
                player.playCard(card);
            noMoreMovesCanBeMade = false;
            return;
        }

        if (player.hasCardsThatCanAttack()) {
            CharacterCard weakestCardToAttack = getCardMostLikelyToGGPlayer();
            ArrayList<CharacterCard> weakestCardsForTheJob = getWeakestCardsForTheJob(weakestCardToAttack);
            for (CharacterCard card : weakestCardsForTheJob) {
                player.attackCard(card, weakestCardToAttack);
            }
            noMoreMovesCanBeMade = false;
            return;
        }
    }

    private CharacterCard getCardMostLikelyToGGPlayer() {
       return getCardWithHighestAttack(player.getBoard().getPlayArea(player.getPlayerNumber()).getCardsInArea());
    }


    private void haveARelaxingTurn() {
        if (player.isThereACardThatCanBePlacedOnBoard()) {
            player.playCard(getCardWIthBestRatingThatCanBePlayedFromHand());
            noMoreMovesCanBeMade = false;
            return; //Returns are used to make controller go though mutiple cycles between
        }

        int randomerNumberer = (int) Math.random() % 20;
        //One in twenty chance of player attacking opponent hero
        if (getCardsWithEnergy(player.getBoard().getPlayArea(player.getPlayerNumber()).getCardsInArea()).size() > 0) {
            if (player.getBoard().getPlayArea(player.getOppositePlayerNumber()).getCardsInArea().isEmpty()) {
                player.attackEnemyHero(getCardWithHighestAttackThatHasEnergy(player.getBoard().getPlayArea(player.getPlayerNumber()).getCardsInArea()));
            } else {
                switch (randomerNumberer) {
                    case 0:
                        player.attackEnemyHero(getCardWithHighestAttackThatHasEnergy(player.getBoard().getPlayArea(player.getPlayerNumber()).getCardsInArea()));
                        noMoreMovesCanBeMade = false;
                        break;

                    default:
                        CharacterCard cardToAttack = getWeakestEnemyCardToAttack();
                        player.attackCard(getCardWithHighestAttackThatHasEnergy(player.getBoard().getPlayArea(player.getPlayerNumber()).getCardsInArea()), cardToAttack);
                        noMoreMovesCanBeMade = false;
                        break;
                }
            }
        }
    }

    private CharacterCard getCardWithHighestAttackThatHasEnergy(ArrayList<CharacterCard> cardsToConsider) {
        return getCardWithHighestAttack(getCardsWithEnergy(cardsToConsider));
    }

    private ArrayList<CharacterCard> getCardsWithEnergy(ArrayList<CharacterCard> cardsToConsider) {
        ArrayList<CharacterCard> cardsWithEnergyToAttack = new ArrayList<>();
        for (CharacterCard card : cardsToConsider)
            if (card.hasEnergyToAttack())
                cardsWithEnergyToAttack.add(card);
        return cardsWithEnergyToAttack;
    }


    private CharacterCard getCardWIthBestRatingThatCanBePlayedFromHand() {
        return getCardWithBestRating(getCardsThatCanBePlayedFromHand());
    }

    private ArrayList<CharacterCard> getCardsThatCanBePlayedFromHand() {
        ArrayList<CharacterCard> cardThatCanBePlayed = new ArrayList<>();
        for (CharacterCard card : player.getHand().getCardsInArea())
            if (player.canCardBePlacedOnPlayArea(card))
                cardThatCanBePlayed.add(card);
        return  cardThatCanBePlayed;
    }

    private CharacterCard getCardWithBestRating(ArrayList<CharacterCard> cards) {
        CharacterCard cardWithBestRating = null;
        float bestRating = 0.f;
        float currentCardRating;
        for (CharacterCard card : cards) {
            currentCardRating = getCardRating(card.getMana(), card.getAttack(), card.getHealth());
            if (cardWithBestRating == null || currentCardRating > bestRating){
                cardWithBestRating = card;
                bestRating = currentCardRating;
            }
        }
        return cardWithBestRating;
    }

    private CharacterCard getWeakestEnemyCardToAttack() {
        return getWeakestCard(player.getBoard().getPlayArea(getEnemyPlayerNumber()).getCardsInArea());
    }

    private CharacterCard getWeakestCard(ArrayList<CharacterCard> cardArrayList) {
        CharacterCard weakestCard = null;
        for (CharacterCard card : cardArrayList)
            if (weakestCard == null || card.getHealth() < weakestCard.getHealth())
                weakestCard = card;
        return weakestCard;
    }

    private CharacterCard getCardWithHighestAttack(ArrayList<CharacterCard> cards) {
        CharacterCard cardWithHighestAttack = null;
        for (CharacterCard card : cards)
            if (cardWithHighestAttack == null || card.getAttack() < cardWithHighestAttack.getAttack())
                cardWithHighestAttack = card;
        return cardWithHighestAttack;
    }

    private ArrayList<CharacterCard> getWeakestCardsForTheJob(CharacterCard enemyCardToKill) {
        //Copy of list of cards in play
        ArrayList<CharacterCard> cardsToSelectToAttackWith = new ArrayList<>(getCardsWithEnergy(player.getBoard().getPlayArea(player.getPlayerNumber()).getCardsInArea()));
        ArrayList<CharacterCard> cardsToAttackWith = new ArrayList<>();
        
        //While the card to kill still has health keep adding the card with the highest attack to the list of cards to attack it
        //Or while the cards to select from still have the potential to kill that card
        while (enemyCardToKill.getHealth() > getTotalAttack(cardsToAttackWith) && getTotalAttack(cardsToSelectToAttackWith) >= enemyCardToKill.getHealth()) {
            CharacterCard currentWeakestCard = getCardWithLowestAttack(cardsToSelectToAttackWith);
            cardsToAttackWith.add(currentWeakestCard);
        }
        return cardsToAttackWith;
    }

    private CharacterCard getCardWithLowestAttack(ArrayList<CharacterCard> cardsToCompare) {
        CharacterCard cardWithHighestAttack = null;
        for (CharacterCard card : cardsToCompare)
            if (cardWithHighestAttack == null || card.getAttack() < cardWithHighestAttack.getAttack())
                cardWithHighestAttack = card;
        return cardWithHighestAttack;
    }

    private int getEnemyPlayerNumber() {
        return (player.getPlayerNumber() + 1) % 2;
    }

    private int getEnemyAttackPotential() {
        return getPlayerAttackPotential(getEnemyPlayerNumber());
    }

    private int getTotalAttack(ArrayList<CharacterCard> cardsToAttack) {
        int totalAttack = 0;
        for (CharacterCard card : cardsToAttack)
            totalAttack += card.getAttack();
        return totalAttack;
    }

    private int getAttackPotential() {
        return getPlayerAttackPotential(player.getPlayerNumber());
    }

    private boolean isEnemyAThreat() {
      return getEnemyAttackPotential() >= player.getHero().getHealth();
    }

    private int getPlayerAttackPotential(int playerNumber) {
        int enemyAttackPotential = 0;
        for (CharacterCard card : player.getBoard().getPlayArea(playerNumber).getCardsInArea())
            enemyAttackPotential += card.getAttack();
        return enemyAttackPotential;
    }
}
