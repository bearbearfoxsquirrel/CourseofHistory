package test.puigames.courseofhistory.framework.engine.audio;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

/**
 * Created by Jordan on 05/11/2016.
 */

public class AndroidAudio implements Audio
{
    private AssetManager assets;
    private SoundPool soundPool;

    public AndroidAudio(Activity activity)
    {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.assets = activity.getAssets();
        this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    }

    public Music newMusic(String filename)
    {
        try
        {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            return new AndroidMusic(assetDescriptor);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Couldn't load music '" + filename + "");
        }
    }

    public Sound newSound(String filename)
    {
        try
        {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            int soundId = soundPool.load(assetDescriptor, 0);
            return new AndroidSound(soundPool, soundId);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Couldn't load sound '" + filename + "'");
        }
    }

    public AssetManager getAssets() {
        return assets;
    }

    public void setAssets(AssetManager assets) {
        this.assets = assets;
    }

    public SoundPool getSoundPool() {
        return soundPool;
    }

    public void setSoundPool(SoundPool soundPool) {
        this.soundPool = soundPool;
    }
}
