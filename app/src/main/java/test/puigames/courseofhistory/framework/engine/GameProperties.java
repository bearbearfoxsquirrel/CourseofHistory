package test.puigames.courseofhistory.framework.engine;



import test.puigames.courseofhistory.framework.engine.audio.Audio;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.resourceloading.ResourceFetcher;
import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Created by Jordan on 25/10/2016.
 */

public interface GameProperties {
    public InputBuddy getInput();

    public ResourceFetcher getResourceFetcher();

    public Audio getAudio();

    public void setScreen(Screen screen) throws IllegalArgumentException;

    public Screen getCurrentScreen();

    public Screen getStartScreen();

    public void calculateScreenSize();

    public int getScreenWidth();

    public int getScreenHeight();
}
