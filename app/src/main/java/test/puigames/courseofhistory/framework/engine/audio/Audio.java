package test.puigames.courseofhistory.framework.engine.audio;

/**
 * Created by Jordan on 25/10/2016.
 */

public interface Audio
{
    Music newMusic(String fileName);

    Sound newSound(String fileName);
}
