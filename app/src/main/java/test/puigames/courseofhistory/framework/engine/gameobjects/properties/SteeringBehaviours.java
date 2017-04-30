package test.puigames.courseofhistory.framework.engine.gameobjects.properties;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;

/**
 * Created by Jordan on 06/03/2017.
 */

public class SteeringBehaviours
{
    //variables for arrive function
    private static Vector arriveDirection = new Vector();
    private static Vector arriveSpeed = new Vector();

    //Store acceleration vector for the specified sprite
    //towards a target position
    public static void seek(Sprite seekingSprite, Vector targetPos, Vector acceleration)
    {
        //return if sprite has reached target
        if(targetPos.getVectorX() == seekingSprite.getOrigin().getOriginX() && targetPos.getVectorY() == seekingSprite.getOrigin().getOriginX())
            acceleration.set(Vector.getZero());
        else
        {
            //determine seek direction
            acceleration.set(targetPos.getVectorX() - seekingSprite.getOrigin().getOriginX(),
                    targetPos.getVectorY() - seekingSprite.getOrigin().getOriginY());
            acceleration.normalise();
            acceleration.multiply(seekingSprite.getMaxAcceleration());
        }
    }

    //Output an acceleration vector for the specified sprite that will cause
    //it to arrive with a stopping velocity at a specified target
    public static void arrive(Sprite arrivingSprite, Vector targetPos, Vector acceleration)
    {
        //Determine the current distance between sprite and target
        arriveDirection.set(targetPos.getVectorX() - arrivingSprite.getOrigin().getOriginX(),
                targetPos.getVectorY() - arrivingSprite.getOrigin().getOriginY());
        float distance = arriveDirection.length();
        arriveDirection.divide(distance);

        //determine the slow down radius
        float slowDownRadius = arrivingSprite.getMaxVelocity() * arrivingSprite.getMaxVelocity()
                / arrivingSprite.getMaxAcceleration();

        //determine target speed
        arriveSpeed.set(arriveDirection);
        if(distance > slowDownRadius)
            arriveSpeed.multiply(arrivingSprite.getMaxVelocity());
        else
            arriveSpeed.multiply(arrivingSprite.getMaxVelocity() * distance / slowDownRadius);

        //determine the arrival acceleration
        acceleration.set(arriveSpeed);
        acceleration.subtract(arrivingSprite.getVelocity());

        if(acceleration.lengthSquared() >
                arrivingSprite.getMaxAcceleration() * arrivingSprite.getMaxAcceleration())
        {
            acceleration.normalise();
            acceleration.multiply(arrivingSprite.getMaxAcceleration());
        }
    }

    public static Vector getArriveDirection() {
        return arriveDirection;
    }

    public static void setArriveDirection(Vector arriveDirection) {
        SteeringBehaviours.arriveDirection = arriveDirection;
    }

    public static Vector getArriveSpeed() {
        return arriveSpeed;
    }

    public static void setArriveSpeed(Vector arriveSpeed) {
        SteeringBehaviours.arriveSpeed = arriveSpeed;
    }
}
