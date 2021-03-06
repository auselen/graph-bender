package se.dolkow.graphbender.ui;

import java.util.ArrayList;

import se.dolkow.graphbender.scene.backgrounds.ColorBackground;
import se.dolkow.graphbender.scene.backgrounds.PictureBackground;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class RenderableManager {

	private static final String TAG = "RenderableManager";
	private final TitleScreen mTitleScreen;
	private final GameScreen mGameScreen;
	
	private Screen mActiveScreen;
	private Background mBackground;
	
	private final ArrayList<Overlay> mOverlays = new ArrayList<Overlay>();
	private int mHeight;
	private int mWidth;
	private Vibrator mVibrator;
	
	public RenderableManager() {
		mTitleScreen = new TitleScreen(this);
		mGameScreen = new GameScreen(this);
		PictureBackground.loadBitmap();
		quitGame();
	}

	private void setScreen(Screen sc) {
		mOverlays.clear();
		mActiveScreen = sc;
		sc.sizeChanged(mWidth, mHeight);
	}
	
	public void startGame() {
		mGameScreen.restart();
		setScreen(mGameScreen);
		setBackground(new PictureBackground());
		addOverlay(OverlayFactory.getRandom("Connect those Jellyfishes!"));
	}
	
	public void setBackground(Background bg) {
		mBackground = bg;
		mBackground.sizeChanged(mWidth, mHeight);
	}
	
	public void addOverlay(Overlay ov) {
		ov.sizeChanged(mWidth, mHeight);
		mOverlays.add(ov);
	}

    public void sizeChanged(int width, int height) {
    	mWidth = width;
    	mHeight = height;
    	if (mBackground != null) {
    		mBackground.sizeChanged(width, height);
    	}
	    mActiveScreen.sizeChanged(width, height);
	    final int N = mOverlays.size();
	    for (int i=0; i<N; ++i) {
	    	mOverlays.get(i).sizeChanged(width, height);
	    }
    }

    public void update(long frameTime, long deltaTime) {
	    mActiveScreen.update(frameTime, deltaTime);
	    int N = mOverlays.size();
	    for (int i=N-1; i>=0; --i) {
	    	boolean alive = mOverlays.get(i).update(frameTime, deltaTime);
	    	if (!alive) {
	    		mOverlays.remove(i);
	    	}
	    }
    }

    public void draw(Canvas c, long frameTime, long deltaTime) {
    	if (mBackground != null) {
    		mBackground.draw(c, frameTime, deltaTime);
    	} else {
    		c.drawColor(0);
    	}
		mActiveScreen.draw(c, frameTime, deltaTime);
	    final int N = mOverlays.size();
	    for (int i=0; i<N; ++i) {
	    	mOverlays.get(i).draw(c, frameTime, deltaTime);
	    }
    }

    public void handleTouch(MotionEvent event) {
	    mActiveScreen.handleTouch(event);
    }

	public boolean backPressed() {
		return mActiveScreen.onBackPressed();
	}

	public void registerVibrator(Vibrator bzzz) {
		mVibrator = bzzz;
	}

	public void bzzz() {
		mVibrator.vibrate(50);
	}

	public boolean handleKeyDown(int keyCode, KeyEvent event) {
		Log.d(TAG, "keydown " + keyCode + " " + event);
		return mActiveScreen.handleKeyDown(keyCode, event);
	}

	public void quitGame() {
		setScreen(mTitleScreen);
		setBackground(new ColorBackground(0xffff66dd));
	}	
}
