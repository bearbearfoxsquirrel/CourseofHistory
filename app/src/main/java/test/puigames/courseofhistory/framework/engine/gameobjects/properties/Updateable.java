package test.puigames.courseofhistory.framework.engine.gameobjects.properties;

import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Created by Michael on 20/04/2017.
 */

public interface Updateable {
    void startTicking(Screen screen);

    void stopTicking(Screen screen);

    void update(float deltaTime);
}
