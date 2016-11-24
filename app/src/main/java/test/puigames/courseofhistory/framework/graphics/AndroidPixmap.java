package test.puigames.courseofhistory.framework.graphics;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.graphics.Pixmap;
import test.puigames.courseofhistory.framework.graphics.Graphics.PixmapFormat;

/**
 * Created by Jordan on 08/11/2016.
 */

public class AndroidPixmap implements Pixmap
{
    Bitmap bitmap;
    PixmapFormat format;

    public AndroidPixmap(Bitmap bitmap, PixmapFormat pixmapFormat)
    {
        this.bitmap = bitmap;
        this.format = format;
    }

    public int getWidth()
    {
        return bitmap.getWidth();
    }

    public int getHeight()
    {
        return bitmap.getHeight();
    }

    public PixmapFormat getFormat()
    {
        return format;
    }

    public void dispose()
    {
        bitmap.recycle();
    }

    public Bitmap getBitmap(){
        return bitmap;
    }


}


