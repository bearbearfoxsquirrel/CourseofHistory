package test.puigames.courseofhistory.framework.engine.resourceloading;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.game.board.Board;
import test.puigames.courseofhistory.framework.game.cards.CharacterCard;

/**
 * Created by Michael on 02/02/2017.
 */

public interface FetchingIO {
    //public JSONArray getJSONArrayFromJSONFile(String url);

    public Bitmap getBitmapFromFile(String url);

    public Board loadBoard(String boardName);

    public CharacterCard[] loadCharacterCards();
}