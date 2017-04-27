package test.puigames.courseofhistory.framework.engine.screen;

import test.puigames.courseofhistory.framework.engine.GameProperties;

/**
 * Created by Michael on 24/11/2016.
 */

public abstract class Level extends Screen {
    public Level(GameProperties gameProperties) {
        super(gameProperties);
    }

    public abstract void load();

}
