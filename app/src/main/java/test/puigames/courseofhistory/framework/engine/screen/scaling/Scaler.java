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
    public void scaleViewport(Viewport viewport){
        viewport = new Viewport(viewport.width * this.scaleFactor, viewport.height * this.scaleFactor);
    }

    public void scaleToScreen(Matrix matrix){
        matrix.setScale(this.scaleFactor, this.scaleFactor);
    }

    public void setScaleFactor(Viewport viewport) {
        float scaleFactor;
        if (screenWidth > viewport.width)
            scaleFactor = screenWidth/viewport.width;
        else
            scaleFactor = viewport.width / screenWidth;
        this.scaleFactor = scaleFactor;
    }

    public float getScaleFactor(){
        return scaleFactor;
    }


}
