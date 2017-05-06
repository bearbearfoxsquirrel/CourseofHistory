package test.puigames.courseofhistory.framework.engine.resourceloading;

import android.graphics.Bitmap;

import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.Hero;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 02/02/2017.
 */

public interface Fetcher {
    Bitmap getBitmapFromFile(String url);

    Board loadBoard(Screen screen, String boardName, Hero[] heroes);

    CharacterCard[] loadCharacterCards(Screen screen, String deckName);
}