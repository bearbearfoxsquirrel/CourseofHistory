package test.puigames.courseofhistory.framework.engine.resourceloading;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Michael on 01/02/2017.
 */

public class ResouceFetcher {
    AndroidFileIO androidFileIO;
    JSONBourne jsonBourne;

    public ResouceFetcher(Context context) {
        this.androidFileIO = new AndroidFileIO(context);
        this.jsonBourne = new JSONBourne();
    }

    public InputStream getInputStream(String url) throws IOException {
        return androidFileIO.readFile(url);
    }

    public JSONArray getArrayFromJSONFile(String url) {
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
        return null;
    }
}
