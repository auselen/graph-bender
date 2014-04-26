package se.dolkow.graphbender;

import se.dolkow.graphbender.ui.Screen;
import se.dolkow.graphbender.ui.ScreenManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class RenderSurface extends SurfaceView implements SurfaceHolder.Callback{
	private final ScreenManager mScreenManager;
	private int mHeight;
	private int mWidth ;
	private boolean mSurfaceAvailable;
	private final Choreographer choreographer;
	private final FrameCallback frameCallback;
	private boolean mDimensionsChanged;

	public RenderSurface(Context context, ScreenManager sman) {
		super(context);
		
		choreographer = Choreographer.getInstance();
		frameCallback = new FrameCallback();
		
		mScreenManager = sman;
		getHolder().addCallback(this);
	}	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mScreenManager.getScreen().handleTouch(event);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		mWidth  = width;
		mHeight = height;
		mDimensionsChanged = true;
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
		Screen mLastScreen = null;
		@Override
        public void doFrame(final long time) {
			if (mSurfaceAvailable) {
				final long delta = time - mLastFrameTime;
				Screen screen = mScreenManager.getScreen();
				if (screen != mLastScreen || mDimensionsChanged) {
					screen.sizeChanged(mWidth, mHeight); // this screen may not have seen the update before
					mLastScreen = screen;
					mDimensionsChanged = false;
				}
				screen.update(time, delta);
				final SurfaceHolder holder = getHolder();
				Canvas c = holder.lockCanvas();
				screen.draw(c, time, delta);
				holder.unlockCanvasAndPost(c);
				requestFrame(); // TODO: only do this if animator or scene tells us it's needed -- avoid redrawing unnecessarily.
			}
			mLastFrameTime = time;
        }
	}
}
