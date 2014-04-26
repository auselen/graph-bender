package se.dolkow.graphbender.ui;

import se.dolkow.graphbender.Metric;
import se.dolkow.graphbender.animation.Animator;
import se.dolkow.graphbender.animation.SpiralAnimator;
import se.dolkow.graphbender.layout.Layout;
import se.dolkow.graphbender.layout.PullInRingLayout;
import se.dolkow.graphbender.logic.Logic;
import se.dolkow.graphbender.logic.Vertex;
import se.dolkow.graphbender.scene.Scenery;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.MotionEvent;

public class ScreenGame extends Screen {
	private Scenery mCurrentScenery;
	private Animator mAnimator;
	private float mTargetY;
	private float mTargetX;
	private int mLevel = 2;
	private Logic mCurrentLogic;
	private Layout mLayout;
	
	public ScreenGame() {
		createLevel(mLevel);
	}
	
	public void createLevel(int n) {
		mCurrentLogic = new Logic(n);
		mCurrentScenery = new Scenery();
		mLayout = new PullInRingLayout();
		mAnimator = new SpiralAnimator();
	}
	
	@Override
	public void draw(Canvas c) {
		mAnimator.update(mCurrentLogic, SystemClock.elapsedRealtimeNanos()); // TODO: use Choreographer as time source instead
		mCurrentScenery.draw(c, mCurrentLogic, mTargetX, mTargetY);
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		int action = event.getAction(); 
		float x = event.getX();
		float y = event.getY();
		if (action == MotionEvent.ACTION_DOWN) {
			int n = mCurrentLogic.getVertexCount();
			for (int i = 0; i < n; i++) {
				Vertex v = mCurrentLogic.getVertex(i);
				if (hits(v, x, y)) {
					if (v.required > 0) {
						v.selected = true;
						mTargetX = x;
						mTargetY = y;
					}
					break;
				}
			}
		} else if ((action == MotionEvent.ACTION_UP)
				|| (action == MotionEvent.ACTION_CANCEL)) {
			int n = mCurrentLogic.getVertexCount();
			int selected = -1;
			int hovered = -1;
			for (int i = 0; i < n; i++) {
				Vertex v = mCurrentLogic.getVertex(i);
				if (v.selected)
					selected = i;
				if (v.hovered)
					hovered = i;
				v.selected = false;
				v.hovered = false;
			}
			if ((selected != -1) && (hovered != -1)) {
				mCurrentLogic.connect(selected, hovered);
				if (mCurrentLogic.satisfied())
					mCurrentLogic = new Logic(++mLevel);
				mLayout.updateDesiredPositions(mCurrentLogic, mWidth, mHeight);
			}
		} else if (action == MotionEvent.ACTION_MOVE) {
			mTargetX = x;
			mTargetY = y;
			int n = mCurrentLogic.getVertexCount();
			for (int i = 0; i < n; i++) {
				Vertex v = mCurrentLogic.getVertex(i);
				if (hits(v, x, y)) {
					if (v.required > 0)
						v.hovered = true;
				} else {
					v.hovered = false;
				}
			}
		}
	}
	
	@Override
	public void surfaceChanged(int width, int height) {
		super.surfaceChanged(width, height);
		mLayout.updateDesiredPositions(mCurrentLogic, mWidth, mHeight);
	}
	
	private static boolean hits(Vertex v, float x, float y) {
		return (Math.abs(v.x - x) < Metric.FINGER_SIZE) &&
				(Math.abs(v.y - y) < Metric.FINGER_SIZE);
	}
}
