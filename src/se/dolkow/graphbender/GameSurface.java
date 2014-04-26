package se.dolkow.graphbender;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView {
	private InputHandler mInputHandler;
	
	public GameSurface(Context context) {
		super(context);
	}	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mInputHandler.onTouchEvent(event);
		return true;
	}

	public void register(InputHandler inputHandler) {
		mInputHandler = inputHandler;
	}
}
