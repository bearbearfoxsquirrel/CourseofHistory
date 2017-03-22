package test.puigames.courseofhistory.framework.game.assets.cards;

import android.graphics.Bitmap;

/**
 * Created by Tommy on 23/11/2016.
 */

public class CharacterCard extends Card {
    //variables
    public String name;
    public String description;
    public int mana;
    //protected Bitmap image;
    public int attack;
    public int health;
    public String abilityDescription;

    public CharacterCard(Bitmap cardImage, String name, String description, int mana, int attack, int health, String abilityDescription) {
        super(cardImage);
        this.name = name;
        this.description = description;
        this.mana = mana;
        this.health = health;
        this.abilityDescription = abilityDescription;

    }

    //This method just allows a cards to attack another card, does not handle death state
    public void attackCard(CharacterCard recipientOfThyFatalBlow) {
        recipientOfThyFatalBlow.health -= this.attack;
        this.health -= recipientOfThyFatalBlow.attack;
    }
}
