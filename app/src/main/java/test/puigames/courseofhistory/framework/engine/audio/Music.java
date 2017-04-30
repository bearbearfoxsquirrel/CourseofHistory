package test.puigames.courseofhistory.framework.engine.audio;

/**
 * Created by Jordan on 25/10/2016.
 */

public interface Music
{
    void play();

    void stop();

    void pause();

    void setLooping(boolean looping);

    void setVolume(float volume);

    boolean isPlaying();

    boolean isStopped();

    boolean isLooping();

    void dispose();
}
