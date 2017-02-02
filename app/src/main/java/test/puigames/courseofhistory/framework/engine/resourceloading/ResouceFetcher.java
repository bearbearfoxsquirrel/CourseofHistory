package test.puigames.courseofhistory.framework.engine.resourceloading;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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


    public InputStream getJSONInputStream(String url) throws IOException {
        return androidFileIO.readFile(url);
    }

    public String getJsonString(InputStream jsonStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(jsonStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        jsonStream.close();
        return stringBuilder.toString();
    }


    public JSONArray getArrayFromJSONFile(String url) {
        String jsonString = "";
        JSONArray jsonArray;

        try {
            jsonString = getJsonString(getJSONInputStream(url));
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
