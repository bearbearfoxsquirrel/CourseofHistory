package test.puigames.courseofhistory.framework.game.assets;

import java.util.Random;
import java.util.Stack;

import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by mjtod on 06/03/2017.
 */

public class Deck extends Stack {

    public void shuffle(CharacterCard[] characterCards){
        Random random = new Random();
        CharacterCard[] temp = new CharacterCard[characterCards.length];
        for(int i = characterCards.length -1; i >=0; i--){
            int ranNum = random.nextInt(i+1);

            temp[i] = characterCards[i];
            characterCards[i] = characterCards[ranNum];
            characterCards[ranNum] = temp[i];
        }
    }
    public void setUpDeck(CharacterCard[] characterCards){
        shuffle(characterCards);
        for(int i = 0; i < characterCards.length; i++){
            super.push(characterCards[i]);
        }
    }

    @Override
    public synchronized Object pop() {
        return super.pop();
    }
}



