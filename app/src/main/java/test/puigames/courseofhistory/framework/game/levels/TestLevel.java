package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.Controlling.Possessor;
import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.screen.Level;
import test.puigames.courseofhistory.framework.game.assets.Animation;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.controllers.CourseOfHistoryMachine;
import test.puigames.courseofhistory.framework.game.controllers.HumanCardGameController;
import test.puigames.courseofhistory.framework.game.controllers.Player;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class TestLevel extends Level
{
    public static int MAX_PLAYERS = 2;
    private String[] deckNames = {"greatMindsCards", "evilLeaderCards"};
    private Animation animation;
    private Bitmap explodeAnimation;
    private Board board;

    Possessor controllers[];
    CourseOfHistoryMachine gameMachine;

    public TestLevel(GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    public void load() {
       try {
           //Setting up the board and spawning it
           board = resourceFetcher.loadBoard("testBoard");
           spawnSprite(board, 480/2, 320/2);

           //Setting up the coin and spawning it
           Bitmap coinSides[] = {resourceFetcher.getBitmapFromFile("images/coins/coin-heads.png"), resourceFetcher.getBitmapFromFile("images/coins/coin-tails.png")};
           Coin coin = new Coin(coinSides, 80, 80);
           spawnSprite(coin, 300, 200);

           //Setting up the players and controllers
           controllers = new HumanCardGameController[MAX_PLAYERS];
           Player[] players = new Player[MAX_PLAYERS];
           for(int i = 0; i < controllers.length; i++) {
               controllers[i] = new HumanCardGameController(gameProperties.getInput());
               players[i] = new Player(resourceFetcher.loadCharacterCards(deckNames[i]), board); //Creating a new player pawn for each controller
               controllers[i].possessPlayer(players[i]); //Giving the player controller a pawn to manipulate for the game

               for(int playersDeckCardIndex = 0; playersDeckCardIndex < players[i].playerDeck.size(); playersDeckCardIndex++)
                   //spawning the cards in from each players deck
                   spawnSprite((CharacterCard)players[i].playerDeck.get(playersDeckCardIndex),(float) Math.random() * 1000, (float) Math.random() * 1000);
           }

           //creating the game machine for turns :)
           gameMachine = new CourseOfHistoryMachine(players, coin);
       } catch(NullPointerException e) {
           Log.d("Loading Error:", "Error fetching resources, returning to menu");
           //TODO do properly
           //Failed loading the gameProperties - won't cause crash if resources set up wrong!
           gameProperties.setScreen(new SplashScreen(this.gameProperties));
       }
    }

    public void updateControllers(float deltaTime) {
        for (Possessor controller: controllers)
            controller.update(deltaTime);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        updateControllers(deltaTime); //Should be called before the game machine is updated
        gameMachine.update(deltaTime);

        for (Player player : gameMachine.players) {
            for (CharacterCard card : player.playArea.cardsInArea)
                card.update(deltaTime);
        }

    }

    private void collisionCheckAndResolve(int turnIndex)
    {
        for(CharacterCard card : controllers[turnIndex].getPlayer().testCards)
        {
            for(CharacterCard card2 : controllers[turnIndex].getPlayer().testCards)
            {
                if(card.checkForCollision(card2))
                    card.resolveCollision(card2, card.overlapAllowance);
            }
        }
    }



    @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
