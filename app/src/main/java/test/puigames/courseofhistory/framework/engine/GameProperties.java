package test.puigames.courseofhistory.framework.engine;



import android.media.MediaPlayer;

import test.puigames.courseofhistory.framework.engine.audio.Audio;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.resourceloading.ResourceFetcher;
import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Created by Jordan on 25/10/2016.
 */

public interface GameProperties {
    InputBuddy getInput();

    ResourceFetcher getResourceFetcher();

    Audio getAudio();

    void setScreen(Screen screen) throws IllegalArgumentException;

    Screen getCurrentScreen();

    Screen getStartScreen();

    void calculateScreenSize();

    int getScreenWidth();

    int getScreenHeight();

    MediaPlayer getMediaPlayer();

    void setMediaPlayer(MediaPlayer mediaPlayer);
}
