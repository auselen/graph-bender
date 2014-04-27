package se.dolkow.graphbender.scene;

import android.graphics.Canvas;
import se.dolkow.graphbender.logic.Logic;

public interface Scenery {

	public void draw(Canvas c, Logic logic, int touchX, int touchY, int selected, int hovered);
}
