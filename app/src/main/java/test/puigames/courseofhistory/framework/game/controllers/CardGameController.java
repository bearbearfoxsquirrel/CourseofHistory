package test.puigames.courseofhistory.framework.game.controllers;

import test.puigames.courseofhistory.framework.engine.Controlling.Possessor;

/**
 * Created by Michael on 03/03/2017.
 */

public abstract class CardGameController implements Possessor {
    public Player player;

    public abstract void update(float deltaTime);

    @Override
    public void possessPlayer(Player player) {

        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}