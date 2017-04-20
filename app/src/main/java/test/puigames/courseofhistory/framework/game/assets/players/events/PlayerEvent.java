package test.puigames.courseofhistory.framework.game.assets.players.events;

import test.puigames.courseofhistory.framework.game.assets.Animation;

/**
 * Created by Michael on 30/03/2017.
 */

public abstract class PlayerEvent implements Eventable {
    protected Animation animation;
    protected float eventDuration;

    public PlayerEvent(Animation animation, float eventDuration){
        this.animation = animation;
        this.eventDuration = eventDuration;
    }
}
