package test.puigames.courseofhistory.framework.graphics;

import android.graphics.Bitmap;

/**
 * Created by Jordan on 25/10/2016.
 */



public interface Pixmap
{
    public int getWidth();

    public int getHeight();

    public Graphics.PixmapFormat getFormat();

    public void dispose();

    public Bitmap getBitmap();
}
