package test.puigames.courseofhistory.framework.game;

import test.puigames.courseofhistory.framework.engine.gameloop.MainGame;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class CourseOfHistory extends MainGame
{

    public Screen getStartScreen() {
        return new SplashScreen(this);
    }



}
