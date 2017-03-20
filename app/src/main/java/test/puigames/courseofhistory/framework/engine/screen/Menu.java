package test.puigames.courseofhistory.framework.engine.screen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameloop.MainGame;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.resourceloading.Fetcher;
import test.puigames.courseofhistory.framework.engine.resourceloading.ResourceFetcher;
import test.puigames.courseofhistory.framework.engine.screen.scaling.Viewport;

/**
 * Created by Christopher on 20/02/2017.
 */

public abstract class Menu extends Screen{

    protected InputBuddy inputBuddy;
    protected Fetcher resourceFetcher;
    protected ArrayList<Bitmap> bitmaps;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public Matrix matrix;

    public Menu(GameProperties gameProperties){
        super(gameProperties);

        resourceFetcher = gameProperties.getResourceFetcher();
        matrix = new Matrix();
        this.bitmaps = new ArrayList<>();
    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        inputBuddy = new InputBuddy(input);
        scaler.scaleTouchInput(inputBuddy);

        for(Bitmap bitmap : bitmaps)
            scaler.scaleToScreen(bitmap, matrix);
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        for(Bitmap bitmap : bitmaps)
            canvas.drawBitmap(bitmap, matrix, paint);
    }

    public abstract void load();

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
