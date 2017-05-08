package test.puigames.courseofhistory.framework.game.levels;

import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.controlling.Controlling;
import test.puigames.courseofhistory.framework.game.CourseOfHistoryMachine;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.AICardGameController;
import test.puigames.courseofhistory.framework.game.screens.MainMenu;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Michael on 07/05/2017.
 */

public class CPUvCPULevel extends Level {
    public CPUvCPULevel(GameProperties gameProperties) {
        super(gameProperties);
    }

    @Override
    protected void load() {
        try {
            super.load();

            Controlling[] controllers = new AICardGameController[CourseOfHistoryMachine.PLAYER_COUNT];
            //Giving the controllers possession of the corresponding player in the game machine
            for (int i = 0; i < controllers.length; i++) {
                //UI is placed at the center of Viewport
                controllers[i] = new AICardGameController(this, gameMachine.getPlayers()[i]);
            }

            for (Controlling controller : controllers)
                controller.startTicking(this);

        } catch(NullPointerException e) {
            Log.e("ERROR", e.getMessage() + "\n" + e.getCause());
            //TODO do properly
            //Failed loading the gameProperties - won't cause crash if resources set up wrong!
            gameProperties.setScreen(new MainMenu(this.gameProperties));
        }
    }
}
