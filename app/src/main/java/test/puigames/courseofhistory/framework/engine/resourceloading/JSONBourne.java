package test.puigames.courseofhistory.framework.engine.resourceloading;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michael on 23/11/2016.
 */

public class JSONBourne {
    //Class used to parse, serialise and deserialise JSON
    public JSONBourne() {

    }

    //add reverse of these methods

    //create serialise an object to json
    //take an array and make it json

    public JSONArray toJsonArray(String jsonString) throws JSONException {
        //converts string to json array
        return new JSONArray(jsonString);
    }

    public JSONObject toJsonObject(String jsonString) throws JSONException {
        //converts string to json object
        return new JSONObject(jsonString);
    }

    public JSONArray fromJSONStringToJsonArray(String jsonString, String arrayName) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONObject(jsonString).getJSONArray("boards");
        } catch (JSONException e) {
            Log.d("Loading Resource: ", "Error converting from String to JSONArray in " + arrayName);
        }
        return jsonArray;
    }
}

