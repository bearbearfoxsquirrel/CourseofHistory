package test.puigames.courseofhistory.framework.engine.gameloop;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import test.puigames.courseofhistory.R;
import test.puigames.courseofhistory.framework.engine.GameProperties;
import test.puigames.courseofhistory.framework.engine.audio.AndroidAudio;
import test.puigames.courseofhistory.framework.engine.audio.Audio;
import test.puigames.courseofhistory.framework.engine.inputfriends.InputBuddy;
import test.puigames.courseofhistory.framework.engine.resourceloading.ResourceFetcher;
import test.puigames.courseofhistory.framework.engine.screen.Screen;

/**
 * Created by Jordan on 08/11/2016.
 */

public class MainGame extends Activity implements GameProperties, Runnable
{
    private FastRenderView renderView;
    private ResourceFetcher resourceFetcher;
    private Audio audio;
    private Screen screen;
    private WakeLock wakeLock;
    private Thread renderThread = null;
    private MediaPlayer mediaPlayer;

    private volatile boolean running = false;
    private volatile boolean drawNeeded = false;

    private long startTime = System.nanoTime();

    private int screenWidth;
    private int screenHeight;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        boolean isLandscape = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;

        renderView = new FastRenderView(this);

        audio = new AndroidAudio(this);

        resourceFetcher = new ResourceFetcher(this);
        screen = getStartScreen();
        setContentView(renderView);

        //Power management - prevent screen from dimming
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CourseOfHistory");

        //MediaPlayer intro
        mediaPlayer = MediaPlayer.create(this, R.raw.intro_theme);
        mediaPlayer.start();
    }

    //GameProperties Loop
    @Override
    public void run() {
        while(running) {
            //synchronises with the UI thread
            synchronized (renderView) {
                //checks if a draw is needed
                if (drawNeeded) {
                    drawNeeded = false;
                    //tells UI thread to call it's onDraw(Canvas) method
                    //Update and Draw calls for screen are in there
                    renderView.postInvalidate();
                }
            }

        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        drawNeeded = true;
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
        mediaPlayer.start();

        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();
        mediaPlayer.pause();
        boolean joined = false;
        while (!joined) {
            try {
                renderThread.join();
                joined = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(isFinishing())   //if activity is destroyed, clean up
            screen.dispose();
    }


    public void setScreen(Screen screen) throws IllegalArgumentException {
        if(screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        this.screen = screen;
        screen.update(0);
    }

    public void calculateScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    public int getScreenWidth(){return screenWidth;}

    public int getScreenHeight(){return screenHeight;}

    public InputBuddy getInput()
    {
        return renderView.getInputBuddy();
    }

    @Override
    public ResourceFetcher getResourceFetcher() {
        return resourceFetcher;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    public Screen getCurrentScreen() {
        return screen;
    }

    @Override
    public Screen getStartScreen()
    {
        return null;
    }

    public FastRenderView getRenderView() {
        return renderView;
    }

    public void setRenderView(FastRenderView renderView) {
        this.renderView = renderView;
    }

    public void setResourceFetcher(ResourceFetcher resourceFetcher) {
        this.resourceFetcher = resourceFetcher;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Screen getScreen() {
        return screen;
    }

    public WakeLock getWakeLock() {
        return wakeLock;
    }

    public void setWakeLock(WakeLock wakeLock) {
        this.wakeLock = wakeLock;
    }

    public Thread getRenderThread() {
        return renderThread;
    }

    public void setRenderThread(Thread renderThread) {
        this.renderThread = renderThread;
    }

    @Override
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @Override
    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isDrawNeeded() {
        return drawNeeded;
    }

    public void setDrawNeeded(boolean drawNeeded) {
        this.drawNeeded = drawNeeded;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }




}
