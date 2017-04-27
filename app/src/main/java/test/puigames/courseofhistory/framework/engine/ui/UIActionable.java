package test.puigames.courseofhistory.framework.engine.ui;

import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;

/**
 * Created by Michael on 23/04/2017.
 */

public interface UIActionable {
    boolean checkForInput(InputBuddy inputBuddy);

    void applyAction();
}
