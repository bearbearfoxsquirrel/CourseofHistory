package test.puigames.courseofhistory.framework.game.assets.cards;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import test.puigames.courseofhistory.framework.engine.gameobjects.GameObject;
import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.gameobjects.properties.BoundingBox;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.game.assets.boards.Board;

/**
 * Created by Jordan on 10/11/2016.
 */

public class Card extends Sprite {
    //private variables

    //constructor
    public Card(Bitmap cardImage) {
        super(cardImage, 60, 83);
    }

    public void translateCard(float updatedX, float updatedY) {
        this.origin.x -= ((origin.x - updatedX) / 2);
        this.origin.y -= ((origin.y - updatedY) / 2);

        maxAcceleration = 60.0f;
        maxVelocity = 20.0f;
        overlapAllowance = MAX_OVERLAP_ALLOWANCE / 5;
    }

    //TODO: make card collision checks on not dragged
    //FIXME: smooth out bounds resolution to be less jaggy
    //TODO:: investigate time and frame timing

    //draw
    @Override
    public void draw(Canvas canvas, float deltaTime) {
        super.draw(canvas, deltaTime);
    }
}
