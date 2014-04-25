package se.dolkow.graphbender;

import android.view.MotionEvent;

public class InputHandler {
	private GameEngine mGameEngine;

	private InputHandler() {
	};

	private static class Holder {
		private static final InputHandler instance = new InputHandler();
	}

	public static InputHandler getInstance() {
		return Holder.instance;
	}

	public void onTouchEvent(MotionEvent event) {
		mGameEngine.onTouchEvent(event);
	}
}
