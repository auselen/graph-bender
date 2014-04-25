package se.dolkow.graphbender.scene;

import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Scenery {
	private final Paint mPaint;
	private final Paint mTextPaint;
	private static final int RADIUS = 25;
	
	public Scenery() {
		mPaint = new Paint();
		mPaint.setColor(0xFFFF0000);
		mTextPaint = new Paint();
		mTextPaint.setColor(0xFFFFFFFF);
		mTextPaint.setTextSize(24);
	}
	
	public void draw(Canvas c, Logic logic) {
		int n = logic.getVertexCount();
		for (int i = 0; i < n; i++) {
			Vertex v = logic.getVertex(i);
			c.drawCircle(v.x, v.y, RADIUS, mPaint);
			c.drawText(""+v.getRequired(), v.x, v.y, mTextPaint);
		}
	}
}
