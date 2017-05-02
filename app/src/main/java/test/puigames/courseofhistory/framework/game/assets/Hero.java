package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Scalable;
import test.puigames.courseofhistory.framework.game.assets.players.events.Damageable;

/**
 * Created by Jordan on 28/04/2017.
 */

public class Hero extends Sprite implements Drawable, Scalable.ImageScalable, Damageable.Attackable
{
    private final int MAX_HEALTH = 30;
    private final int YOU_DIED = 0;
    private int currentHealth;
    private float health_offset_x = 14.f;
    private float health_offset_y = 12.f;

    public Hero(Screen screen, Bitmap heroBitmap, int width, int height)
    {
        super(screen, heroBitmap, width, height);
        this.currentHealth = MAX_HEALTH;
        paint.setColor(Color.rgb(255, 255, 255));
        paint.setTextSize(12);
    }

    @Override
    public void place(Screen screen, float placementX, float placementY)
    {
        super.place(screen, placementX, placementY);
    }

    @Override
    public void scale(float scaleFactorX, float scaleFactorY)
    {
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

    @Override
    public void draw(Canvas canvas, float deltaTime)
    {
        super.draw(canvas, deltaTime);
        drawHealth(canvas);
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
    }

    private void drawHealth(Canvas canvas)
    {
        canvas.drawText(Integer.toString(currentHealth), (getOrigin().getOriginX() + health_offset_x), (getOrigin().getOriginY() + health_offset_y), paint);
    }
}
