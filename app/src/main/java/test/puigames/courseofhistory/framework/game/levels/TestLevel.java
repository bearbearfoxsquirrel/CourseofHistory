package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Bitmap;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.controlling.Controlling;
import test.puigames.courseofhistory.framework.engine.screen.Level;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.Deck;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.StatImage;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.CourseOfHistoryMachine;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.HumanCardGameController;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class TestLevel extends Level {
    private static float PLAYER_OFFSET_X = 240;


    private static float PLAYER_DECK_ROTATION = 0.f;
    private static float PLAYER_DECK_PLACEMENT_X = 175.f;
    private static float PLAYER_DECK_PLACEMENT_Y = 85.f;

    private static float BOARD_PLAYER_AREA_ROTATION = 0.f;
    private static float BOARD_PLAYER_AREA_OFFSET_X = 0.f;
    private static float BOARD_PLAYER_AREA_OFFSET_Y = 75.f;

    private static float PLAYER_HAND_ROTATION = 0.f;
    private static float PLAYER_HAND_OFFSET_X = 0.f;
    private static float PLAYER_HAND_OFFSET_Y = 160.f;

    private static float BOARD_ROTATION = 0.f;
    private static float BOARD_OFFSET_X = 0.f;
    private static float BOARD_OFFSET_Y = 0.f;

    private static float MANA_ROTATION = 0.f;
    private static float MANA_OFFSET_X = -210.f;
    private static float MANA_OFFSET_Y = 70.f;
    private static float MANA_PADDING = 10.f;



    private final String[] DECK_NAMES = {"greatMindsCards", "evilLeaderCards"};
    private final String[] TEST_CARD_NAMES = {"cards1", "cards2"};
    private final Placer peterPiperPickedAPlacer = new Placer(this);

    CourseOfHistoryMachine gameMachine;

    public TestLevel(GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    @Override
    protected void load() {
       try {
           //Setting up the board and spawning it
           Board board = resourceFetcher.loadBoard(this, "testBoard");

           //Setting up the coin and spawning it
           Bitmap coinSides[] = {resourceFetcher.getBitmapFromFile("images/coins/coin-heads.png"), resourceFetcher.getBitmapFromFile("images/coins/coin-tails.png")};
           Coin coin = new Coin(this, coinSides, 80, 80);

           Player[] players = new Player[CourseOfHistoryMachine.PLAYER_COUNT];

           //Load mana images
           Bitmap manaTypes[] = {resourceFetcher.getBitmapFromFile("images/mana/mana.png"),
                   resourceFetcher.getBitmapFromFile("images/mana/mana-used.png")};

           final int numSize = 10;
           Bitmap numImages[] = new Bitmap[numSize];
           for(int i = 0; i < numSize; i++) {

               numImages[i] = resourceFetcher.getBitmapFromFile("images/numbers/" +Integer.toString(i)+".png");
           }

           StatImage[] statImage = { new StatImage(this, numImages,6, 7), new StatImage(this, numImages,5, 6), new StatImage(this, numImages,5, 6)};



           //Creates a controller and a player for each participant
           for(int i = 0; i < players.length; i++) {
               players[i] = new Player(resourceFetcher.loadCharacterCards(this, DECK_NAMES[i], statImage), board, new Deck(this, resourceFetcher.getBitmapFromFile("images/splashscreen/splash.png")), i); //Creating a new player pawn for each controller
               //TODO give proper deck image!!!

               for (int j = 0; j < players[i].getMAX_MANA(); j++)
                   players[i].getMana()[j] = new Mana(this, manaTypes);
           }

           //creating the game machine for processing the game
           gameMachine = new CourseOfHistoryMachine(players, coin, board);
           setUpGamePiecePositions(this, viewport.getCenterX(), viewport.getCenterY());

           Controlling[] controllers = new HumanCardGameController[CourseOfHistoryMachine.PLAYER_COUNT];
           //Giving the controllers possession of the corresponding player in the game machine
           for (int i = 0; i < controllers.length; i++) {
               //UI is placed at the center of Viewport
               controllers[i] = new HumanCardGameController(this, inputBuddy, gameMachine.getPlayers()[i], resourceFetcher.getBitmapFromFile("images/backgrounds/starting_hand_selection_ui_background.png"), resourceFetcher.getBitmapFromFile("images/buttons/confirmation_button.png"), resourceFetcher.getBitmapFromFile("images/buttons/confirmation_button.png"), resourceFetcher.getBitmapFromFile("images/buttons/end_turn_button.png"), viewport.getCenterX(), viewport.getCenterY() );
           }

           gameMachine.startTicking(this);

           for (Controlling controller : controllers)
               controller.startTicking(this);

       } catch(NullPointerException e) {
//           Log.d("Loading Error:", "Error fetching resources, returning to menu");
           Log.e("ERROR", e.getMessage() + "\n" + e.getCause());
           //TODO do properly
           //Failed loading the gameProperties - won't cause crash if resources set up wrong!
           gameProperties.setScreen(new SplashScreen(this.gameProperties));
       }
    }

    private float workOutPlayerRotation(int playerNumber) {
        return ((playerNumber % 2) * 180) % 360;
    }

    private void setUpGamePiecePositions(Screen screen, float placementX, float placementY) {
        gameMachine.getBoard().place(screen, peterPiperPickedAPlacer.findAbsolutePosition(placementX, BOARD_OFFSET_X), peterPiperPickedAPlacer.findAbsolutePosition(placementY, BOARD_OFFSET_Y), 0);

        float anchorX = gameMachine.getBoard().getPosX();
        float anchorY = gameMachine.getBoard().getPosY();

        for(int i = 0; i < gameMachine.getPlayers().length; i++) {
            //Find player rotation
            Player currentPlayer = gameMachine.getPlayers()[i];
            currentPlayer.setRotation(workOutPlayerRotation(i));

            peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(currentPlayer.getPlayerDeck(), anchorX, anchorY,
                    PLAYER_DECK_PLACEMENT_X, PLAYER_DECK_PLACEMENT_Y, currentPlayer.getRotation(), peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), PLAYER_DECK_ROTATION));

            peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(currentPlayer.getBoard().getPlayAreas()[i], anchorX, anchorY,
                    BOARD_PLAYER_AREA_OFFSET_X, BOARD_PLAYER_AREA_OFFSET_Y, currentPlayer.getRotation(), peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), PLAYER_DECK_ROTATION));

            peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(currentPlayer.getBoard().getCardHands()[i], placementX, placementY,
                    PLAYER_HAND_OFFSET_X, PLAYER_HAND_OFFSET_Y, currentPlayer.getRotation(), peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), PLAYER_DECK_ROTATION));

            float manaRelOffsetFromLast = 0;
            for (Mana mana : currentPlayer.getMana()) {
                peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(mana, placementX, placementY, MANA_OFFSET_X + manaRelOffsetFromLast, MANA_OFFSET_Y, currentPlayer.getRotation(), peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), PLAYER_DECK_ROTATION));
                manaRelOffsetFromLast += MANA_PADDING;
            }
            // players[i].hero.setUpGamePiecePositions(); TODO
        }
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
