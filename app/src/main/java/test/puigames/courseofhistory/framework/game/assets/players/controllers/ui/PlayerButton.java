package test.puigames.courseofhistory.framework.game.assets.players.controllers.ui;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.engine.ui.MenuButton;
import test.puigames.courseofhistory.framework.game.assets.players.Player;

/**
 * Created by Michael on 27/04/2017.
 */

public abstract class PlayerButton extends MenuButton {
    Player player;

    public PlayerButton(Screen screen, Player player, Bitmap buttonImage, float width, float height) {
        super(screen, buttonImage, width, height);
        this.player = player;
    }

    @Override //Overridden because fancy for loop isn't liked by game
    public boolean checkForInput(InputBuddy inputBuddy){
        for (int i = 0; i < inputBuddy.getTouchEvents().size(); i++)
            if (boundingBox.isTouchOn(inputBuddy.getTouchEvents().get(i)) && inputBuddy.getTouchEvents().get(i).type == Input.TouchEvent.TOUCH_UP)
                return true;
        return false;
    }
}
