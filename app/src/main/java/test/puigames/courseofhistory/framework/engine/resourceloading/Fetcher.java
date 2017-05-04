package test.puigames.courseofhistory.framework.engine.resourceloading;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.StatImage;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 02/02/2017.
 */

public interface Fetcher {
    //public JSONArray getJSONArrayFromJSONFile(String url);

    Bitmap getBitmapFromFile(String url);

    Board loadBoard(Screen screen, String boardName);

    CharacterCard[] loadCharacterCards(Screen screen, String deckName, StatImage[] statImages);
}