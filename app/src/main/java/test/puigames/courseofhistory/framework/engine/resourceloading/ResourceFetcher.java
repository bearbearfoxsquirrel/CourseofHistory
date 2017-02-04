package test.puigames.courseofhistory.framework.engine.resourceloading;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Michael on 01/02/2017.
 */

public class ResourceFetcher implements FetchingIO {
    AndroidFileIO androidFileIO;
    GraphicsIO graphicsIO;
    JSONBourne jsonBourne;

    public ResourceFetcher(Context context) {
        this.androidFileIO = new AndroidFileIO(context);
        this.jsonBourne = new JSONBourne();
        this.graphicsIO = new GraphicsIO(context);
    }

    private InputStream getInputStream(String url) throws IOException {
        return androidFileIO.readFile(url);
    }

    @Override
    public JSONArray getJSONArrayFromJSONFile(String url) throws NullPointerException{
        String jsonString = "";
        JSONArray jsonArray;

        try {
            jsonString =jsonBourne.getJsonString(getInputStream(url));
        } catch (IOException e) {
            Log.d("Loading Resource: ", "Error loading resource " + url);
        }

        try {
            jsonArray = jsonBourne.toJsonArray(jsonString);
            return jsonArray;
        } catch (JSONException e){
            Log.d("Loading Resource: ",  "Error Processing JSON at " + url);
        }
        throw new NullPointerException();
    }

    @Override
    public Bitmap getBitmapFromFile(String url) throws NullPointerException{
        try {
            return graphicsIO.loadBitmap(url, Bitmap.Config.ARGB_4444);
        } catch (IOException e) {
            Log.d("Loading Resource: ",  "Error Processing Image at " + url);
        }
        throw  new NullPointerException();
    }
}
