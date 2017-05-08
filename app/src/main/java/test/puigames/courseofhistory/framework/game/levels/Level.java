package test.puigames.courseofhistory.framework.game.levels;

import android.graphics.Bitmap;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.controlling.Controlling;
import test.puigames.courseofhistory.framework.engine.screen.Placer;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.CourseOfHistoryMachine;
import test.puigames.courseofhistory.framework.game.assets.CardHand;
import test.puigames.courseofhistory.framework.game.assets.Coin;
import test.puigames.courseofhistory.framework.game.assets.Deck;
import test.puigames.courseofhistory.framework.game.assets.Hero;
import test.puigames.courseofhistory.framework.game.assets.Mana;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.game.assets.players.controllers.HumanCardGameController;
import test.puigames.courseofhistory.framework.game.screens.MainMenu;
import test.puigames.courseofhistory.framework.game.screens.SplashScreen;

/**
 * Created by Michael on 24/11/2016.
 */

public abstract class Level extends Screen {
    private static float PLAYER_DECK_ROTATION = 0.f;
    private static float PLAYER_DECK_PLACEMENT_X = 190.f;
    private static float PLAYER_DECK_PLACEMENT_Y = 105.f;

    private static float BOARD_PLAYER_AREA_ROTATION = 0.f;
    private static float BOARD_PLAYER_AREA_OFFSET_X = 0.f;
    private static float BOARD_PLAYER_AREA_OFFSET_Y = 48.f;

    private static float PLAYER_HAND_ROTATION = 0.f;
    private static float PLAYER_HAND_OFFSET_X = 0.f;
    private static float PLAYER_HAND_OFFSET_Y = 130.f;

    private static int PLAYER_HAND_WIDTH = 340;
    private static int PLAYER_HAND_HEIGHT = 65;

    private static float BOARD_ROTATION = 0.f;
    private static float BOARD_OFFSET_X = 0.f;
    private static float BOARD_OFFSET_Y = 0.f;

    private static float MANA_ROTATION = 0.f;
    private static float MANA_OFFSET_X = -230.f;
    private static float MANA_OFFSET_Y = 75.f;
    private static float MANA_PADDING = 10.f;
    private static int MANA_WIDTH = 10;
    private static int MANA_HEIGHT = 15;

    private final int HERO_PORTRAIT_SIZE = 60;
    private final float HERO_OFFSET_X = -210.f;
    private final float HERO_OFFSET_Y = 115.f;

    private final int HEROES_BITMAPS_LENGTH = 6;


    private final String[] DECK_NAMES = {"greatMindsCards", "evilLeaderCards"};
    private final Placer peterPiperPickedAPlacer = new Placer(this);

    protected CourseOfHistoryMachine gameMachine;

    public Level(GameProperties gameProperties) {
        super(gameProperties);
        load();
    }

    @Override
    protected void load() {
        try {
            //Setting up the coin and spawning it
            Bitmap[] coinSides = {
                    resourceFetcher.getBitmapFromFile("images/coins/coin-heads.png"),
                    resourceFetcher.getBitmapFromFile("images/coins/coin-tails.png")};
            Coin coin = new Coin(this, coinSides);


            //Load stat images
            final int numSize = 10;
            Bitmap numImages[] = new Bitmap[numSize];
            for (int i = 0; i < numSize; i++)
                numImages[i] = resourceFetcher.getBitmapFromFile("images/numbers/" + Integer.toString(i) + ".png");


            //Load hero bitmaps
            Bitmap[] greatMindsBitmaps = new Bitmap[HEROES_BITMAPS_LENGTH];
            Bitmap[] worldLeadersBitmaps = new Bitmap[HEROES_BITMAPS_LENGTH];
            for(int i = 0; i < HEROES_BITMAPS_LENGTH; i++) {
                greatMindsBitmaps[i] = resourceFetcher.getBitmapFromFile("images/heroes/great_minds/greatMinds" + (i + 1) + ".png");
                worldLeadersBitmaps[i] = resourceFetcher.getBitmapFromFile("images/heroes/world_leaders/worldLeaders" + (i + 1) + ".png");
            }
            int superRandomNumber = 0; //note: not actually that random

            //Create heroes
            Hero[] heroes = new Hero[CourseOfHistoryMachine.PLAYER_COUNT];
            for (int i = 0; i < CourseOfHistoryMachine.PLAYER_COUNT; i++) {
                superRandomNumber = (int) Math.floor(Math.random() * HEROES_BITMAPS_LENGTH);
                if(i == 0)
                    heroes[i] = new Hero(this, greatMindsBitmaps[superRandomNumber], numImages, HERO_PORTRAIT_SIZE, HERO_PORTRAIT_SIZE); //create heroes
                else
                    heroes[i] = new Hero(this, worldLeadersBitmaps[superRandomNumber], numImages, HERO_PORTRAIT_SIZE, HERO_PORTRAIT_SIZE);
            }


            //Setting up the board and spawning it
            Board board = resourceFetcher.loadBoard(this, "testBoard", heroes);


            //Create players
            Player[] players = new Player[CourseOfHistoryMachine.PLAYER_COUNT];

            //Load mana images
            Bitmap[] manaTypes = {
                    resourceFetcher.getBitmapFromFile("images/mana/mana.png"),
                    resourceFetcher.getBitmapFromFile("images/mana/mana-used.png")};

            //Creates a controller and a player for each participant
            for (int i = 0; i < players.length; i++) {
                players[i] = new Player(
                        resourceFetcher.loadCharacterCards(this, DECK_NAMES[i]),
                        board, new Deck(this, resourceFetcher.getBitmapFromFile("images/deck/deck.png")),
                        new CardHand(this, resourceFetcher.getBitmapFromFile("images/card_areas/play-area.png"),
                                PLAYER_HAND_WIDTH, PLAYER_HAND_HEIGHT), i); //Creating a new player pawn for each controller

                for (int j = 0; j < players[i].getMAX_MANA(); j++)
                    players[i].getMana()[j] = new Mana(this, manaTypes, MANA_WIDTH, MANA_HEIGHT); //create mana
            }


            //creating the game machine for processing the game
            gameMachine = new CourseOfHistoryMachine(players, coin, board);
            setUpGamePiecePositions(this, viewport.getCenterX(), viewport.getCenterY());
            gameMachine.startTicking(this); //starts game machine - IT LIVES

        } catch(NullPointerException e) {
            Log.e("ERROR", e.getMessage() + "\n" + e.getCause());
            //TODO do properly
            //Failed loading the gameProperties - won't cause crash if resources set up wrong!
            gameProperties.setScreen(new MainMenu(this.gameProperties));
        }
    }

