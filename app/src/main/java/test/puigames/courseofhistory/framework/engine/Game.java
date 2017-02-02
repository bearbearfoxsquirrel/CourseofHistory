package test.puigames.courseofhistory.framework.engine;



import test.puigames.courseofhistory.framework.FileIO;
import test.puigames.courseofhistory.framework.audio.Audio;
import test.puigames.courseofhistory.framework.graphics.Graphics;
import test.puigames.courseofhistory.framework.engine.resourceloading.GraphicsIO;
import test.puigames.courseofhistory.framework.input.Input;

/**
 * Created by Jordan on 25/10/2016.
 */

public interface Game
{

    public Input getInput();

    public Graphics getGraphics();

    public GraphicsIO getGraphicsIO();

    public Audio getAudio();

    public FileIO getFileIO();

    public void setScreen(Screen screen) throws IllegalArgumentException;

    public Screen getCurrentScreen();

    public Screen getStartScreen();

    public void calculateScreenSize();

    public int getScreenWidth();

    public int getScreenHeight();
}
