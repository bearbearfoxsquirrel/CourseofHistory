package test.puigames.courseofhistory.framework.engine.resourceloading;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import test.puigames.courseofhistory.framework.game.Board.Board;
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

    @Override
    public Board loadBoard(String boardName) throws NullPointerException{
        String boardUrl = "boards.json";
        String jsonString = null;

        try {
            jsonString = getJSONString(boardUrl);
        } catch (IOException e) {
            Log.d("Loading Resource: ", "Error loading resource from" +  boardUrl);
        }

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONObject(jsonString).getJSONArray("boards");
        } catch (JSONException e) {
            Log.d("Loading Resource: ", "Error converting from String to JSONArray in " + boardUrl);
        }

        Board board = null;
        int index = 0;
        try {
            while (index < jsonArray.length()) {
                JSONObject jsonBoard = jsonArray.getJSONObject(index);
                if (jsonBoard.getString("boardName").equals(boardName)) {
                    board = new Board(getBitmapFromFile(jsonBoard.getString("url")));
                }
                index++;
            }
        } catch (JSONException e) {
            Log.d("Loading Resource: ", "Cannot find board of name: " + index);
        }
        return board;
    }

    //For now just returns all cards
    //TODO: loadCharacterCards will be used to load a specified set of cards e.g. decks and all cards
    @Override
    public CharacterCard[] loadCharacterCards() {
        String characterCardsUrl = "cardtests.json";
        String jsonString = null;

        try {
            jsonString = getJSONString(characterCardsUrl);
        } catch (IOException e) {
            Log.d("Loading Resource: ", "Error loading resource from" + characterCardsUrl);
        }

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONObject(jsonString).getJSONArray("cards");
        } catch (JSONException e) {
            Log.d("Loading Resource: ", "Error converting from String to JSONArray");
        }

        CharacterCard[] characterCards = new  CharacterCard[jsonArray.length()];
        int index = 0;
        try {
            while (index < jsonArray.length()) {
                JSONObject jsonCard = jsonArray.getJSONObject(index);
                characterCards[index] =  new CharacterCard(getBitmapFromFile(jsonCard.getString("portraitSrc")), (float) Math.random() * 1000, (float) Math.random() * 1000,
                        jsonCard.getString("name"),
                        jsonCard.getString("description"),
                        jsonCard.getInt("mana"),
                        jsonCard.getInt("attack"),
                        jsonCard.getInt("health"),
                        jsonCard.getString("abilityDescription"));
                index++;
            }
            //creating a new character card with attributes from JSONObject
            // an arbitrary spawn point is also set until we decide how to spawn cards
        } catch (JSONException e) {
            Log.d("Loading Resource: ",  "Cannot find card of ID: " + index);
        }
        return characterCards;
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