    private float workOutPlayerRotation(int playerNumber) {
        return ((playerNumber % 2) * 180) % 360;
    }

    private void setUpGamePiecePositions(Screen screen, float placementX, float placementY) {
        gameMachine.getBoard().place(screen, peterPiperPickedAPlacer.findAbsolutePosition(placementX, BOARD_OFFSET_X),
                peterPiperPickedAPlacer.findAbsolutePosition(placementY, BOARD_OFFSET_Y), BOARD_ROTATION);

        float anchorX = gameMachine.getBoard().getPosX();
        float anchorY = gameMachine.getBoard().getPosY();

        for(int i = 0; i < gameMachine.getPlayers().length; i++) {
            //Find player rotation
            Player currentPlayer = gameMachine.getPlayer(i);
            currentPlayer.setRotation(workOutPlayerRotation(i));

            peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(currentPlayer.getPlayerDeck(), anchorX, anchorY,
                    PLAYER_DECK_PLACEMENT_X, PLAYER_DECK_PLACEMENT_Y, currentPlayer.getRotation(),
                    peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), PLAYER_DECK_ROTATION));


            peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(currentPlayer.getBoard().getPlayAreas()[i], anchorX, anchorY,
                    BOARD_PLAYER_AREA_OFFSET_X, BOARD_PLAYER_AREA_OFFSET_Y, currentPlayer.getRotation(),
                    peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), workOutPlayerRotation(i)  + BOARD_PLAYER_AREA_ROTATION));


            peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(currentPlayer.getHand(), placementX, placementY,
                    PLAYER_HAND_OFFSET_X, PLAYER_HAND_OFFSET_Y, currentPlayer.getRotation(),
                    peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), PLAYER_HAND_ROTATION));


            float manaRelOffsetFromLastX = 0;
            float manaRelOffsetFromLastY = 0;
            int counter = 0;
            for (Mana mana : currentPlayer.getMana()) {
                peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(mana, placementX, placementY,
                        MANA_OFFSET_X + manaRelOffsetFromLastX, MANA_OFFSET_Y + manaRelOffsetFromLastY, currentPlayer.getRotation(),
                        peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), MANA_ROTATION));
                manaRelOffsetFromLastX += MANA_PADDING;
                counter++;
                if(counter == 5) {
                    manaRelOffsetFromLastX = 0;
                    manaRelOffsetFromLastY -= MANA_HEIGHT;
                }
            }


            peterPiperPickedAPlacer.placePlaceableRelativeToAnchorPoint(currentPlayer.getHero(), anchorX, anchorY,
                    HERO_OFFSET_X, HERO_OFFSET_Y, currentPlayer.getRotation(),
                    peterPiperPickedAPlacer.workOutObjectRotation(currentPlayer.getRotation(), PLAYER_DECK_ROTATION));
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
