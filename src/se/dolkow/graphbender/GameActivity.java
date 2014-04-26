package se.dolkow.graphbender;

import se.dolkow.graphbender.ui.RenderableManager;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends Activity {
	private static final String TAG = "GameActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Metric.setScale(getResources().getDisplayMetrics().density);
		RenderSurface surface = new RenderSurface(this, new RenderableManager());
		setContentView(surface);
		Log.d(TAG, "created");
	}
}
