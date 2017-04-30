package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Bitmap;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.controlling.Controlling;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Placeable;
import test.puigames.courseofhistory.framework.engine.screen.Level;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.Deck;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.CourseOfHistoryMachine;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.HumanCardGameController;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.StartingHandSelectionUI;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Jordan on 10/11/2016.
 */

public class TestLevel extends Level {
    private static float PLAYER_DECK_ROTATION = 0.f;
    private static float PLAYER_DECK_PLACEMENT_X = 100.f;
    private static float PLAYER_DECK_PLACEMENT_Y = 50.f;

    private static float BOARD_PLAYER_AREA_ROTATION = 0.f;
    private static float BOARD_PLAYER_AREA_OFFSET_X = 0.f;
    private static float BOARD_PLAYER_AREA_OFFSET_Y = 30.f;

    private static float PLAYER_HAND_ROTATION = 0.f;
    private static float PLAYER_HAND_OFFSET_X = 0.f;
    private static float PLAYER_HAND_OFFSET_Y = 60.f;

    private static float BOARD_ROTATION = 0.f;
    private static float BOARD_OFFSET_X = 0.f;
    private static float BOARD_OFFSET_Y = 0.f;

    private static float MANA_ROTATION = 0.f;
    private static float MANA_OFFSET_X = -200.f;
    private static float MANA_OFFSET_Y = 70.f;
    private static float MANA_PADDING = 10.f;



    private final String[] DECK_NAMES = {"greatMindsCards", "evilLeaderCards"};
    private final String[] TEST_CARD_NAMES = {"cards1", "cards2"};
    StartingHandSelectionUI startingHandSelectionUI;

    CourseOfHistoryMachine gameMachine;

    public TestLevel(GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    @Override
    public void load() {
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

           //Creates a controller and a player for each participant
           for(int i = 0; i < players.length; i++) {
               players[i] = new Player(resourceFetcher.loadCharacterCards(this, DECK_NAMES[i]), board, new Deck(this, resourceFetcher.getBitmapFromFile("images/splashscreen/splash.png")), i); //Creating a new player pawn for each controller
               //TODO give proper deck image!!!

               for (int j = 0; j < players[i].MAX_MANA; j++)
                   players[i].mana[j] = new Mana(this, manaTypes);
           }

           //creating the game machine for processing the game
           gameMachine = new CourseOfHistoryMachine(players, coin, board);
           gameMachine.startTicking(this);

           Controlling[] controllers = new HumanCardGameController[CourseOfHistoryMachine.PLAYER_COUNT];
           //Giving the controllers possession of the corresponding player in the game machine
           for (int i = 0; i < controllers.length; i++) {
               controllers[i] = new HumanCardGameController(this, inputBuddy, gameMachine.players[i], resourceFetcher.getBitmapFromFile("images/backgrounds/starting_hand_selection_ui_background.png"), resourceFetcher.getBitmapFromFile("images/buttons/confirmation_button.png"), resourceFetcher.getBitmapFromFile("images/buttons/end_turn_button.png"));
               controllers[i].startTicking(this);
           }

           setUpGamePiecePositions(this, viewport.centerX, viewport.centerY);

       } catch(NullPointerException e) {
//           Log.d("Loading Error:", "Error fetching resources, returning to menu");
           Log.e("ERROR", e.getMessage() + "\n" + e.getCause());
           //TODO do properly
           //Failed loading the gameProperties - won't cause crash if resources set up wrong!
           gameProperties.setScreen(new SplashScreen(this.gameProperties));
       }
    }

    public float workOutObjectRotation(float playerRotation, float objectRotation) {
        return playerRotation + objectRotation;
    }

    //TODO WORK OUT WHEN TO PLACE OBJECTS
    public float workOutPlayerRotation(int playerNumber) {
        return ((playerNumber % 2) * 180) % 360;
    }

    //finds a position either x or y rotated around a point
    private void placePlaceableRelativeToAnchorPoint(Screen screen, Placeable placeable, float anchorPosX, float anchorPosY, float offsetX, float offsetY, float rotationAroundAnchor, float placeableRotation) {
        rotationAroundAnchor = rotationAroundAnchor *  ((float) Math.PI / 180); //Convert degrees to radians

        float sin = (float) Math.sin(rotationAroundAnchor);
        float cos = (float) Math.cos(rotationAroundAnchor);

        offsetX += anchorPosX; //Get the absolute positions to relative positions
        offsetY += anchorPosY;

        //Rotate clockwise
        float rotatedX = anchorPosX + ((offsetX - anchorPosX) * cos) + ((offsetY - anchorPosY) * sin); // new position of x after rotating around the anchor
        float rotatedY = anchorPosY + ((offsetX - anchorPosX) * -sin) + ((offsetY - anchorPosY) * cos); // New position of y after rotating around the anchor

        //Placing the object where it should be
        placeable.place(screen,  rotatedX, rotatedY, placeableRotation);
    }

    private float findAbsolutePosition(float anchor, float offset) {
        return anchor + offset;
    }

    public void setUpGamePiecePositions(Screen screen, float placementX, float placementY) {
        gameMachine.board.place(screen, findAbsolutePosition(placementX, BOARD_OFFSET_X), findAbsolutePosition(placementY, BOARD_OFFSET_Y), 0);
       // gameMachine.board.setUpGamePiecePositions(screen, (placementX), (placementY), 0);

        float anchorX = gameMachine.board.getPosX();
        float anchorY = gameMachine.board.getPosY();
        for(int i = 0; i < gameMachine.players.length; i++) {
            //Find player rotation
            gameMachine.players[i].setRotation(workOutPlayerRotation(i));
            Player currentPlayer = gameMachine.players[i];

            placePlaceableRelativeToAnchorPoint(this, gameMachine.players[i].playerDeck, anchorX, anchorY,
                    PLAYER_DECK_PLACEMENT_X, PLAYER_DECK_PLACEMENT_Y, gameMachine.players[i].getRotation(), workOutObjectRotation(gameMachine.players[i].getRotation(), PLAYER_DECK_ROTATION));

            placePlaceableRelativeToAnchorPoint(this, currentPlayer.board.playAreas[i], anchorX, anchorY,
                    BOARD_PLAYER_AREA_OFFSET_X, BOARD_PLAYER_AREA_OFFSET_Y, gameMachine.players[i].getRotation(), workOutObjectRotation(currentPlayer.getRotation(), PLAYER_DECK_ROTATION));

            placePlaceableRelativeToAnchorPoint(this, currentPlayer.board.cardHands[i], placementX, placementY,
                    PLAYER_HAND_OFFSET_X, PLAYER_HAND_OFFSET_Y, currentPlayer.getRotation(), workOutObjectRotation(currentPlayer.getRotation(), PLAYER_DECK_ROTATION));

            float manaRelOffsetFromLast = 0;
            for (Mana mana : gameMachine.players[i].mana) {
                placePlaceableRelativeToAnchorPoint(this, mana, placementX, placementY, MANA_OFFSET_X + manaRelOffsetFromLast, MANA_OFFSET_Y, currentPlayer.getRotation(), workOutObjectRotation(currentPlayer.getRotation(), PLAYER_DECK_ROTATION));
                manaRelOffsetFromLast += MANA_PADDING;
            }
            // players[i].hero.setUpGamePiecePositions(); TODO
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
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
