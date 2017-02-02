package test.puigames.courseofhistory.framework.engine.resourceloading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michael on 23/11/2016.
 */

public class JSONBourne {

    public JSONBourne() {

    }

    public JSONArray toJsonArray(String jsonString) throws JSONException {
        return new JSONArray(jsonString);
    }

    public JSONObject toJsonObject(String jsonString) throws JSONException {
        return new JSONObject(jsonString);
    }
}

