package se.dolkow.graphbender.ui;

import android.view.KeyEvent;
import android.view.MotionEvent;

interface Screen extends Renderable {
	/** Handle a touch event */
	public void handleTouch(MotionEvent event);
	
	/** Update state for the next frame */
	public void update(long frameTime, long deltaTime);

	/**
	 * @return true if the event was handled
	 */
	public boolean handleKeyDown(int keyCode, KeyEvent event);
}
