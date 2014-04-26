package se.dolkow.graphbender.scene;

import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Scenery {
	private final Paint mPaint;
	private final Paint mSelectedPaint;
	private final Paint mTextPaint;
	private Paint mTargetLinePaint;
	private static final int RADIUS = 25;
	
	public Scenery() {
		mPaint = new Paint();
		mPaint.setColor(0xFFFF0000);
		mTextPaint = new Paint();
		mTextPaint.setColor(0xFFFFFFFF);
		mTextPaint.setTextSize(24);
		mSelectedPaint = new Paint();
		mSelectedPaint.setColor(0xFF00FF00);
		mTargetLinePaint = new Paint();
		mTargetLinePaint.setColor(Color.YELLOW);
		mTargetLinePaint.setStrokeWidth(7);
	}
	
	public void draw(Canvas c, Logic logic, float targetX, float targetY) {
		c.drawColor(Color.BLACK);
		int n = logic.getVertexCount();
		for (int i = 0; i < n; i++) {
			Vertex v = logic.getVertex(i);
			if (v.selected)
				c.drawLine(v.x, v.y, targetX, targetY, mTargetLinePaint);
			c.drawCircle(v.x, v.y, RADIUS, v.selected ? mSelectedPaint : mPaint);
			c.drawText(""+v.getRequired(), v.x, v.y, mTextPaint);
		}
	}
}
