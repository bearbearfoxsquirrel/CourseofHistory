package test.puigames.courseofhistory.framework.engine.screen;

import android.graphics.Canvas;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Updateable;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.resourceloading.ResourceFetcher;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Scalable;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Scaler;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Viewport;

/**
 * Created by Jordan on 25/10/2016.
 */

public abstract class Screen {
    protected InputBuddy inputBuddy;
    protected final GameProperties gameProperties;
    protected final ResourceFetcher resourceFetcher;
    protected Scaler scaler;
    protected Viewport viewport;
    public ArrayList<Drawable> drawables;
    public ArrayList<Updateable> updateables;
    public ArrayList<Scalable> scalables;
    public ArrayList<Scalable.ImageScalable> imageScalables;

    final static float DEFAULT_LEVEL_HEIGHT = 320.f;
    final static float DEFAULT_LEVEL_WIDTH = 480.f;

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
        this.resourceFetcher = this.gameProperties.getResourceFetcher();
        this.inputBuddy = gameProperties.getInput();
        this.viewport = new Viewport(DEFAULT_LEVEL_WIDTH, DEFAULT_LEVEL_HEIGHT);
        this.scaler = new Scaler(gameProperties, viewport);
        this.drawables = new ArrayList<>();
        this.updateables = new ArrayList<>();
        this.scalables = new ArrayList<>();
        this.imageScalables = new ArrayList<>();
    }

    public abstract void load();

    public void update(float deltaTime) {
        scaler.scaleTouchInput(gameProperties.getInput());
        for (Updateable updateable : updateables)
            updateable.update(deltaTime);
    }

    public void draw(Canvas canvas, float deltaTime) {
        for (Scalable scalable : scalables)
            scaler.scaleToScreen(scalable);

        for (Scalable.ImageScalable imageScalable : imageScalables)
            scaler.scaleToScreen(imageScalable);

        for (Drawable drawable : drawables)
            drawable.draw(canvas, deltaTime);
    }

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
}
