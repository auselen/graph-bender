package se.dolkow.graphbender.ui;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class Screen {
	protected ScreenManager mScreenManager;
	protected int mWidth;
	protected int mHeight;

	public abstract void draw(Canvas c);

	public abstract void onTouchEvent(MotionEvent event);

	public void surfaceChanged(int width, int height) {
		mWidth = width;
		mHeight = height;
	}

	public void registerManager(ScreenManager manager) {
		mScreenManager = manager;
	}
}
