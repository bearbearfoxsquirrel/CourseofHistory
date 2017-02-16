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
    private float scaleFactor;

    public Scaler(GameProperties gameproperties, Viewport viewport){
        initialiseScreen(gameproperties, viewport);
        setScaleFactor(viewport);
    }
    private void initialiseScreen(GameProperties gameproperties, Viewport viewport){
        gameproperties.calculateScreenSize();
        screenWidth = gameproperties.getScreenWidth();
        screenHeight = gameproperties.getScreenHeight();
    }
    public Viewport scaleViewport(Viewport viewport, float scaleFactor){

        viewport.width = viewport.width * scaleFactor;
        viewport.height = viewport.height * scaleFactor;
        return viewport;
    }

    public void scaleToScreen(Matrix matrix){
        matrix.setScale(this.scaleFactor, this.scaleFactor);
    }

    public void setScaleFactor(Viewport viewport) {
        float screenAspect = screenWidth / screenHeight;
        float viewportAspect =  viewport.height/ viewport.width;

        float scaleFactor;
        if (screenAspect > viewportAspect)
            scaleFactor = screenWidth / viewport.height;
        else
            scaleFactor = screenWidth / viewport.width;
        this.scaleFactor = scaleFactor;
    }

    public float getScaleFactor(){
        return scaleFactor;
    }


}
