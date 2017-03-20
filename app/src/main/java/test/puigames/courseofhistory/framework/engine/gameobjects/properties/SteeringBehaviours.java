package test.puigames.courseofhistory.framework.engine.gameobjects.properties;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;

/**
 * Created by Jordan on 06/03/2017.
 */

public class SteeringBehaviours
{

    //Store acceleration vector for the specified sprite
    //towards a target position
    public static void seek(Sprite seekingSprite, Vector targetPos, Vector acceleration)
    {
        //return if sprite has reached target
        if(targetPos.x == seekingSprite.origin.x && targetPos.y == seekingSprite.origin.x)
            acceleration.set(Vector.Zero);
        else
        {
            //determine seek direction
            acceleration.set(targetPos.x - seekingSprite.origin.x,
                    targetPos.y - seekingSprite.origin.y);
            acceleration.normalise();
            acceleration.multiply(seekingSprite.maxAcceleration);
        }
    }

    //variables for arrive function
    private static Vector arriveDirection = new Vector();
    private static Vector arriveSpeed = new Vector();

    //Output an acceleration vector for the specified sprite that will cause
    //it to arrive with a stopping velocity at a specified target
    public static void arrive(Sprite arrivingSprite, Vector targetPos, Vector acceleration)
    {
        //Determine the current distance between sprite and target
        arriveDirection.set(targetPos.x - arrivingSprite.origin.x,
                targetPos.y - arrivingSprite.origin.y);
        float distance = arriveDirection.length();
        arriveDirection.divide(distance);

        //determine the slow down radius
        float slowDownRadius = arrivingSprite.maxVelocity * arrivingSprite.maxVelocity
                / arrivingSprite.maxAcceleration;

        //determine target speed
        arriveSpeed.set(arriveDirection);
        if(distance > slowDownRadius)
            arriveSpeed.multiply(arrivingSprite.maxVelocity);
        else
            arriveSpeed.multiply(arrivingSprite.maxVelocity * distance / slowDownRadius);

        //determine the arrival acceleration
        acceleration.set(arriveSpeed);
        acceleration.subtract(arrivingSprite.velocity);

        if(acceleration.lengthSquared() >
                arrivingSprite.maxAcceleration * arrivingSprite.maxAcceleration)
        {
            acceleration.normalise();
            acceleration.multiply(arrivingSprite.maxAcceleration);
        }
    }
}
