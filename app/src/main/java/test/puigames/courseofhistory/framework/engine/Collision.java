package test.puigames.courseofhistory.framework.engine;

/**
 * Created by Jordan on 08/02/2017.
 */

public interface Collision
{
    public boolean isCollision(BoundingBox boundingBox1,
                               BoundingBox boundingBox2);

    public BoundingBox.bound determineAndResolveCollision(Sprite sprite1, Sprite sprite2);
}
