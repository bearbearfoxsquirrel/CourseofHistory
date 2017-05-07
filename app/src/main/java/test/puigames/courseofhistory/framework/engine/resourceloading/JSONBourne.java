package test.puigames.courseofhistory.framework.engine.resourceloading;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michael on 23/11/2016.
 */

public class JSONBourne {
    //Class USED to parse, serialise and deserialise JSON
    public JSONBourne() {
    }


    public JSONObject searchJSONArray(JSONArray array, String searchField,String searchValue) throws JSONException {
        //Takes an array and is given a field to search and a value to find the match of
        int first = 0;
        int last = array.length();
        JSONObject jsonObject;
        do {
            int middle = (first + last) / 2;
            jsonObject = array.getJSONObject(middle);
            if (jsonObject.getString(searchField).equalsIgnoreCase(searchValue))
                return jsonObject;
            else if (jsonObject.getString(searchField).compareToIgnoreCase(searchValue) < 0)
                last = middle--;
            else
                first = middle++;
        } while (first <= last);
        return jsonObject;
    }

    public JSONArray fromJSONStringToJsonArray(String jsonString, String arrayName) {
        //Takes in an array in the form of a string along with the name of the array's name
        //And returns a JSON array object containing that array
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONObject(jsonString).getJSONArray(arrayName);
        } catch (JSONException e) {
            Log.d("Loading Resource: ", "Error converting from String to JSONArray in " + arrayName);
        }
        return jsonArray;
    }
}

