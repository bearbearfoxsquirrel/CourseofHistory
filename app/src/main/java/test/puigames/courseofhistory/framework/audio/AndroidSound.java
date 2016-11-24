package test.puigames.courseofhistory.framework.audio;

import android.media.SoundPool;

/**
 * Created by Jordan on 05/11/2016.
 */

public class AndroidSound implements Sound
{
    int soundId;
    SoundPool soundPool;

    public AndroidSound(SoundPool soundPool, int soundId)
    {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    public void play(float volume)
    {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    public void dispose()
    {
        soundPool.unload(soundId);
    }
}
