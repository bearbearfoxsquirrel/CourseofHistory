package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Bitmap;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.controlling.Controlling;
import test.puigames.courseofhistory.framework.engine.screen.Placer;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.CourseOfHistoryMachine;
import test.puigames.courseofhistory.framework.game.assets.CardHand;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.Deck;
import test.puigames.courseofhistory.framework.game.assets.Hero;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.HumanCardGameController;
import test.puigames.courseofhistory.framework.game.screens.MainMenu;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class PvPLevel extends Level {

    public PvPLevel(GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    @Override
    protected void load() {
       try {
           super.load();

           Controlling[] controllers = new HumanCardGameController[CourseOfHistoryMachine.PLAYER_COUNT];
           //Giving the controllers possession of the corresponding player in the game machine
           for (int i = 0; i < controllers.length; i++) {
               //UI is placed at the center of Viewport
               controllers[i] = new HumanCardGameController(this, inputBuddy, gameMachine.getPlayers()[i],
                       resourceFetcher.getBitmapFromFile("images/backgrounds/starting_hand_selection_ui_background.png"),
                       resourceFetcher.getBitmapFromFile("images/buttons/confirmation_button.png"),
                       resourceFetcher.getBitmapFromFile("images/buttons/confirmation_button.png"),
                       resourceFetcher.getBitmapFromFile("images/buttons/end_turn_button.png"), viewport.getCenterX(), viewport.getCenterY() );
           }

           for (Controlling controller : controllers)
               controller.startTicking(this);

       } catch(NullPointerException e) {
//         Log.d("Loading Error:", "Error fetching resources, returning to menu");
           Log.e("ERROR", e.getMessage() + "\n" + e.getCause());
           //TODO do properly
           //Failed loading the gameProperties - won't cause crash if resources set up wrong!
           gameProperties.setScreen(new MainMenu(this.gameProperties));
       }
     }
}
