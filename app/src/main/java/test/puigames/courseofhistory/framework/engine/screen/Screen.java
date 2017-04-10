package test.puigames.courseofhistory.framework.engine.screen;

import android.graphics.Canvas;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Scaler;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Viewport;

/**
 * Created by Jordan on 25/10/2016.
 */

public abstract class Screen
{
    //protected AndroidInput input;
    protected final GameProperties gameProperties;
    protected Scaler scaler;
    protected Viewport viewport;
    final static float LEVEL_HEIGHT = 320.f;
    final static float LEVEL_WIDTH = 480.f;

    /**
     * Constructor stores passed in GameProperties object
     * instance and stores in a final member available to
     * all subclasses. This way we can achieve two things
     *
     *      1. Access to low-level modules of the GameProperties interface
     *      to play back audio, draw to the screen, get input,
     *      read & write files
     *
     *      2. Can set a new current Screen by GameProperties.setScreen() when
     *      appropriate (eg. transitioning after a button is pressed)
     * @param gameProperties
     */

    public Screen(GameProperties gameProperties) {
        this.gameProperties = gameProperties;
        viewport = new Viewport(LEVEL_WIDTH, LEVEL_HEIGHT);
        scaler = new Scaler(gameProperties, viewport);
    }

    public void update(float deltaTime) {
        scaler.scaleTouchInput(gameProperties.getInput());

    }

    public abstract void draw(Canvas canvas, float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
}
