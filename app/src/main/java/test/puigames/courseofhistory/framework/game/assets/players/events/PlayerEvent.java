package test.puigames.courseofhistory.framework.game.assets.players.events;

import test.puigames.courseofhistory.framework.game.assets.Animation;

/**
 * Created by Michael on 27/03/2017.
 */

public abstract class PlayerEvent implements Interactable {
    private Interactable sourceObject;
    private Interactable targetObject;

    protected float eventDuration;

    //sound eventSound;
    Animation eventAnimation;

    public PlayerEvent(Interactable sourceObject, Interactable targetObject, float eventDuration, Animation animation) {
        this.sourceObject = sourceObject;
        this.targetObject = targetObject;
        this.eventDuration = eventDuration;
        this.eventAnimation = animation;
    }

    public abstract void startEvent();

    public abstract void applyEffect();

    public abstract void update();

}
