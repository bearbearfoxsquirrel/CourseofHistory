package test.puigames.courseofhistory.framework.game.assets.cards;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.ViewDebug;

import test.puigames.courseofhistory.framework.game.assets.players.events.Damageable;

/**
 * Created by Tommy on 23/11/2016.
 */

public class CharacterCard extends Card implements Damageable.Attackable {
    //variables
    public String name;
    public String description;
    public int mana;
    //protected Bitmap image;
    public int attack;
    public int health;
    public String abilityDescription;
    public Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public float manaLocationX = 5f;
    public float manaLocationY = 5f;
    public float attackLocationX = 5f;
    public float attackLocationY = 60f;
    public float healthLocationX = 40f;
    public float healthLocationY = 60f;


    public CharacterCard(Bitmap cardImage, String name, String description, int mana, int attack, int health, String abilityDescription) {
        super(cardImage);
        this.name = name;
        this.description = description;
        this.mana = mana;
        this.attack = attack;
        this.health = health;
        this.abilityDescription = abilityDescription;

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
        canvas.drawText(Integer.toString(mana), (getOrigin().x)+manaLocationX, (getOrigin().y)+manaLocationY, paint);
        canvas.drawText(Integer.toString(attack), (getOrigin().x)+attackLocationX, (getOrigin().y)+attackLocationY, paint);
        canvas.drawText(Integer.toString(health), (getOrigin().x)+healthLocationX, (getOrigin().y)+healthLocationY, paint);
    }

    public void attack(Damageable recipientOfThyFatalBlow) {
        recipientOfThyFatalBlow.applyDamage(this.attack);
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