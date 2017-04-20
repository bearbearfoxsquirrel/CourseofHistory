package test.puigames.courseofhistory.framework.game.assets.players.events;

import test.puigames.courseofhistory.framework.game.assets.Animation;

/**
 * Created by Michael on 27/03/2017.
 */

public abstract class Attack extends PlayerEvent implements Eventable{
    protected Damageable.Attackable sourceObject;
    private Damageable targetObject;

    public Attack(Damageable.Attackable sourceObject, Damageable targetObject, float eventDuration, Animation animation) {
        super(animation, eventDuration);
        this.sourceObject = sourceObject;
        this.targetObject = targetObject;
    }

    @Override
    public void startEvent() {
        sourceObject.attack(targetObject);
    }

}
