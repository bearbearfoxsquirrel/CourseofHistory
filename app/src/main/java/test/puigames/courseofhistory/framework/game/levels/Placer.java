package test.puigames.courseofhistory.framework.game.levels;

import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Origin;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.Placeable;
import test.puigames.courseofhistory.framework.engine.screen.Screen;

public class Placer {
    Screen screenToPlaceTo;
    float anchorPosX;
    float anchorPosY;


    public Placer(Screen screen) {
        this.screenToPlaceTo = screen;
    }

    public Placer(Screen screen, float anchorX, float anchorY) {
        this.screenToPlaceTo = screen;
        this.anchorPosX = anchorX;
        this.anchorPosY = anchorY;
    }

    float workOutObjectRotation(float playerRotation, float objectRotation) {
        return playerRotation + objectRotation;
    }//finds a position either x or y rotated around a point

    public void placePlaceableRelativeToAnchorPoint(Placeable placeable, float anchorPosX, float anchorPosY, float offsetX, float offsetY, float rotationAroundAnchor, float placeableRotation) {
        //Placing the object where it should be
        Origin rotatedPlacement = findObsolutePositionRelativeToAnchor(anchorPosX, anchorPosY, offsetX, offsetY, rotationAroundAnchor);
        placeable.place(screenToPlaceTo, rotatedPlacement.x, rotatedPlacement.y, placeableRotation);
    }

    public void placePlaceableRelativeToAnchorPoint(Placeable placeable, float offsetX, float offsetY, float rotationAroundAnchor, float placeableRotation) {
        //Placing the object where it should be
        Origin rotatedPlacement = findObsolutePositionRelativeToAnchor(this.anchorPosX, this.anchorPosY, offsetX, offsetY, rotationAroundAnchor);
        placeable.place(screenToPlaceTo, rotatedPlacement.x, rotatedPlacement.y, placeableRotation);
    }


    public Origin findObsolutePositionRelativeToAnchor(float anchorPosX, float anchorPosY, float offsetX, float offsetY, float rotationAroundAnchor) {
        rotationAroundAnchor = rotationAroundAnchor * ((float) Math.PI / 180); //Convert degrees to radians
        float sin = (float) Math.sin(rotationAroundAnchor);
        float cos = (float) Math.cos(rotationAroundAnchor);

        offsetX += anchorPosX; //Get the absolute positions to relative positions
        offsetY += anchorPosY;

        //Rotate clockwise
        float rotatedX = anchorPosX + ((offsetX - anchorPosX) * cos) + ((offsetY - anchorPosY) * sin); // new position of x after rotating around the anchor
        float rotatedY = anchorPosY + ((offsetX - anchorPosX) * -sin) + ((offsetY - anchorPosY) * cos); // New position of y after rotating around the anchor
        return new Origin(rotatedX, rotatedY);
    }

    float findAbsolutePosition(float anchor, float offset) {
        return anchor + offset;
    }
}