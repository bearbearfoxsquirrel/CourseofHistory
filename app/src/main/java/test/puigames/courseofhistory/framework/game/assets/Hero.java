package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Stack;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.screen.Placer;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.players.events.Damageable;

/**
 * Created by Jordan on 28/04/2017.
 */

public class Hero extends Sprite implements  Damageable.Attackable
{
    private final static float HEALTH_STAT_FIRST_CHAR_OFFSET_X = -4.5f;
    private final static float HEALTH_STAT_FIRST_CHAR_OFFSET_Y = 34.f;
    private final static float HEALTH_STAT_FOLLOWING_CHAR_PADDING = 9.f;

    private final static int MAX_HEALTH = 30;
    private final static int YOU_DIED = 0;

    private int currentHealth;
    StatImage[] healthStat = new StatImage[Integer.toString(MAX_HEALTH).length()]; //Each number of the players health

    Placer placer;

    public Hero(Screen screen, Bitmap heroBitmap, Bitmap[] healthStatNumbers, int width, int height)
    {
        super(screen, heroBitmap, width, height);
        this.currentHealth = MAX_HEALTH;
        paint.setColor(Color.rgb(255, 255, 255));
        paint.setTextSize(12);

        for (int i = 0; i < healthStat.length; i++)
            healthStat[i] = new StatImage(screen, healthStatNumbers, 6, 7);
    }

    @Override
    public void place(Screen screen, float placementX, float placementY, float rotation) {
        super.place(screen, placementX, placementY, rotation);
        placer = new Placer(screen, placementX, placementY);

        float currentCharPadding = 0.f;
        for (StatImage healthFigure : healthStat) {
            placer.placePlaceableRelativeToAnchorPoint(healthFigure,
                    HEALTH_STAT_FIRST_CHAR_OFFSET_X + currentCharPadding,
                    HEALTH_STAT_FIRST_CHAR_OFFSET_Y,
                    this.rotation,
                    rotation);
         //  healthFigure.place(screen, placementX + currentCharPadding, placementY, rotation);
            currentCharPadding += HEALTH_STAT_FOLLOWING_CHAR_PADDING;
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Stack<Integer> healthStatDigits = getDigits(currentHealth);

        for (int i = 0; i < healthStat.length; i++) {
            if (!healthStatDigits.isEmpty())
                healthStat[i].setState(healthStat[i].fromIntToStatState(healthStatDigits.pop()));
            else
                healthStat[i].remove(currentScreen);
        }
    }


    private Stack<Integer> getDigits(int numberToGetDigitsOf) {
        Stack<Integer> digits = new Stack<>();
        while (numberToGetDigitsOf > 0) {
            digits.add(numberToGetDigitsOf % 10);
            numberToGetDigitsOf /= 10;
        }
        return digits;
    }

    @Override
    public void scale(float scaleFactorX, float scaleFactorY) {
        super.scale(scaleFactorX, scaleFactorY);
    }

    @Override
    public int getHealth()
    {
        return currentHealth;
    }

    @Override
    public void applyDamage(int damage)
    {
        currentHealth -= damage;
    }

    @Override
    public boolean isDeaders()
    {
        return currentHealth <= YOU_DIED;
    }

    @Override
    public void attack(Damageable recipientOfThyFatalBlow)
    {
        recipientOfThyFatalBlow.applyDamage(0);
    }

    @Override
    public int getAttack()
    {
        return 0;
    }

    @Override
    public boolean hasEnergyToAttack()
    {
        return false;
    }

    public int getCurrentHealth()
    {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth)
    {
        this.currentHealth = currentHealth;
    }
}
