package test.puigames.courseofhistory.framework.engine.screen.scaling;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import test.puigames.courseofhistory.framework.engine.GameProperties;

/**
 * Created by mjtod on 13/02/2017.
 */

public class Scaler {
    private float screenWidth;
    private float screenHeight;
    private float scaleFactorX;
    private float scaleFactorY;

    public Scaler(GameProperties gameproperties, Viewport viewport){
        initialiseScreen(gameproperties, viewport);
        setScaleFactor(viewport);
    }
    private void initialiseScreen(GameProperties gameproperties, Viewport viewport){
        gameproperties.calculateScreenSize();
        screenWidth = gameproperties.getScreenWidth();
        screenHeight = gameproperties.getScreenHeight();
    }
    public void scaleViewport(Viewport viewport){
        viewport = new Viewport(viewport.width * this.scaleFactorX, viewport.height * this.scaleFactorY);
    }

    public void scaleToScreen(Matrix matrix){
        matrix.setScale(this.scaleFactorX, this.scaleFactorY);
    }

    public void setScaleFactor(Viewport viewport) {
        float scaleFactorX;
        float scaleFactorY;
        //For calculating scalefactor x
        if (screenWidth > viewport.height)
            scaleFactorX = screenWidth/viewport.height;
        else
            scaleFactorX = viewport.height / screenWidth;
        this.scaleFactorX = scaleFactorX;

        //For calculating scalefactor y
        if(screenHeight > viewport.width)
            scaleFactorY = screenHeight/ viewport.width;
        else
            scaleFactorY = viewport.width/ screenHeight;
        this.scaleFactorY = scaleFactorY;

    }

    public float getScaleFactorX(){
        return scaleFactorX;
    }
    public float getScaleFactorY(){ return scaleFactorY;}


}
