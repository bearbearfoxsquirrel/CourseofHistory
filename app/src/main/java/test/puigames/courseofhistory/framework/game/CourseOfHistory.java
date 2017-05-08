package test.puigames.courseofhistory.framework.game;

import test.puigames.courseofhistory.framework.engine.gameloop.MainGame;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.levels.CPUvCPULevel;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class CourseOfHistory extends MainGame
{
    /**
     * If you need things to work and other screens are crashing
     * change this to return new [screenName](this)
     */
    public Screen getStartScreen() {
        return new SplashScreen(this);
    }
}
