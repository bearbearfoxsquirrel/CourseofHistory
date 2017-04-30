package test.puigames.courseofhistory.framework.engine.resourceloading;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jordan on 22/11/2016.
 */

public class GraphicsIO
{
    private Context context;
    private AssetManager assetManager;
    private String externalStoragePath;

    public GraphicsIO(Context context)
    {
        this.context = context;
        assetManager = context.getAssets();
        externalStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
    }

    public InputStream readAsset(String assetName) throws IOException
    {
        return assetManager.open(assetName);
    }

    public Bitmap loadBitmap(String fileName, Bitmap.Config format) throws IOException
    {
        Options options = new Options();
        options.inPreferredConfig = format;
        InputStream in = null;
        Bitmap bitmap = null;
        try
        {
            in = assetManager.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if(bitmap == null)
            {
                throw new IOException("Couldn't load bitmap '"
                        + fileName + "'");
            }
        }
        catch(IOException e)
        {
            throw new IOException("Couldn't load bitmap '"
            + fileName + "'");
        }
        finally
        {
            if(in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public String getExternalStoragePath() {
        return externalStoragePath;
    }

    public void setExternalStoragePath(String externalStoragePath) {
        this.externalStoragePath = externalStoragePath;
    }

}
