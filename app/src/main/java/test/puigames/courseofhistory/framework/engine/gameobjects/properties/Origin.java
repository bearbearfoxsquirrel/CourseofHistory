package test.puigames.courseofhistory.framework.engine.gameobjects.properties;

/**
 * Created by Michael on 21/11/2016.
 */

public class Origin {
    private float x;
    private float y;

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

    public Origin(Origin origin) {
        this.x = origin.x;
        this.y = origin.y;
    }

    public boolean equals(Origin other) {
        return (this.x == other.x && this.y == other.y);
    }

    public void setOrigin(Origin origin) {
        this.x = origin.x;
        this.y = origin.y;
    }

    public void setOrigin(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getOriginX() {
        return x;
    }

    public void setOriginX(float x) {
        this.x = x;
    }

    public float getOriginY() {
        return y;
    }

    public void setOriginY(float y) {
        this.y = y;
    }
}
