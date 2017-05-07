package test.puigames.courseofhistory.framework.game.assets.abilities;

/**
 * Created by Jordan on 01/05/2017.
 */

public abstract class Ability
{
    public enum AbilityType
    {
        BUFF, INSPIRE, CHARGE, TAUNT, HEAL, UNIQUE
    }

    protected AbilityType abilityType;

    public Ability(AbilityType abilityType)
    {
        this.abilityType = abilityType;
    }
}
