package test.puigames.courseofhistory.framework.game.assets.cards;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.players.events.Damageable;

/**
 * Created by Tommy on 23/11/2016.
 */

public class CharacterCard extends Card implements Damageable.Attackable {
    //variables
    private String name;
    private String description;
    private int mana;
    private int attack;
    private int health;
    private String abilityDescription;
    private int currentAttackEnergy;
    private int maxAttackEnergy;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float manaLocationX = 5f;
    private float manaLocationY = 5f;
    private float attackLocationX = 5f;
    private float attackLocationY = 60f;
    private float healthLocationX = 40f;
    private float healthLocationY = 60f;


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

        paint.setColor(Color.rgb(255,255, 255));
        paint.setTextSize(12);
    }

    @Override
    public void draw(Canvas canvas, float deltatime){
        super.draw(canvas, deltatime);
        drawCardStats(canvas);
    }


    public void drawCardStats(Canvas canvas) {
        //To update it
        canvas.drawText(Integer.toString(mana), (getOrigin().getOriginX())+manaLocationX, (getOrigin().getOriginX())+manaLocationY, paint);
        canvas.drawText(Integer.toString(attack), (getOrigin().getOriginX())+attackLocationX, (getOrigin().getOriginY())+attackLocationY, paint);
        canvas.drawText(Integer.toString(health), (getOrigin().getOriginX())+healthLocationX, (getOrigin().getOriginY())+healthLocationY, paint);
    }

    public void attack(Damageable recipientOfThyFatalBlow) {
        recipientOfThyFatalBlow.applyDamage(this.attack);
        currentAttackEnergy--;
    }

    @Override
    public void scale(float scaleFactorX, float scaleFactorY) {
        //TODO Matthew pls intement
    }


    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public boolean hasEnergyToAttack() {
        return currentAttackEnergy > 0;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getAbilityDescription() {
        return abilityDescription;
    }

    public void setAbilityDescription(String abilityDescription) {
        this.abilityDescription = abilityDescription;
    }

    public int getCurrentAttackEnergy() {
        return currentAttackEnergy;
    }

    public void setCurrentAttackEnergy(int currentAttackEnergy) {
        this.currentAttackEnergy = currentAttackEnergy;
    }

    public int getMaxAttackEnergy() {
        return maxAttackEnergy;
    }

    public void setMaxAttackEnergy(int maxAttackEnergy) {
        this.maxAttackEnergy = maxAttackEnergy;
    }

    @Override
    public Paint getPaint() {
        return paint;
    }

    @Override
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public float getManaLocationX() {
        return manaLocationX;
    }

    public void setManaLocationX(float manaLocationX) {
        this.manaLocationX = manaLocationX;
    }

    public float getManaLocationY() {
        return manaLocationY;
    }

    public void setManaLocationY(float manaLocationY) {
        this.manaLocationY = manaLocationY;
    }

    public float getAttackLocationX() {
        return attackLocationX;
    }

    public void setAttackLocationX(float attackLocationX) {
        this.attackLocationX = attackLocationX;
    }

    public float getAttackLocationY() {
        return attackLocationY;
    }

    public void setAttackLocationY(float attackLocationY) {
        this.attackLocationY = attackLocationY;
    }

    public float getHealthLocationX() {
        return healthLocationX;
    }

    public void setHealthLocationX(float healthLocationX) {
        this.healthLocationX = healthLocationX;
    }

    public float getHealthLocationY() {
        return healthLocationY;
    }

    public void setHealthLocationY(float healthLocationY) {
        this.healthLocationY = healthLocationY;
    }




}