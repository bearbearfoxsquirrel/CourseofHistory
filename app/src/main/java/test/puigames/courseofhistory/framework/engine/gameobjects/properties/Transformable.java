package test.puigames.courseofhistory.framework.engine.gameobjects.properties;

/**
 * Created by Michael on 30/04/2017.
 */

//Interface that could be added for animations ect
public interface Transformable {
    void translate(float positionXToAdd, float positionYToAdd);

    void setNewPosition(float positionX, float positionY);

    void setRotatation(float rotation);

    void adjustRotation(float rotation);

    void rotateAroundPoint(float rotation, float anchorX, float anchorY);

    void setDimensions(float widthNew, float heightNew);
}
