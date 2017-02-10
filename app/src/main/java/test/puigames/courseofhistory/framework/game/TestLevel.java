package test.puigames.courseofhistory.framework.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Arrays;

import test.puigames.courseofhistory.framework.engine.Game;
import test.puigames.courseofhistory.framework.engine.Level;
import test.puigames.courseofhistory.framework.game.Board.Board;
import test.puigames.courseofhistory.framework.game.cards.Card;
import test.puigames.courseofhistory.framework.game.cards.CharacterCard;
import test.puigames.courseofhistory.framework.input.AndroidInput;

/**
 * Created by Jordan on 10/11/2016.
 */

public class TestLevel extends Level
{
    private Board board;
    private CharacterCard[] testCards;

    public TestLevel(Game game) {
        super(game);
        load();
    }

    public void load() {
       try {
           //Bitmap boardImage = game.getResourceFetcher().getBitmapFromFile("board.png");
           board = resourceFetcher.loadBoard("testBoard");
           this.testCards = resourceFetcher.loadCharacterCards();
           sprites.add(board);
           sprites.addAll(Arrays.asList(testCards));
       } catch(NullPointerException e) {
           Log.d("Loading Error:", "Error fetching resources, returning to menu");
           //Failed loading the game - won't cause crash is resources set up wrong!
           game.setScreen(new SplashScreen(this.game));
       }
        try {
            this.sprites.add(resourceFetcher.loadBoard("testBoard"));
            this.sprites.addAll(Arrays.asList(resourceFetcher.loadCharacterCards()));
        } catch(NullPointerException e) {
            Log.d("Loading Error:", "Error fetching resources, returning to menu");
            //Failed loading the game - won't cause crash is resources set up wrong!
            game.setScreen(new SplashScreen(this.game));
        }

    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);
        for (Card card : testCards)
            card.update(inputBuddy, deltaTime, testCards, board);
    }

    @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {

    }
}
