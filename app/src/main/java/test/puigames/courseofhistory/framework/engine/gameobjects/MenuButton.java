package test.puigames.courseofhistory.framework.engine.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;

/**
* Created by Christopher on 09/03/2017.
*/

public class MenuButton extends GameObject implements Drawable {

    Bitmap buttonImage;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MenuButton(Bitmap buttonImage, Paint paint){
        super(0, 0);
        this.buttonImage = buttonImage;
        this.boundingBox = new BoundingBox(width, height, origin);
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(buttonImage, matrix, paint);
    }

    public Bitmap getImage(){
        return buttonImage;
    }

    public Origin getOrigin(){
        return origin;
    }
}
