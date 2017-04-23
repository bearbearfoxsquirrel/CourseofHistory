package test.puigames.courseofhistory.framework.game.assets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 19/04/2017.
 */

public class StartingHandSelector {
    public Set<CharacterCard> cardsToKeep;
    public Set<CharacterCard> cardsToToss;

    public StartingHandSelector(CharacterCard[] cards){
        cardsToKeep = new HashSet<>();
        cardsToToss = new HashSet<>();

        cardsToKeep.addAll(Arrays.asList(cards));
    }
}
