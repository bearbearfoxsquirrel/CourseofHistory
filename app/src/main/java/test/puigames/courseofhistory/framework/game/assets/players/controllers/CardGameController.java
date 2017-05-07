package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.controlling.Controlling;
import test.puigames.courseofhistory.framework.engine.screen.Screen;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.game.assets.players.events.Eventable;

/**
 * Created by Michael on 03/03/2017.
 */

public abstract class CardGameController implements Controlling {
    protected Player player;
    protected ArrayList<Eventable> playerEvents;
    protected Screen screen;

    public CardGameController(Screen screen, Player player) {
        this.screen = screen;
        this.player = player;
    }

    @Override
    public abstract void update(float deltaTime);

    @Override
    public void startTicking(Screen screen) {
        if (!screen.isInUpdateables(this))
            screen.addToUpdateables(this);
    }

    @Override
    public void stopTicking(Screen screen) {
        if (screen.isInUpdateables(this))
            screen.removeFromUpdateables(this);
    }

    public CardGameController() {
        this.playerEvents = new ArrayList<>();
    }

    @Override
    public void possessPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Eventable> getPlayerEvents() {
        return playerEvents;
    }

    public void setPlayerEvents(ArrayList<Eventable> playerEvents) {
        this.playerEvents = playerEvents;
    }
}