package test.puigames.courseofhistory.framework.game.assets.players.events;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.game.assets.Animation;
import test.puigames.courseofhistory.framework.game.assets.cards.CharacterCard;

/**
 * Created by Michael on 27/03/2017.
 */

public abstract class Attack extends PlayerEvent {
    public Attack(CharacterCard sourceObject, GameObject targetObject, float eventDuration, Animation animation) {
        super(sourceObject, targetObject, eventDuration, animation);
      //  this.source = sourceObject;
       // this.target = targetObject;
    }

    @Override
    public void startEvent() {

    }

    @Override
    public void applyEffect() {

        //source.fight(target);
        //sourceObject(targetObject);

    }

}
