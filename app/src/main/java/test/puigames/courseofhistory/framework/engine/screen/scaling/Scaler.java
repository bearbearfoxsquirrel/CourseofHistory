package test.puigames.courseofhistory.framework.engine.screen.scaling;

import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.Input;

/**
 * Created by mjtod on 13/02/2017.
 */

public class Scaler {
    private float screenWidth;
    private float screenHeight;
    private float scaleFactorX;
    private float scaleFactorY;
    private Viewport viewport;

    public Scaler(GameProperties gameproperties, Viewport viewport){
        this.viewport = viewport;
        initialiseScreen(gameproperties);
    }
    private void initialiseScreen(GameProperties gameproperties){
        gameproperties.calculateScreenSize();
        screenWidth = gameproperties.getScreenWidth();
        screenHeight = gameproperties.getScreenHeight();
        setScaleFactor(viewport.width, viewport.height);
    }

    public void scaleToScreen(Sprite sprite){
        sprite.matrix.reset();
        sprite.matrix.postScale((sprite.width / sprite.image.getWidth()) * scaleFactorX,  (sprite.height / sprite.image.getHeight()) * scaleFactorY);
        sprite.matrix.postRotate(0, scaleFactorX * sprite.image.getWidth()
               / 2.0f, scaleFactorY * sprite.image.getHeight() / 2.0f);
        sprite.matrix.postTranslate((sprite.origin.x - sprite.width / 2) * scaleFactorX, (sprite.origin.y - sprite.height / 2) * scaleFactorY);
        //Updating of matrix done only here
    }

    public void scaleTouchInput(InputBuddy inputBuddy) {
        for(Input.TouchEvent touchEvent : inputBuddy.touchEvents) {
            touchEvent.x /= scaleFactorX;
            touchEvent.y /= scaleFactorY;
        }
    }

    public void setScaleFactor(float viewportWidth, float viewportHeight)
    {
        float scaleFactorX;
        float scaleFactorY;
        //For calculating scalefactor x
        if (screenWidth > viewportWidth)
            scaleFactorX = screenWidth / viewportWidth;
        else
            scaleFactorX = viewportWidth / screenWidth;
        this.scaleFactorX = scaleFactorX;

        //For calculating scalefactor y
        if (screenHeight > viewportHeight)
            scaleFactorY = screenHeight / viewportHeight;
        else
            scaleFactorY = viewportHeight / screenHeight;
        this.scaleFactorY = scaleFactorY;
    }

    public float getScaleFactorX(){
        return scaleFactorX;
    }
    public float getScaleFactorY(){ return scaleFactorY;}


}
