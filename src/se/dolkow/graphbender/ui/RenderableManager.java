package se.dolkow.graphbender.ui;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.view.MotionEvent;


public class RenderableManager {

	private final TitleScreen mTitleScreen;
	private final GameScreen mGameScreen;
	
	private Screen mActiveScreen;
	
	private final ArrayList<Overlay> mOverlays = new ArrayList<Overlay>();
	private int mHeight;
	private int mWidth;
	
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
	}
	
	public void addOverlay(Overlay ov) {
		ov.sizeChanged(mWidth, mHeight);
		mOverlays.add(ov);
	}

    public void sizeChanged(int width, int height) {
    	mWidth = width;
    	mHeight = height;
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
		mActiveScreen.draw(c, frameTime, deltaTime);
	    final int N = mOverlays.size();
	    for (int i=0; i<N; ++i) {
	    	mOverlays.get(i).draw(c, frameTime, deltaTime);
	    }
    }

    public void handleTouch(MotionEvent event) {
	    mActiveScreen.handleTouch(event);
    }
	
}
