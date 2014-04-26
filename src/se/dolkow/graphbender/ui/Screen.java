package se.dolkow.graphbender.ui;

import android.view.KeyEvent;
import android.view.MotionEvent;

interface Screen extends Renderable {
	/** Handle a touch event */
	public void handleTouch(MotionEvent event);
	
	/** Update state for the next frame */
	public void update(long frameTime, long deltaTime);

	public void handleKeyDown(int keyCode, KeyEvent event);
}
