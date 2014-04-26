package se.dolkow.graphbender;

import se.dolkow.graphbender.ui.RenderableManager;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends Activity {
	private static final String TAG = "GameActivity";
	private RenderableManager mRenderableManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Metric.setScale(getResources().getDisplayMetrics().density);
		mRenderableManager = new RenderableManager();
		RenderSurface surface = new RenderSurface(this, mRenderableManager);
		setContentView(surface);
		Log.d(TAG, "created");
	}
	
	@Override
	public void onBackPressed() {
		if (mRenderableManager.backPressed())
			super.onBackPressed();
	}
}
