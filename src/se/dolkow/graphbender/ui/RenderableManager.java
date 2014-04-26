package se.dolkow.graphbender.ui;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.os.Vibrator;
import android.view.MotionEvent;

public class RenderableManager {

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
		mActiveScreen = mTitleScreen;
	}

	private void setScreen(Screen sc) {
		mActiveScreen = sc;
		sc.sizeChanged(mWidth, mHeight);
	}
	
	public void startGame() {
		setScreen(mGameScreen);
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
		if (mActiveScreen == mGameScreen) {
			mActiveScreen = mTitleScreen;
			return false;
		} else {
			return true;
		}
	}

	public void registerVibrator(Vibrator bzzz) {
		mVibrator = bzzz;
	}

	public void bzzz() {
		mVibrator.vibrate(100);
	}
	
}
