package se.dolkow.graphbender.scene.backgrounds;

import se.dolkow.graphbender.ui.Background;
import android.graphics.Canvas;
import android.util.Log;

public class ColorBackground implements Background {

	private final int color;
	
	public ColorBackground(int rgb) {
		color = 0xff000000 | rgb;
	}
	
	@Override
	public void draw(Canvas c, long frameTime, long deltaTime) {
		Log.i("Hej", "draw " + color);
		c.drawColor(color);
	}

	@Override
    public void sizeChanged(int width, int height) {
    }

}
