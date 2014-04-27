package se.dolkow.graphbender;

import se.dolkow.graphbender.ui.RenderableManager;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class GameActivity extends Activity {
	private RenderableManager mRenderableManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Globals.sAppResources == null) {
			Globals.sAppResources = getApplicationContext().getResources();
		}
		if (Globals.sSharedPrefs == null) {
			Globals.sSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		}
		Metric.setScale(getResources().getDisplayMetrics().density);
		mRenderableManager = new RenderableManager();
		RenderSurface surface = new RenderSurface(this, mRenderableManager);
		setContentView(surface);
	}
	
	@Override
	public void onBackPressed() {
		if (mRenderableManager.backPressed())
			super.onBackPressed();
	}
}
