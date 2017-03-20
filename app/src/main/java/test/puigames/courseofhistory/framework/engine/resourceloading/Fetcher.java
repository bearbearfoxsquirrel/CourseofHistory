package test.puigames.courseofhistory.framework.engine.resourceloading;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 02/02/2017.
 */

public interface Fetcher {
    //public JSONArray getJSONArrayFromJSONFile(String url);

    public Bitmap getBitmapFromFile(String url);

    public Board loadBoard(String boardName);

    public CharacterCard[] loadCharacterCards(String deckName);
}