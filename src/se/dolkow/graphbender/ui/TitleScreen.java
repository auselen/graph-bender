package se.dolkow.graphbender.ui;

import se.dolkow.graphbender.Metric;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

public class TitleScreen implements Screen {
	private Paint mPaintTitle;
	private final RenderableManager mScreenManager;
	
	private static final String STR_TITLE = "graph-bender";
	private static final String TAG = "ScreenTitle";

	public TitleScreen(RenderableManager screenManager) {
		Log.d(TAG, "ScreenTitle");
		mPaintTitle = new Paint();
		mPaintTitle.setTextSize(Metric.SCALE * 45);
		mPaintTitle.setTypeface(Typeface.MONOSPACE);
		mPaintTitle.setColor(Color.WHITE);
		mScreenManager = screenManager;
	}

	@Override
	public void draw(Canvas c, long time, long delta) {
		final int w = c.getWidth();
		final int h = c.getHeight();
		c.drawColor(Color.BLACK);
		c.drawText(STR_TITLE, (w- mPaintTitle.measureText(STR_TITLE)) / 2, h / 2, mPaintTitle);
	}

	@Override
	public void handleTouch(MotionEvent event) {
		mScreenManager.startGame();
	}

	@Override
    public void sizeChanged(int width, int height) {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void update(long frameTime, long deltaTime) {
	    // TODO Auto-generated method stub
	    
    }
}
