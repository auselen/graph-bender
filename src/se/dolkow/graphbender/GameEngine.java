package se.dolkow.graphbender;

import se.dolkow.graphbender.layout.Layout;
import se.dolkow.graphbender.layout.RingLayout;
import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;
import se.dolkow.graphbender.scene.Scenery;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

/*
 * Keeps the game flow
 */
public class GameEngine implements Callback {
	private static final String TAG = "GameEngine";
	private Logic mCurrentLogic;
	private Scenery mCurrentScenery;
	private GameSurface mGameSurface;
	private Layout mLayout;
	private volatile boolean mSurfaceAvailable;

	public GameEngine(GameSurface surface) {
		mGameSurface = surface;
		mGameSurface.getHolder().addCallback(this);
		createLevel(10);
		new GraphLoop().start();
	};
	
	public void createLevel(int n) {
		mCurrentLogic = new Logic(n);
		mCurrentScenery = new Scenery();
		mLayout = new RingLayout(mCurrentLogic);
	}

	public void onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		int n = mCurrentLogic.getVertexCount();
		for (int i = 0; i < n; i++) {
			Vertex v = mCurrentLogic.getVertex(i);
			//if (hits(v, x, y)) {
			//}
		}
	}
	
	class GraphLoop extends Thread {
		@Override
		public void run() {
			while (true) {
				mLayout.updateDesiredPositions();
				if (mSurfaceAvailable) {
					SurfaceHolder holder = mGameSurface.getHolder();
					Canvas c = holder.lockCanvas();
					mCurrentScenery.draw(c, mCurrentLogic);
					holder.unlockCanvasAndPost(c);
				}
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mLayout.updateBounds(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mSurfaceAvailable = true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mSurfaceAvailable = false;
	}
}
