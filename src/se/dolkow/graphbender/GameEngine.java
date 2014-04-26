package se.dolkow.graphbender;

import se.dolkow.graphbender.animation.Animator;
import se.dolkow.graphbender.animation.ProportionalAnimator;
import se.dolkow.graphbender.layout.Layout;
import se.dolkow.graphbender.layout.RingLayout;
import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;
import se.dolkow.graphbender.scene.Scenery;
import android.graphics.Canvas;
import android.graphics.Point;
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
	private Animator mAnimator;
	private volatile boolean mSurfaceAvailable;
	private InputHandler mInputHandler;
	private float mTargetY;
	private float mTargetX;

	public GameEngine(GameSurface surface) {
		mGameSurface = surface;
		mGameSurface.getHolder().addCallback(this);
		mInputHandler = new InputHandler(this);
		mGameSurface.register(mInputHandler);
		createLevel(10);
		new GraphLoop().start();
	};
	
	public void createLevel(int n) {
		mCurrentLogic = new Logic(n);
		mCurrentScenery = new Scenery();
		mLayout = new RingLayout(mCurrentLogic);
		mAnimator = new ProportionalAnimator();
	}

	public void onTouchEvent(MotionEvent event) {
		int action = event.getAction(); 
		float x = event.getX();
		float y = event.getY();
		if (action == MotionEvent.ACTION_DOWN) {
			int n = mCurrentLogic.getVertexCount();
			for (int i = 0; i < n; i++) {
				Vertex v = mCurrentLogic.getVertex(i);
				if (hits(v, x, y)) {
					v.selected = true;
					mTargetX = x;
					mTargetY = y;
					break;
				}
			}
		} else if ((action == MotionEvent.ACTION_UP)
				|| (action == MotionEvent.ACTION_CANCEL)) {
			int n = mCurrentLogic.getVertexCount();
			for (int i = 0; i < n; i++) {
				Vertex v = mCurrentLogic.getVertex(i);
				v.selected = false;
			}
		} else if (action == MotionEvent.ACTION_MOVE) {
			mTargetX = x;
			mTargetY = y;
			int n = mCurrentLogic.getVertexCount();
			for (int i = 0; i < n; i++) {
				Vertex v = mCurrentLogic.getVertex(i);
				if (hits(v, x, y)) {
					v.hovered = true;
				} else {
					v.hovered = false;
				}
			}
		}
		mLayout.updateDesiredPositions(); // TODO: this is probably not the best place for this. We should make sure that re-layout is done at any time that the logic may have changed.
	}

	private static boolean hits(Vertex v, float x, float y) {
		float vx = v.x;
		float vy = v.y;
		return (Math.abs(vx - x) < 25) && (Math.abs(vy - y) < 25);
	}
	
	class GraphLoop extends Thread {
		@Override
		public void run() {
			while (true) {
				mAnimator.update(mCurrentLogic, System.nanoTime()); // TODO: use Choreographer as time source instead
				if (mSurfaceAvailable) {
					SurfaceHolder holder = mGameSurface.getHolder();
					Canvas c = holder.lockCanvas();
					mCurrentScenery.draw(c, mCurrentLogic, mTargetX, mTargetY);
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
