package test.puigames.courseofhistory.framework.engine.gameloop;

import android.graphics.Canvas;
import android.view.View;

import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;

/**
 * Created by Jordan on 08/11/2016.
 */

public class FastRenderView extends View {
    private MainGame game;
    private volatile boolean running = false;
    private float deltaTime;
    private float startTime;
    private AndroidInput input;
    private InputBuddy inputBuddy;

    public FastRenderView(MainGame game) {
        super(game);
        this.game = game;
        input = new AndroidInput(game, this, this.getScaleX(), this.getScaleY());
        inputBuddy = new InputBuddy(input);
        startTime = System.nanoTime();
    }

    public void resume() {
        running = true;
    }

    //Called when the loops calls postInvalidate()
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //convert delta time from ns to s
        deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
        startTime = System.nanoTime();

        inputBuddy.update();
        //Calls the update and draw methods of the current screen that is active
        game.getCurrentScreen().update(deltaTime);
        game.getCurrentScreen().draw(canvas, deltaTime);

        //Tells the loop that another draw is now needed
        game.setDrawNeeded(true);
    }

    public void pause() {
        running = false;
    }

    public MainGame getGame() {
        return game;
    }

    public void setGame(MainGame game) {
        this.game = game;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public AndroidInput getInput() {
        return input;
    }

    public void setInput(AndroidInput input) {
        this.input = input;
    }

    public InputBuddy getInputBuddy() {
        return inputBuddy;
    }

    public void setInputBuddy(InputBuddy inputBuddy) {
        this.inputBuddy = inputBuddy;
    }
}
