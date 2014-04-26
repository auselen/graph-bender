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
	private Paint mPaintDesc;
	
	private static final String STR_TITLE = "graph-bender";
	private static final String TAG = "ScreenTitle";
	private static final String STR_DESC = "bring down those vertices to zero!";
	private static final String STR_DESC2 = "<tap on screen to begin your quest>";
	
	public TitleScreen(RenderableManager screenManager) {
		Log.d(TAG, "ScreenTitle");
		mPaintTitle = new Paint();
		mPaintTitle.setTextSize(Metric.SCALE * 45);
		mPaintTitle.setTypeface(Typeface.MONOSPACE);
		mPaintTitle.setColor(Color.WHITE);
		mPaintDesc = new Paint();
		mPaintDesc.setTextSize(Metric.SCALE * 18);
		mPaintDesc.setTypeface(Typeface.SANS_SERIF);
		mPaintDesc.setColor(Color.WHITE);
		mScreenManager = screenManager;
	}

	@Override
	public void draw(Canvas c, long time, long delta) {
		final int w = c.getWidth();
		final int h = c.getHeight();
		c.drawColor(Color.BLACK);
		int x = (int) ((w - mPaintTitle.measureText(STR_TITLE)) / 2);
		int y = h / 2;
		c.drawText(STR_TITLE, x, y, mPaintTitle);
		c.drawText(STR_DESC, x, y + 100, mPaintDesc);
		c.drawText(STR_DESC2, x, y + 100 + 20 * Metric.SCALE, mPaintDesc);
	}

	@Override
	public void handleTouch(MotionEvent event) {
		mScreenManager.startGame();
	}

	@Override
    public void sizeChanged(int width, int height) {
    }

	@Override
    public void update(long frameTime, long deltaTime) {
	    // TODO Auto-generated method stub
	    
    }
}
