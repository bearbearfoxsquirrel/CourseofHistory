package test.puigames.courseofhistory.framework.game.assets.cards;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.players.events.Damageable;

/**
 * Created by Tommy on 23/11/2016.
 */

public class CharacterCard extends Card implements Damageable.Attackable {
    //variables
    public String name;
    public String description;
    public int mana;
    public int attack;
    public int health;
    public String abilityDescription;
    public int currentAttackEnergy;
    public int maxAttackEnergy;

    public CharacterCard(Screen screen, Bitmap cardImage, String name, String description, int mana, int attack, int health, String abilityDescription) {
        super(screen, cardImage);
        this.name = name;
        this.description = description;
        this.mana = mana;
        this.attack = attack;
        this.health = health;
        this.abilityDescription = abilityDescription;
        this.currentAttackEnergy = 1;
        this.maxAttackEnergy = 1;
    }

    public void attack(Damageable recipientOfThyFatalBlow) {
        recipientOfThyFatalBlow.applyDamage(this.attack);
        currentAttackEnergy--;
    }

    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public boolean hasEnergyToAttack() {
        return true;
        //TODO implement some way of knowing that an attack can be made or not
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void applyDamage(int damage) {
        this.health -= damage;
    }

    @Override
    public boolean isDeaders() {
       return this.health <= 0;
    }

}