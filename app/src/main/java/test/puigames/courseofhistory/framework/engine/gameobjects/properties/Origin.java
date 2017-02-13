package test.puigames.courseofhistory.framework.engine.gameobjects.properties;

/**
 * Created by Michael on 21/11/2016.
 */

public class Origin {
    public float x;
    public float y;

    public Origin(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Origin{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
