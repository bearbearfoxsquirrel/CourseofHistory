package test.puigames.courseofhistory.framework.engine;

import android.util.Log;

/**
 * Created by Jordan on 26/01/2017.
 */

public class CollisionDetector implements Collision
{
    //determine if the two specified bounding boxes are in collision
    public boolean isCollision(BoundingBox boundingBox1,
                               BoundingBox boundingBox2)
    {
        return (boundingBox1.left < boundingBox2.right)
                && (boundingBox1.right > boundingBox2.left)
                && (boundingBox1.top < boundingBox2.bottom)
                && (boundingBox1.bottom > boundingBox2.top);
    }

    //determines which bound was broken and (hopefully) resolves the collision peacefully
    public BoundingBox.bound determineAndResolveCollision(Sprite sprite1, Sprite sprite2)
    {
        BoundingBox.bound collisionType = BoundingBox.bound.NONE;

        BoundingBox boundingBox1 = sprite1.getBoundingBox();
        BoundingBox boundingBox2 = sprite2.getBoundingBox();

        if(isCollision(boundingBox1, boundingBox2))
        {
            //determine side of *least collision*
            float collisionDepth = Float.MAX_VALUE;

            //Check top
            float topOverlap = (boundingBox2.bottom - boundingBox1.top);
            if(topOverlap > 0 && topOverlap < collisionDepth)
            {
                collisionType = BoundingBox.bound.TOP;
                collisionDepth = topOverlap;
            }

            //Check bottom
            float bottomOverlap = (boundingBox1.bottom - boundingBox2.top);
            if(bottomOverlap > 0 && bottomOverlap < collisionDepth)
            {
                collisionType = BoundingBox.bound.BOTTOM;
                collisionDepth = bottomOverlap;
            }

            //Check right
            float rightOverlap = (boundingBox1.right - boundingBox2.left);
            if(rightOverlap > 0 && rightOverlap < collisionDepth)
            {
                collisionType = BoundingBox.bound.RIGHT;
                collisionDepth = rightOverlap;
            }

            //Check left
            float leftOverlap = (boundingBox2.right - boundingBox1.left);
            if(leftOverlap > 0 && leftOverlap < collisionDepth)
            {
                collisionType = BoundingBox.bound.LEFT;
                collisionDepth = leftOverlap;
            }

            //Separate if needed
            switch (collisionType)
            {
                case TOP:
                    sprite1.origin.y += (collisionDepth);
                    break;
                case BOTTOM:
                    sprite1.origin.y -= (collisionDepth);
                    break;
                case LEFT:
                    sprite1.origin.x += (collisionDepth);
                    break;
                case RIGHT:
                    sprite1.origin.x -= (collisionDepth);
                    break;
                case NONE:
                    break;
            }
        }
        return collisionType;
    }
}
