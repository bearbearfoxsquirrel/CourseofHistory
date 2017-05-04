package test.puigames.courseofhistory.framework.engine.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;

import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Created by Michael on 04/05/2017.
 */

public class TextUIElement extends UIElement {
    private String text;
    private int textSize;

    public TextUIElement(Screen screen, String text, int textSize) {
        super(screen, null, textSize, textSize);
        this.text = text;
        this.textSize = textSize;
        this.paint = new TextPaint(textSize);
        paint.setColor(Color.rgb(0, 0 , 0));
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        canvas.save();
        canvas.rotate(rotation);
        canvas.drawText(text, getPosX(), getPosY(), paint);
        canvas.restore();
    }

    @Override
    public void scale(float scaleFactorX, float scaleFactorY){
        float averageScaleFactor = scaleFactorX + scaleFactorY / 2;
        this.paint.setTextSize(textSize * averageScaleFactor);
    }

}
