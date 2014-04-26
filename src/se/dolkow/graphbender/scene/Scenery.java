package se.dolkow.graphbender.scene;

import java.util.ArrayList;

import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Pair;

public class Scenery {
	private final Paint mPaint;
	private final Paint mSelectedPaint;
	private final Paint mHoveredPaint;
	private final Paint mTextPaint;
	private final Paint mConnectedPaint;
	private Paint mTargetLinePaint;
	private static final int RADIUS = 25;
	
	public Scenery() {
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mTextPaint = new Paint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(24);
		mSelectedPaint = new Paint();
		mSelectedPaint.setColor(Color.BLUE);
		mHoveredPaint = new Paint();
		mHoveredPaint.setColor(Color.GREEN);
		mTargetLinePaint = new Paint();
		mTargetLinePaint.setColor(Color.YELLOW);
		mTargetLinePaint.setStrokeWidth(7);
		mTargetLinePaint.setStyle(Style.STROKE);
		mTargetLinePaint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));
		mConnectedPaint = new Paint();
		mConnectedPaint.setColor(Color.MAGENTA);
		mConnectedPaint.setStrokeWidth(7);
	}
	
	public void draw(Canvas c, Logic logic, float targetX, float targetY) {
		c.drawColor(Color.BLACK);
		ArrayList<Pair<Vertex, Vertex>> pairs = logic.getConnectedPairs();
		for (Pair<Vertex, Vertex> pair : pairs) {
			c.drawLine(pair.first.x, pair.first.y, pair.second.x, pair.second.y, mConnectedPaint);
		}
		int n = logic.getVertexCount();
		for (int i = 0; i < n; i++) {
			Vertex v = logic.getVertex(i);
			if (v.selected)
				c.drawLine(v.x, v.y, targetX, targetY, mTargetLinePaint);
			c.drawCircle(v.x, v.y, RADIUS, v.selected ? mSelectedPaint : v.hovered ? mHoveredPaint : mPaint);
			c.drawText(""+v.getRequired(), v.x, v.y, mTextPaint);
		}
		
	}
}
