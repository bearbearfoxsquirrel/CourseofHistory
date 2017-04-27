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
    public ManaState manaState;
    public Bitmap[] manaType; //0 is available, 1 is used

    public enum ManaState
    {
        available, used
    }

    //mana state refers to if mana is available to use by the player, or is used (unavailable)
    public Mana(Screen screen, Bitmap[] manaType)
    {
        //width=10, height=15
        super(screen, manaType[0], 10, 15);
        manaState = ManaState.available;
        this.manaType = manaType;
    }
}
