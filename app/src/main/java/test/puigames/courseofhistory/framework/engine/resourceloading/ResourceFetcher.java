package test.puigames.courseofhistory.framework.engine.resourceloading;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 01/02/2017.
 */

public class ResourceFetcher implements Fetcher {
    AndroidFileIO androidFileIO;
    GraphicsIO graphicsIO;
    JSONBourne jsonBourne;

    final static String BOARDS_URL = "json_files/boards.json";
    final static String CARDS_URL = "json_files/characterCard.json";
    final static String BOARDS_ARRAY_NAME = "boards";


    public ResourceFetcher(Context context) {
        this.androidFileIO = new AndroidFileIO(context);
        this.jsonBourne = new JSONBourne();
        this.graphicsIO = new GraphicsIO(context);
    }

    @Override
    public Board loadBoard(String boardName) throws NullPointerException {
        //Takes a boards name as an input and returns the corresponding board object.
        //If a board is not found a null pointer is returned
        JSONArray boardJsonArray = jsonBourne.fromJSONStringToJsonArray(getStringFromFile(BOARDS_URL), BOARDS_ARRAY_NAME);

        //Assume that JSON file is sorted!!!!
        Board board = null;
        try {
            JSONObject jsonBoard = jsonBourne.searchJSONArray(boardJsonArray, "boardName", boardName);
            board = new Board(getBitmapFromFile(jsonBoard.getString("url"))); //where board is created
        } catch (JSONException e) {
            Log.d("Loading Resource: ", "Cannot find board of name: " + boardName);
            throw new NullPointerException();
        }
        return board;
    }

    //For now just returns all cards
    //TODO: loadCharacterCards will be used to load a specified set of cards e.g. decks and all cards
    @Override
    public CharacterCard[] loadCharacterCards(String cardsNames) throws NullPointerException{
        JSONArray cardJsonArray = jsonBourne.fromJSONStringToJsonArray(getStringFromFile(CARDS_URL), cardsNames);

        CharacterCard[] characterCards = new CharacterCard[cardJsonArray.length()];
        int index = 0;
        try {
            while (index < cardJsonArray.length()) {
                JSONObject jsonCard = cardJsonArray.getJSONObject(index);
                //creating a new character card with attributes from JSONObject
                // an arbitrary spawn point is also set until we decide how to spawn cards
                characterCards[index] = new CharacterCard(getBitmapFromFile(jsonCard.getString("portraitSrc")), (float) Math.random() * 100, (float) Math.random() * 100,
                        jsonCard.getString("name"),
                        jsonCard.getString("charDescription"),
                        jsonCard.getInt("mana"),
                        jsonCard.getInt("health"),
                        jsonCard.getInt("attack"),
                        jsonCard.getString("abilityName"));
                index++;
            }
        } catch (JSONException e) {
            Log.d("Loading Resource: ", "Cannot find card of ID: " + index);
            throw new NullPointerException();
        }
        return characterCards;
    }


    @Override
    public Bitmap getBitmapFromFile(String url) throws NullPointerException {
        //allows loading from a bitmap from a given url using Android's AssetManager
        try {
            return graphicsIO.loadBitmap(url, Bitmap.Config.ARGB_4444);
        } catch (IOException e) {
            Log.d("Loading Resource: ", "Error Processing Image at " + url);
        }
        throw new NullPointerException();
    }

    public String getStringFromFile(String url) {
        //gets a string from a specified file url, handles errors within itself
        String outputString = null;
        try {
            outputString = getStringFromInputStream(getStreamFromFile(url));
        } catch (IOException e) {
            Log.d("Loading Resource: ", "Error loading resource from" + url);
        }
        return outputString;
    }

    public InputStream getStreamFromFile(String url) throws IOException {
        //Used to load an input stream from a given file url
        return androidFileIO.readAsset(url);
    }

    public String getStringFromInputStream(InputStream inputStream) throws IOException {
        //From a input stream a string will be returned
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        inputStream.close();
        return stringBuilder.toString();
    }

}