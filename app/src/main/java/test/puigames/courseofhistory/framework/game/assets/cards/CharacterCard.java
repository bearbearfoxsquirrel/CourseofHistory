package test.puigames.courseofhistory.framework.game.assets.cards;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.StatImage;
import test.puigames.courseofhistory.framework.game.assets.players.events.Damageable;
import test.puigames.courseofhistory.framework.engine.screen.Placer;

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

    private Placer perryPlacablePlacer;

    private float[] statLocationsX = {-25, 25, -25};
    private float[] statLocationsY = {-35, 35, 35};

    private StatImage [] statImages;


    public CharacterCard(Screen screen, StatImage[] statImages, Bitmap cardImage, String name, String description, int mana, int attack, int health, String abilityDescription) {
        super(screen, cardImage);
        this.name = name;
        this.description = description;

        this.perryPlacablePlacer = new Placer(screen);

        this.mana = mana;
        this.attack = attack;
        this.health = health;
        this.abilityDescription = abilityDescription;
        this.currentAttackEnergy = 1;
        this.maxAttackEnergy = 1;

        this.statImages = statImages;

     //   for(int i = 0; i < statImages.length; i++)
        //    this.statImages[i] = statImages[i];

        CheckStat(mana, this.statImages[0]);
        CheckStat(health, this.statImages[1]);
        CheckStat(attack, this.statImages[2]);
    }

    public void updateCardStats(){
        CheckStat(mana, this.statImages[0]);
        CheckStat(health, this.statImages[1]);
        CheckStat(attack, this.statImages[2]);

        for(int i = 0; i < statImages.length; i++){
            perryPlacablePlacer.placePlaceableRelativeToAnchorPoint(statImages[i], getPosX(), getPosY(), statLocationsX[i], statLocationsY[i], this.rotation, this.rotation);
            //    statImages[i].updateStats();
        //    statImages[i].setRotation(rotation);
         //   statImages[i].getOrigin().setOrigin(getPosX() + statLocationsX[i], getPosY() + statLocationsY[i]);
        }
    }

    public void CheckStat(int statToCheck, StatImage statImage){
        switch(statToCheck) {
            case 0:
                statImage.setState(StatImage.Number.ZERO);
                break;
            case 1:
                statImage.setState(StatImage.Number.ONE);
                break;
            case 2:
                statImage.setState(StatImage.Number.TWO);
                break;
            case 3:
                statImage.setState(StatImage.Number.THREE);
                break;
            case 4:
                statImage.setState(StatImage.Number.FOUR);
                break;
            case 5:
                statImage.setState(StatImage.Number.FIVE);
                break;
            case 6:
                statImage.setState(StatImage.Number.SIX);
                break;
            case 7:
                statImage.setState(StatImage.Number.SEVEN);
                break;
            case 8:
                statImage.setState(StatImage.Number.EIGHT);
                break;
            case 9:
                statImage.setState(StatImage.Number.NINE);
                break;
            default:
                statImage.setState(StatImage.Number.UNASSIGNED);
                break;
        }

    }

    @Override
    public void place(Screen screen, float placementX, float placementY, float rotation){
        super.place(screen, placementX, placementY, rotation);
        for(int i = 0; i < statImages.length; i++){
            perryPlacablePlacer.placePlaceableRelativeToAnchorPoint(statImages[i], getPosX(), getPosY(), statLocationsX[i], statLocationsY[i], this.rotation, this.rotation);
        }
    }

    @Override
    public void remove(Screen screen){
        super.remove(screen);
        for (StatImage statImage : statImages)
            statImage.remove(screen);
    }

    @Override
    public void draw(Canvas canvas, float deltaTime){
        super.draw(canvas, deltaTime);
        drawCardStats(canvas, deltaTime);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updateCardStats();
    }


    private void drawCardStats(Canvas canvas, float deltaTime) {
        for (StatImage statImage : statImages)
            statImage.draw(canvas, deltaTime);
        //canvas.drawText(Integer.toString(mana), (getOrigin().getOriginX())+manaLocationX, (getOrigin().getOriginX())+manaLocationY, paint);
        //canvas.drawText(Integer.toString(attack), (getOrigin().getOriginX())+attackLocationX, (getOrigin().getOriginY())+attackLocationY, paint);
        //canvas.drawText(Integer.toString(health), (getOrigin().getOriginX())+healthLocationX, (getOrigin().getOriginY())+healthLocationY, paint);
    }

    public void attack(Damageable recipientOfThyFatalBlow) {
        recipientOfThyFatalBlow.applyDamage(this.attack);
        currentAttackEnergy--;
    }

    @Override
    public void scale(float scaleFactorX, float scaleFactorY) {
        super.scale(scaleFactorX, scaleFactorY);

        for (StatImage statImage : statImages)
            statImage.scale(scaleFactorX, scaleFactorY);
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