package se.dolkow.graphbender.scene;

import java.util.Random;

import se.dolkow.graphbender.Globals;
import se.dolkow.graphbender.Metric;
import se.dolkow.graphbender.R;
import se.dolkow.graphbender.logic.Vertex;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
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
	private Bitmap[][] sprites;
	private long mTime;
	private int spriteI;
	
	public FirstScenery() {
		Bitmap jelly = BitmapFactory.decodeResource(Globals.sAppResources, R.drawable.jelly);
		sprites = new Bitmap[4][3];
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 4; y++) {
				sprites[y][x] = Bitmap.createBitmap(jelly, x * 54, y * 54, 54, 54);
			}
		}
        
		mRegularPaint = new Paint();
		mRegularPaint.setColor(Color.RED);
		mRegularPaint.setAntiAlias(true);
		
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
		mTargetLinePaint.setAlpha(200);
		Bitmap lightningBitmap = BitmapFactory.decodeResource(Globals.sAppResources, R.drawable.lightning_texture);
		Shader lightningTextureShader = new BitmapShader(lightningBitmap, TileMode.MIRROR, TileMode.MIRROR);
		mTargetLinePaint.setShader(lightningTextureShader);
		
		//mTargetLinePaint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));
		mConnectedPaint = new Paint();
		mConnectedPaint.setColor(Color.MAGENTA);
		mConnectedPaint.setStyle(Style.STROKE);
		mConnectedPaint.setStrokeWidth(7);
		mConnectedPaint.setAntiAlias(true);
		mRand = new Random();
		mTime = System.currentTimeMillis();
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
		int x = v.x; // + mRand.nextInt(v.required*2+1);
		int y = v.y; // + mRand.nextInt(v.required*2+1);
		//c.drawCircle(x, y, Metric.VERTEX_RADIUS,
		//		v.selected ? mSelectedPaint : v.hovered ? mHoveredPaint : mRegularPaint);
		long time = System.currentTimeMillis();
		if (time > mTime + 200) {
			spriteI++;
			mTime = time;
		}
		c.drawBitmap(sprites[0][spriteI % 3], x - 27 , y - 27, null);
		int r = v.getRequired();
		c.drawText(r > 0 ? "" + r : "✓", x - mTextMargin, y + mTextMargin, mTextPaint);
    }



}
