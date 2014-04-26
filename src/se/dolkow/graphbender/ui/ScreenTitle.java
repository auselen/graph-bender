package se.dolkow.graphbender.ui;

import se.dolkow.graphbender.GameSurface;
import se.dolkow.graphbender.Metric;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

public class ScreenTitle extends Screen {
	private Paint mPaintTitle;
	private static final String STR_TITLE = "graph-bender";
	private static final String TAG = "ScreenTitle";

	public ScreenTitle(GameSurface gameSurface) {
		super(gameSurface);
		Log.d(TAG, "ScreenTitle");
		mPaintTitle = new Paint();
		mPaintTitle.setTextSize(Metric.SCALE * 45);
		mPaintTitle.setTypeface(Typeface.MONOSPACE);
		mPaintTitle.setColor(Color.WHITE);
	}

	@Override
	public void draw(Canvas c) {
		int w = c.getWidth();
		int h = c.getHeight();
		c.drawColor(Color.BLACK);
		c.drawText(STR_TITLE, (w- mPaintTitle.measureText(STR_TITLE)) / 2, h / 2, mPaintTitle);
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		//mScreenManager.startGame();
	}


}
