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

import test.puigames.courseofhistory.framework.game.Board.Board;
import test.puigames.courseofhistory.framework.game.cards.CharacterCard;

/**
 * Created by Michael on 01/02/2017.
 */

public class ResourceFetcher implements FetchingIO {
    AndroidFileIO androidFileIO;
    GraphicsIO graphicsIO;
    JSONBourne jsonBourne;

    final static String BOARDS_URL = "boards.json";
    final static String CARDS_URL = "cardtests.json";
    final static String CARDS_ARRAY_NAME = "cards";
    final static String BOARDS_ARRAY_NAME = "boards";


    public ResourceFetcher(Context context) {
        this.androidFileIO = new AndroidFileIO(context);
        this.jsonBourne = new JSONBourne();
        this.graphicsIO = new GraphicsIO(context);
    }

    @Override
    public Board loadBoard(String boardName) throws NullPointerException {
        JSONArray boardJsonArray = jsonBourne.fromJSONStringToJsonArray(getStringFromFile(BOARDS_URL), BOARDS_ARRAY_NAME);

        Board board = null;
        try {
            for (int index = 0; index < boardJsonArray.length(); index++) {
                JSONObject jsonBoard = boardJsonArray.getJSONObject(index);
                if (jsonBoard.getString("boardName").equals(boardName)) {
                    board = new Board(getBitmapFromFile(jsonBoard.getString("url")));
                    //if (board == null) throw new NullPointerException();
                    // If a null board is created a null pointer exception is thrown
                }
            }
        } catch (JSONException e) {
            Log.d("Loading Resource: ", "Cannot find board of name: " + boardName);
        }
        return board;
    }

    //For now just returns all cards
    //TODO: loadCharacterCards will be used to load a specified set of cards e.g. decks and all cards
    @Override
    public CharacterCard[] loadCharacterCards() throws NullPointerException{
        JSONArray cardJsonArray = jsonBourne.fromJSONStringToJsonArray(getStringFromFile(CARDS_URL), CARDS_ARRAY_NAME);

        CharacterCard[] characterCards = new CharacterCard[cardJsonArray.length()];
        int index = 0;
        try {
            while (index < cardJsonArray.length()) {
                JSONObject jsonCard = cardJsonArray.getJSONObject(index);
                //creating a new character card with attributes from JSONObject
                // an arbitrary spawn point is also set until we decide how to spawn cards
                characterCards[index] = new CharacterCard(getBitmapFromFile(jsonCard.getString("portraitSrc")), (float) Math.random() * 1000, (float) Math.random() * 1000,
                        jsonCard.getString("name"),
                        jsonCard.getString("description"),
                        jsonCard.getInt("mana"),
                        jsonCard.getInt("attack"),
                        jsonCard.getInt("health"),
                        jsonCard.getString("abilityDescription"));
                index++;
                //if (characterCards[index] == null) throw new NullPointerException();
                // If a null card is created a null pointer exception is thrown
            }
        } catch (JSONException e) {
            Log.d("Loading Resource: ", "Cannot find card of ID: " + index);
        }
        return characterCards;
    }


    @Override
    public Bitmap getBitmapFromFile(String url) throws NullPointerException {
        try {
            return graphicsIO.loadBitmap(url, Bitmap.Config.ARGB_4444);
        } catch (IOException e) {
            Log.d("Loading Resource: ", "Error Processing Image at " + url);
        }
        throw new NullPointerException();
    }

    public String getStringFromFile(String url) {
        String outputString = null;
        try {
            outputString = getStringFromInputStream(getStreamFromFile(url));
        } catch (IOException e) {
            Log.d("Loading Resource: ", "Error loading resource from" + url);
        }
        return outputString;
    }

    public InputStream getStreamFromFile(String url) throws IOException {
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