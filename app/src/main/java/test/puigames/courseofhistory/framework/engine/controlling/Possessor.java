package test.puigames.courseofhistory.framework.engine.controlling;


import test.puigames.courseofhistory.framework.game.assets.players.Player;

/**
 * Created by Michael on 31/03/2017.
 */

public interface Possessor {
    void possessPlayer(Player player);

    void update(float deltaTime);

    Player getPlayer();
}
