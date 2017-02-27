package test.puigames.courseofhistory.framework.engine.screen.scaling;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;

/**
 * Created by mjtod on 13/02/2017.
 */

public class Scaler {
    private float screenWidth;
    private float screenHeight;
    private float viewportHeight;
    private float viewportWidth;
    private float scaleFactorX;
    private float scaleFactorY;

    public Scaler(GameProperties gameproperties){
        initialiseScreen(gameproperties);
    }
    private void initialiseScreen(GameProperties gameproperties){
        gameproperties.calculateScreenSize();
        screenWidth = gameproperties.getScreenWidth();
        screenHeight = gameproperties.getScreenHeight();
    }
    public void scaleViewport(float width, float height){
        width = width * this.scaleFactorX;
        height = height * this.scaleFactorY;
    }




    public void setScaleFactor(float viewportWidth, float viewportHeight) {
        float scaleFactorX;
        float scaleFactorY;
        //For calculating scalefactor x
        if (screenWidth > viewportWidth)
            scaleFactorX = screenWidth/viewportWidth;
        else
            scaleFactorX = viewportWidth / screenWidth;
        this.scaleFactorX = scaleFactorX;

        //For calculating scalefactor y
        if(screenHeight > viewportHeight)
            scaleFactorY = screenHeight/ viewportHeight;
        else
            scaleFactorY = viewportHeight/ screenHeight;
        this.scaleFactorY = scaleFactorY;

    }

    public float getScaleFactorX(){
        return scaleFactorX;
    }
    public float getScaleFactorY(){ return scaleFactorY;}


}
