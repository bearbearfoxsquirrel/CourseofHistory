package test.puigames.courseofhistory.framework.engine.gameloop;

import android.graphics.Canvas;
import android.view.View;

import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;

/**
 * Created by Jordan on 08/11/2016.
 */

public class FastRenderView extends View {


    MainGame game;
    volatile boolean running = false;

    float deltaTime;
    float startTime;

    AndroidInput input;
    InputBuddy inputBuddy;

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
        game.drawNeeded = true;
    }

    public void pause() {
        running = false;
//        this.pause();
    }
}
