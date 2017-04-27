package test.puigames.courseofhistory.framework.engine.collision;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;

/**
 * Created by Jordan on 26/01/2017.
 */

public class CollisionDetector implements Collision
{
    //determine if the two specified bounding boxes are in collision
    public boolean isCollision(BoundingBox boundingBox1, BoundingBox boundingBox2)
    {
        return (boundingBox1.left < boundingBox2.right)
                && (boundingBox1.right > boundingBox2.left)
                && (boundingBox1.top < boundingBox2.bottom)
                && (boundingBox1.bottom > boundingBox2.top);
    }

    //object = object2
    //this = object1
    public boolean checkForCollision(BoundingBox boundingBox1, BoundingBox boundingBox2)
    {
        if(boundingBox1.equals(boundingBox2))
            return false;
        else if(boundingBox1.isOverlapping(boundingBox2))
            return true;
        return false;
    }

    public void resolveCollision(GameObject object1, GameObject object2, float overlapModifier)
    {
        if(overlapModifier == object1.MIN_OVERLAP_ALLOWANCE || object1.equals(object2))
            return;
        else
            object1.boundingBox.getCollisionDetector()
                    .determineAndResolveCollision(object1, object2, overlapModifier);
    }

    //determines which bound was broken and (hopefully) resolves the collision peacefully
    private void determineAndResolveCollision(GameObject object1, GameObject
            object2, double overlapModifier)
    {
        BoundingBox.bound collisionType = BoundingBox.bound.NONE;

        BoundingBox boundingBox1 = object1.boundingBox;
        BoundingBox boundingBox2 = object2.boundingBox;

        if(checkForCollision(boundingBox1, boundingBox2))
        {
            //determine side of *least collision*
            float collisionDepth = Float.MAX_VALUE;

            //Check top
            float topOverlap = (boundingBox2.bottom - boundingBox1.top);
            if(topOverlap > 0.0f && topOverlap < collisionDepth)
            {
                collisionType = BoundingBox.bound.TOP;
                collisionDepth = topOverlap;
            }

            //Check bottom
            float bottomOverlap = (boundingBox1.bottom - boundingBox2.top);
            if(bottomOverlap > 0.0f && bottomOverlap < collisionDepth)
            {
                collisionType = BoundingBox.bound.BOTTOM;
                collisionDepth = bottomOverlap;
            }

            //Check right
            float rightOverlap = (boundingBox1.right - boundingBox2.left);
            if(rightOverlap > 0.0f && rightOverlap < collisionDepth)
            {
                collisionType = BoundingBox.bound.RIGHT;
                collisionDepth = rightOverlap;
            }

            //Check left
            float leftOverlap = (boundingBox2.right - boundingBox1.left);
            if(leftOverlap > 0.0f && leftOverlap < collisionDepth)
            {
                collisionType = BoundingBox.bound.LEFT;
                collisionDepth = leftOverlap;
            }

            //Separate if needed
            switch (collisionType)
            {
                case TOP:
                    object1.origin.y += (collisionDepth * overlapModifier);
                    break;
                case BOTTOM:
                    object1.origin.y -= (collisionDepth * overlapModifier);
                    break;
                case LEFT:
                    object1.origin.x += (collisionDepth * overlapModifier);
                    break;
                case RIGHT:
                    object1.origin.x -= (collisionDepth * overlapModifier);
                    break;
                case NONE:
                    break;
            }
        }
    }

    /**
     * Attempts to separate two objects. Usually called when two object's origins are the same
     * @param object1 - object to separate
     * @param object2 - (other) object to separate
     * @param separationAmount - amount objects will be separated by
     */
    public void separate(GameObject object1, GameObject object2, float separationAmount)
    {

    }

    /**
     * Only needed to be called if the bounding box you are testing against
     * this one is not encapsulated
     * @see test.puigames.courseofhistory.framework.engine.gameobjects
     * .properties.BoundingBox.isEncapsulated();
     *
     * Keeps sprite2 within sprite1's bounding box, plus some padding: 2
     *
     * @param sprite1 - the game object's bounding box we are testing against sprite
     *          we are containing the sprite within this bounding box
     * @param sprite2 - sprite you want to keep in bounds
     */
    public void keepInsideBoundingBox(Sprite sprite1, Sprite sprite2)
    {
        if(sprite2.boundingBox.left < sprite1.boundingBox.left)
            sprite2.origin.x = ((sprite1.boundingBox.left + sprite2.halfWidth) + 2);
        else if(sprite2.boundingBox.right > sprite1.boundingBox.right)
            sprite2.origin.x = ((sprite1.boundingBox.right - sprite2.halfWidth) - 2);
        else if(sprite2.boundingBox.top < sprite1.boundingBox.top)
            sprite2.origin.y = ((sprite1.boundingBox.top + sprite2.halfHeight) + 2);
        else if(sprite2.boundingBox.bottom > sprite1.boundingBox.bottom)
            sprite2.origin.y = ((sprite1.boundingBox.bottom - sprite2.halfHeight) - 2);
    }
}
