package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;
import android.graphics.Rect;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;

/**
 * Created by Tommy on 10/03/2017.
 */

public class Animation extends Sprite {

    // ////////////////////////////////////////////////////////////////////
    // Animation Properties
    // ////////////////////////////////////////////////////////////////////

    /**
     * Bitmap holding the frames of this animation
     */
    private Bitmap animationFrames;

    public Bitmap getBitmap() {
        return animationFrames;
    }

    /**
     * Width and height of each frame of the animation
     */
    private int frameWidth;
    private int frameHeight;

    /**
     * Number of frames in the animation
     */
    private int frameCount;

    /**
     * Index of the current frame of animation
     */
    private int currentFrame;

    /**
     * Display period for each frame alongside a frame timer
     */
    private double frameTimer;
    private double framePeriod;

    /**
     * Boolean flag determining if the animation should loop
     */
    private boolean isLooping = false;

    /**
     * Boolean flag determining if the animation is currently playing
     */
    private boolean isPlaying = false;

    // ////////////////////////////////////////////////////////////////////
    // Animation Constructor and Methods
    // ////////////////////////////////////////////////////////////////////

    /**
     * Create a new animation
     *
     * @param //animationFrames Bitmap holding the frames of the animation
     * @param frameCount Number of horizontal frames in the animation
     *        (assumed to be of equal width)
     */

    public Animation(Bitmap bitmap, float spawnX, float spawnY, int width, int height, int frameCount )
    {
        super(bitmap, spawnX, spawnY, width, height);

        this.animationFrames = bitmap;
        this.frameCount = frameCount;

        frameHeight = animationFrames.getHeight();
        frameWidth = animationFrames.getWidth() / frameCount;
    }

    /**
     * Trigger playback of this animation
     *
     * @param animationPeriod Period over which the animation should play
     * @param loop True if the animation should play repeatedly
     */
    public void play(double animationPeriod, boolean loop) {
        framePeriod = animationPeriod / (double) frameCount;
        currentFrame = -1;
        isLooping = loop;

        isPlaying = true;
    }

    /**
     * Update which frame of the animation should be displayed
     *
     * @param elapsedTime Elapsed time since the last update
     */
    public void update(double elapsedTime) {
        if (!isPlaying)
            return;

        // If this is the first time update has been called since the
        // play method was called then reset the current frame and timer
        if (currentFrame == -1) {
            currentFrame = 0;
            frameTimer = 0.0;
        }

        // Update the amount of time accumulated against this frame
        frameTimer += elapsedTime;

        // If the frame display duration has been exceeded then try to
        // go on to the next frame, looping or stopping if the end of
        // the animation has been reached.
        if (frameTimer >= framePeriod) {
            currentFrame++;
            if (currentFrame >= frameCount) {
                if (isLooping) {
                    currentFrame = 0;
                } else {
                    currentFrame = frameCount - 1;
                    isPlaying = false;
                }
            }

            // 'Reset' the frame timer
            frameTimer -= framePeriod;
        }
    }

    /**
     * Update the specified rect object to contain the region of the bitmap
     * holding the current frame of animation.
     *
     * @param sourceRect Rect object to be updated
     */
    public void getSourceRect(Rect sourceRect) {
        if(currentFrame >= 0)
            sourceRect.set(currentFrame * frameWidth, 0, currentFrame
                    * frameWidth + frameWidth, frameHeight);
    }
}