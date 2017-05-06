package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Bitmap;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.controlling.Controlling;
import test.puigames.courseofhistory.framework.engine.screen.Level;
import test.puigames.courseofhistory.framework.engine.screen.Placer;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.CardHand;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.Deck;
import test.puigames.courseofhistory.framework.game.assets.Hero;
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

    private final int HERO_PORTRAIT_SIZE = 60;
    private final float HERO_OFFSET_X = -200.f;
    private final float HERO_OFFSET_Y = 115.f;


    private final String[] DECK_NAMES = {"greatMindsCards", "evilLeaderCards"};
    private final String[] TEST_CARD_NAMES = {"cards1", "cards2"};
    private final Placer peterPiperPickedAPlacer = new Placer(this);

    private CourseOfHistoryMachine gameMachine;

    public TestLevel(GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    @Override
    protected void load() {
       try {
            //Load hero bitmaps
            Bitmap[] heroBitmaps = {
                resourceFetcher.getBitmapFromFile("images/heroes/great-minds-hero.jpg"),
                resourceFetcher.getBitmapFromFile("images/heroes/evil-leaders-hero.jpg")};

           Hero[] heroes = new Hero[CourseOfHistoryMachine.PLAYER_COUNT];
           for(int i = 0; i < CourseOfHistoryMachine.PLAYER_COUNT; i++)
               heroes[i] = new Hero(this, heroBitmaps[i], HERO_PORTRAIT_SIZE, HERO_PORTRAIT_SIZE); //create heroes

           //Setting up the board and spawning it
           Board board = resourceFetcher.loadBoard(this, "testBoard", heroes);

           //Setting up the coin and spawning it
           Bitmap[] coinSides = {
                   resourceFetcher.getBitmapFromFile("images/coins/coin-heads.png"),
                   resourceFetcher.getBitmapFromFile("images/coins/coin-tails.png")};
           Coin coin = new Coin(this, coinSides);

           //Create players
           Player[] players = new Player[CourseOfHistoryMachine.PLAYER_COUNT];

           //Load mana images
           Bitmap[] manaTypes = {
                   resourceFetcher.getBitmapFromFile("images/mana/mana.png"),
                   resourceFetcher.getBitmapFromFile("images/mana/mana-used.png")};

           //Load stat images
           final int numSize = 10;
           Bitmap numImages[] = new Bitmap[numSize];
           for(int i = 0; i < numSize; i++)
               numImages[i] = resourceFetcher.getBitmapFromFile("images/numbers/" +Integer.toString(i)+".png");

           StatImage[] statImage = {
                   new StatImage(this, numImages,6, 7),
                   new StatImage(this, numImages,5, 6),
                   new StatImage(this, numImages,5, 6)}; //load stat images

           //Creates a controller and a player for each participant
           for(int i = 0; i < players.length; i++) {
               players[i] = new Player(resourceFetcher.loadCharacterCards(this, DECK_NAMES[i]), board, new Deck(this, resourceFetcher.getBitmapFromFile("images/splashscreen/splash.png")), new CardHand(this) ,i); //Creating a new player pawn for each controller
               //TODO give proper deck image!!!

               for (int j = 0; j < players[i].getMAX_MANA(); j++)
                   players[i].getMana()[j] = new Mana(this, manaTypes); //create mana
           }

           //creating the game machine for processing the game
           gameMachine = new CourseOfHistoryMachine(players, coin, board);
           setUpGamePiecePositions(this, viewport.getCenterX(), viewport.getCenterY());

           Controlling[] controllers = new HumanCardGameController[CourseOfHistoryMachine.PLAYER_COUNT];
           //Giving the controllers possession of the corresponding player in the game machine
           for (int i = 0; i < controllers.length; i++) {
               //UI is placed at the center of Viewport
               controllers[i] = new HumanCardGameController(this, inputBuddy, gameMachine.getPlayers()[i], resourceFetcher.getBitmapFromFile("images/backgrounds/starting_hand_selection_ui_background.png"),
                       resourceFetcher.getBitmapFromFile("images/buttons/confirmation_button.png"), resourceFetcher.getBitmapFromFile("images/buttons/confirmation_button.png"),
                       resourceFetcher.getBitmapFromFile("images/buttons/end_turn_button.png"), viewport.getCenterX(), viewport.getCenterY() );
           }
           gameMachine.startTicking(this); //starts game machine - IT LIVES

           for (Controlling controller : controllers)
               controller.startTicking(this);

       } catch(NullPointerException e) {
//         Log.d("Loading Error:", "Error fetching resources, returning to menu");
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
            Player currentPlayer = gameMachine.getPlayer(i);
            currentPlayer.setRotation(workOutPlayerRotation(i));

            peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(currentPlayer.getPlayerDeck(), anchorX, anchorY,
                    PLAYER_DECK_PLACEMENT_X, PLAYER_DECK_PLACEMENT_Y, currentPlayer.getRotation(), peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), PLAYER_DECK_ROTATION));

            peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(currentPlayer.getBoard().getPlayAreas()[i], anchorX, anchorY,
                    BOARD_PLAYER_AREA_OFFSET_X, BOARD_PLAYER_AREA_OFFSET_Y, currentPlayer.getRotation(), peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), BOARD_ROTATION));

            peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(currentPlayer.getHand(), placementX, placementY,
                    PLAYER_HAND_OFFSET_X, PLAYER_HAND_OFFSET_Y, currentPlayer.getRotation(), peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), PLAYER_HAND_ROTATION));

            float manaRelOffsetFromLast = 0;
            for (Mana mana : currentPlayer.getMana()) {
                peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(mana, placementX, placementY, MANA_OFFSET_X + manaRelOffsetFromLast, MANA_OFFSET_Y, currentPlayer.getRotation(), peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), MANA_ROTATION));
                manaRelOffsetFromLast += MANA_PADDING;
            }

            peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(currentPlayer.getHero(), anchorX, anchorY,
                    HERO_OFFSET_X, HERO_OFFSET_Y, currentPlayer.getRotation(), peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), PLAYER_DECK_ROTATION));
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
