package se.dolkow.graphbender;

import se.dolkow.graphbender.ui.RenderableManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.view.Choreographer;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class RenderSurface extends SurfaceView implements SurfaceHolder.Callback {
	private final RenderableManager mScreenManager;
	private boolean mSurfaceAvailable;
	private final Choreographer choreographer;
	private final FrameCallback frameCallback;
	
	public RenderSurface(Activity context, RenderableManager sman) {
		super(context);
		
		choreographer = Choreographer.getInstance();
		frameCallback = new FrameCallback();
		
		mScreenManager = sman;
		getHolder().addCallback(this);
		
		Vibrator bzzz = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		mScreenManager.registerVibrator(bzzz);

		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return mScreenManager.handleKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mScreenManager.handleTouch(event);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		mScreenManager.sizeChanged(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mSurfaceAvailable = true;
		requestFrame();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mSurfaceAvailable = false;
	}
	
	private void requestFrame() {
		choreographer.postFrameCallback(frameCallback);
	}
	
	class FrameCallback implements Choreographer.FrameCallback {
		long mLastFrameTime = System.nanoTime();
		@Override
        public void doFrame(final long time) {
			if (mSurfaceAvailable) {
				final long delta = time - mLastFrameTime;
				mScreenManager.update(time, delta);
				final SurfaceHolder holder = getHolder();
				Canvas c = holder.lockCanvas();
				mScreenManager.draw(c, time, delta);
				holder.unlockCanvasAndPost(c);
				requestFrame(); // TODO: only do this if animator or scene tells us it's needed -- avoid redrawing unnecessarily.
			}
			mLastFrameTime = time;
        }
	}
}
