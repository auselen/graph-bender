package se.dolkow.graphbender.scene.backgrounds;

import android.graphics.Canvas;
import android.graphics.Color;

public class BlackBackground implements Background {

	@Override
	public void draw(Canvas c) {
		c.drawColor(Color.BLACK);
	}

}
