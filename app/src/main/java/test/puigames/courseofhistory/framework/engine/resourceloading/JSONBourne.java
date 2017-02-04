package test.puigames.courseofhistory.framework.engine.resourceloading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    public String getJsonString(InputStream jsonStream) throws IOException {
        //From a input stream a string in json format will be returned
        BufferedReader reader = new BufferedReader(new InputStreamReader(jsonStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        jsonStream.close();
        return stringBuilder.toString();
    }
}

