package test.puigames.courseofhistory.framework.game.assets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 19/04/2017.
 */

public class StartingHandSelector {
    public static final int STARTING_HAND_SIZE = 3;

    private Set<CharacterCard> cardsToKeep;
    private Set<CharacterCard> cardsToToss;

    public StartingHandSelector(CharacterCard[] cards){
        cardsToKeep = new HashSet<>();
        cardsToToss = new HashSet<>();

        cardsToKeep.addAll(Arrays.asList(cards)); //Initially all cards are kept
    }
    public void selectCardToToss(CharacterCard cardToBeRemoved) {
        cardsToToss.add(cardToBeRemoved);
        cardsToKeep.remove(cardToBeRemoved);
    }

    public void deselectCardToToss(CharacterCard cardToBeKept) {
        cardsToKeep.add(cardToBeKept);
        cardsToToss.remove(cardToBeKept);
    }

    public Set<CharacterCard> getCardsToKeep() {
        return cardsToKeep;
    }

    public void setCardsToKeep(Set<CharacterCard> cardsToKeep) {
        this.cardsToKeep = cardsToKeep;
    }

    public Set<CharacterCard> getCardsToToss() {
        return cardsToToss;
    }

    public void setCardsToToss(Set<CharacterCard> cardsToToss) {
        this.cardsToToss = cardsToToss;
    }
}
