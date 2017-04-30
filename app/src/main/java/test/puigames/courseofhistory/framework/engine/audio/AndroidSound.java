package test.puigames.courseofhistory.framework.engine.audio;

import android.media.SoundPool;

/**
 * Created by Jordan on 05/11/2016.
 */

public class AndroidSound implements Sound
{
    private int soundId;
    private SoundPool soundPool;

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

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public SoundPool getSoundPool() {
        return soundPool;
    }

    public void setSoundPool(SoundPool soundPool) {
        this.soundPool = soundPool;
    }
}
