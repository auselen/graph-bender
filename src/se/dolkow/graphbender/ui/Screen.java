package se.dolkow.graphbender.ui;

import se.dolkow.graphbender.GameSurface;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public abstract class Screen implements SurfaceHolder.Callback {
	protected ScreenManager mScreenManager;
	public volatile boolean mSurfaceAvailable;
	protected int mHeight;
	protected int mWidth;
	protected GameSurface mGameSurface;

	public abstract void draw(Canvas c);
	public abstract void onTouchEvent(MotionEvent event);

	public Screen(GameSurface gameSurface) {
		mGameSurface = gameSurface;
		gameSurface.getHolder().addCallback(this);
	}

	public void registerManager(ScreenManager manager) {
		mScreenManager = manager;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mWidth = width;
		mHeight = height;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mSurfaceAvailable = true;
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mSurfaceAvailable = false;
	}
}
