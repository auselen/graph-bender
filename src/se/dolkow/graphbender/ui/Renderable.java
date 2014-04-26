package se.dolkow.graphbender.ui;

import android.graphics.Canvas;

interface Renderable {

	/** Canvas size may have changed */
	public void sizeChanged(int width, int height);
	
	/** Draw! */
	public void draw(Canvas c, long frameTime, long deltaTime);
	
}
