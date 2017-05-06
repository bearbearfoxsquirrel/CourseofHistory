package test.puigames.courseofhistory.framework.engine.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Created by Michael on 04/05/2017.
 */

public class TextUIElement extends UIElement {
    private String text;
    private int textSize;
    private float scaledPosX;
    private float scaledPosY;

    public TextUIElement(Screen screen, String text, int textSize) {
        super(screen, null, textSize, textSize);
        this.text = text;
        this.textSize = textSize;
        this.paint = new TextPaint(textSize);
        this.paint.setColor(Color.BLACK);
        this.paint.setUnderlineText(false);
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paint.setShadowLayer(4 , 1, 1, Color.GRAY);
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        canvas.save();
        canvas.rotate(rotation);
        canvas.drawText(text, scaledPosX, scaledPosY, paint);
        canvas.restore();
    }

    @Override
    public void scale(float scaleFactorX, float scaleFactorY){
        float averageScaleFactor = scaleFactorX + scaleFactorY / 2;
        this.paint.setTextSize(textSize * averageScaleFactor);
        this.scaledPosX = getPosX() * scaleFactorX;
        this.scaledPosY = getPosY() * scaleFactorY;
    }

}
