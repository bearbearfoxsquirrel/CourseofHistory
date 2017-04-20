package test.puigames.courseofhistory.framework.game.assets.players.events;

import test.puigames.courseofhistory.framework.game.assets.Animation;

/**
 * Created by Michael on 30/03/2017.
 */

public class CardAttack extends Attack implements Eventable {
    private Damageable.Attackable targetObject;

    public CardAttack(Damageable.Attackable sourceObject, Damageable.Attackable targetObject, float eventDuration, Animation animation) {
        super(sourceObject, targetObject, eventDuration, animation);
        this.targetObject = targetObject;
    }

    @Override
    public void startEvent() {
        sourceObject.attack(targetObject);
        targetObject.attack(sourceObject);
    }

    @Override
    public void update(float deltaTime) {
        eventDuration -= deltaTime;

        if (eventDuration <= 0 )
            startEvent();
        //TODO when certain point in aniomation apply effect
    }
}
