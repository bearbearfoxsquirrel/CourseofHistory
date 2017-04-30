package test.puigames.courseofhistory.framework.game.assets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 19/04/2017.
 */

public class StartingHandSelector {
    private Set<CharacterCard> cardsToKeep;
    private Set<CharacterCard> cardsToToss;

    public StartingHandSelector(CharacterCard[] cards){
        cardsToKeep = new HashSet<>();
        cardsToToss = new HashSet<>();

        cardsToKeep.addAll(Arrays.asList(cards));
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
