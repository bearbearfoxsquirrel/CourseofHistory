package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Arrays;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.inputfriends.subfriends.AndroidInput;
import test.puigames.courseofhistory.framework.engine.screen.Level;
import test.puigames.courseofhistory.framework.game.PlayArea;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;
import test.puigames.courseofhistory.framework.game.controllers.CardGameController;
import test.puigames.courseofhistory.framework.game.controllers.HumanController;
import test.puigames.courseofhistory.framework.game.controllers.Player;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class TestLevel extends Level
{
    private Board board;
    private CharacterCard[] evilLeaderCards = new CharacterCard[36];
    private CharacterCard[] greatMindsCards = new CharacterCard[36];
    private CharacterCard[][] cardsHolder = {evilLeaderCards, greatMindsCards};
    private String[] deckNames = {"greatMindsCards", "evilLeaderCards"};

    CardGameController cardGameControllers[];

    private Coin coin;

    public TestLevel(GameProperties gameProperties) {
        super(gameProperties);
        cardGameControllers = new CardGameController[2];
        load();
        sprites.add(cardGameControllers[0].player.drawCardFromDeck());
        sprites.add(cardGameControllers[1].player.drawCardFromDeck());
        sprites.add(cardGameControllers[0].player.drawCardFromDeck());
        sprites.add(cardGameControllers[1].player.drawCardFromDeck());
        sprites.add(cardGameControllers[0].player.drawCardFromDeck());
        sprites.add(cardGameControllers[1].player.drawCardFromDeck());
        sprites.add(cardGameControllers[0].player.drawCardFromDeck());
        sprites.add(cardGameControllers[1].player.drawCardFromDeck());


    }

    public void load() {
       try {
           this.board = resourceFetcher.loadBoard("testBoard");
           sprites.add(board);

           //this.evilLeaderCards = resourceFetcher.loadCharacterCards("evilLeaderDeck");
           //sprites.addAll(Arrays.asList(evilLeaderCards));

          // this.greatMindsCards = resourceFetcher.loadCharacterCards("greatMindsDeck");
          // sprites.addAll(Arrays.asList(greatMindsCards));

           coin = new Coin(resourceFetcher.getBitmapFromFile("images/coins/coin-heads.png"),
                   240, 160, 80, 80);
           sprites.add(coin);

           //for (CardGameController contestant : cardGameControllers)
           for(int i = 0; i < cardGameControllers.length; i++) {
               this.cardsHolder[i] = resourceFetcher.loadCharacterCards(deckNames[i]);
               //sprites.addAll(Arrays.asList(cardsHolder[i]));
               this.cardGameControllers[i] = new HumanController(new Player(cardsHolder[i], board));
           }

       } catch(NullPointerException e) {
           Log.d("Loading Error:", "Error fetching resources, returning to menu");
           //Failed loading the gameProperties - won't cause crash if resources set up wrong!
           gameProperties.setScreen(new SplashScreen(this.gameProperties));
       }
    }

    @Override
    public void update(float deltaTime, AndroidInput input) {
        super.update(deltaTime, input);

        //while game not won
        for(int turnIndex = 0; turnIndex < cardGameControllers.length; turnIndex++) {
            cardGameControllers[turnIndex].update(inputBuddy, deltaTime);
            switch (cardGameControllers[turnIndex].player.playerCurrentState) {
              //  case CREATED:
                case TAKING_TURN:
                    switch (cardGameControllers[turnIndex].currentControllerState) {
                        case MOVING_CARD_IN_HAND:
                            cardGameControllers[turnIndex].updateCardsInHand(deltaTime);
                            break;
                        case ATTACKING:
                       //     cardGameControllers[turnIndex].
                          //          pawn.attack(cardGameControllers[turnIndex].);
                    }
                    break;
            }
            board.update(inputBuddy, deltaTime);
        }
//        decideTurn();

//        scaler.scaleToScreen(playArea);


    }


    private void decideTurn()
    {
        if(coin.faceUp == Coin.Result.HEADS)
            coin.setImage(resourceFetcher.getBitmapFromFile("images/coins/coin-heads.png"));
        else
            coin.setImage((resourceFetcher.getBitmapFromFile("images/coins/coin-tails.png")));
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

    public void spawn(Sprite sprite) {

    }

    @Override
    public void dispose()
    {

    }
}
