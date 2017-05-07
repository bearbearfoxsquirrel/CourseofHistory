package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Created by Jordan on 11/04/2017.
 */

public class Mana extends Sprite
{
    //Current state of mana
    private ManaState manaState;
    private Bitmap[] manaType; //0 is AVAILABLE, 1 is USED

    public enum ManaState
    {
        AVAILABLE, USED
    }

    //mana state refers to if mana is AVAILABLE to use by the player, or is USED (unavailable)
    public Mana(Screen screen, Bitmap[] manaType, int width, int height)
    {
        super(screen, manaType[1], width, height);
        manaState = ManaState.USED; //initially used
        this.manaType = manaType;
    }

    public void update(float deltaTime)
    {
        super.update(deltaTime);
        switch (manaState) {
            case AVAILABLE:
                setBitmap(manaType[0]);
                break;
            case USED:
                setBitmap(manaType[1]);
        }
    }
    public ManaState getManaState() {
        return manaState;
    }

    public void setManaState(ManaState manaState) {
        this.manaState = manaState;
    }

    public Bitmap[] getManaType() {
        return manaType;
    }

    public void setManaType(Bitmap[] manaType) {
        this.manaType = manaType;
    }
}
