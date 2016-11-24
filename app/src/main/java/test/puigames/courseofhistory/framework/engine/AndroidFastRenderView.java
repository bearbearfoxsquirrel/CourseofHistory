package test.puigames.courseofhistory.framework.engine;

import android.graphics.Canvas;
import android.view.View;

import test.puigames.courseofhistory.framework.input.AndroidInput;

/**
 * Created by Jordan on 08/11/2016.
 */

public class AndroidFastRenderView extends View {

    AndroidGame game;
    Thread renderThread = null;
    volatile boolean running = false;

    float deltaTime;
    float startTime;

    AndroidInput input;

    public AndroidFastRenderView(AndroidGame game) {
        super(game);
        this.game = game;
        //this.deltaTime = deltaTime;
        input = new AndroidInput(game, this, this.getScaleX(), this.getScaleY());
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


        //Calls the update and draw methods of the current screen that is active
        game.getCurrentScreen().update(deltaTime, input);
        game.getCurrentScreen().draw(canvas, deltaTime);
        //Log.d("Is canvas HA:", canvas.isHardwareAccelerated() + " ");

        //Tells the loop that another draw is now needed
        game.drawNeeded = true;


    }

    public void pause() {
        running = false;
        this.pause();
//        while (true) //replace with !userNotQuit()
//        {
//          /*  try {
//                renderThread.join();
//                return;
//            } catch (InterruptedException e) {
//
//            }*/
//        }
    }
}
