package test.puigames.courseofhistory.framework.engine.collision;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;

/**
 * Created by Jordan on 08/02/2017.
 */

public interface Collision
{
    boolean checkForCollision(BoundingBox boundingBox1, BoundingBox boundingBox2);

    void resolveCollision(GameObject object1, GameObject object2, float overlapModifier);
}
