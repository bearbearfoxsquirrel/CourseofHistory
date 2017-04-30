package test.puigames.courseofhistory.framework.game.assets;

import android.graphics.Bitmap;
import android.graphics.Rect;

import test.puigames.courseofhistory.framework.engine.gameobjects.Sprite;
import test.puigames.courseofhistory.framework.engine.screen.Screen;

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
     * @param// animationFrames Bitmap holding the frames of the animation
     * @param frameCount Number of horizontal frames in the animation
     *        (assumed to be of equal width)
     */

    public Animation(Screen screen, Bitmap bitmap, int width, int height, int frameCount )
    {
        super(screen, bitmap, width, height);

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
    public Bitmap getBitmap() {
        return animationFrames;
    }

    public Bitmap getAnimationFrames() {
        return animationFrames;
    }

    public void setAnimationFrames(Bitmap animationFrames) {
        this.animationFrames = animationFrames;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public double getFrameTimer() {
        return frameTimer;
    }

    public void setFrameTimer(double frameTimer) {
        this.frameTimer = frameTimer;
    }

    public double getFramePeriod() {
        return framePeriod;
    }

    public void setFramePeriod(double framePeriod) {
        this.framePeriod = framePeriod;
    }

    public boolean isLooping() {
        return isLooping;
    }

    public void setLooping(boolean looping) {
        isLooping = looping;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}