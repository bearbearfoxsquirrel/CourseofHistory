package test.puigames.courseofhistory.framework.game.assets.players.controllers;

import java.util.ArrayList;

import test.puigames.courseofhistory.framework.engine.controlling.Controlling;
import test.puigames.courseofhistory.framework.game.assets.players.Player;
import test.puigames.courseofhistory.framework.game.assets.players.events.Eventable;

/**
 * Created by Michael on 03/03/2017.
 */

public abstract class CardGameController implements Controlling {
    public Player player;
    public ArrayList<Eventable> playerEvents;

    @Override
    public abstract void update(float deltaTime);

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
}