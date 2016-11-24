package test.puigames.courseofhistory.framework.game;

import android.util.DisplayMetrics;

import test.puigames.courseofhistory.framework.engine.AndroidGame;
import test.puigames.courseofhistory.framework.engine.Screen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class CourseOfHistory extends AndroidGame
{

    public Screen getStartScreen() {
        return new SplashScreen(this);
    }



}
