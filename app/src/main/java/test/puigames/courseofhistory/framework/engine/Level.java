package test.puigames.courseofhistory.framework.engine;

import android.graphics.Canvas;

import java.util.List;

import test.puigames.courseofhistory.framework.input.AndroidInput;

/**
 * Created by Michael on 24/11/2016.
 */

public abstract class Level extends Screen{
    List<Sprite> sprites;
    protected InputBuddy inputBuddy;

    public Level(Game game) {
        super(game);
    }

   // public void load() {

  //  }



    @Override
    public void update(float deltaTime, AndroidInput input) {
        inputBuddy = new InputBuddy(input);
    }


    @Override
    public void draw(Canvas canvas, float deltaTime)
    {
        for (Sprite sprite: sprites) {
            sprite.draw(canvas, deltaTime);
        }
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {

    }
}
