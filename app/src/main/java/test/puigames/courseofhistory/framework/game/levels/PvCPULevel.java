package test.puigames.courseofhistory.framework.game.levels;

import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.controlling.Controlling;
import test.puigames.courseofhistory.framework.game.CourseOfHistoryMachine;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.AICardGameController;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.HumanCardGameController;
import test.puigames.courseofhistory.framework.game.screens.MainMenu;

/**
 * Created by Jordan on 08/05/2017.
 */

public class PvCPULevel extends Level
{
    public PvCPULevel(GameProperties gameProperties)
    {
        super(gameProperties);
    }

    @Override
    protected void load()
    {
        try
        {
            super.load();

            Controlling[] controllers = new Controlling[CourseOfHistoryMachine.PLAYER_COUNT];

            for (int i = 0; i < controllers.length; i++)
            {
                //UI is placed at the center of Viewport
                if(i == 0)
                    controllers[i] = new HumanCardGameController(this, inputBuddy, gameMachine.getPlayers()[i],
                            resourceFetcher.getBitmapFromFile("images/backgrounds/starting_hand_selection_ui_background.png"),
                            resourceFetcher.getBitmapFromFile("images/buttons/button_confirm.png"),
                            resourceFetcher.getBitmapFromFile("images/buttons/button_confirm.png"),
                            resourceFetcher.getBitmapFromFile("images/buttons/button_end-turn.png"), viewport.getCenterX(), viewport.getCenterY() );
                else
                    controllers[i] = new AICardGameController(this, gameMachine.getPlayers()[i]);
            }

            for (Controlling controller : controllers)
                controller.startTicking(this);
        }
        catch(NullPointerException e)
        {
            Log.e("ERROR", e.getMessage() + "\n" + e.getCause());
            //TODO do properly
            //Failed loading the gameProperties - won't cause crash if resources set up wrong!
            gameProperties.setScreen(new MainMenu(this.gameProperties));
        }

    }
}
