package test.puigames.courseofhistory.framework.engine.gameobjects.properties;

import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Created by Michael on 21/04/2017.
 */

public interface Placeable {
    void place(Screen screen, float placementX, float placementY, float rotation);

    void remove(Screen screen);
}
