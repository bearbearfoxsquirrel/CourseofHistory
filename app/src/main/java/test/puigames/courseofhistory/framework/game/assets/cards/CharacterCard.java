package test.puigames.courseofhistory.framework.game.assets.cards;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.game.assets.players.events.Interactable;

/**
 * Created by Tommy on 23/11/2016.
 */

public class CharacterCard extends Card implements Interactable.Fightable.Damageable.Attackable {
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
        this.attack = attack;
        this.health = health;
        this.abilityDescription = abilityDescription;

    }

    @Override
    public void fight(Fightable target) {

    }

    @Override
    public void attack(Damageable target) {
        target.applyDamage(this.attack);
    }

    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public void applyDamage(int damageToApply) {
        this.health -= damageToApply;
    }

    @Override
    public int getHealth() {
        return this.health;
    }


    //Handles only decrementing of targets health
   /* public void applyAttack(Interactable.Damageable recipientOfThyFatalBlow) {
        recipientOfThyFatalBlow.applyDamage(this.attack);
    }

    //Handles only decrementing of this objects health
    public void applyDamage(int thyOpponentsAttackPoints) {
        this.health -= thyOpponentsAttackPoints;
    }

    @Override
    public void fight(Fightable target) {
    }


    //public void interact(Interactable target) {

   // }*/
}