package test.puigames.courseofhistory.framework.engine.screen.scaling;

import test.puigames.courseofhistory.framework.engine.GameProperties;
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

    public void scaleToScreen(Scalable.ImageScalable scalable) {
        scalable.getMatrix().reset();
        scalable.scale(scaleFactorX, scaleFactorY);

}

    public void scaleToScreen(Scalable scalable) {
        scalable.getMatrix().reset();
        scalable.getMatrix().postScale((scalable.getWidth() / scalable.getWidth()) * scaleFactorX,
                (scalable.getHeight() / scalable.getHeight()) * scaleFactorY);
        scalable.getMatrix().postRotate(0, scaleFactorX * scalable.getWidth()/ 2.0f,
                scaleFactorY * scalable.getHeight() / 2.0f);
        scalable.getMatrix().postTranslate((scalable.getOrigin().x - scalable.getWidth() / 2) * scaleFactorX,
                (scalable.getOrigin().y - scalable.getHeight() / 2) * scaleFactorY);
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
