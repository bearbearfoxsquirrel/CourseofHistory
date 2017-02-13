package test.puigames.courseofhistory.framework.engine.audio;

/**
 * Created by Jordan on 25/10/2016.
 */

public interface Audio
{
    public Music newMusic(String fileName);

    public Sound newSound(String fileName);
}
