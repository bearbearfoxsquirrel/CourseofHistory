package test.puigames.courseofhistory.framework.engine.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Drawable;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;

/**
* Created by Christopher on 09/03/2017.
*/

public class MenuButton extends UIElement implements Drawable{

    public MenuButton(Bitmap buttonImage, float width, float height){
        super(buttonImage, width, height);
    }
}
