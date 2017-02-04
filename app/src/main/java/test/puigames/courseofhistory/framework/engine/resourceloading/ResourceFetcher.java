package test.puigames.courseofhistory.framework.engine.resourceloading;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import test.puigames.courseofhistory.framework.game.cards.CharacterCard;

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

    //gets a usable JSON string from the AndroidFileIO
    public String getJSONString(String url) throws IOException{
            return jsonBourne.getJsonString(androidFileIO.readAsset(url));
    }

   /* @Override
    public JSONArray getJSONArrayFromJSONFile(String url) throws NullPointerException{
        String jsonString = "";
        JSONArray jsonArray;

        try {
            jsonString =jsonBourne.getJsonString(androidFileIO.readAsset(url));
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
*/

    //take a string in and return a single character card
    @Override
    public CharacterCard loadCharacterCard(int cardID, String jsonString) throws NullPointerException {
        JSONObject card;
        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray cards = json.getJSONArray("cards");
            card = cards.getJSONObject(cardID);

            //creating a new character card with attributes from JSONObject
            // an arbitrary spawn point is also set until we decide how to spawn cards
            return new CharacterCard(getBitmapFromFile("blank-card.png"), (float) Math.random() * 1000, (float) Math.random() * 1000,
                    card.getString("name"),
                    card.getString("description"),
                    card.getInt("mana"),
                    card.getInt("attack"),
                    card.getInt("health"),
                    card.getString("abilityDescription"));
        } catch (JSONException e) {
            Log.d("Loading Resource: ",  "Cannot find card of ID: " + cardID);
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
