package se.dolkow.graphbender.ui;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Screen {
	/** Handle a touch event */
	public void handleTouch(MotionEvent event);
	
	/** Canvas size may have changed */
	public void sizeChanged(int width, int height);
	
	/** Update state for the next frame */
	public void update(long frameTime, long deltaTime);
	
	/** Draw! */
	public void draw(Canvas c, long frameTime, long deltaTime);

}
