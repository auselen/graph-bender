package se.dolkow.graphbender.scene;

import java.util.Random;

import se.dolkow.graphbender.Metric;
import se.dolkow.graphbender.logic.Vertex;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;

public class FirstScenery extends AbstractScenery {
	private final Paint mRegularPaint;
	private final Paint mSelectedPaint;
	private final Paint mHoveredPaint;
	private final Paint mTextPaint;
	private final Paint mConnectedPaint;
	private Paint mTargetLinePaint;
	private Random mRand;
	private float mTextMargin;
	
	public FirstScenery() {

        RadialGradient circleGradient = new RadialGradient(Metric.VERTEX_RADIUS / 4,
        		Metric.VERTEX_RADIUS / 4,
        		Metric.VERTEX_RADIUS / 4, 
        		new int[] {Color.RED, Color.WHITE, Color.GREEN},
        		new float[] {0.0f, 0.8f, 1.0f}, 
        		android.graphics.Shader.TileMode.CLAMP);  
        
        PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(0xA5FF0000, Mode.SRC_ATOP);
		mRegularPaint = new Paint();
		mRegularPaint.setColor(Color.RED);
		mRegularPaint.setAntiAlias(true);
		mRegularPaint.setShader(circleGradient);
		mRegularPaint.setColorFilter(colorFilter);
		
		mTextPaint = new Paint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(Metric.VERTEX_TEXT_SIZE);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTypeface(Typeface.MONOSPACE);
		mTextMargin = mTextPaint.measureText("0") / 2;

		mSelectedPaint = new Paint();
		mSelectedPaint.setColor(Color.BLUE);
		mSelectedPaint.setAntiAlias(true);
		
		mHoveredPaint = new Paint();
		mHoveredPaint.setColor(Color.GREEN);
		mHoveredPaint.setAntiAlias(true);;
		
		mTargetLinePaint = new Paint();
		mTargetLinePaint.setColor(Color.YELLOW);
		mTargetLinePaint.setStrokeWidth(7);
		mTargetLinePaint.setStyle(Style.STROKE);
		mTargetLinePaint.setAntiAlias(true);
		Shader shader = new LinearGradient(0, 0, 0, 40, Color.WHITE, Color.BLUE, TileMode.CLAMP);
		mTargetLinePaint.setAlpha(200);
		mTargetLinePaint.setShader(shader);
		
		//mTargetLinePaint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));
		mConnectedPaint = new Paint();
		mConnectedPaint.setColor(Color.MAGENTA);
		mConnectedPaint.setStyle(Style.STROKE);
		mConnectedPaint.setStrokeWidth(7);
		mConnectedPaint.setAntiAlias(true);
		mRand = new Random();
	}

	@Override
	protected void drawBackground(Canvas c) {
		c.drawColor(Color.BLACK);
	}

	@Override
	protected void drawEdge(Canvas c, Vertex v1, Vertex v2) {
		SceneryUtils.drawlineWithRandomWalk(c, v1.x, v1.y, v2.x, v2.y, mConnectedPaint);
	}

	@Override
	protected void drawTargetLine(Canvas c, Vertex v, int touchX, int touchY) {
		SceneryUtils.drawlineWithRandomWalk(c, v.x, v.y, touchX, touchY, mTargetLinePaint);
	}

	@Override
    protected void drawVertex(Canvas c, Vertex v) {
		int x = v.x + mRand.nextInt(v.required*2+1);
		int y =  v.y + mRand.nextInt(v.required*2+1);
		c.drawCircle(x, y, Metric.VERTEX_RADIUS,
				v.selected ? mSelectedPaint : v.hovered ? mHoveredPaint : mRegularPaint);
		c.drawText("" + v.getRequired(), x - mTextMargin, y + mTextMargin, mTextPaint);
    }



}
